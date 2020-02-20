package name.xs.rpc.common.context;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import name.xs.rpc.common.beans.registry.ProviderMetaInfo;

/**
 * @author xs
 * create time:2020-02-20 11:27:22
 */
public class RegistryContext {
    private static final RegistryContext i = new RegistryContext();
    private RegistryContext() {}
    public static RegistryContext instance() {return i;}

    /**
     * key=inerfaceName，value=providerMetaInfo
     */
    private Map<String, List<ProviderMetaInfo>> interfaceNameIndexMap = new ConcurrentHashMap<>();

    public List<ProviderMetaInfo> refreshIndex(String interfaceName, List<ProviderMetaInfo> providerMetaInfoList) {
        return this.interfaceNameIndexMap.put(interfaceName, providerMetaInfoList);
    }

}
