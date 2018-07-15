package com.meritit.customize.redis;

import org.apache.commons.lang.StringUtils;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * jredis操作帮助类
 * 
 * @author Lenovo
 * 
 */
public class JRedisUtil {
	private JedisPool pool = null;
	private final String redisHost = "191.168.1.32";//本地测试
//	private final String redisHost = "121.40.231.204";
	private final int redisPort = 6379;
	private final int timeout = 30 * 1000;
	private final String redisPassword = "";

	private JRedisUtil() {
		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// 控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
		config.setMaxIdle(200);
		// 表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
		config.setMaxWaitMillis(1000 * 30);
		// 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
		config.setTestOnBorrow(true);

		if (StringUtils.isEmpty(redisPassword)) {
			pool = new JedisPool(config, redisHost, redisPort, timeout);
		} else {
			pool = new JedisPool(config, redisHost, redisPort, timeout,
					redisPassword);
		}

	}

	public static JRedisUtil getInstance() {
		return SingletonHolder.instance;
	}

	private static class SingletonHolder {
		/**
		 * 静态初始化器，由JVM来保证线程安全
		 */
		private static JRedisUtil instance = new JRedisUtil();
	}

	public RedisOpr getJredisObject(String type) {
		if (type == "list") {
			return new JRedisList(pool);
		}
		
		if(type == "set"){
			return new JRedisSet(pool);
		}
		
		if(type == "string"){
			return new JRedisString(pool);
		}
		return null;
	}

	public static void main(String args[]) {
		RedisOpr object = JRedisUtil.getInstance().getJredisObject("set");
		JRedisSet set = (JRedisSet)object;
		System.out.println(set.smembers(1, "2017-03-17"));
		System.out.println(set.smembers("2017-03-17"));
	}
}
