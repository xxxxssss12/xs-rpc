package name.xs.rpc.common.beans.remote;

/**
 * @author xs
 * create time:2020-01-30 09:09:56
 */
public interface Server {
    void start(Integer port, ServerHandler handler);
    void stop();
    boolean isRunning();
}
