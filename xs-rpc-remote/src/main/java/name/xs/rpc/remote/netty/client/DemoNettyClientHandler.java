package name.xs.rpc.remote.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import name.xs.rpc.protocol.Message;

public class DemoNettyClientHandler extends ChannelInboundHandlerAdapter {

    //接收到数据后调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("[client]channelRead..");
        Message msg1 = (Message) msg;
        System.out.println(msg1.getData());
    }

    //完成时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("[client]channelReadComplete");
        ctx.flush();
    }

    //发生异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        System.out.println("[client]exceptionCaught");
        cause.printStackTrace();
        ctx.close();
    }
}
