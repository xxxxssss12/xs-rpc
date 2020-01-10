package name.xs.rpc.config;

import java.io.Serializable;

/**
 * 元数据
 * 1. host
 * 2. 端口
 * 3. 服务名称
 * 4. 接口名
 * 5. 接口当前版本号
 * 6. 服务暴露时间
 * 7. 方法名，逗号分割
 *
 * @author 熊顺
 * @CreateTime 2019/12/10 10:06
 */
public class ProviderMetadata implements Serializable {
    private String host;
    private Integer port;
    private String serviceId;
    private String interfaceName;
    private String interfaceVersion;
    private long exportTimestamp;
    private String methodsName;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public long getExportTimestamp() {
        return exportTimestamp;
    }

    public void setExportTimestamp(long exportTimestamp) {
        this.exportTimestamp = exportTimestamp;
    }

    public String getMethodsName() {
        return methodsName;
    }

    public void setMethodsName(String methodsName) {
        this.methodsName = methodsName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
