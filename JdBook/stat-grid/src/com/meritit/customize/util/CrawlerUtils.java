package com.meritit.customize.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CrawlerUtils {

	protected static Logger logger = Logger.getLogger(CrawlerUtils.class);
	
	/**
	 * 超时时间
	 */
	private static final int TIMEOUT = 5000;
	
	/**
	 * 访问失败尝试次数
	 */
	private static final int MAX = 3;
	
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36";
	
	private static final String COOKIE = "BIGipServerpool_ext_sgccecp=wDtKYDmPLQtlTJ8qlHEOFwPG8f3ZUQb0ybxJSGEw3CzcMrslxVUignOn0cheAsug2z37P6j/qDsXSXg=; WL_JSESSIONID=p7TjY3MfYDM1HDQhf0ZJLNpzTnJHZcstxsdnknV970y2QXPMlMnF!948530714";
	
	public static Document crawler(String url, String method){
		Document doc = null;
		String[] cookies = COOKIE.split(";");
		Map<String, String> cookieMap = new HashMap<String, String>();
		for(String line : cookies){
			String[] rows = line.split("=");
			if(rows.length < 2){
				continue;
			}
			cookieMap.put(rows[0], rows[1]);
		}
		for(int i = 0; i < MAX; i++){
			try {
				if("GET".equalsIgnoreCase(method)){
					doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookieMap).timeout(TIMEOUT).get();
				}else{
					doc = Jsoup.connect(url).userAgent(USER_AGENT).cookies(cookieMap).timeout(TIMEOUT).post();
				}
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
	
}
