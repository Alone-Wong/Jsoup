package com.meritit.customize;



import org.apache.log4j.Logger;


import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.customize.thread.WeatherThread;

public class WeatherCrawler {
	
	static Logger logger = Logger.getLogger(WeatherCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		
		Runnable r1 = new WeatherThread();

		ThreadPoolHandler.getInstance().execute(r1);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
	}
	
	
	public static void main(String[] args) {
		WeatherCrawler crawler = new WeatherCrawler();
		crawler.crawlerHandler();
	}
	
}