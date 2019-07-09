package org.xs.rpc.protocol;

/**
 * create by xs
 * create time:2019-07-09 16:07:15
 */
public interface Encoder {

    byte[] encode(Message msg);
}
