package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.QYXFThread;

public class QYXFCrawler {
	
	static Logger logger = Logger.getLogger(QYXFCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		
		String cUrl=url.getProperty("qyxfSum1");
		String pUrl=url.getProperty("qyxfSum2");
	
		Runnable r1 = new QYXFThread(cUrl,pUrl);
		
		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		QYXFCrawler crawler = new QYXFCrawler();
		crawler.crawlerHandler();
	}
	
}