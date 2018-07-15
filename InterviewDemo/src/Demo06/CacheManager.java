package Demo06;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CacheManager {
	// 缓存
	@SuppressWarnings("unchecked")
	private static HashMap cacheMap = new HashMap();

	// 请求几次 去要新的
	private static int num = 10;// 执行次数-----------0表示为开启

	private static int count = 0;// 计数-----和记录当前的秒数

	// 单实例构造方法
	private CacheManager() {
		super();
	}

	// 得到缓存

	public synchronized static Object getCache(String key) {
		return (Object) cacheMap.get(key);
	}

	// 判断是否存在一个缓存

	public synchronized static boolean hasCache(String key) {
		return cacheMap.containsKey(key);
	}

	// 清除所有缓存
	public synchronized static void clearAll() {
		cacheMap.clear();
	}

	// 清除指定的缓存
	public synchronized static void clearOnly(String key) {
		cacheMap.remove(key);
	}

	// 载入缓存
	public synchronized static void putCache(String key, Object obj) {
		cacheMap.put(key, obj);
	}

	// 判断缓存是否终止
	public static boolean cacheExpired() {
		System.out.println("次数" + count);
		if (num == 0) {
			return true;// 终止使用缓存
		} else {
			if (count >= num) {
				count = 0;// 重置count
				return true;
			}
			count++;
			System.out.println("---------------------------------------请求缓存+" + count + "次数");
			return false;
		}

	}

	// 获取缓存中的大小
	public static int getCacheSize() {
		return cacheMap.size();
	}

	// 获取缓存对象中的所有键值名称

	public static ArrayList<String> getCacheAllkey() {
		ArrayList a = new ArrayList();
		try {
			Iterator i = cacheMap.entrySet().iterator();
			while (i.hasNext()) {
				java.util.Map.Entry entry = (java.util.Map.Entry) i.next();
				a.add((String) entry.getKey());
			}
		} catch (Exception ex) {
		}
		return a;
	}

}
