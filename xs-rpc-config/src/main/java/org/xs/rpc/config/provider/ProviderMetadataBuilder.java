package org.xs.rpc.config.provider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author 熊顺
 * @CreateTime 2019/12/10 10:08
 */
public class ProviderMetadataBuilder {

    public static ProviderMetadata buildMetadata(ProviderConfig providerConfig, ServiceConfig serviceConfig) {
        ProviderMetadata metadata = new ProviderMetadata();
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
