package name.xs.rpc.remote.netty.server;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import name.xs.rpc.common.beans.CommonResult;
import name.xs.rpc.common.beans.XsRpcExceptionSerialize;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.protocol.xsp.XspHeader;
import name.xs.rpc.protocol.xsp.XspMessage;
import name.xs.rpc.protocol.xsp.XspMessageBuilder;
import name.xs.rpc.remote.netty.protocol.ProtocolContext;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;

public class DemoNettyServerHandler extends ChannelInboundHandlerAdapter {

//    private ProviderHandler providerHandler;
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
//        ByteBuf buf = Unpooled.buffer(data.length); // netty自定义缓存
//        buf.writeBytes(data);
//        ctx.writeAndFlush(buf);
        System.out.println("客户端连入：" + ctx.channel().remoteAddress().toString());
    }
    // 接受请求后处理类
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            if (msg instanceof Message) {
                Message msg1 = (Message) msg;
                System.out.println(msg1.getData());

                //此处写接收到客户端请求后的业务逻辑
                CommonResult result = new CommonResult();
                result.setValue(3);
                String content = JSON.toJSONString(result);
                String uuid = msg1.getSessionId();

                Message message = ProtocolContext.getMessageBuilder().buildMessage(content, uuid);
                ctx.writeAndFlush(message);
                System.out.println("服务端响应：" + message.getData());
            } else {
                throw new XsRpcException(ErrorEnum.SERVER_01);
            }
        } catch (Exception e) {
            Constant.LOG.error("server invoke error!", e);
            CommonResult result = new CommonResult();
            XsRpcExceptionSerialize serialize = new XsRpcExceptionSerialize();
            serialize.setClassName(e.getClass().getName());
            serialize.setMessage(e.getMessage());
            result.setException(serialize);
            Message message = ProtocolContext.getMessageBuilder().buildMessage(JSON.toJSONString(result));
            ctx.writeAndFlush(message);
            System.out.println("服务端响应：" + message.getData());
        }
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

//    public ProviderHandler getProviderHandler() {
//        return providerHandler;
//    }

//    public void setProviderHandler(ProviderHandler providerHandler) {
//        this.providerHandler = providerHandler;
//    }
}
