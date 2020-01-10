package name.xs.rpc.common.beans;

/**
 * @author 熊顺
 * @CreateTime 2020/1/10 15:56
 */
public interface Request {

    String getServiceId();

    String getMethodName();

    String getServiceInterfaceName();

    String[] getParameterTypes();

    String[] getArguments();

    String getSignatures();
}
