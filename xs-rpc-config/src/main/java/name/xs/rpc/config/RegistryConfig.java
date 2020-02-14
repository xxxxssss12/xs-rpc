package name.xs.rpc.config;

/**
 * 注册中心配置
 *
 * @author xs
 * create time:2019-11-14 09:20:22
 */
public class RegistryConfig {
    private String protocol;    // 协议
    private String host;        // 主机
    private Integer port;       // 端口
    private String username;    // 用户名
    private String password;    // 密码

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
