package name.xs.rpc.test.jedis;

/**
 * @author xs
 * create time:  2020/2/15 20:17
 */
public class SubscribeTest {

    public static void main(String[] args) {
        RedisConfig config = new RedisConfig("localhost", 6379, 1, 1, null, 1000);
        RedisUtil.instance().init(config);
        RedisUtil.instance().getJedis().subscribe(new MyJedisPubSub(), "mychannel");// 阻塞方法，需要新开线程处理
    }
}
