package com.meritit.common.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/*
 * 根据年月地区编码-》获取json串
 */

public class WCrawlerUtils {
	private static final int MAX = 3;
	protected static Logger logger = Logger.getLogger(WCrawlerUtils.class);
	
	public static JSONObject getJSPage(String initURL) throws MalformedURLException{
		
		URL url = new URL(initURL);
		
		String data = null;
		int code=0;
		JSONObject json=null;

		int wrong=1;
		while(wrong <= MAX){
			
			try {
			    
				//获取URL对象所表示的资源的字节输入流
				HttpURLConnection con =(HttpURLConnection) url.openConnection();
				code= con.getResponseCode(); 
				
				
				//System.out.println(code); 
				//设置read time out
				con.setReadTimeout(5000);
				InputStream is = con.getInputStream();
				
				
				//将字节输入流转换为字符输入流
				InputStreamReader isr = new InputStreamReader(is, "GB2312");
				
				//为字符输入流添加缓冲
				BufferedReader br = new BufferedReader(isr);
				
				data = br.readLine();//读取数据               
				
				br.close(); 
				isr.close();
			    is.close();
			  
			     //获取需求数据内容
				json=JSON.parseObject(data.substring(16, data.length()-1));    
				
				if(json!=null){
					break;
				}
			    
			} catch (MalformedURLException e) {
		       e.printStackTrace();
			} catch (IOException e) {
				
				e.printStackTrace();
				
				if(code==502||code==503){
					logger.info("第"+wrong+"次重连->");
					wrong++;
				}
				if(code==404){
					logger.info("wrong|无此数据->"+initURL);
					return null;
				}
			 
			}
			
			
		}
		
		return json;
	}
	 
	
	
	//根据地区编码获取url
	 public static String initURL(String areaCode,int year,int month){
		 
		 	JSONObject json=null;
		 	
		 	//无0
		 	String m1=String.valueOf(month);
		 	//有0
		 	String m2=m1;
		 	if(month<10){
		 		m2="0"+m1;
			 }
		 	
		 	String y=String.valueOf(year);
		 	
		 	
			
		 	String date=y+m2;
			String url="http://tianqi.2345.com/t/wea_history/js/";
			String url0=null;
			 
			 
			 //2016年3月前数据
			 if(Integer.valueOf(date)<201603){
			
				 url0=url+areaCode+"_"+y+m1+".js";
			 
			 }else {
		
				 url0=url+date+"/"+areaCode+"_"+y+m2+".js";
			 }
			
	
		
			 return url0;
		 
	 }

}
