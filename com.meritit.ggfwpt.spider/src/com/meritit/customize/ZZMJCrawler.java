package com.meritit.customize;

import java.util.Properties;

import org.apache.log4j.Logger;

import com.meritit.common.thread.pool.ThreadPoolHandler;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.thread.ZZMJThread;

/**
 * 住宅总建筑面积 
 * @author viki
 *
 */

public class ZZMJCrawler {
	
	static Logger logger = Logger.getLogger(ZZMJCrawler.class);
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		Properties url=PropertyUtils.loadProp("url");
		
		
		String cUrl=url.getProperty("zzmjSum1");
		String cUrl2=url.getProperty("zzmjSum2");
		String pUrl3=url.getProperty("zzmjSum3");
		String pUrl4=url.getProperty("zzmjSum4");
	
		Runnable r1 = new ZZMJThread(cUrl,cUrl2,pUrl3,pUrl4);
		
		ThreadPoolHandler.getInstance().execute(r1);

		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	
}
	
	public static void main(String[] args) {
		ZZMJCrawler crawler = new ZZMJCrawler();
		crawler.crawlerHandler();
	}
	
}