package name.xs.registry;

import name.xs.rpc.common.beans.registry.ProviderMetaInfo;
import name.xs.rpc.config.provider.ServiceConfig;

import java.util.List;

/**
 * 和注册中心交互
 *
 * @author xs
 * create time:  2020/2/17 17:51
 */
public interface RegistryTransfer {
    ProviderMetaInfo export(ServiceConfig<?> serviceConfig);

    List<ProviderMetaInfo> pull(String interfaceName);

}
