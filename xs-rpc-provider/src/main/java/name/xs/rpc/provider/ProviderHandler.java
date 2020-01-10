package name.xs.rpc.provider;

import name.xs.rpc.common.beans.CommonResult;
import name.xs.rpc.common.beans.Result;
import name.xs.rpc.protocol.Message;

/**
 * @author 熊顺
 * @CreateTime 2020/1/10 9:44
 */
public interface ProviderHandler {

    Result handle(Message message);
}
