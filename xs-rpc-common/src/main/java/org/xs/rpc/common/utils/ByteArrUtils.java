package org.xs.rpc.common.utils;

/**
 * create by xs
 * create time:2019-07-09 16:28:23
 */
public class ByteArrUtils {

    public static byte[] getBytes(byte[] data, int start, int length) {
        byte[] arr = new byte[length];
        for (int i=start; i< length + start; i++) {
            arr[i-start] = data[i];
        }
        return arr;
    }

    /**
     * 大端法, 四字节byte数组转int
     *
     * @param data
     * @param index
     * @return
     */
    public static int readInt(byte[] data, int index) {
        // TODO
        return -1;
    }
}
