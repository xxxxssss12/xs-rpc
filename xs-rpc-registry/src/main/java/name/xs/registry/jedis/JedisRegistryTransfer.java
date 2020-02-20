package name.xs.registry.jedis;

import name.xs.registry.RegistryTransfer;
import name.xs.rpc.common.beans.registry.ProviderMetaInfo;
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
    public ProviderMetaInfo export(ServiceConfig<?> serviceConfig) {
        return null;
    }

    @Override
    public List<ProviderMetaInfo> pull(String interfaceName) {
        return null;
    }
}
