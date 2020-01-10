package name.xs.rpc.common.beans;

/**
 * create by xs
 * create time:2019-12-08 22:46:16
 */
public class CommonResult implements Result {

    private Object value;
    private XsRpcExceptionSerialize exception;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public XsRpcExceptionSerialize getException() {
        return exception;
    }

    @Override
    public void setException(XsRpcExceptionSerialize exception) {
        this.exception = exception;
    }

    @Override
    public boolean hasException() {
        return exception != null;
    }
}
