package name.xs.rpc.common.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import name.xs.rpc.common.constants.Constant;
import org.yaml.snakeyaml.Yaml;


/**
 * @author 熊顺
 * @CreateTime 2019/12/11 9:33
 */
public class YamlUtils {
    private static final Logger log = LoggerFactory.getLogger(Constant.LOGGER_NAME);

    private static Yaml yaml = new Yaml();

    public static Properties load(File file) {
        if (file == null) {
            log.error("YAML load file error!file is null");
            return null;
        }
        try (InputStream is = new FileInputStream(file)) {
            return load(is);
        } catch (Exception e) {
            log.error("YAML load file error!fileName=" + file.getName(), e);
        }
        return null;
    }

    public static Properties load(InputStream is) {
        Map<String, Object> map = yaml.load(is);
        if (map == null || map.isEmpty()) {
            return null;
        }
        Properties properties = new Properties();
        dfsLoad(map, "", properties);
        return properties;
    }

    private static void dfsLoad(Map<String, Object> map, String key, Properties properties) {
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String tmpKey = key == null || key.length() == 0 ? entry.getKey() : key + "." + entry.getKey();
            if (entry.getValue() instanceof String) {
                properties.put(tmpKey, entry.getValue());
            } else {
                dfsLoad((Map<String, Object>) entry.getValue(), tmpKey, properties);
            }
        }
    }
}