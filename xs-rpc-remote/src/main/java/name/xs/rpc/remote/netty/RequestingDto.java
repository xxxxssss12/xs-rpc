package name.xs.rpc.remote.netty;

import name.xs.rpc.protocol.Message;
import name.xs.rpc.remote.netty.client.NettyRequestExecutor;

import java.util.concurrent.CountDownLatch;

/**
 * create by xs
 * create time:2020-01-28 19:23:28
 */
public class RequestingDto {
    private Message responseMessage;
    private Message requestMessage;
    private CountDownLatch countDownLatch;

    public RequestingDto(Message requestMessage, CountDownLatch countDownLatch) {
        this.requestMessage = requestMessage;
        this.countDownLatch = countDownLatch;
    }

    public Message getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(Message responseMessage) {
        this.responseMessage = responseMessage;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public Message getRequestMessage() {
        return requestMessage;
    }

    public void setRequestMessage(Message requestMessage) {
        this.requestMessage = requestMessage;
    }
}
