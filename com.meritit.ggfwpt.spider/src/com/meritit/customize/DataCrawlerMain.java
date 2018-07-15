package com.meritit.customize;

import com.meritit.common.thread.pool.ThreadPoolHandler;


public class DataCrawlerMain{

	public void run() {
		WeatherCrawler weather = new WeatherCrawler();
		weather.crawlerHandler();
		
	}
	
	public void cancel(){
		ThreadPoolHandler.getInstance().shutdown();
	}
}
