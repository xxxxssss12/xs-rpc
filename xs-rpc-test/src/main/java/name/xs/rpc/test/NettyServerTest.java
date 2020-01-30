package name.xs.rpc.test;

import name.xs.rpc.proxy.ProxyFactory;
import name.xs.rpc.remote.netty.server.DemoNettyServerHandler;
import name.xs.rpc.remote.netty.server.XsRpcNettyServer;

/**
 * create by xs
 * create time:2020-01-30 09:20:43
 */
public class NettyServerTest {
    public static void main(String[] args) throws Throwable {
        ProxyFactory.getLocalProxy(FirstServiceImpl.class);
        XsRpcNettyServer.instance().start(10000, new DemoNettyServerHandler());
    }
}
