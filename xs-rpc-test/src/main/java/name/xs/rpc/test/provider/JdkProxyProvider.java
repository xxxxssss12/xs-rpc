package name.xs.rpc.test.provider;

import name.xs.rpc.common.ErrorEnum;
import name.xs.rpc.common.beans.CommonResult;
import name.xs.rpc.common.beans.Result;
import name.xs.rpc.common.beans.XsRpcExceptionSerialize;
import name.xs.rpc.common.exceptions.XsRpcException;

import java.lang.reflect.Method;

/**
 * create by xs
 * create time:2019-12-08 22:24:00
 */
public class JdkProxyProvider<T> implements Provider {

    private T instance;

    public JdkProxyProvider(T instance) {
        this.instance = instance;
    }

    /**
     * 执行调用
     *
     * @param methodName 方法名
     * @param parameterTypes 参数类型list
     * @param arguments 参数list
     * @return org.xs.rpc.common.beans.Result
     */
    @Override
    public Result invoke(String methodName, Class<?>[] parameterTypes, Object[] arguments) {
        if (instance == null) {
            throw new XsRpcException(ErrorEnum.PROXY_01);
        }
        Result rs = new CommonResult();
        try {
            Method method = instance.getClass().getMethod(methodName, parameterTypes);
            Object result = method.invoke(instance, arguments);
            rs.setValue(result);
        } catch (Throwable e) {
            rs.setException(new XsRpcExceptionSerialize(e));
        }
        return rs;
    }
}
