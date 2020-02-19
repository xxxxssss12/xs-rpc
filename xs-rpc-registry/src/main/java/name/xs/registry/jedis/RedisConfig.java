package name.xs.registry.jedis;

import java.io.Serializable;

/**
 * @author xs
 * create time:  2020/2/15 19:58
 */
public class RedisConfig implements Serializable {

    private String host;
    private int port = 6379;
    private int maxActive;
    private int maxIdle;
    private String password;
    private int maxWait = 1000;

    public RedisConfig(String host, int port, int maxActive, int maxIdle, String password, int maxWait) {
        this.host = host;
        this.port = port;
        this.maxActive = maxActive;
        this.maxIdle = maxIdle;
        this.password = password;
        this.maxWait = maxWait;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(int maxActive) {
        this.maxActive = maxActive;
    }

    public int getMaxIdle() {
        return maxIdle;
    }

    public void setMaxIdle(int maxIdle) {
        this.maxIdle = maxIdle;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(int maxWait) {
        this.maxWait = maxWait;
    }
}
