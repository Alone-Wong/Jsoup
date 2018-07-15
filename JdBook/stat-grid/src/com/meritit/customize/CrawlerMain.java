package com.meritit.customize;

import com.meritit.customize.thread.pool.ThreadPoolHandler;


public class CrawlerMain{

	public void run() {
		StatGridCrawler sgc = new StatGridCrawler();
		sgc.crawlerHandler();
	}
	
	public void cancel(){
		ThreadPoolHandler.getInstance().shutdown();
	}
}
