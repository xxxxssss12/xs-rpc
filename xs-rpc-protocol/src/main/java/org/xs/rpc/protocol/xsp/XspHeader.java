package org.xs.rpc.protocol.xsp;

import org.xs.rpc.protocol.Header;

/**
 * 自定义协议头
 *
 * create by xs
 * create time:2019-07-09 16:04:38
 */
public class XspHeader implements Header {
    private byte tag;
    /*  编码*/
    private byte encode;
    /*加密*/
    private byte encrypt;
    /*其他字段*/
    private byte extend1;
    /*其他2*/
    private byte extend2;
    /*会话id*/
    private String sessionid;
    /*包的长度, 是base64编码后的包长度*/
    private int length = 1024;
    /*命令*/
    private int cammand;

    public XspHeader() {

    }

    public XspHeader(String sessionid) {
        this.encode = 0;
        this.encrypt = 0;
        this.sessionid = sessionid;
    }

    public XspHeader(byte tag, byte encode, byte encrypt, byte extend1, byte extend2, String sessionid, int length, int cammand) {
        this.tag = tag;
        this.encode = encode;
        this.encrypt = encrypt;
        this.extend1 = extend1;
        this.extend2 = extend2;
        this.sessionid = sessionid;
        this.length = length;
        this.cammand = cammand;
    }

    @Override
    public String toString() {
        return "XspHeader{" +
                "tag=" + tag +
                ", encode=" + encode +
                ", encrypt=" + encrypt +
                ", extend1=" + extend1 +
                ", extend2=" + extend2 +
                ", sessionid='" + sessionid + '\'' +
                ", length=" + length +
                ", cammand=" + cammand +
                '}';
    }

    public byte getTag() {
        return tag;
    }

    public void setTag(byte tag) {
        this.tag = tag;
    }

    public byte getEncode() {
        return encode;
    }

    public void setEncode(byte encode) {
        this.encode = encode;
    }

    public byte getEncrypt() {
        return encrypt;
    }

    public void setEncrypt(byte encrypt) {
        this.encrypt = encrypt;
    }

    public byte getExtend1() {
        return extend1;
    }

    public void setExtend1(byte extend1) {
        this.extend1 = extend1;
    }

    public byte getExtend2() {
        return extend2;
    }

    public void setExtend2(byte extend2) {
        this.extend2 = extend2;
    }

    public String getSessionid() {
        return sessionid;
    }

    public void setSessionid(String sessionid) {
        this.sessionid = sessionid;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getCammand() {
        return cammand;
    }

    public void setCammand(int cammand) {
        this.cammand = cammand;
    }
}
