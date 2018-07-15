package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.GDZCThread;

public class GDZCCrawler {
	
	static Logger logger = Logger.getLogger(GDZCCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		
		String cUrl=url.getProperty("gdzcSum1");
		String pUrl=url.getProperty("gdzcSum2");
	
		Runnable r1 = new GDZCThread(cUrl,pUrl);
		
		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		GDZCCrawler crawler = new GDZCCrawler();
		crawler.crawlerHandler();
	}
	
}