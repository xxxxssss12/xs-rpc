package org.xs.rpc.test.provider;

import org.xs.rpc.common.beans.Result;

import java.lang.reflect.InvocationTargetException;

/**
 * create by xs
 * create time:2019-12-08 22:20:44
 */
public interface Provider {

    Result invoke(String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Exception;
}
