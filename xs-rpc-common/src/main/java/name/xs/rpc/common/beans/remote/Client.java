package name.xs.rpc.common.beans.remote;

import name.xs.rpc.common.beans.protocol.Message;

/**
 * @author xs
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

    String getRemoteHost();
    int getRemotePort();

}
