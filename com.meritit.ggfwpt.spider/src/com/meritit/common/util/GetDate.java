package com.meritit.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;


/*
 * 获取当前月-增量
 */
public class GetDate {
	
	public static void getDate() {

		Long time = new Date().getTime();
			
		SimpleDateFormat dateCode = new SimpleDateFormat("yyyyMM");
		String insertdate = dateCode.format(time);
		System.out.println(insertdate);
		
		SimpleDateFormat year = new SimpleDateFormat("yyyy");
		String now = year.format(time);
		System.out.println(now);
		
		SimpleDateFormat date = new SimpleDateFormat("dd");
		String now2 = date.format(time);
		System.out.println(now2);

			
	 }
	public static void test(){

		//获取所要爬取的城市列表
		
		Properties properties=PropertyUtils.loadProp("city");
		Set city = properties.keySet();	 
		System.out.println("共"+city.size()+"个地区");		
				

		String[] cities=new String[city.size()];
		//按地区开始遍历

		Iterator c = city.iterator(); 
		int i=0;
		while (c.hasNext()) {  
		
			String key = (String) c.next();
			cities[i++]=key;
		
		}
		
		for(int j=0;j<cities.length;j++){
		 
		
			String key = cities[j];
			System.out.println(key);
		}
	}
	
	public static void main(String args[]){
//		//获取hbase中上一次插入时间
//		String lastTime="2017-12-01 18:03:09";
//		int lyear=Integer.parseInt(lastTime.substring(0, 4));
//		int lmonth=Integer.parseInt(lastTime.substring(5, 7));
//		int lday=Integer.parseInt(lastTime.substring(8, 10));
//		
//		System.out.println(lyear);
//		System.out.println(lmonth);
//		System.out.println(lday);
		test();
				
	}
}
