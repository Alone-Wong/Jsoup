package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.FCYGDPThread;
import com.meritit.customize.thread.FCYGDP_FSThread;

public class FCYGDPCrawler {
	
	static Logger logger = Logger.getLogger(FCYGDPCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		//全国
		String cUrl=url.getProperty("fcygdpSum1");
		String qUrl=url.getProperty("fcygdpSum2");
		
		//分省
		String pUrl=url.getProperty("fcygdpSum3_1");
		String pUrl2=url.getProperty("fcygdpSum3_2");
		String pUrl3=url.getProperty("fcygdpSum3_3");
		String pUrl4=url.getProperty("fcygdpSum3_4");
		
		Runnable r1 = new FCYGDPThread(cUrl,qUrl);
		Runnable r2 = new FCYGDP_FSThread(pUrl,pUrl2,pUrl3,pUrl4);
		
		ThreadPoolHandler.getInstance().execute(r1);
		ThreadPoolHandler.getInstance().execute(r2);
		
		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		FCYGDPCrawler crawler = new FCYGDPCrawler();
		crawler.crawlerHandler();
	}
	
}