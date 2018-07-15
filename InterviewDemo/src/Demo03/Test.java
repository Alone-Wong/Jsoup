package Demo03;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Test {
	
	public static void main(String[] args) {
		
		Map<String,Integer> map = new HashMap<String ,Integer>();
		
		map.put("11", 1);
		map.put("22", 2);
		map.put("33", 3);
		
		System.out.println(map);
		System.out.println("---------------------");
		
		List<String> arrayList = new ArrayList<>(map.keySet());
		System.err.println(arrayList);
		
		List<Entry<String,Integer>> list = new ArrayList<>(map.entrySet());
		
		for (Entry<String, Integer> entry : list) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			System.out.println(key+"======="+value);
		}
		
		
		System.err.println("====================");
		
//		String s1 = new String("hello");
//		String s2 = "hello";
//		System.out.println(s1 == s2);
		System.err.println("hello"=="hello");
		
		
		
	}

}
