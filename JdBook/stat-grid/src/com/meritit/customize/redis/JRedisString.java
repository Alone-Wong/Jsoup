package com.meritit.customize.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JRedisString implements RedisOpr {

	private JedisPool pool;
	private Jedis jedis;
	

	public JRedisString(JedisPool pool) {
		this.pool = pool;
		this.jedis = pool.getResource();
	}
	
	public void set(String key, String value){
		jedis.select(0);
		jedis.set(key, value);
	}
	
	public void set(int index, String key, String value){
		jedis.select(index);
		jedis.set(key, value);
	}
	
	
	public Object getValue(String key){
		jedis.select(0);
		return jedis.get(key);
	}
	
	public Object getValue(int index, String key){
		jedis.select(index);
		return jedis.get(key);
	}
	
	public boolean exists(String key){
		jedis.select(0);
		return jedis.exists(key);
	}
	
	public boolean exists(int index, String key){
		jedis.select(index);
		return jedis.exists(key);
	}
	
	public void expire(String key, int seconds){
		jedis.select(0);
		jedis.expire(key, seconds);
	}
	
	public void expire(int index, String key, int seconds){
		jedis.select(index);
		jedis.expire(key, seconds);
	}
	
	@Override
	public Long getLength(String listName) {
		
		return null;
	}

	@Override
	public void close() {
		if (jedis != null) {
			pool.returnResourceObject(jedis);
		}
	}

	@Override
	public void clearAll(String listName) {
	

	}

}
