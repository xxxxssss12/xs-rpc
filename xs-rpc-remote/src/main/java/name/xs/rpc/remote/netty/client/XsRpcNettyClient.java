package name.xs.rpc.remote.netty.client;

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
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.enums.ThreadNameEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.common.beans.protocol.Message;
import name.xs.rpc.common.beans.remote.Client;
import name.xs.rpc.common.beans.remote.ClientHandler;
import name.xs.rpc.common.context.RemoteContext;
import name.xs.rpc.common.beans.remote.RequestingDto;
import name.xs.rpc.common.context.ProtocolContext;
import name.xs.rpc.protocol.jlsp.JlspDecoder;
import name.xs.rpc.remote.netty.protocol.NettyDecoderAdapter;
import name.xs.rpc.remote.netty.protocol.NettyEncoderAdapter;
import name.xs.rpc.remote.netty.protocol.ProtocolContextInit;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.concurrent.*;

/**
 * @author xs
 * create time:2020-01-27 12:03:48
 */
public class XsRpcNettyClient implements Client {

    private ChannelFuture channel;
    private EventLoopGroup group;

    private String remoteHost;
    private Integer remotePort;

    public XsRpcNettyClient(String host, Integer port, ClientHandler handler) {
        start(host, port, handler);
    }
    @Override
    public void start(String host, Integer port, ClientHandler handler) {
        Constant.LOG.info("[XsRpcNettyClient] do start...");
        if (isRunning()) {
            throw new XsRpcException(ErrorEnum.NETTY_02);
        }
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
            group = new NioEventLoopGroup();
            Bootstrap b = new Bootstrap();
            ProtocolContextInit.init();
            b.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            // 分隔符配置
                            ByteBuf delemiter = Unpooled.copiedBuffer(ProtocolContext.instance().getSeperateCharacter());
                            ChannelPipeline p = ch.pipeline();
                            p.addLast(new LoggingHandler(LogLevel.DEBUG));
                            p.addLast(new DelimiterBasedFrameDecoder(32 * 1024, delemiter));
//                            p.addLast((NettyDecoderAdapter) ProtocolContext.instance().getDecoder());
                            // ByteToMessageDecoder 不能单例。
                            p.addLast(new NettyDecoderAdapter(ProtocolContext.instance().getXsDecoder()));
                            p.addLast((NettyEncoderAdapter) ProtocolContext.instance().getEncoder());
                            p.addLast((ChannelHandler) handler);
                        }
                    });

            // Start the client.
            final ChannelFuture f = b.connect(host, port).sync();
            channel = f;
            Constant.LOG.info("[XsRpcNettyClient] do start... bootstrap config over");
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
            t.setName("xsrpc-" + ThreadNameEnum.NETTY_CLIENT.getCode());
            t.start();
            c.await();
            RemoteContext.instance().addClient(host, port, this);
            Constant.LOG.info("[XsRpcNettyClient] do start... finish!remote:host={}, port={}", host, port);
//            return f;
        } catch (InterruptedException e) {
            Constant.LOG.error("[XsRpcNettyClient] do start... error!", e);
            throw new XsRpcException(ErrorEnum.NETTY_01);
        }
    }

    @Override
    public void stop() {
        String host = null;
        Integer port = null;
        if (channel != null) {
            InetSocketAddress address = (InetSocketAddress) channel.channel().remoteAddress();
            host = address.getHostName();
            port = address.getPort();
            channel.channel().close();
        }
        if (group != null) {
            group.shutdownGracefully();
        }
        if (StringUtils.isNotBlank(host) && port != null) {
            RemoteContext.instance().removeClient(host, port);
        }
    }

    /**
     *
     * @param msg 要发出的消息
     * @param timeout <=0,永不超时
     * @return
     */
    @Override
    public Message send(Message msg, long timeout) {
        // 进行中的请求
        RequestingDto requestingDto = new RequestingDto(msg, new CountDownLatch(1));
        RemoteContext.instance().addRequestingDto(requestingDto);
        NettyRequestTask task = new NettyRequestTask(msg, requestingDto.getCountDownLatch(), this.channel, timeout);
        Future<Message> future = RemoteContext.instance().getRequestThreadPool().submit(task);
        try {
            Message responseMessage = null;
            if (timeout > 0) {
                // 阻塞
                responseMessage =  future.get(timeout, TimeUnit.MILLISECONDS);
            } else {
                // 阻塞
                responseMessage =  future.get();
            }
            if (responseMessage != null) {
                requestingDto.setResponseMessage(responseMessage);
            }
            return responseMessage;
        } catch (InterruptedException e) {
            Constant.LOG.error("[XsRpcNettyClient] send error", e);
            throw new XsRpcException(ErrorEnum.NETTY_04);
        } catch (ExecutionException e) {
            Constant.LOG.error("[XsRpcNettyClient] send error", e);
            throw new XsRpcException(ErrorEnum.NETTY_05);
        } catch (TimeoutException e) {
            Constant.LOG.error("[XsRpcNettyClient] send timeout", e);
            throw new XsRpcException(ErrorEnum.NETTY_06);
        } catch (Exception e) {
            Constant.LOG.error("[XsRpcNettyClient] send error", e);
            throw new XsRpcException(ErrorEnum.NETTY_07);
        }
    }

    @Override
    public boolean isRunning() {
        if (group == null) {
            return false;
        }
        return !group.isShutdown();
    }

    @Override
    public String getRemoteHost() {
        return this.remoteHost;
    }

    @Override
    public int getRemotePort() {
        return this.remotePort == null ? 0 : this.remotePort;
    }
}
