package name.xs.rpc.config.util;

import name.xs.rpc.common.beans.registry.ProviderMetaInfo;
import name.xs.rpc.config.provider.MethodConfig;
import name.xs.rpc.config.provider.ProviderConfig;
import name.xs.rpc.config.provider.ServiceConfig;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author xs
 * @CreateTime 2019/12/10 10:08
 */
public class ProviderMetaInfoBuilder {

    public static ProviderMetaInfo buildMetaInfo(ProviderConfig providerConfig, ServiceConfig serviceConfig) {
        ProviderMetaInfo metadata = new ProviderMetaInfo();
        metadata.setServiceId(serviceConfig.getServiceId());
        metadata.setHost(providerConfig.getHost());
        metadata.setPort(providerConfig.getPort());
        metadata.setExportTimestamp(providerConfig.getExportTimestamp());
        metadata.setInterfaceName(serviceConfig.getInterfaceClass().getName());
        metadata.setMethodsName(buildMethodsName(serviceConfig.getMethodConfigList()));
        return metadata;
    }

    private static String buildMethodsName(List<MethodConfig> methodConfigList) {
        if (methodConfigList == null || methodConfigList.isEmpty()) {
            return null;
        }
        Set<String> methodNameList = new HashSet<>();
        for (MethodConfig methodConfig : methodConfigList) {
            methodNameList.add(methodConfig.getName());
        }
        StringBuilder sb = new StringBuilder();
        methodNameList.forEach(s -> sb.append(s).append(","));
        return sb.substring(0, sb.length() - 1);
    }
}
