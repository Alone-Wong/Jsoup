package com.meritit.customize.redis;

import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * List集合操作
 * 
 * @author renyi
 * 
 */
public class JRedisSet implements RedisOpr{

	private JedisPool pool;
	private Jedis jedis;

	public JRedisSet(JedisPool pool) {
		this.pool = pool;
		this.jedis = pool.getResource();
	}

	public void close() {
		if (jedis != null) {
			pool.returnResourceObject(jedis);
		}
	}

	public void clearAll(String listName) {
		jedis.select(0);
		if(getLength(listName)>0){
			jedis.ltrim(listName, 0, 0);
		}
	}

	@Override
	public Long getLength(String setName) {
		jedis.select(0);
		return jedis.scard(setName);
	}

	/**
	 * 使用set集合加入redis
	 * @param setName 键
	 * @param member 值
	 */
	public void sadd(String setName, String member){
		jedis.select(0);
		jedis.sadd(setName, member);
	}
	
	/**
	 * 使用set集合加入redis
	 * @param index 库编号
	 * @param setName
	 * @param member
	 */
	public void sadd(int index, String setName, String member){
		jedis.select(index);
		jedis.sadd(setName, member);
	}
	
	/**
	 * 取出redis集合中的数据
	 * @param setName 键
	 * @return
	 */
	public Set<String> smembers(String setName){
		jedis.select(0);
		return jedis.smembers(setName);
	}
	
	public Set<String> smembers(int index, String setName){
		jedis.select(index);
		return jedis.smembers(setName);
	}
	
	/**
	 * 判断元素是否存在于setName集合中
	 * @param setName 键
	 * @param member 元素
	 * @return
	 */
	public boolean sismember(String setName, String member){
		jedis.select(0);
		return jedis.sismember(setName, member);
	}
	
	public boolean sismember(int index, String setName, String member){
		jedis.select(index);
		return jedis.sismember(setName, member);
	}
	
	/**
	 * 设置过期时间
	 * @param setName 键
	 * @param seconds 秒
	 * @return
	 */
	public long expire(String setName, int seconds){
		jedis.select(0);
		return jedis.expire(setName, seconds);
	}
	
	public long expire(int index, String setName, int seconds){
		jedis.select(index);
		return jedis.expire(setName, seconds);
	}
	
	public long ttl(String setName){
		jedis.select(0);
		return jedis.ttl(setName);
	}
}
