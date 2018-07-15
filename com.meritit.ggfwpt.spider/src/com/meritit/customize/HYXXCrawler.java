package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.HYXXThread;
import com.meritit.customize.thread.HYXX_FSThread;

public class HYXXCrawler {
	
	static Logger logger = Logger.getLogger(HYXXCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		//全国
		String cUrl=url.getProperty("hyxxSum1");
		
		//分省
		String url2_0=url.getProperty("hyxxSum2_0");
		String url2_1=url.getProperty("hyxxSum2_1");
		String url2_2=url.getProperty("hyxxSum2_2");
		String url2_3=url.getProperty("hyxxSum2_3");
		String url2_4=url.getProperty("hyxxSum2_4");
		String url2_5=url.getProperty("hyxxSum2_5");
		String url2_6=url.getProperty("hyxxSum2_6");
		String url2_7=url.getProperty("hyxxSum2_7");
		String url2_8=url.getProperty("hyxxSum2_8");
		String url2_9=url.getProperty("hyxxSum2_9");
		String url2_A=url.getProperty("hyxxSum2_A");
		String url2_B=url.getProperty("hyxxSum2_B");
		String url2_C=url.getProperty("hyxxSum2_C");
		String url2_D=url.getProperty("hyxxSum2_D");
		String url2_E=url.getProperty("hyxxSum2_E");
		String url2_F=url.getProperty("hyxxSum2_F");
		String url2_G=url.getProperty("hyxxSum2_G");
		String url2_H=url.getProperty("hyxxSum2_H");
		String url2_I=url.getProperty("hyxxSum2_I");
		String url2_J=url.getProperty("hyxxSum2_J");

		
		Runnable r1 = new HYXXThread(cUrl);
		Runnable r2 = new HYXX_FSThread(url2_0,url2_1,url2_2,url2_3,url2_4,url2_5,url2_6,url2_7,url2_8,url2_9,url2_A,url2_B,url2_C,url2_D,url2_E,url2_F,url2_G,url2_H,url2_I,url2_J);
		
		ThreadPoolHandler.getInstance().execute(r1);
		ThreadPoolHandler.getInstance().execute(r2);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		HYXXCrawler crawler = new HYXXCrawler();
		crawler.crawlerHandler();
	}
	
}