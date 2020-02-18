package name.xs.registry;

import name.xs.rpc.config.ProviderMetadata;
import name.xs.rpc.config.provider.ServiceConfig;

import java.util.List;

/**
 * 和注册中心交互
 *
 * @author xs
 * create time:  2020/2/17 17:51
 */
public interface RegistryTransfer {
    ProviderMetadata export(ServiceConfig<?> serviceConfig);

    List<ProviderMetadata> pull(String interfaceName);

}
