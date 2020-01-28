package name.xs.rpc.remote;

import name.xs.rpc.protocol.Message;

/**
 * create by xs
 * create time:2020-01-28 10:19:32
 */
public interface Client {
    void start(String host, Integer port, ClientHandler handler);
    void stop();

    /**
     *
     * @param msg
     * @param timeout <=0,永不超时
     * @return
     */
    Message send(Message msg, long timeout);

    boolean isRunning();

}
