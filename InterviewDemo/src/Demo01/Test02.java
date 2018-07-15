package Demo01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class Test02 {
	public static void main(String[] args) {
		
		List<Object> list = new ArrayList<>();
		
		list.add("abc");
		list.add("dcd");
		list.add("aaa");
		
		
		Object[] array = list.toArray();
		
		Arrays.sort(array);
		
		List<Object> list2 = Arrays.asList(array);
		
		list2.set(1, "ccc");
		
		System.out.println(list2);
		
		
		
	}
	
}
