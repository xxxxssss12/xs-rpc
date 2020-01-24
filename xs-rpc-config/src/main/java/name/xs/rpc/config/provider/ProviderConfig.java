package name.xs.rpc.config.provider;

import java.util.List;
import java.util.Map;

/**
 * 提供方配置信息
 * create by xs, 2019-06-30 17:04
 */
public class ProviderConfig {

    private String host;    // 主机名

    private Integer port;   // 监听端口

    private Long exportTimestamp;

    private Map<String, String> envParams; // 环境变量

    private List<ServiceConfig> serviceConfigList;  // 提供列表

    private Integer handleThreadCount;  // 处理请求线程数

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public Map<String, String> getEnvParams() {
        return envParams;
    }

    public void setEnvParams(Map<String, String> envParams) {
        this.envParams = envParams;
    }

    public List<ServiceConfig> getServiceConfigList() {
        return serviceConfigList;
    }

    public void setServiceConfigList(List<ServiceConfig> serviceConfigList) {
        this.serviceConfigList = serviceConfigList;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Long getExportTimestamp() {
        return exportTimestamp;
    }

    public void setExportTimestamp(Long exportTimestamp) {
        this.exportTimestamp = exportTimestamp;
    }

    public Integer getHandleThreadCount() {
        return handleThreadCount;
    }

    public void setHandleThreadCount(Integer handleThreadCount) {
        this.handleThreadCount = handleThreadCount;
    }
}
