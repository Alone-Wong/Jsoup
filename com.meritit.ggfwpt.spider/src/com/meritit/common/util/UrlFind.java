package com.meritit.common.util;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import sun.misc.BASE64Encoder;

public class UrlFind {
	
	public static Set<String> getDate(String recordtime,String taskid,String ruleid){
		String areaCode="71048";
		Set<String> dates=new TreeSet<String>();
		
		//当前时间
		Long time = new Date().getTime();
		SimpleDateFormat dateCode = new SimpleDateFormat("yyyyMMdd");
		String insertdate = dateCode.format(time);

		int year=Integer.parseInt(insertdate.substring(0,4));
		int month=Integer.parseInt(insertdate.substring(4,6));
		int date=Integer.parseInt(insertdate.substring(6,8)); 
		

		//解析历史时间
		int lyear=Integer.parseInt(recordtime.substring(0, 4));
		int lmonth=Integer.parseInt(recordtime.substring(4, 6));
		int lday=Integer.parseInt(recordtime.substring(6, 8));
		
		String dateS=null;
		String id=null;
		//从最近一个月开始倒序爬取（lyearlmonth-now）
		labe:while(lyear<=year){
			 while(lmonth<=12){
				  while(lday<=31){
					  
					  String y=String.valueOf(lyear);
					  String m=String.valueOf(lmonth);
					  String d=String.valueOf(lday);

					  if(lmonth<10){
					 	m="0"+String.valueOf(lmonth);
					   }
					   if(lday<10){
						 d="0"+String.valueOf(lday);
					   }
					   
					   
					   dateS=y+"-"+m+"-"+d+"-"+areaCode;
					   
					   try {
							String encodeUrl = new BASE64Encoder().encode(dateS.getBytes("utf-8"));
							id=taskid+"-"+ruleid + "-" + encodeUrl;
							
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					   
					   
					   
					   //当天
					   if(lyear-year==0&&lmonth-month==0&&lday-date==0){
						   dates.add(id);
						   System.out.println(dateS);
						   break labe;
					   }
					   System.out.println(dateS);
					   dates.add(id);
					  
					   lday++;
					   
				   }////end date
				   lday=1;
				   lmonth++;
				   
			  }//end month
			  lmonth=1;
			  lyear++;
		 }//end year
		
		return dates;
	}
	
	
	public static void main(String args[]){
		String taskid="1513137043037";
		String ruleid="001";
		String recordtime="20170102";
		Set<String> dates=getDate(recordtime,taskid,ruleid);
		
		Iterator<String> it = dates.iterator();
		
		//待查询数据
		String dateS=null;
		while(it.hasNext()){
			dateS=it.next();
			
			//System.out.println(dateS+"->");	
			
		}
//		
//		
//		
//		System.out.println(urlR);
//		
//		
//		
//		
//		
//		
//		

		
		
		
		
		
		
		
		
		
		
		
		

	}
}
