package name.xs.registry.jedis;

import name.xs.rpc.common.constants.Constant;
import redis.clients.jedis.JedisPubSub;

/**
 * @author xs
 * create time:  2020/2/15 20:19
 */
public class ProviderChangeJedisPubSub extends JedisPubSub {

    @Override
    public void onMessage(String channel, String message) {
        Constant.LOG.info("{} channel={}, message={}", "MyJedisPubSub", channel, message);
    }
}
