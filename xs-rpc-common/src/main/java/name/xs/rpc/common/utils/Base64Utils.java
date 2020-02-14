package name.xs.rpc.common.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

/**
 * @author xs
 * create time: 2019-07-13 15:13
 */
public class Base64Utils {
    private static final Base64 base64 = new Base64();

    public static byte[] encode(String data) throws UnsupportedEncodingException {
        return encode(data, "utf-8");
    }
    public static byte[] encode(String data, String charset) throws UnsupportedEncodingException {
        byte[] bytes = data.getBytes(charset);
        return base64.encode(bytes);
    }

    public static String decode(byte[] data) throws UnsupportedEncodingException {
        return decode(data, "utf-8");
    }
    public static String decode(byte[] data, String charset) throws UnsupportedEncodingException {
        return new String(base64.decode(data), charset);
    }
}
