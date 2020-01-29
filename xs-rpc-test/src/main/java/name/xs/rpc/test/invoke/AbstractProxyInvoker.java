package name.xs.rpc.test.invoke;

import name.xs.rpc.common.beans.Result;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * create by xs
 * create time:2020-01-29 12:07:45
 */
public abstract class AbstractProxyInvoker<T> implements Invoker, InvocationHandler {

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 有filter先过filter
        Result rs = invoke(method,  method.getParameterTypes(), args);
        if (rs == null) {
            throw new XsRpcException(ErrorEnum.PROXY_02);
        }
        if (rs.hasException()) {
            throw new XsRpcException(ErrorEnum.PROXY_03, rs.getException());
        }
        return rs.getValue();
    }
}
