package name.xs.rpc.remote.netty.client;

import io.netty.channel.ChannelFuture;
import name.xs.rpc.common.beans.protocol.Message;
import name.xs.rpc.common.context.RemoteContext;
import name.xs.rpc.common.beans.remote.RequestingDto;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xs
 * create time:2020-01-28 19:26:15
 */
public class NettyRequestTask implements Callable<Message> {

    private Message requestMessage;
    private CountDownLatch countDownLatch;
    private ChannelFuture channelFuture;
    private Long timeout;

    public NettyRequestTask(Message requestMessage, CountDownLatch countDownLatch, ChannelFuture channelFuture, Long timeout) {
        this.requestMessage = requestMessage;
        this.countDownLatch = countDownLatch;
        this.channelFuture = channelFuture;
        this.timeout = timeout;
    }

    @Override
    public Message call() throws Exception {
        channelFuture.channel().writeAndFlush(requestMessage);
        if (this.timeout == null || this.timeout <= 0) {
            countDownLatch.await(); // 阻塞方法，在XsRpcNettyClientHandler中countdown
        } else {
            countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
        }
        RequestingDto requestingDto = RemoteContext.instance().getRequestingDto(requestMessage.getSessionId());
        return requestingDto.getResponseMessage();
    }
}
