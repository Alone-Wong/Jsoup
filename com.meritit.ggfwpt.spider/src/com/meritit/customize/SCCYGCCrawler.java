package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.SCCYGCThread;

/**
 * 三次产业构成
 * @author merit
 *
 */
public class SCCYGCCrawler {
	
	static Logger logger = Logger.getLogger(SCCYGCCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		String cUrl=url.getProperty("sccygcSum1");
		Runnable r1 = new SCCYGCThread(cUrl);
		
		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		SCCYGCCrawler crawler = new SCCYGCCrawler();
		crawler.crawlerHandler();
	}
	
}