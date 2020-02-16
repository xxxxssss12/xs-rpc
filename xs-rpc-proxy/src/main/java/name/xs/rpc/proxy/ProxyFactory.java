package name.xs.rpc.proxy;

import name.xs.rpc.common.context.ProxyContext;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import name.xs.rpc.proxy.invoke.LocalProxyInvoker;
import name.xs.rpc.proxy.invoke.RemoteInvoker;

import java.lang.reflect.Proxy;

/**
 * @author xs
 * create time:2019-07-15 16:09:28
 */
public class ProxyFactory {

    public static <T> T getLocalProxy(final Class<T> interfaceClass, T originImpl) throws Exception {
        return getProxyInstance(interfaceClass, false, originImpl);
    }
    public static <T> T getRemoteProxy(final Class<T> interfaceClass) throws Exception {
        return getProxyInstance(interfaceClass, true, null);
    }

    private static <T> T getProxyInstance(Class<T> interfaceClass, boolean isRemote, T originImpl) throws Exception {
        Object obj = null;
        if (!isRemote) {
            // 优先本地
            obj = ProxyContext.instance().getLocalProxyObjMap().get(interfaceClass.getName());
        } else if (ProxyContext.instance().getLocalProxyObjMap().get(interfaceClass.getName()) != null) {
            obj = ProxyContext.instance().getLocalProxyObjMap().get(interfaceClass.getName());
        } else {
            obj = ProxyContext.instance().getRemoteProxyObjMap().get(interfaceClass.getName());
        }
        if (obj == null) {
            //参数一：被代理对象的类加载器，固定写法
            //参数二：被代理对象实现的接口，固定写法
            //参数三：使用的是策略模式，固定写法,如何调用真实对象的方法
            //代理对象调用的任何方法都会触发此方法执行
            if (!isRemote) {
                if (originImpl == null) {
                    throw new XsRpcException(ErrorEnum.PROXY_04);
                }
                obj = Proxy.newProxyInstance(originImpl.getClass().getClassLoader(), originImpl.getClass().getInterfaces(),
                        /* 参数一：代理对象本身的引用，一般不用
                         * 参数二：代理对象调用的方法
                         * 参数三：代理对象调用的方法的参数
                         */
                        new LocalProxyInvoker<>(originImpl));
                ProxyContext.instance().getLocalProxyObjMap().put(interfaceClass.getName(), obj);
                ProxyContext.instance().getOrginObjMap().put(interfaceClass.getName(), originImpl);
            } else {
                obj = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass},
                        new RemoteInvoker<>(interfaceClass));
                ProxyContext.instance().getRemoteProxyObjMap().put(interfaceClass.getName(), obj);
            }
        }
        return (T) obj;
    }
}
