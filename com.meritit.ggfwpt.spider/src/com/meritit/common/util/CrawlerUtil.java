package com.meritit.common.util;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class CrawlerUtil {
	private static final int MAX = 3;
	protected static Logger logger = Logger.getLogger(WCrawlerUtils.class);
	
	public static Document getHtml(String url){
		Document doc = null;
		
		for(int i = 0; i < MAX; i++){
			try {
				doc = Jsoup.connect(url).timeout(9000).get();
				
				if(null != doc){
					break;
				}
				
			} catch (IOException e) {
				if(i + 1 >= MAX){
					logger.error("访问页面失败，url ==>" + url, e);
				}else{
					logger.info("正在尝试第" + (i+1) + "次");
				}
			}
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		
		return doc;
	}
	
	private static JSONObject getContent(String url){
		String doc=getHtml(url).toString();
		String start="<body>";
		String end="</body>";
		int s=doc.indexOf(start)+start.length();
		int e=doc.indexOf(end);
		String content=doc.substring(s,e);
		JSONObject json=JSON.parseObject(content);
		return json;
		
	}
	

	
	public static JSONObject crawler(String url){

		JSONObject json=getContent(url);
		return json;

	}
	
	

	
	public static void main(String args[]){
		String code="56171";
		String url="http://tianqi.2345.com/today-"+code+".htm";
		Document doc=getHtml(url);
		String param=doc.getElementsByClass("parameter").text();
		System.out.println("1:"+param);
		int qw=param.indexOf("当前气温");
		int fx=param.indexOf("风向");
		int fl=param.indexOf("风力");
		int sd=param.indexOf("湿度");
		String qw2=param.substring(qw+5, fx);
		String fx2=param.substring(fx+3, fl);
		String fl2=param.substring(fl+3, sd);
		System.out.println(qw2);
		System.out.println(fx2);
		System.out.println(fl2);	
		
		
		String param2=doc.getElementsByClass("day").text();
		int day=param2.indexOf("今日白天");
		int high=param2.indexOf("最高");
		String dayW=param2.substring(day+5, high);
		String high2=param2.substring(high+3);
		
		String param3=doc.getElementsByClass("night").text();
		System.out.println("2:"+param3);
		int night=param3.indexOf("今天夜间");
		int low=param3.indexOf("最低");
		String nightW=param3.substring(day+5, high);
		String low2=param3.substring(low+3);
	
		String d_n_weather = dayW +"~"+nightW;
		
		System.out.println(d_n_weather);
	}

}
