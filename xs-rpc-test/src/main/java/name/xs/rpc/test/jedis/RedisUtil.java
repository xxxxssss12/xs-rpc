package name.xs.rpc.test.jedis;

import name.xs.rpc.common.constants.Constant;
import name.xs.rpc.common.enums.ErrorEnum;
import name.xs.rpc.common.exceptions.XsRpcException;
import redis.clients.jedis.*;
import redis.clients.jedis.params.SetParams;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author xs
 * create time:  2020/2/15 19:53
 */
public class RedisUtil {
    private RedisConfig redisConfig;
    private boolean alreadyInitialized = false;

    private JedisPool pool;

    private RedisUtil() {}
    private static RedisUtil i = new RedisUtil();
    public static RedisUtil instance() {return i; }
    public synchronized void init(RedisConfig redisConfig) {
        if (alreadyInitialized) {
            return;
        }
        this.redisConfig = redisConfig;
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(redisConfig.getMaxActive());
        config.setMaxIdle(redisConfig.getMaxIdle());
        // 如果为true，则得到的jedis实例均是可用的
        config.setTestOnBorrow(true);
        // 是否进行有效性检查
        config.setTestOnReturn(false);
        //在空闲时检查有效性, 默认false
        config.setTestWhileIdle(false);
        if (redisConfig.getPassword() == null || redisConfig.getPassword().isEmpty()) {

            pool = new JedisPool(config
                    , redisConfig.getHost()
                    , redisConfig.getPort()
                    , redisConfig.getMaxWait());
        } else {
            pool = new JedisPool(config
                    , redisConfig.getHost()
                    , redisConfig.getPort()
                    , redisConfig.getMaxWait()
                    , redisConfig.getPassword());
        }
        this.alreadyInitialized = true;
        Constant.LOG.info("RedisUtil init success: {}:{}", redisConfig.getHost(), redisConfig.getPort());
    }

    public Jedis getJedis() {
        return pool.getResource();
    }

    public double incrByFloat(String key, double value) {
        assertAlreadyInitialized();
        try (Jedis jedis = pool.getResource()) {
            return jedis.incrByFloat(key, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return -1L;
    }

    private void assertAlreadyInitialized() {
        if (!this.alreadyInitialized) {
            throw new XsRpcException(ErrorEnum.REGISTRY_01);
        }
    }

    public double incrBy(String key, long value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.incrBy(key, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return -1L;
    }

    public boolean setex(String key, int extime, String value) {
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.setex(key, extime, value);
            if (result.equals("OK")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean set(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.set(key, value);
            if (result.equals("OK")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean set(byte[] key, byte[] value) {
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.set(key, value);
            if (result.equals("OK")) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean set(String key, int expire, String value) {
        try (Jedis jedis = pool.getResource()) {
            String result = jedis.setex(key, expire, value);
            if (result.equals("OK")) {
                return true;
            }
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return false;
    }

    public String get(String key) {
        String result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.get(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return result;
    }

    public String getWithException(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.get(key);
        }
    }

    public byte[] get(byte[] key) {
        byte[] result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.get(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return result;
    }

    public Set<String> keys(String pattern) {
        Set<String> result = null;
        try (Jedis jedis = pool.getResource()) {
            result = jedis.keys(pattern);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return result;
    }


    public long del(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.del(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return 0;
    }

    public long del(byte[] key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.del(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return 0;
    }


    public boolean exists(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.exists(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return false;
    }

    public boolean hexists(String key, String field) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.hexists(key, field);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        }
        return false;
    }

    /**
     * 头入队列
     *
     */
    public Long lpush(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.lpush(key, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        }
    }

    /**
     * 未入队列
     *
     */
    public Long rpush(String key, String value) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.rpush(key, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        }
    }

    /**
     * 从左出队列
     */
    public String lpop(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.lpop(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        }
    }

    /**
     * 从右出队列
     */
    public String rpop(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.rpop(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        }
    }

    /**
     * Return the length of the list stored
     */
    public Long llen(String key) {
        try (Jedis jedis = pool.getResource()) {
            return jedis.llen(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        }
    }

    /**
     * 有序集合
     */
    public void addSet(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            Long score = jedis.zcard(key);
            jedis.zadd(key, ++score, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
    }

    /**
     * 有序集合
     */
    public void addSet(String key, Long score, String value) {
        Jedis jedis = pool.getResource();
        try {
            jedis.zadd(key, score, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
    }

    /**
     * 有序集合
     */
    public long zadd(String key, Long score, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.zadd(key, score, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1l;
    }

    /**
     * 有序集合
     */
    public long zincrby(String key, Long score, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.zincrby(key, score, value).longValue();
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1l;
    }

    /**
     * 有序集合
     */
    public Long zrevrank(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.zrevrank(key, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1l;
    }

    /**
     * 得到有序集合
     */
    public Set<String> getSet(String key) {
        Jedis jedis = pool.getResource();
        try {

            Set<String> sets = jedis.zrange(key, 0, -1);
            return sets;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public String loadScript(String script) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.scriptLoad(script);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 分页得到有序集合
     */
    public Set<String> zrange(String key, int size) {
        Jedis jedis = pool.getResource();
        try {
            Set<String> sets = jedis.zrange(key, 0, size);
            return sets;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 分页得到有序集合
     */
    public Set<String> zrange(String key, long start, int size) {
        Jedis jedis = pool.getResource();
        try {
            Set<String> sets = jedis.zrange(key, start, size);
            return sets;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 分页得到有序集合(索引倒序)
     */
    public Set<String> zrevrange(String key, long start, int size) {
        Jedis jedis = pool.getResource();
        try {
            Set<String> sets = jedis.zrevrange(key, start, size);
            return sets;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    /**
     * 分页得到有序集合(score倒序)
     */
    public Set<String> zrevrangeByScore(String key, int start, int size) {
        Jedis jedis = pool.getResource();
        try {
            Set<String> sets = jedis.zrevrangeByScore(key, Double.MAX_VALUE, Double.MIN_VALUE, start, size);
            return sets;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public Long zcard(String key) {
        Jedis jedis = pool.getResource();
        try {
            Long total = jedis.zcard(key);
            return total;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return 0L;
        } finally {
            jedis.close();
        }
    }

    /**
     * 获取成员分数
     */
    public Double zscore(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Double score = jedis.zscore(key, member);
            return score;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return 0.0;
        } finally {
            if (jedis != null)
                jedis.close();
        }
    }

    /**
     * 获取排名分数分数
     */
    public Set<Tuple> zrevrangeWithScores(String key, long start, long end) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Set<Tuple> score = jedis.zrevrangeWithScores(key, start,end);
            return score;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null)
                jedis.close();
        }
        return null;
    }

    /**
     * 删除指定的元素
     */
    public Long zrem(String key, String... values) {
        Jedis jedis = pool.getResource();
        try {
            Long count = jedis.zrem(key, values);
            return count;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return 0l;
        } finally {
            jedis.close();
        }
    }

    /**
     * 统计set中的数据个数
     */
    public Long zcount(String key, Double min, Double max) {
        Jedis jedis = pool.getResource();
        try {
            Long cnt = jedis.zcount(key, min, max);
            return cnt;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public Map<String, String> hget(String key) {
        Jedis jedis = pool.getResource();

        Map<String, String> map = null;
        try {
            map = jedis.hgetAll(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return map;
    }

    public Set<String> hKeys(String mapKey) {
        Jedis jedis = pool.getResource();
        Set<String> set = null;
        try {
            set = jedis.hkeys(mapKey);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return set;
    }
    public void hmset(String key, Map<String, String> values) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hmset(key, values);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
    }

    public void hmset(String key, Map<String, String> values, int expireSeconds) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hmset(key, values);
            jedis.expire(key, expireSeconds);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
    }


    public boolean hset(String key, String field, String fieldVal) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hset(key, field, fieldVal);
            return true;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return false;
    }


    public boolean hset(String key, String field, String fieldVal,int expire) {
        Jedis jedis = pool.getResource();
        try {
            jedis.hset(key, field, fieldVal);
            jedis.expire(key,expire);
            return true;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return false;
    }

    public boolean hset(String key, Map<String, String> map) {
        Jedis jedis = pool.getResource();
        try {
            for (Map.Entry<String, String> e : map.entrySet()) {
                jedis.hset(key, e.getKey(), e.getValue());
            }
            return true;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return false;
    }

    public boolean hset(String key, Map<String, String> map, int expire) {
        Jedis jedis = pool.getResource();
        try {
            for (Map.Entry<String, String> e : map.entrySet()) {
                jedis.hset(key, e.getKey(), e.getValue());
            }
            jedis.expire(key, expire);
            return true;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return false;
    }

    /**
     * 批量更新redis
     *
     * s
     * @param maps
     * 
     */
    public boolean batchHset(List<String> keys, List<Map<String, String>> maps) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            Pipeline pipeline = jedis.pipelined();
            int index = 0;
            for (String key : keys) {
                pipeline.hmset(key, maps.get(index++)); //key -map
            }
            if (index % keys.size() == 0) {
                pipeline.sync();
            }
            return true;
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null) {
                jedis.disconnect();
                jedis.close();
            }
        }
        return false;
    }

    public long incr(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.incr(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public long hincr(String key, String field) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hincrBy(key, field, 1);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public long hincrby(String key, String field, int num) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hincrBy(key, field, num);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public double hincrbyFloat(String key, String field, double num) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hincrByFloat(key, field, num);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1D;
    }

    public long decr(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.decr(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public long decrby(String key, Long value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.decrBy(key, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public long setnx(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.setnx(key, "1");
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public long setnx(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.setnx(key, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public long setnx(String key, int expire, String value) {
        Jedis jedis = pool.getResource();
        try {
            SetParams setParams = new SetParams();
            setParams.ex(expire);
            setParams.nx();
            if ("OK".equals(jedis.set(key, value, setParams))) {
                return 1;
            }
            return 0;

        } catch (Exception e) {
            Constant.LOG.error("redis异常:", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public void expire(String key, int second) {
        Jedis jedis = pool.getResource();
        try {
            jedis.expire(key, second);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
    }

    public void expireat(String key, long unixtime){
        Jedis jedis = pool.getResource();
        try {
            jedis.expireAt(key,unixtime);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
    }

    public long sadd(String key, String... members) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.sadd(key, members);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public long scard(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.scard(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1L;
    }

    public Set<String> smembers(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.smembers(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return null;
    }

    public String spop(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.spop(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return null;
    }

    /**
     * 
     * @param count 返回个数
     * 
     */
    public Set<String> spop(String key, int count) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.spop(key, count);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return null;
    }

    public long srem(String redisKey, String... strings) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.srem(redisKey, strings);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            jedis.close();
        }
        return -1l;
    }

    public List<String> hmget(String key, String... fields) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hmget(key, fields);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public String hget(String key, String field) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hget(key, field);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }

    public List<String> hVals(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.hvals(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }
    public Long ttl(String key) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.ttl(key);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return -2L;
        } finally {
            jedis.close();
        }
    }

    public List<String> lrange(String key, Long start, Long end) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.lrange(key, start, end);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return null;
        } finally {
            jedis.close();
        }
    }


    public long ldel(String key, String value) {
        Jedis jedis = pool.getResource();
        try {
            return jedis.lrem(key, 0, value);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
            return 0L;
        } finally {
            jedis.close();
        }
    }

    public Object eval(String script, List<String> keys, List<String> args) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.eval(script, keys, args);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
        return null;
    }

    public Long hlen(String mapKey) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hlen(mapKey);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
        return null;
    }

    public String srandMember(String key) {
        List<String> list = srandMember(key, 1);
        if (list == null || list.isEmpty()) return null;
        else return list.get(1);
    }

    public List<String> srandMember(String key, int count) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.srandmember(key, count);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;
    }

    public Object evalsha(String sha, List<String> keys, List<String> args) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.evalsha(sha, keys, args);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
        return null;
    }

    /**
     * 查看value值是否存在
     */
    public boolean sismember(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.sismember(key, member);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    public long hdel(String key, String field) {
        Jedis jedis = null;
        try {
            jedis = pool.getResource();
            return jedis.hdel(key, field);
        } catch (Exception e) {
            Constant.LOG.error("redis error", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0;
    }

    public RedisConfig getRedisConfig() {
        return redisConfig;
    }

    public void setRedisConfig(RedisConfig redisConfig) {
        this.redisConfig = redisConfig;
    }
}
