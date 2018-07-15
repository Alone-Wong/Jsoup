package com.meritit.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONObject;

public class PropReadTest {
	
	protected static Logger logger = Logger.getLogger(PropReadTest.class);
	/*
	 * 获取省市县属性
	 */
	
	 
	 
	 public static void main(String[] args) {
		 
		 Long time = new Date().getTime();
			
		 SimpleDateFormat dateCode = new SimpleDateFormat("yyyy");
		 String insertdate = dateCode.format(time);
		 
		 SimpleDateFormat dateCode1 = new SimpleDateFormat("MM");
		 String insertdate1 = dateCode1.format(time);
		 
		 SimpleDateFormat dateCode2 = new SimpleDateFormat("dd");
		 String insertdate2 = dateCode2.format(time);
			
		 
		 JSONObject json=null;
//		String[] pro={"海南","河北","河南", "黑龙江", "湖北", "湖南", "吉林","江苏", "江西", "辽宁", "内蒙古", "宁夏", "青海", "山东", "山西", "陕西", "上海", "四川", "台湾", "天津", "西藏", "香港", "新疆", "云南", "浙江","重庆"};
//
//		int j=0;
//		 for(int i=18;i<44;i++){
//			 String x=Integer.toString(i);
//			 String province=pro[j++];
//			  printAll(province,x);
//		 }
		 
		 Properties properties=PropertyUtils.loadProp("city");
		 Set keyValue = properties.keySet();
		 System.out.println(keyValue.size());
		 System.out.println("最新日期："+insertdate+"-"+insertdate1+"-"+insertdate2);
		 
		 
		 int count=1;

		 
		 for (Iterator it = keyValue.iterator(); it.hasNext();)
		  {
			 
			 
			 
//			 int year=2017;
//			 int month=3;
			 int year=Integer.parseInt(insertdate);
			 int month=Integer.parseInt(insertdate1);
			 int date=Integer.parseInt(insertdate2);
			 if(date==1) month--;
			 
			 String key = (String) it.next();
			 String url=null;
			 logger.info("开始抓取"+GetCity.getProvince(key)+" 数据");
			 
			 
			 
			
			
	
			 labe: while(year>=2011){
				 while(month>=1){
					 logger.info("开始抓取第"+count+"条数据："+year+"年"+month+"月数据");
					 
					 url=WCrawlerUtils.initURL(key,year,month--);
					 //json=WCrawlerUtils
					 count++;
					 
					 if(json==null){
						count--;
						break labe;
					 }
					 
					 System.out.println(json);
		//			 getInfo(key);
				 }
				 month=12;
				 year--;
			 }
		  }


			
	 }
	 
	 public static void printAll(String province,String code){
		 Properties properties=PropertyUtils.loadProp("area");

//			System.out.println(properties.size());  
//			int i=1;
//			for (String key : properties.stringPropertyNames()) {  
//				  System.out.println((i++)+":"+properties.getProperty(key));  
//			}  
			
			String x=properties.getProperty("provqx["+code+"]").replaceAll("'", "");
			String[] provqx=x.substring(1, x.length()-1).split("[|,]");
			 for(String s:provqx){
		        	//System.out.println(s);
		        	System.out.println(s.substring(0, 5)+"="+s.substring(7,s.length()));
//		        	System.out.println(s.substring(s.lastIndexOf("-")+1, s.length()));
		        
		        	
			 }
			 String y=properties.getProperty("prov["+code+"]").replaceAll("'", "");
				String[] prov=y.split("[|,]");
				 for(String s:prov){
					 System.out.println(s.substring(0, 5)+"="+province+s.substring(7,s.length()));
			        	//System.out.println(s.substring(0, 5)+"="+s.substring(7, 10));
			        	
			  }
//			
//			String[] sArr=country.split("[|,,]");
//	        for(String s:sArr){
//	        	System.out.println(s);
//	            System.out.println(s.substring(0, 5)+"="+s.substring(7, 10));
//	            
//	            System.out.println(s.substring(11, s.length()));
//	        }
//	        
//	        
//	        
//	        
//	        
//	        
//			
//			while(x.indexOf("|")<0){
//				
//			}
			
			//Map<String,String> map= new HashMap<String,String>();
//			for(int j=0;i<strs.length;j++){
//				System.out.println(strs[j]);  
//			}		
			
				
				
	 }
}
