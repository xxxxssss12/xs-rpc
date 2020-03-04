package name.xs.rpc.common;

import org.slf4j.Logger;

/**
 * @author xs
 * create time:  2020/3/4, 0004 9:19
 */
public class XsRpcLogger {

    private Logger log;

    public XsRpcLogger(Logger log) {
        this.log = log;
    }


    private String concat(Class<?> clazz, String msg) {
        return "[" + getClassName(clazz) + "] " + msg;
    }

    private String getClassName(Class<?> clazz) {
        return clazz == null ? " " : clazz.getName();
    }

    public void error(Class<?> clazz, String msg) {
        log.error(concat(clazz, msg));
    }

    public void error(Class<?> clazz, String msg, Throwable e) {
        log.error(concat(clazz, msg), e);
    }

    public void info(Class<?> clazz, String msg) {
        log.info(concat(clazz, msg));
    }

    public void info(Class<?> clazz, String msg, Object... objs) {
        log.info(concat(clazz, msg), objs);
    }

    public void debug(Class<?> clazz, String msg) {
        log.debug(concat(clazz, msg));
    }
    public void debug(Class<?> clazz, String msg, Object... objs) {
        log.debug(concat(clazz, msg), objs);
    }

    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }
}
