package Demo01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Test01 {
	public static void main(String[] args) {
		// int a = 1000, b = 1000;
		// System.out.println(a == b);
		// Integer c = 1000, d = 1000;
		// System.out.println(c == d);
		// Integer e = 100, f = 100;
		// System.out.println(e == f);

		// List<Object> list = new ArrayList<>();
		// list.add("string");
		// list.add(2);
		// list.add("null");
		// System.out.println(list);

		String s = "abscddafg";

		String[] str = { "b", "a", "f", "e", "d", "c" };

		Arrays.sort(str);

		System.out.println("binary=====" + Arrays.binarySearch(str, "d"));

		System.out.println(Arrays.toString(str));

		Integer ii = new Integer(5);
		System.err.println(ii);
		
		String string = "a1b2cdefJJJ";
		count(string);

		//////////////////////////////////////////
		System.out.println("====-----------------");
		String  ss = "ab aa    d d  d";
		String replace = ss.replace(" ", "");
		System.err.println(replace.length());
		
		
		
	}
	
	
	public static void count(String str){
		
		char[] chs = str.toCharArray();
		
		int big = 0;
		int small = 0 ;
		int zm = 0;
		
		for (char c : chs) {
			if (Character.isUpperCase(c)) {
				big++;
			}else if (Character.isLowerCase(c)) {
				small ++;
			}else {
				zm++;
			}
		}
		
		System.err.println("big=="+big);
		System.out.println("small=="+small);
		System.err.println("zm=="+zm);
		
		
		
		
		System.out.println("------");
		
		Date date = new Date();
		System.err.println(date.getTime());
		
		List<Object> list = new ArrayList<>();
		list.add(1);
		list.add("dd");
		System.err.println(list);
		
		
		System.err.println("=========================");
		
		List<Object> list2 = new ArrayList<>();
		list2.add("1");
		list2.add("2");
		list2.add("3");
		ListIterator<Object> iterator = list2.listIterator();
		
		while (iterator.hasNext()) {
			String  str2 = (String) iterator.next();
//			System.err.println(str2);
		}
		
		while (iterator.hasPrevious()) {
			String  str2 = (String) iterator.previous();
			System.err.println(str2);
		}
			
		
		
		
	}
	
}
