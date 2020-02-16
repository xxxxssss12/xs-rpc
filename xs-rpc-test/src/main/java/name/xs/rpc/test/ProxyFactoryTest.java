package name.xs.rpc.test;

import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.proxy.ProxyFactory;
import name.xs.rpc.common.beans.remote.Client;
import name.xs.rpc.common.context.RemoteContext;

/**
 * @author xs
 * create time:2019-07-15 16:23:43
 */
public class ProxyFactoryTest {
    public static void main(String[] args) throws Throwable {
        FirstService newService = ProxyFactory.getRemoteProxy(FirstService.class);
        try {
            System.out.println(newService.add(1, 2));
            System.out.println(newService.toJson("testKey1", "testV1", "testKey2", "testV2"));
            System.out.println(newService.substract(1, 2));
        } catch (Exception e) {
            Constant.LOG.error("main error", e);
        }
        Client c = RemoteContext.instance().getClient("127.0.0.1", 10020);
        if (c!=null) {
            c.stop();
        }
        RemoteContext.instance().getRequestThreadPool().shutdown();
    }
}
