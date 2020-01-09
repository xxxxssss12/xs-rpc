package org.xs.rpc.test.proxy;

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
    private static Map<String, Object> proxyObjMap = new HashMap<>();

    public static Object getProxy(final Class clazz) {
        return getProxyInstance(clazz);
    }

    private static Object getOriInstance(Class clazz) throws Throwable {
        Object obj = orginObjMap.get(clazz.getName());
        if (obj == null) {
            obj = clazz.getConstructor().newInstance();
            orginObjMap.put(clazz.getName(), obj);
        }
        return obj;
    }

    private static Object getProxyInstance(Class clazz) {
        Object obj = proxyObjMap.get(clazz.getName());
        if (obj == null) {
            //参数一：被代理对象的类加载器，固定写法
            //参数二：被代理对象实现的接口，固定写法
            //参数三：使用的是策略模式，固定写法,如何调用真实对象的方法
            //代理对象调用的任何方法都会触发此方法执行
            obj = Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(),
                    /* 参数一：代理对象本身的引用，一般不用
                     * 参数二：代理对象调用的方法
                     * 参数三：代理对象调用的方法的参数
                     */
                    (proxy, method, args) -> {
                        //前置增强
                        System.out.println("before...");
                        Object result = method.invoke(getOriInstance(clazz), args);//调用真实对象中的方法
                        //后置增强
                        System.out.println("after...");
                        return result;
                    });
        }
        return obj;
    }
}
