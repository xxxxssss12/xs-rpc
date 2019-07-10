package org.xs.rpc.common.utils;

/**
 * create by xs
 * create time:2019-07-09 16:28:23
 */
public class ByteArrUtils {

    public static byte[] getBytes(byte[] data, int start, int length) {
        byte[] arr = new byte[length];
        for (int i = start; i < length + start; i++) {
            arr[i - start] = data[i];
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
        int value = (int) (
                ((data[index] & 0xFF) << 24)
                        | ((data[index + 1] & 0xFF) << 16)
                        | ((data[index + 2] & 0xFF) << 8)
                        | (data[index + 3] & 0xFF)
        );
        return -1;
    }

    public static Byte[] intToBytes(int num) {
        Byte[] bytes = new Byte[4];
        bytes[3] = (byte) (num & 0xFF);
        bytes[2] = (byte) ((num >> 8) & 0xFF);
        bytes[1] = (byte) ((num >> 16) & 0xFF);
        bytes[0] = (byte) ((num >> 24) & 0xFF);
        return bytes;
    }
}
