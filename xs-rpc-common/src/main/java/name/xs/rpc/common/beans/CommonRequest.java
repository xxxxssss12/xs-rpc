package name.xs.rpc.common.beans;

/**
 * 1. serviceId
 * 2. methodName
 * 3. ServiceInterfaceName
 * 4. ParameterTypes - 方法入参类型数组——string类型，类全限定名
 * 5. Arguments - 入参值，由string转化
 * 6. signatures 签名
 *
 * @author 熊顺
 * @CreateTime 2020/1/10 14:27
 */
public class CommonRequest implements Request {
    private String serviceId;
    private String methodName;
    private String serviceInterfaceName;
    private String[] parameterTypes;
    private String[] arguments;
    private String signatures;

    @Override
    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    @Override
    public String getServiceInterfaceName() {
        return serviceInterfaceName;
    }

    public void setServiceInterfaceName(String serviceInterfaceName) {
        this.serviceInterfaceName = serviceInterfaceName;
    }

    @Override
    public String[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    @Override
    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    @Override
    public String getSignatures() {
        return signatures;
    }

    public void setSignatures(String signatures) {
        this.signatures = signatures;
    }
}
