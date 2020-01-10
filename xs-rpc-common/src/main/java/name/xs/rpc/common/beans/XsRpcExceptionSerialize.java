package name.xs.rpc.common.beans;

import java.io.Serializable;

/**
 * exception serializable
 *
 * create by xs
 * create time:2020/1/9 17:27
 */
public class XsRpcExceptionSerialize implements Serializable {
    private String className;
    private String message;

    public XsRpcExceptionSerialize() {

    }

    public XsRpcExceptionSerialize(Throwable e) {
        this.className = e.getClass().getName();
        this.message = e.getMessage();
    }
    public XsRpcExceptionSerialize(String className, String message) {
        this.className = className;
        this.message = message;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
