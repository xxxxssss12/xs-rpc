package org.xs.rpc.protocol.xsp;

/**
 * 常量定义
 *
 * create by xs
 * create time:2019-07-09 16:17:39
 */
public class XspConstant {
    /**
     * 一个包最大32k
     */
    public static final int MAX_LENGTH = 32767;
    /**
     * 包头长度
     **/
    public static final int HEAD_LENGHT = 45;
    /**
     * 标志头
     **/
    public static final byte PACKAGE_TAG = 0x01;
    /**
     * 编码格式
     */
    public static final String CHARSET = "UTF-8";

    public static enum PackageDef {
        TAG(0, 1),
        ENCODE(1, 1),
        ENCRYPT(2,1),
        EXTEND1(3,1),
        EXTEND2(4,1),
        SESSIONID(5,32),
        LENGTH(37,4),
        COMMAND(41, 4),
        DATA(45, -1)
        ;
        private int index;  // 位置
        private int offset; // 偏移量
        PackageDef(int index, int offset) {
            this.index = index;
            this.offset = offset;
        }

        public int getIndex() {
            return index;
        }

        public int getOffset() {
            return offset;
        }
    }
}
