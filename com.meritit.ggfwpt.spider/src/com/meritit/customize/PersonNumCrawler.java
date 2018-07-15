package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.PersonNumThread;

public class PersonNumCrawler {
	
	static Logger logger = Logger.getLogger(PersonNumCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		
		String cUrl=url.getProperty("personNum1");
		String pUrl=url.getProperty("personNum2");
	
		Runnable r1 = new PersonNumThread(cUrl,pUrl);

		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		PersonNumCrawler crawler = new PersonNumCrawler();
		crawler.crawlerHandler();
	}
	
}