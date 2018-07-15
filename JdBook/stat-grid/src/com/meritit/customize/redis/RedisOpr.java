package com.meritit.customize.redis;

public interface RedisOpr {

	public Long getLength(String listName);
	public void close();
	public void clearAll(String listName);
}
