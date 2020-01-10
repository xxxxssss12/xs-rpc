package name.xs.rpc.test.proxy;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

/**
 * create by xs
 * create time:2019-07-15 16:00:50
 */
public class FirstServiceImpl implements FirstService {
    @Override
    public Integer add(int a, int b) {
        return a+b;
    }

    @Override
    public JSONObject toJson(String keyA, String valueA, String keyB, String valueB) {
        JSONObject obj = new JSONObject();
        if (StringUtils.isNotBlank(keyA) && StringUtils.isNoneBlank(valueA)) {
            obj.put(keyA, valueA);
        }
        if (StringUtils.isNotBlank(keyB) && StringUtils.isNoneBlank(valueB)) {
            obj.put(keyB, valueB);
        }
        return obj;
    }
}
