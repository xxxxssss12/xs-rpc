package name.xs.rpc.common.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 熊顺
 * @CreateTime 2019/12/11 9:50
 */
public class Constant {
    public static final String LOGGER_NAME = "XSRPC";

    public static final Logger LOG = LoggerFactory.getLogger(LOGGER_NAME);

    public static final int CPU_NUM = Runtime.getRuntime().availableProcessors();
}
