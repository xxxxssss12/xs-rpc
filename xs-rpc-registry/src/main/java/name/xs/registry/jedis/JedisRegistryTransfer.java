package name.xs.registry.jedis;

import name.xs.registry.RegistryTransfer;
import name.xs.rpc.config.ProviderMetadata;
import name.xs.rpc.config.provider.ServiceConfig;

import java.util.List;

/**
 * @author xs
 * create time:2020-02-19 18:04:27
 */
public class JedisRegistryTransfer implements RegistryTransfer {

    public JedisRegistryTransfer() {
    }
    @Override
    public ProviderMetadata export(ServiceConfig<?> serviceConfig) {
        return null;
    }

    @Override
    public List<ProviderMetadata> pull(String interfaceName) {
        return null;
    }
}
