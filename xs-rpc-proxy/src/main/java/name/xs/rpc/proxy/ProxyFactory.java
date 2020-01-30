package name.xs.rpc.proxy;

import name.xs.rpc.common.context.ProxyContext;
import name.xs.rpc.proxy.invoke.LocalProxyInvoker;
import name.xs.rpc.proxy.invoke.RemoteInvoker;

import java.lang.reflect.Proxy;

/**
 * create by xs
 * create time:2019-07-15 16:09:28
 */
public class ProxyFactory {

    public static <T> T getLocalProxy(final Class<T> clazz) throws Exception {
        return getProxyInstance(clazz, false);
    }

    public static <T> T getRemoteProxy(final Class<T> interfase) throws Exception {
        return getProxyInstance(interfase, true);
    }
    private static <T> T getOriInstance(Class<T> clazz) throws Exception {
        T obj = (T) ProxyContext.instance().getOrginObjMap().get(clazz.getInterfaces()[0].getName());
        if (obj == null) {
            obj = clazz.getConstructor().newInstance();
            ProxyContext.instance().getOrginObjMap().put(clazz.getInterfaces()[0].getName(), obj);
        }
        return obj;
    }

    private static <T> T getProxyInstance(Class<T> clazz, boolean isRemote) throws Exception {
        Object obj = null;
        if (!isRemote) {
            // 优先本地
            obj = ProxyContext.instance().getLocalProxyObjMap().get(clazz.getInterfaces()[0].getName());
        } else if (ProxyContext.instance().getLocalProxyObjMap().get(clazz.getName()) != null) {
            obj = ProxyContext.instance().getLocalProxyObjMap().get(clazz.getName());
        } else {
            obj = ProxyContext.instance().getRemoteProxyObjMap().get(clazz.getName());
        }
        if (obj == null) {
            //参数一：被代理对象的类加载器，固定写法
            //参数二：被代理对象实现的接口，固定写法
            //参数三：使用的是策略模式，固定写法,如何调用真实对象的方法
            //代理对象调用的任何方法都会触发此方法执行
            if (!isRemote) {
                obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                        /* 参数一：代理对象本身的引用，一般不用
                         * 参数二：代理对象调用的方法
                         * 参数三：代理对象调用的方法的参数
                         */
                        new LocalProxyInvoker<>(getOriInstance(clazz)));
                ProxyContext.instance().getLocalProxyObjMap().put(clazz.getInterfaces()[0].getName(), obj);
            } else {
                obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                        new RemoteInvoker<>(clazz));
                ProxyContext.instance().getRemoteProxyObjMap().put(clazz.getName(), obj);
            }
        }
        return (T) obj;
    }
}
