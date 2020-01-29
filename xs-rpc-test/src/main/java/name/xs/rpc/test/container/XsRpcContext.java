package name.xs.rpc.test.container;

import java.util.Map;

/**
 * create by xs
 * create time:2019-12-08 22:16:39
 */
public class XsRpcContext {

    private XsRpcContext() {}

    private static XsRpcContext r = new XsRpcContext();

    public static XsRpcContext instance() {return r;}

    /**
     * key为interfaceName
     */
    private Map<String, Object> originInstanceMap;

}
