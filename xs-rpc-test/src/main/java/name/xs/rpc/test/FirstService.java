package name.xs.rpc.test;

import com.alibaba.fastjson.JSONObject;

/**
 * create by xs
 * create time:2019-07-15 15:53:42
 */
public interface FirstService {

    Integer add(int a, int b);

    JSONObject toJson(String keyA, String valueA, String keyB, String valueB);
}
