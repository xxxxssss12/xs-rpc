package name.xs.rpc.protocol.jlsp;

import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.common.beans.protocol.Message;
import name.xs.rpc.common.beans.protocol.MessageBuilder;

import java.util.UUID;

/**
 * @author xs
 * create time: 2019-07-14 22:13
 */
public class JlspMessageBuilder implements MessageBuilder {

    @Override
    public Message buildMessage(String data) {
        String sessionId = UUID.randomUUID().toString().replaceAll("-", "");
        return buildMessage(data, sessionId);
    }

    @Override
    public Message buildMessage(String data, String sessionId) {
        if (sessionId == null || sessionId.length() != 32) {
            throw new XsRpcException(ErrorEnum.PROTOCOL_01);
        }
        return new JlspMessage(data, sessionId);
    }
}
