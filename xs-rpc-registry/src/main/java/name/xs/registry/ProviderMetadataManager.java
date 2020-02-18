package name.xs.registry;

import name.xs.rpc.config.ProviderMetadata;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元数据管理类
 * @author xs
 * create time:2020-02-18 15:45:23
 */
public class ProviderMetadataManager {

    /**
     * key=inerfaceName，value=provider元数据
     */
    Map<String, List<ProviderMetadata>> interfaceNameIndexMap = new ConcurrentHashMap<>();

    /**
     * key=host:port，value=providerInterfaceName
     */
    Map<String, List<String>> hostIndexMap = new ConcurrentHashMap<>();
}
