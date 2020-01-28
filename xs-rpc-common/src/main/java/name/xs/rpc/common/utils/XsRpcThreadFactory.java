package name.xs.rpc.common.utils;

import name.xs.rpc.common.enums.ThreadNameEnum;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * create by xs
 * create time:2020-01-28 09:10:39
 */
public class XsRpcThreadFactory implements ThreadFactory {
    private String name;
    private AtomicInteger threadNumber = new AtomicInteger(0);

    public XsRpcThreadFactory(ThreadNameEnum nameEnum) {
        this.name = "xsrpc-" + nameEnum.getCode() + "-threadpool-";
    }
    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r);
        t.setName(this.name + threadNumber.incrementAndGet());
        return t;
    }
}
