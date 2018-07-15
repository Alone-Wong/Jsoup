package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;

import com.meritit.customize.thread.JYPersonNumThread;

public class JYRNCrawler {
	
	static Logger logger = Logger.getLogger(JYRNCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		
		String cUrl=url.getProperty("jyPersonNum1");
		String pUrl=url.getProperty("jyPersonNum2");
	
		Runnable r1 = new JYPersonNumThread(cUrl,pUrl);

		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		JYRNCrawler crawler = new JYRNCrawler();
		crawler.crawlerHandler();
	}
	
}