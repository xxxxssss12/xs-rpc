package name.xs.rpc.common.exceptions;

import name.xs.rpc.common.beans.XsRpcExceptionSerialize;
import name.xs.rpc.common.enums.ErrorEnum;

/**
 * create by xs
 * create time:2019-12-08 22:27:15
 */
public class XsRpcException extends RuntimeException {

    public XsRpcException(ErrorEnum error) {
        super(error.getCode() + ": " + error.getDesc());
    }

    public XsRpcException(ErrorEnum error, XsRpcExceptionSerialize remoteExceptionInfo) {
        super(error.getCode() + ": " + error.getDesc()
                + (remoteExceptionInfo == null ? "" : ";remoteExceptionClass: " + remoteExceptionInfo.getClassName())
                + (remoteExceptionInfo == null ? "" : "; remoteExceptionMsg: " + remoteExceptionInfo.getMessage()));
    }
}
