package name.xs.rpc.test.proxy;

import name.xs.rpc.test.invoke.LocalProxyInvoker;
import name.xs.rpc.test.invoke.RemoteInvoker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

/**
 * create by xs
 * create time:2019-07-15 16:09:28
 */
public class ProxyFactory {
    private static Map<String, Object> orginObjMap = new HashMap<>();
    private static Map<String, Object> localProxyObjMap = new HashMap<>();
    private static Map<String, Object> remoteProxyObjMap = new HashMap<>();

    public static <T> T getLocalProxy(final Class<T> clazz) throws Throwable {
        return getProxyInstance(clazz, false);
    }

    public static <T> T getRemoteProxy(final Class<T> interfase) throws Throwable {
        return getProxyInstance(interfase, true);
    }
    private static <T> T getOriInstance(Class<T> clazz) throws Throwable {
        T obj = (T) orginObjMap.get(clazz.getInterfaces()[0].getName());
        if (obj == null) {
            obj = clazz.getConstructor().newInstance();
            orginObjMap.put(clazz.getInterfaces()[0].getName(), obj);
        }
        return obj;
    }

    private static <T> T getProxyInstance(Class<T> clazz, boolean isRemote) throws Throwable {
        Object obj = null;
        if (!isRemote) {
            // 优先本地
            obj = localProxyObjMap.get(clazz.getInterfaces()[0].getName());
        } else if (localProxyObjMap.get(clazz.getName()) != null) {
            obj = localProxyObjMap.get(clazz.getName());
        } else {
            obj = remoteProxyObjMap.get(clazz.getName());
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
                localProxyObjMap.put(clazz.getInterfaces()[0].getName(), obj);
            } else {
                obj = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz},
                        new RemoteInvoker<>(clazz));
                remoteProxyObjMap.put(clazz.getName(), obj);
            }
        }
        return (T) obj;
    }
}
