package org.xs.rpc.protocol.xsp;

import org.xs.rpc.protocol.Message;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * create by xs
 * create time:2019-07-09 16:05:41
 */
public class XspMessage implements Message {
    private String data;
    private XspHeader header;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public XspMessage(XspHeader header) {
        this.header = header;
    }

    public XspHeader getHeader() {
        return header;
    }

    public void setHeader(XspHeader header) {
        this.header = header;
    }

    public XspMessage(XspHeader header, String data) {
        this.header = header;
        this.data = data;
    }

    public byte[] toByte() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        out.write(header.getTag());
        out.write(header.getEncode());
        out.write(header.getEncrypt());
        out.write(header.getExtend1());
        out.write(header.getExtend2());
        byte[] bb = new byte[32];
        byte[] bb2 = header.getSessionid().getBytes();
        for (int i = 0; i < bb2.length; i++) {
            bb[i] = bb2[i];
        }

        try {
            out.write(bb);

            byte[] bbb = data.getBytes("UTF-8");
            out.write(intToBytes2(bbb.length));
            out.write(intToBytes2(header.getCammand()));
            out.write(bbb);
            out.write('\n');
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    /**
     * 大端
     * @param newint
     * @return
     */
    public static byte[] intToByte(int newint) {
        byte[] intbyte = new byte[4];
        intbyte[3] = (byte) ((newint >> 24) & 0xFF);
        intbyte[2] = (byte) ((newint >> 16) & 0xFF);
        intbyte[1] = (byte) ((newint >> 8) & 0xFF);
        intbyte[0] = (byte) (newint & 0xFF);
        return intbyte;
    }

    public static int bytesToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF) | ((src[offset + 1] & 0xFF) << 8) | ((src[offset + 2] & 0xFF) << 16) | ((src[offset + 3] & 0xFF) << 24));
        return value;
    }

    /**
     * 小端
     * @param value
     * @return
     */
    public static byte[] intToBytes2(int value) {
        byte[] src = new byte[4];
        src[0] = (byte) ((value >> 24) & 0xFF);
        src[1] = (byte) ((value >> 16) & 0xFF);
        src[2] = (byte) ((value >> 8) & 0xFF);
        src[3] = (byte) (value & 0xFF);
        return src;
    }
}
