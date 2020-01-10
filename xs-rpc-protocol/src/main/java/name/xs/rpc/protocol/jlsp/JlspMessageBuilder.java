package name.xs.rpc.protocol.jlsp;

import name.xs.rpc.protocol.Message;
import name.xs.rpc.protocol.MessageBuilder;

/**
 * create by xs, 2019-07-14 22:13
 */
public class JlspMessageBuilder implements MessageBuilder {

    @Override
    public Message buildMessage(String data) {

        return new JlspMessage(data);
    }
}
