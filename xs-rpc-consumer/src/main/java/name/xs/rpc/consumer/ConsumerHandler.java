package name.xs.rpc.consumer;

import name.xs.rpc.common.beans.Result;
import name.xs.rpc.protocol.Message;

/**
 * @author 熊顺
 * @CreateTime 2020/1/10 10:42
 */
public interface ConsumerHandler {

    Result handle(Message message);

}
