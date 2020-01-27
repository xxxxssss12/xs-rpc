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
import name.xs.rpc.common.ErrorEnum;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.test.netty.protocol.ProtocolContext;

import java.util.concurrent.CountDownLatch;

/**
 * create by xs
 * create time:2020-01-27 12:03:48
 */
public class XsRpcNettyClient {

    public ChannelFuture start(String host, Integer port, ChannelHandler handler) {

        Constant.LOG.info("[XsRpcNettyClient] do start...");
        // 暂不支持ssl加密
        // Configure the client.
        /*创建一个Bootstrap b实例用来配置启动客户端
         * b.group指定NioEventLoopGroup来处理连接，接收数据
         * b.channel指定通道类型
         * b.option配置参数
         * b.handler客户端成功连接服务器后就会执行
         * b.connect客户端连接服务器
         * b.sync阻塞配置完成并启动
         */
        try {
            EventLoopGroup group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ProtocolContext.init();
                            // 分隔符配置
                            ByteBuf delemiter = Unpooled.copiedBuffer(ProtocolContext.getSeperateCharacter());
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.INFO));
                            p.addLast(new DelimiterBasedFrameDecoder(32 * 1024, delemiter));
                            p.addLast(ProtocolContext.getDecoder());
                            p.addLast(ProtocolContext.getEncoder());
                            p.addLast(handler);
                        }
                    });

            // Start the client.
            final ChannelFuture f = b.connect(host, port).sync();
            System.out.println("[XsRpcNettyClient] do start...bootstrap config over");
            // Wait until the connection is closed.
            final CountDownLatch c = new CountDownLatch(1);
            Thread t = new Thread(() -> {
                try {
                    c.countDown();
                    f.channel().closeFuture().sync();
                } catch (InterruptedException e) {
                    Constant.LOG.error("[XsRpcNettyClient] channel start error!", e);
                } finally {
                    group.shutdownGracefully();
                }
            });
            t.start();
            c.await();
            Constant.LOG.info("[XsRpcNettyClient] do start... over!");
            return f;
        } catch (InterruptedException e) {
            Constant.LOG.error("[XsRpcNettyClient] do start... error!", e);
            throw new XsRpcException(ErrorEnum.NETTY_01);
        }
    }
}
