package com.alone.tongjinianjian;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class 江门 {

	public static void main(String[] args) {
		String url = "http://tjj.jiangmen.gov.cn/tjsj/tjnj/201606/t20160613_481140.html";
		String selector = "body > div.w1003 > div:nth-child(2) > div.xlbox > p:nth-child(7) > strong:nth-child(3) > a[href]";
		String filefolder = "C:/Users/74707/Desktop/数据/广东/江门/统计年鉴/2012/";
		CrawlerUtil.dirCheck(filefolder);
		Document doc = CrawlerUtil.getHtml(url);
		Elements eles = doc.select(selector);
		for (Element a : eles) {
			String href = a.attr("abs:href");
			String path = a.text();
			CrawlerUtil.downloadFromHtml(filefolder+path+".pdf", href);
		}
	}
	
	
	

}
