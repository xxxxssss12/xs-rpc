package name.xs.rpc.config;

/**
 * @author 熊顺
 * @CreateTime 2019/12/10 19:57
 */
public class AppConfig {
    private String serviceId;
    private String name;
    private Integer port;
    private int threadNumber = 10;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public int getThreadNumber() {
        return threadNumber;
    }

    public void setThreadNumber(int threadNumber) {
        this.threadNumber = threadNumber;
    }
}
