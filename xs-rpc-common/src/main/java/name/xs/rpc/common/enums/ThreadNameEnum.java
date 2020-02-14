package name.xs.rpc.common.enums;

/**
 * 线程名
 *
 * @author xs
 * create time:2020-01-28 09:22:41
 */
public enum ThreadNameEnum {
    NETTY_CLIENT("nettyClient", "netty客户端"),
    CLIENT_REQUEST("clientReq", "客户端请求"),

    ;
    private String code;
    private String desc;

    ThreadNameEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public String getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
