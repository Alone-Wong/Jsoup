package com.meritit.customize.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * List集合操作
 * 
 * @author Lenovo
 * 
 */
public class JRedisList implements RedisOpr{

	private JedisPool pool;
	private Jedis jedis;

	public JRedisList(JedisPool pool) {
		this.pool = pool;
		this.jedis = pool.getResource();
	}

	/**
	 * java 对象可以转换成json进行存储 不带阻塞的从左侧推入数据
	 * 
	 * @param jedis
	 */
	public void lPublish(String listName, String value) {
		try {
			jedis.lpush(listName.getBytes(), value.getBytes());
		} catch (Exception e) {
			throw new RuntimeException("Redis出现错误！", e);
		}
	}

	/**
	 * 从列表集合右侧弹出数据
	 * 
	 * @param jedis
	 * @param listName
	 *            集合名词
	 * @return
	 */
	public String rPop(String listName) {
		try {
			String data = jedis.rpop(listName);
			if (data == null || data.length() <= 0) {
				return null;
			}
			return data;
		} catch (Exception e) {
			throw new RuntimeException("Redis出现错误！", e);
		}

	}

	public Long getLength(String listName) {
		return jedis.llen(listName);
	}

	public void close() {
		if (jedis != null) {
			pool.returnResourceObject(jedis);
		}
	}

	public void clearAll(String listName) {
		if(getLength(listName)>0){
			jedis.ltrim(listName, 0, 0);
		}
	}


}
