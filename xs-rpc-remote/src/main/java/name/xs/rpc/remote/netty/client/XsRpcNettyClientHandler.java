package name.xs.rpc.remote.netty.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.remote.ClientHandler;
import name.xs.rpc.remote.netty.RemoteContext;
import name.xs.rpc.remote.netty.RequestingDto;

/**
 * create by xs
 * create time:2020-01-27 12:05:09
 */
public class XsRpcNettyClientHandler extends ChannelInboundHandlerAdapter implements ClientHandler {
    //接收到数据后调用...
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Constant.LOG.debug("[XsRpcNettyClientHandler] channelRead..");
        if (msg instanceof Message) {
            Message msg1 = (Message) msg;
            Constant.LOG.debug("[XsRpcNettyClientHandler] channelRead,msg={},sessionId={}", msg1.getData(), msg1.getSessionId());
            RequestingDto requestingDto = RemoteContext.instance().getRequestingDto(msg1.getSessionId());
            if (requestingDto != null) {
                requestingDto.setResponseMessage(msg1);
                requestingDto.getCountDownLatch().countDown();
            }
        } else {
            Constant.LOG.error("[XsRpcNettyClientHandler] unknown msg");
        }
    }

    //完成时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        Constant.LOG.debug("[XsRpcNettyClientHandler] channelReadComplete");
        ctx.flush();
    }

    //发生异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        Constant.LOG.error("[XsRpcNettyClientHandler] exceptionCaught", cause);
//        ctx.close();
    }
}
