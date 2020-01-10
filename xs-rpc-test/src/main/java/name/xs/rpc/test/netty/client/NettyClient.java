package name.xs.rpc.test.netty.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.test.netty.protocol.ProtocolContext;

import java.util.Scanner;

public class NettyClient {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final String HOST = System.getProperty("host", "127.0.0.1");
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));
    static final int SIZE = Integer.parseInt(System.getProperty("size", "256"));

    static Channel channel;
    public static void main(String[] args) throws Exception {
        System.out.println("EchoClient.main");
        // Configure SSL.git
        final SslContext sslCtx;
        if (SSL) {
            sslCtx = SslContextBuilder.forClient()
                    .trustManager(InsecureTrustManagerFactory.INSTANCE).build();
        } else {
            sslCtx = null;
        }

        // Configure the client.
        /*创建一个Bootstrap b实例用来配置启动客户端
         * b.group指定NioEventLoopGroup来处理连接，接收数据
         * b.channel指定通道类型
         * b.option配置参数
         * b.handler客户端成功连接服务器后就会执行
         * b.connect客户端连接服务器
         * b.sync阻塞配置完成并启动
         */
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ProtocolContext.init();
                            // 分隔符配置
                            ByteBuf delemiter= Unpooled.copiedBuffer(ProtocolContext.getSeperateCharacter());
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc(), HOST, PORT));
                            }
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new DelimiterBasedFrameDecoder(32 * 1024,delemiter));
                            p.addLast(ProtocolContext.getDecoder());
                            p.addLast(ProtocolContext.getEncoder());
                            p.addLast(new NettyClientHandler());
                        }
                    });

            // Start the client.
            ChannelFuture f = b.connect(HOST, PORT).sync();
            channel = f.channel();
            System.out.println("EchoClient.main ServerBootstrap配置启动完成");

            readMsgByConsole();
            // Wait until the connection is closed.
            f.channel().closeFuture().sync();
            System.out.println("EchoClient.end");
        } finally {
            // Shut down the event loop to terminate all threads.
            group.shutdownGracefully();
        }
    }

    private static void readMsgByConsole() {
        new Thread(() -> {
            for (;;) {
                Scanner sc = new Scanner(System.in);
                String line = sc.nextLine();
                if ("end".equalsIgnoreCase(line)) {
                    System.out.println("程序结束！");
                    System.exit(0);
                }
                Message msg = ProtocolContext.getMessageBuilder().buildMessage(line);
//                    try {
                    channel.writeAndFlush(msg);
                    System.out.println("[client]消息发出.." + msg.getData());
//                    } catch (ProtocolException e) {
//                        e.printStackTrace();
//                    }
            }
        }).start();
    }
}
