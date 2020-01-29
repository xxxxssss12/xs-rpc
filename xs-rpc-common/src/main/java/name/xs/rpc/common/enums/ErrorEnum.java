package name.xs.rpc.common.enums;

/**
 * create by xs
 * create time:2019-12-08 22:27:27
 */
public enum ErrorEnum {
    SUCCESS("000000", "success"),
    PROXY_01("000001", "can not find instance"),// 没找到实例
    PROXY_02("000002", "wrong method return"),// 返回数据错误
    PROXY_03("000003", "remote request exception"),// 远程调用异常
    NETTY_01("001001", "netty client start error"), // netty客户端启动异常
    NETTY_02("001002", "netty client has bean start"), // netty客户端已经启动
    NETTY_03("001003", "request thread pool full"), // 请求线程池已满
    NETTY_04("001004", "request interrupt exception"), // 请求中断
    NETTY_05("001005", "request execution exception"), // 请求执行异常
    NETTY_06("001006", "request timeout exception"), // 请求超时
    NETTY_07("001007", "request unknown exception"), // 未知异常
    PROTOCOL_01("002001", "sessionId length error"), // sessionId长度不正确
    SERVER_01("003001", "client request format wrong"), // 客户端请求格式错误
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
