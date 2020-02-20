package name.xs.rpc.common.exceptions;

import name.xs.rpc.common.beans.XsRpcExceptionSerialize;
import name.xs.rpc.common.enums.ErrorEnum;

/**
 * @author xs
 * create time:2019-12-08 22:27:15
 */
public class XsRpcException extends RuntimeException {

    public XsRpcException(ErrorEnum error) {
        super(getMsg(error, null));
    }

    public XsRpcException(ErrorEnum error, String... args) {
        super(getMsg(error, args));
    }

    private static String getMsg(ErrorEnum error, String... args) {
        StringBuilder sb = new StringBuilder();
        String desc = error.getDesc();
        sb.append(error.getCode()).append(": ");
        if (args != null) {
            sb.append(String.format(desc, args));
        } else {
            sb.append(desc);
        }
        return sb.toString();
    }
}
