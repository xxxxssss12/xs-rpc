package name.xs.rpc.test.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.protocol.xsp.XspHeader;
import name.xs.rpc.protocol.xsp.XspMessage;
import name.xs.rpc.provider.ProviderHandler;
import name.xs.rpc.test.netty.protocol.ProtocolContext;

import java.util.concurrent.atomic.AtomicInteger;

//@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    private ProviderHandler providerHandler;
    private AtomicInteger requestCount = new AtomicInteger(0);
    /**
     * 客户端连接成功时触发
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        byte data[] = ProtocolContext.getEncoder().getEncoder().encode(
            ProtocolContext.getMessageBuilder().buildMessage("[server]connect success!")
        );
        ByteBuf buf = Unpooled.buffer(data.length); // netty自定义缓存
        buf.writeBytes(data);
        ctx.writeAndFlush(buf);
        System.out.println("客户端连入：" + ctx.channel().remoteAddress().toString());
    }
    // 接受请求后处理类
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Message msg1 = (Message) msg;
        System.out.println(msg1.getData());

        //此处写接收到客户端请求后的业务逻辑
        String content = "[server]hello world,this is nettyServer,request count=" + requestCount.incrementAndGet();
        XspHeader header = new XspHeader((byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, "713f17ca614361fb257dc6741332caf2", content.getBytes("UTF-8").length, 1);
        Message message = new XspMessage(header, content);
        ctx.writeAndFlush(message);
        System.out.println("服务端响应：" + message.getData());
    }

    //读取完成后处理方法
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("[server]EchoServerHandler.channelReadComplete");
    }

    //异常捕获处理方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }

    public ProviderHandler getProviderHandler() {
        return providerHandler;
    }

    public void setProviderHandler(ProviderHandler providerHandler) {
        this.providerHandler = providerHandler;
    }
}
