package org.xs.rpc.test.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.xs.rpc.protocol.xsp.XspMessage;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //接收到数据后调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("[client]channelRead..");
        XspMessage msg1 = (XspMessage) msg;
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
