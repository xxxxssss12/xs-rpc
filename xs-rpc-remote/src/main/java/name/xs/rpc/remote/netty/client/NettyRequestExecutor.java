package name.xs.rpc.remote.netty.client;

import io.netty.channel.ChannelFuture;
import name.xs.rpc.protocol.Message;
import name.xs.rpc.remote.netty.RemoteContext;
import name.xs.rpc.remote.netty.RequestingDto;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * create by xs
 * create time:2020-01-28 19:26:15
 */
public class NettyRequestExecutor implements Callable<Message> {

    private Message requestMessage;
    private CountDownLatch countDownLatch;
    private ChannelFuture channelFuture;
    private Long timeout;

    public NettyRequestExecutor(Message requestMessage, CountDownLatch countDownLatch, ChannelFuture channelFuture, Long timeout) {
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
