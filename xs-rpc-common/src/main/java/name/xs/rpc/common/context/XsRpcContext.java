package name.xs.rpc.common.context;

import name.xs.rpc.common.event.DefaultEventBus;
import name.xs.rpc.common.event.EventBus;

/**
 * @author xs
 * create time:2019-12-08 22:16:39
 */
public class XsRpcContext {

    private XsRpcContext() {}

    private static XsRpcContext r = new XsRpcContext();

    public static XsRpcContext instance() {return r;}

    private EventBus eventBus = new DefaultEventBus();

    public ProxyContext getProxyContext() {return ProxyContext.instance(); }

    public RemoteContext getRemoteContext() {return RemoteContext.instance(); }

    public ProtocolContext getProtocolContext() {return ProtocolContext.instance(); }

    public RegistryContext getRegistryContext() {return RegistryContext.instance(); }

    public EventBus getEventBus() {
        return eventBus;
    }
}
