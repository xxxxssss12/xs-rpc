package org.xs.rpc.test.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.xs.rpc.protocol.xsp.XspConstant;
import org.xs.rpc.protocol.xsp.XspDecoder;
import org.xs.rpc.protocol.xsp.XspEncoder;
import org.xs.rpc.protocol.xsp.XspMessageBuilder;
import org.xs.rpc.test.netty.protocol.DecoderAdapter;
import org.xs.rpc.test.netty.protocol.EncoderAdapter;
import org.xs.rpc.test.netty.protocol.ProtocolContext;

public class NettyServer {
    static final boolean SSL = System.getProperty("ssl") != null;
    static final int PORT = Integer.parseInt(System.getProperty("port", "8007"));

    public static void main(String[] args) throws Exception {
        System.out.println("EchoServer.main start");
        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }
        // Configure the server.
        /* 步骤
         * 创建一个ServerBootstrap b实例用来配置启动服务器
         * b.group指定NioEventLoopGroup来接收处理新连接
         * b.channel指定通道类型
         * b.option设置一些参数
         * b.handler设置日志记录
         * b.childHandler指定连接请求，后续调用的channelHandler
         * b.bind设置绑定的端口
         * b.sync阻塞直至启动服务
         */
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ProtocolContext.init();
                            // 分隔符配置
                            ByteBuf delemiter= Unpooled.copiedBuffer(ProtocolContext.getSeperateCharacter());
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            //先使用DelimiterBasedFrameDecoder解码
                            p.addLast(new DelimiterBasedFrameDecoder(32 * 1024, delemiter));
                            p.addLast(ProtocolContext.getDecoder());
                            p.addLast(ProtocolContext.getEncoder());
                            p.addLast(new NettyServerHandler());
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(PORT).sync();
            System.out.println("EchoServer.main ServerBootstrap配置启动完成");

            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
            System.out.println("EchoServer.main end");
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
