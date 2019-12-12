package org.xs.rpc.test.utiltest;

import java.io.File;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xs.rpc.common.constants.Constant;
import org.xs.rpc.common.utils.YamlUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author 熊顺
 * @CreateTime 2019/12/11 9:45
 */
public class YamlTest {
    private static Logger log = LoggerFactory.getLogger(Constant.LOGGER_NAME);
    public static void main(String[] args) {
        File file = new File("D:\\Workspaces\\IdeaProjects\\xs-rpc\\xs-rpc-test\\src\\resources\\test.yml");
        Properties load = YamlUtils.load(file);
        System.out.println(JSON.toJSONString(load));
        log.info("result={}", JSON.toJSONString(load, SerializerFeature.PrettyFormat));
    }
}
