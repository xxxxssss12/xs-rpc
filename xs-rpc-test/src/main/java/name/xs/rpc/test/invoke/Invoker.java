package name.xs.rpc.test.invoke;

import name.xs.rpc.common.beans.Result;

import java.lang.reflect.Method;

/**
 * create by xs
 * create time:2019-12-08 22:20:44
 */
public interface Invoker {

    Result invoke(Method method, Class<?>[] parameterTypes, Object[] arguments) throws Exception;
}
