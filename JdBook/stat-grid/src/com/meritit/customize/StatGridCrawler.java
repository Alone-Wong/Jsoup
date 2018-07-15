package com.meritit.customize;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.meritit.customize.model.CompanyCode;
import com.meritit.customize.redis.JRedisSet;
import com.meritit.customize.redis.JRedisString;
import com.meritit.customize.redis.JRedisUtil;
import com.meritit.customize.thread.FZhBThread;
import com.meritit.customize.thread.ZhBThread;
import com.meritit.customize.thread.pool.ThreadPoolHandler;
import com.meritit.customize.util.CrawlerUtils;
import com.meritit.customize.util.PropertyUtils;

public class StatGridCrawler {

	static Logger logger = Logger.getLogger(StatGridCrawler.class);

	private static Properties prop;
	
	static{
		prop = new Properties();
	     try {
	    	 InputStream is = StatGridCrawler.class.getClassLoader().getResourceAsStream("detail.properties");
	    	 prop.load(is);
	     } catch (IOException e) {
	       e.printStackTrace();
	     }
	     PropertyUtils.loadProp("config");
	}
	
	/**
	 * 爬虫入口
	 */
	public void crawlerHandler(){
		String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");
		String deadline = PropertyUtils.getValueByKey("deadline");
		//获取所有招标公司
		String initUrl = "http://ecp.sgcc.com.cn/topic_project_list.jsp?columnName=topic10&company_id=&site=global&status=00&project_name=all&pageSize=200&pageNo=";
		Document document = CrawlerUtils.crawler(initUrl + 1, "GET");
		List<CompanyCode> companyList = obtainCompanys(document);

		Object obj = JRedisUtil.getInstance().getJredisObject("set");
		JRedisSet jset = null;
		if(obj instanceof JRedisSet){
			jset = (JRedisSet)obj;
		}
		
		Object o = JRedisUtil.getInstance().getJredisObject("string");
		JRedisString jstr = null;
		if(o instanceof JRedisString){
			jstr = (JRedisString)o;
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -1);
		String yesterday = sdf.format(c.getTime());
		
		logger.info("开始抓取日期：" + (deadline == null ? yesterday : deadline) + "；共有" + companyList.size() + "个网省信息");
		
		logger.info("文件存储方式：" + fileStoreType);
		
		SimpleDateFormat hhFormat = new SimpleDateFormat("HH");
		
		boolean isFirst = true;
		
		while(true){
			
			if(!isFirst){
				try {
					//休息1小时
					logger.info("正在睡眠1小时...");
					Thread.sleep(60*60*1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			isFirst = false;
			
			//时间超过晚上8点，停止抓取
			if(hhFormat.format(new Date()).compareTo("18") >= 0){
				logger.info("时间超过18点，停止抓取！！！");
				break;
			}
			
			logger.info("开始遍历抓取各网省数据！");
			
			for(CompanyCode cc : companyList){
				
				String code = cc.getCode();
				initUrl = "http://ecp.sgcc.com.cn/topic_project_list.jsp?columnName=topic10&company_id="+code+"&site=global&status=00&project_name=all&pageSize=200&pageNo=";

				Runnable r1 = new ZhBThread(prop, initUrl, fileStoreType, cc.getCompany(), jset, jstr, yesterday, deadline);
				ThreadPoolHandler.getInstance().execute(r1);
				
				String _initUrl = "http://ecp.sgcc.com.cn/topic_news_list.jsp?columnName=topic21&site=global&company_id="+code+"&column_code1=014001008&column_code2=014002008&pageSize=200&pageNo=";

				Runnable r2 = new FZhBThread(_initUrl, fileStoreType, cc.getCompany(), jset, jstr, yesterday, deadline);
				ThreadPoolHandler.getInstance().execute(r2);
				try {
					Thread.sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
		}

		//释放线程池资源
		ThreadPoolHandler.getInstance().shutdown();
	}
	
	
	/**
	 * 获取所有招标公司
	 * @param doc
	 * @return
	 */
	private List<CompanyCode> obtainCompanys(Document doc){
		List<CompanyCode> list = new ArrayList<>();
		Elements els = doc.select(".clearfix");
		els = els.get(0).children();
		for(Element ele : els){
			ele = ele.select("a").get(0);
			String company = ele.attr("name");
			if(StringUtils.isEmpty(company)){
				company = ele.text().trim();
			}
			String id = ele.attr("id");
			CompanyCode cc = new CompanyCode(id, company);
			list.add(cc);
		}
		return list;
	}
	
	
	public static void main(String[] args) {
		StatGridCrawler crawler = new StatGridCrawler();
		crawler.crawlerHandler();
	}
	
}
