package com.alone.tongjinianjian;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alone.utils.CrawlerUtil;


public class JsonToDownload {

	public static void main(String[] args) throws IOException {
		HashMap<String, String > map = new HashMap<>();
		String url = "http://www.hzsin.gov.cn/yearbooksmenu.jspx?path=oaimage";
		String json = Jsoup.connect(url).ignoreContentType(true).timeout(15000).get().select("body").text();
		String basepath = "C:/Users/74707/Desktop/数据/广东/惠州/统计年鉴/";
		String baseurl="http://www.hzsin.gov.cn/u/frame/www/oaimage/";
		System.out.println(json);
		JSONArray array = JSON.parseArray(json);
		for(int i=0;i<array.size();i++){
			JSONObject jsonObject = array.getJSONObject(i);
			String text = jsonObject.getString("text");
//			System.out.println(text);
			String thispath=basepath+text+"/";
			JSONArray children = jsonObject.getJSONArray("children");
			for(int j=0;j<children.size();j++){
				JSONObject son = children.getJSONObject(j);
				String sontext = son.getString("text");
				String childrenpath=thispath+sontext+"/";
				JSONArray grandson = son.getJSONArray("children");
				for(int k=0;k<grandson.size();k++){
					JSONObject small = grandson.getJSONObject(k);
					String link = small.getString("link");
					String filename = small.getString("text").replace(".pdf", "")+".png";
//					System.out.println(childrenpath+"="+link);
//					map.put(baseurl+link, childrenpath);
					CrawlerUtil.dirCheck(childrenpath);
					CrawlerUtil.downloadFromHtml(childrenpath+filename, baseurl+link);
				}
			}
		}
		
		
		
	}

	
	
	
}
