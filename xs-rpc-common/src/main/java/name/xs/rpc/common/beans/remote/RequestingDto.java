package name.xs.rpc.common.beans.remote;

import name.xs.rpc.common.beans.protocol.Message;

import java.util.concurrent.CountDownLatch;

/**
 * 进行中的请求对象
 *
 * @author xs
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
