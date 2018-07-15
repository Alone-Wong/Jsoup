package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.GXCZThread;
/**
 * 高新技术产业行业产值及增加值（高技术产业）
 * @author viki
 *
 */
public class GXCZCrawler {
	
	static Logger logger = Logger.getLogger(GXCZCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		String cUrl=url.getProperty("gxczSum");
		Runnable r1 = new GXCZThread(cUrl);
		
		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		GXCZCrawler crawler = new GXCZCrawler();
		crawler.crawlerHandler();
	}
	
}