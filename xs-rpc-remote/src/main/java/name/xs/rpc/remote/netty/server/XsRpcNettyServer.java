package name.xs.rpc.remote.netty.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.remote.Server;
import name.xs.rpc.remote.ServerHandler;
import name.xs.rpc.remote.netty.protocol.ProtocolContext;

/**
 * create by xs
 * create time:2020-01-30 09:09:39
 */
public class XsRpcNettyServer implements Server {
    private static XsRpcNettyServer i = new XsRpcNettyServer();
    private XsRpcNettyServer() {}
    public static XsRpcNettyServer instance() {
        return i;
    }

    private EventLoopGroup bossGroup;
    private EventLoopGroup workerGroup;

    @Override
    public void start(Integer port, ServerHandler handler) {
        Constant.LOG.info("[XsRpcNettyServer] start bootstrap");
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
        if (this.isRunning()) {
            return;
        }
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) {
                            ProtocolContext.init();
                            // 分隔符配置
                            ByteBuf delemiter = Unpooled.copiedBuffer(ProtocolContext.getSeperateCharacter());
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            //先使用DelimiterBasedFrameDecoder解决粘包
                            p.addLast(new DelimiterBasedFrameDecoder(32 * 1024, delemiter));
                            p.addLast(ProtocolContext.getDecoder());
                            p.addLast(ProtocolContext.getEncoder());
                            p.addLast((ChannelHandler) handler);
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(port).sync();
            Constant.LOG.info("[XsRpcNettyServer] ServerBootstrap配置启动完成,port={}", port);
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
            Constant.LOG.info("[XsRpcNettyServer] start finish");
        } catch(Exception e) {
            Constant.LOG.error("[XsRpcNettyServer] start error", e);
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public void stop() {
        if (this.isRunning()) {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    @Override
    public boolean isRunning() {
        if (bossGroup == null || workerGroup == null) {
            return false;
        }
        return !(bossGroup.isShutdown() || bossGroup.isShuttingDown());
    }
}
