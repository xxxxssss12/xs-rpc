package org.xs.rpc.protocol;

/**
 * create by xs
 * create time:2019-07-2019-07-09 16:07:23
 */
public interface Decoder {

    Message decode(byte[] data);
}
