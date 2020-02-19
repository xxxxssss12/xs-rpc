package name.xs.registry;

import name.xs.registry.jedis.JedisRegistryTransfer;
import name.xs.rpc.common.context.XsRpcContext;
import name.xs.rpc.common.event.Event;
import name.xs.rpc.common.event.EventEnum;
import name.xs.rpc.common.event.EventListener;
import name.xs.rpc.config.ProviderMetadata;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 元数据管理类，理论上也可以单例，最好是单例。。要不要单例？暂时先多例好了
 *
 * @author xs
 * create time:2020-02-18 15:45:23
 */
public class ProviderMetadataManager implements EventListener<String> {

    /**
     * key=inerfaceName，value=provider元数据
     */
    private Map<String, List<ProviderMetadata>> interfaceNameIndexMap = new ConcurrentHashMap<>();

    /**
     * key=host:port，value=providerInterfaceName
     *
     * 这个不好维护，先不使用。
     */
    private Map<String, Set<String>> hostIndexMap = new ConcurrentHashMap<>();

    private RegistryTransfer registryTransfer;

    public ProviderMetadataManager() {
        XsRpcContext.instance().getEventBus().subscribe(EventEnum.SERVICE_PROVIDER_CHANGE, this);
        this.registryTransfer = new JedisRegistryTransfer();
    }

    public ProviderMetadataManager(RegistryTransfer registryTransfer) {
        XsRpcContext.instance().getEventBus().subscribe(EventEnum.SERVICE_PROVIDER_CHANGE, this);
        this.registryTransfer = registryTransfer;
    }

    @Override
    public String getId() {
        return "ProviderMetadataManager";
    }

    @Override
    public void onEventPublish(Event<String> event) {
        EventEnum eventEnum = event.getEventEnum();
        if (eventEnum == EventEnum.SERVICE_PROVIDER_CHANGE) {
            newProviderExport(event.getData());
        }
    }

    private void newProviderExport(String interfaceName) {
        if (interfaceName == null || interfaceName.length() == 0) {
            return;
        }
        // 这个interface，此节点是否需要？
        if (!XsRpcContext.instance().getProxyContext().getRemoteProxyObjMap().containsKey(interfaceName)) {
            return;
        }
        List<ProviderMetadata> providerMetadataList = registryTransfer.pull(interfaceName);
        if (providerMetadataList != null) {
            handleMetadataList(interfaceName, providerMetadataList);
        }
    }

    private void handleMetadataList(String interfaceName, List<ProviderMetadata> providerMetadataList) {
        // 即便是空数组，也需要刷新本地索引
        if (providerMetadataList == null) {
            return;
        }
        if (!interfaceNameIndexMap.containsKey(interfaceName)) {
            interfaceNameIndexMap.putIfAbsent(interfaceName, providerMetadataList);
        }
    }

    private String getHostInfo(ProviderMetadata providerMetadata) {
        if (providerMetadata == null) {
            return null;
        }
        return providerMetadata.getHost() + ":" + providerMetadata.getPort();
    }


}
