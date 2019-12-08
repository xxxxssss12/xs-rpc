package org.xs.rpc.common.beans;

/**
 * create by xs
 * create time:2019-12-08 22:46:16
 */
public class CommonResult implements Result {

    private Object value;
    private Throwable exception;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    @Override
    public void setException(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public boolean hasException() {
        return exception != null;
    }
}
