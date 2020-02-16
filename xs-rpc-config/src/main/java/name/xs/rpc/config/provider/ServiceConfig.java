package name.xs.rpc.config.provider;

import name.xs.rpc.config.AppConfig;
import name.xs.rpc.config.RegistryConfig;
import name.xs.rpc.proxy.ProxyFactory;

import java.util.List;

/**
 * 单个服务配置
 * @author xs
 * create time: 2019-06-30 17:07
 */
public class ServiceConfig<T> {

    private AppConfig appConfig;
    private RegistryConfig registryConfig;

    private String serviceId;           // 服务id
    private String name;                // 服务名称
    private Class<T> interfaceClass;    // 接口类
    private T implementsObj;   // 实现类对象
//    private String protocol;            // 使用协议
    private int timeout = -1;           // 超时毫秒，-1永不超时

    private List<MethodConfig> methodConfigList;    // 方法列表

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class<T> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<T> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public T getImplementsObj() {
        return implementsObj;
    }

    public void setImplementsObj(T implementsObj) {
        this.implementsObj = implementsObj;
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

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public T export() {
        // TODO
        T localProxy = null;
        try {
             localProxy = ProxyFactory.getLocalProxy(interfaceClass, implementsObj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return localProxy;
    }
}
