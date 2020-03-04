package name.xs.rpc.proxy.invoke;

import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.beans.CommonResult;
import name.xs.rpc.common.beans.Result;
import name.xs.rpc.common.beans.XsRpcExceptionSerialize;
import name.xs.rpc.common.exceptions.XsRpcException;

import java.lang.reflect.Method;

/**
 * 本地代理invoker，provider使用
 *
 * @author xs
 * create time:2019-12-08 22:24:00
 */
public class LocalProxyInvoker<T> extends AbstractProxyInvoker<T>  {
    protected T instance;

    public LocalProxyInvoker(T instance) {
        super();
        this.instance = instance;
    }

    /**
     * 执行调用
     *
     * @param method 方法
     * @param parameterTypes 参数类型list
     * @param arguments 参数list
     * @return org.xs.rpc.common.beans.Result
     */
    @Override
    public Result invoke(Method method, Class<?>[] parameterTypes, Object[] arguments) {
        if (instance == null) {
            throw new XsRpcException(ErrorEnum.PROXY_01);
        }
        Result rs = new CommonResult();
        try {
            Object result = method.invoke(instance, arguments);
            rs.setValue(result);
        } catch (Throwable e) {
            Constant.LOG.error(this.getClass(), "local invoke method error", e);
            rs.setException(new XsRpcExceptionSerialize(e));
        }
        return rs;
    }
}
