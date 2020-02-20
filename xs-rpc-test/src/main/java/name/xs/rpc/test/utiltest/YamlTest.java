package name.xs.rpc.test.utiltest;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.utils.YamlUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author xs
 * @CreateTime 2019/12/11 9:45
 */
public class YamlTest {
    private static Logger log = LoggerFactory.getLogger(Constant.LOGGER_NAME);
    public static void main(String[] args) {
        Properties load = YamlUtils.load(YamlTest.class.getResourceAsStream("/test.yml"));
        log.info("result={}", JSON.toJSONString(load, SerializerFeature.PrettyFormat));
    }
}
