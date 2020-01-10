package name.xs.rpc.test.provider;

import name.xs.rpc.common.beans.Result;

/**
 * create by xs
 * create time:2019-12-08 22:20:44
 */
public interface Provider {

    Result invoke(String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Exception;
}
