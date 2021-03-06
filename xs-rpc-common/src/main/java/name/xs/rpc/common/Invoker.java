package name.xs.rpc.common;

import name.xs.rpc.common.beans.Result;

import java.lang.reflect.Method;

/**
 * @author xs
 * create time:2019-12-08 22:20:44
 */
public interface Invoker {

    Result invoke(Method method, Class<?>[] parameterTypes, Object[] arguments) throws Exception;
}
