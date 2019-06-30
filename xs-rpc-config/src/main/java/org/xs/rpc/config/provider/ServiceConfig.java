package org.xs.rpc.config.provider;

import java.util.List;

/**
 * 单个服务配置
 * create by xs, 2019-06-30 17:07
 */
public class ServiceConfig {
    private String name;                // 服务名称
    private Class<?> interfaceClass;    // 接口类
    private Class<?> implementsClass;   // 实现类
    private String protocol;            // 使用协议
    private int timeout = -1;           // 超时毫秒，-1永不超时

    private List<MethodConfig> methodConfigList;    // 方法列表
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public Class<?> getImplementsClass() {
        return implementsClass;
    }

    public void setImplementsClass(Class<?> implementsClass) {
        this.implementsClass = implementsClass;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public List<MethodConfig> getMethodConfigList() {
        return methodConfigList;
    }

    public void setMethodConfigList(List<MethodConfig> methodConfigList) {
        this.methodConfigList = methodConfigList;
    }
}
