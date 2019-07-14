package org.xs.rpc.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * create by xs
 * create time:2019-07-09 16:28:23
 */
public class ByteArrUtils {

    public static final byte ZERO_ASCII = 0x30;
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

    public static List<Byte> strToBytes(String str) {
        try {
            byte[] bytes = str.getBytes("utf-8");
            List<Byte> byteList = new ArrayList<>(bytes.length);
            for (byte bt : bytes) {
                byteList.add(bt);
            }
            return byteList;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean equals(byte[] arr1, byte[] arr2) {
        if (arr1 == null || arr2 == null) {
            return false;
        }
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i=0; i<arr1.length; i++) {
            if (arr1[i] != arr2[i]) {
                return false;
            }
        }
        return true;
    }
}
