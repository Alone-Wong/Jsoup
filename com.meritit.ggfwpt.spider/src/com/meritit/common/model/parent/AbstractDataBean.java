package com.meritit.common.model.parent;

import java.util.HashMap;
import java.util.Map;

/**
 * 封装数据实体的抽象类
 * 
 * 1.在init中设置任务id和规则id
 * 2.封装的数据必须含有url(数据链接),id(UUID随机码),inserttime(毫秒值)
 * 
 * @author renyi
 *
 * @param <K>
 * @param <V>
 */
public abstract class AbstractDataBean<K, V> {

	private Map<K, V> dataMap = new HashMap<K, V>();
	
	protected AbstractDataBean(){
		init();
	}
	
	/**
	 * 抽象接口,实现数据的初始化
	 * 需要设置taskid, ruleid
	 */
	public abstract void init();
	
	/**
	 * 设置元素键、值
	 * @param key
	 * @param value
	 */
	public void setValue(K key, V value){
		dataMap.put(key, value);
	}
	
	/**
	 * 根据键获取元素值
	 * @param key
	 * @return
	 */
	public V getValue(K key){
		return dataMap.get(key);
	}
	
	/**
	 * 获取数据结果
	 * @return
	 */
	public Map<K, V> getResult(){
		
		if(!dataMap.containsKey("taskid")){
			throw new NullPointerException("taskid不能为空，请在init方法中进行设置！");
		}
		
		if(!dataMap.containsKey("ruleid")){
			throw new NullPointerException("ruleid不能为空，请在init方法中进行设置！");
		}
		
		if(!dataMap.containsKey("id")){
			throw new NullPointerException("id不能为空，请设置，为UUID随机码！");
		}
		
		if(!dataMap.containsKey("url")){
			throw new NullPointerException("url不能为空，请设置！");
		}
		
		if(!dataMap.containsKey("inserttime")){
			throw new NullPointerException("inserttime不能为空，请设置，为毫秒值！");
		}
		
		return dataMap;
	}
}
