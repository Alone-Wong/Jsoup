package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.GXJZCThread;

/**
 * 高新技术产业净资产估算（高技术产业）
 * @author merit
 *
 */
public class GXJZCCrawler {
	
	static Logger logger = Logger.getLogger(GXJZCCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		String cUrl=url.getProperty("gxjzcSum");
		Runnable r1 = new GXJZCThread(cUrl);
		
		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		GXJZCCrawler crawler = new GXJZCCrawler();
		crawler.crawlerHandler();
	}
	
}