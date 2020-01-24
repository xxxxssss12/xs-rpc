package name.xs.rpc.test.container;

import name.xs.rpc.test.provider.Provider;

import java.util.Map;

/**
 * create by xs
 * create time:2019-12-08 22:16:39
 */
public class XsRpcContext {

    private XsRpcContext() {}

    private static XsRpcContext r = new XsRpcContext();
    public static XsRpcContext instance() {return r;}

    private Map<String, Provider> serviceIdMap;


    public Map<String, Provider> getServiceIdMap() {
        return serviceIdMap;
    }

    public void setServiceIdMap(Map<String, Provider> serviceIdMap) {
        this.serviceIdMap = serviceIdMap;
    }
}
