package com.meritit.common.util;

import java.util.regex.Pattern;

public class IsNUm {
	public static void main(String args[]){
//		String key="321";
//		
//		Boolean number= true;
//		
//		for (int at = key.length();--at>=0;){   
//			
//			if (!Character.isDigit(key.charAt(at))){
//				number=false;
//				break;
//			  
//			}
//		}
//		
//		System.out.println(number);
//		
		String urls="http://221.226.86.104/file/nj2004/2017/zonghe/index.htm";
		String x="1-1.htm";
		String href = urls.substring(0,urls.lastIndexOf("/")+1)+x;
		System.out.println(href);
	}
}














