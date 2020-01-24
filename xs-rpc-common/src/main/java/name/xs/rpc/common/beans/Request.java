package name.xs.rpc.common.beans;

/**
 * @author 熊顺
 * create time:2020-01-10 15:56:00
 */
public interface Request {

    String getServiceId();

    String getMethodName();

    String getServiceInterfaceName();

    String[] getParameterTypes();

    String[] getArguments();

    String getSignatures();
}
