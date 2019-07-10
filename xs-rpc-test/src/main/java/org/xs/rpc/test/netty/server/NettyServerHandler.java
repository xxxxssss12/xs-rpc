package org.xs.rpc.test.netty.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.xs.rpc.protocol.Header;
import org.xs.rpc.protocol.Message;
import org.xs.rpc.protocol.xsp.XspHeader;
import org.xs.rpc.protocol.xsp.XspMessage;

@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 接受请求后处理类
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Message msg1 = (Message) msg;
        System.out.println(msg1.getData());

        //此处写接收到客户端请求后的业务逻辑
        String content = "hello world,this is nettyServer";
        XspHeader header = new XspHeader((byte) 0, (byte) 1, (byte) 1, (byte) 1, (byte) 0, "713f17ca614361fb257dc6741332caf2", content.getBytes("UTF-8").length, 1);
        Message message = new XspMessage(header, content);
        ctx.writeAndFlush(message);
    }

    //读取完成后处理方法
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("EchoServerHandler.channelReadComplete");
        //ctx.flush();
    }

    //异常捕获处理方法
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
    }
}
