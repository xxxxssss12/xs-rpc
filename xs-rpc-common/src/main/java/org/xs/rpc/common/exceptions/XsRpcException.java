package org.xs.rpc.common.exceptions;

import org.xs.rpc.common.ErrorEnum;

/**
 * create by xs
 * create time:2019-12-08 22:27:15
 */
public class XsRpcException extends RuntimeException {

    public XsRpcException(ErrorEnum error) {
        super(error.getDesc());
    }
}
