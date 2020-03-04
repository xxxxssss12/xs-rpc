package name.xs.rpc.remote.netty.client;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.beans.protocol.Message;
import name.xs.rpc.common.beans.remote.ClientHandler;
import name.xs.rpc.common.context.RemoteContext;
import name.xs.rpc.common.beans.remote.RequestingDto;

/**
 * @author xs
 * create time:2020-01-27 12:05:09
 */
@ChannelHandler.Sharable
public class XsRpcNettyClientHandler extends ChannelInboundHandlerAdapter implements ClientHandler {
    //接收到数据后调用...
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        Constant.LOG.debug(this.getClass(), "channelRead..");
        if (msg instanceof Message) {
            Message msg1 = (Message) msg;
            Constant.LOG.debug(this.getClass(), "channelRead,msg={},sessionId={}", msg1.getData(), msg1.getSessionId());
            // 根据请求的sessionId获取进行中的请求
            RequestingDto requestingDto = RemoteContext.instance().getRequestingDto(msg1.getSessionId());
            if (requestingDto != null) {
                requestingDto.setResponseMessage(msg1);
                // 唤醒
                requestingDto.getCountDownLatch().countDown();
            }
        } else {
            Constant.LOG.error(this.getClass(), "unknown msg");
        }
    }

    //完成时调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        Constant.LOG.debug(this.getClass(), "channelReadComplete");
        ctx.flush();
    }

    //发生异常时调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        // Close the connection when an exception is raised.
        Constant.LOG.error(this.getClass(), "exceptionCaught", cause);
    }
}
