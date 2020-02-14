package name.xs.rpc.common.beans.protocol;

/**
 * @author xs
 * create time: 2019-07-13 15:23
 */
public interface MessageBuilder {

    Message buildMessage(String data);
    Message buildMessage(String data, String sessionId);
}
