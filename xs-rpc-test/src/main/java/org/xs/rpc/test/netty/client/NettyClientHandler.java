package org.xs.rpc.test.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.xs.rpc.test.netty.protocol.Header;
import org.xs.rpc.test.netty.protocol.Message;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    //客户端连接服务器后调用，简单的就ctx.writeAndFlush(ByteBuf)，复杂点就自定义编解码器
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive");
        //ctx.writeAndFlush(Unpooled.copiedBuffer("hello world",CharsetUtil.UTF_8));
        String content = "hello world,this is netty client";
        Header header = new Header((byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, "713f17ca614361fb257dc6741332caf2", content.getBytes("UTF-8").length, 1);
        Message message = new Message(header, content);
        ctx.writeAndFlush(message);

    }

    //接收到数据后调用
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {

        Message msg1 = (Message) msg;

        System.out.println(msg1.getData());
    }

    //完成时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("channelReadComplete");
        ctx.flush();
    }

    //发生异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
