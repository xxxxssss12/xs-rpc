package name.xs.rpc.common.context;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xs
 * create time:2020-01-29 23:52:45
 */
public class ProxyContext {
    private static final ProxyContext i = new ProxyContext();
    private ProxyContext() {}
    public static ProxyContext instance() {return i;}

    private Map<String, Object> orginObjMap = new ConcurrentHashMap<>();
    private Map<String, Object> localProxyObjMap = new ConcurrentHashMap<>();
    private Map<String, Object> remoteProxyObjMap = new ConcurrentHashMap<>();

    public Map<String, Object> getOrginObjMap() {
        return orginObjMap;
    }

    public void setOrginObjMap(Map<String, Object> orginObjMap) {
        this.orginObjMap = orginObjMap;
    }

    public Map<String, Object> getLocalProxyObjMap() {
        return localProxyObjMap;
    }

    public void setLocalProxyObjMap(Map<String, Object> localProxyObjMap) {
        this.localProxyObjMap = localProxyObjMap;
    }

    public Map<String, Object> getRemoteProxyObjMap() {
        return remoteProxyObjMap;
    }

    public void setRemoteProxyObjMap(Map<String, Object> remoteProxyObjMap) {
        this.remoteProxyObjMap = remoteProxyObjMap;
    }
}
