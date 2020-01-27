package name.xs.rpc.common;

/**
 * create by xs
 * create time:2019-12-08 22:27:27
 */
public enum ErrorEnum {
    SUCCESS("000000", "success"),
    PROXY_01("000001", "can not find instance"),// 没找到实例
    NETTY_01("001001", "netty client start error"), // netty客户端启动异常
    ;
    private String code;
    private String desc;

    ErrorEnum(String code, String desc) {
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
