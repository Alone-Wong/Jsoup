package com.meritit.customize.thread;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.meritit.customize.QueueSender;
import com.meritit.customize.model.FBasicModel;
import com.meritit.customize.model.FDetailModel;
import com.meritit.customize.redis.JRedisSet;
import com.meritit.customize.redis.JRedisString;
import com.meritit.customize.util.CrawlerUtils;
import com.meritit.customize.util.FastDFSClient;

public class FZhBThread implements Runnable{

	private String initUrl;
	private String company;
	private String fileStoreType;
	private JRedisSet jset;
	private JRedisString jstr;
	private String yesterday;
	private String deadline;
	
	private static String preDetailUrl = "http://ecp.sgcc.com.cn/html/news/";
	
	protected static Logger logger = Logger.getLogger(FZhBThread.class);
	
	public FZhBThread(){}
	
	public FZhBThread(String initUrl, String fileStoreType, String company, JRedisSet jset, 
			JRedisString jstr, String yesterday, String deadline) {
		this.initUrl = initUrl;
		this.fileStoreType = fileStoreType;
		this.company = company;
		this.jset = jset;
		this.jstr = jstr;
		this.yesterday = yesterday;
		this.deadline = deadline;
	}
	
	@Override
	public void run() {
		logger.info("非招标公告，正在抓取：" + company);
		initRollPageCrawler(company);
	}
	
	/**
	 * 翻页抓取所有信息
	 */
	private void initRollPageCrawler(String company){
		Document doc = CrawlerUtils.crawler(initUrl + 1, "GET");
		Elements els = doc.select(".page");
		int num = 1;
		if(els.size() > 0){
			Element ele = els.get(0);
			ele = ele.children().last().previousElementSibling();
			num = Integer.parseInt(ele.text());
		}
		logger.info("非招标公告，总共页数 : " + num);
		rollPageCrawlerSender(company, num);
	}
	
	
	/**
	 * 增量抓取前三页，并发送到ActiveMQ
	 */
	private void rollPageCrawlerSender(String company, int num){
		
		String inserttime = new Date().getTime() + "";
		for(int i = 1; i <= num; i++){
			List<String> urlList = new ArrayList<String>();
			String url = initUrl + i;
			logger.info("非招标公告，开始抓取第" + i + "页数据！URL==>" + url);
			Document doc = CrawlerUtils.crawler(url, "GET");
			
			List<FBasicModel> infoList = new ArrayList<>();
			
			boolean isRollPage = parseHtml(doc, urlList, inserttime, infoList);
			
			logger.info("非招标公告，["+ company +"]共抓取数据" + infoList.size() + "条！！！");
			
			/**
			 * 解析详细信息
			 */
			List<FDetailModel> detailList = new ArrayList<FDetailModel>();
			for(String detailUrl : urlList){
				logger.info("非招标公告，正在解析详细信息URL==>" + detailUrl);
				FDetailModel dm = parseDetailHtml(detailUrl, "GET");
				dm.setValue("url", detailUrl);
				dm.setValue("inserttime", inserttime);
				detailList.add(dm);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
			/**
			 * 发送数据到ActiveMQ
			 */
			if(infoList != null && infoList.size() > 0){
				for(FBasicModel bm : infoList){
					String json = JSON.toJSONString(bm.getResult());
					System.out.println(json);
					QueueSender.sendMsg(json);
				}
				logger.info("非招标公告，[" + company + "]推送成功，第"+ i +"页推送概要信息" + infoList.size() + "条数据！");
			}
			if(detailList != null && detailList.size() > 0){
				for(FDetailModel dm : detailList){
					QueueSender.sendMsg(JSON.toJSONString(dm.getResult()));
				}
				logger.info("非招标公告，[" + company + "]推送成功，第"+ i +"页推送详细信息" + detailList.size() + "条数据！");
			}
			
			if(!isRollPage){
				logger.info("非招标公告，["+ company +"]满足条件数据已抓取完毕，停止翻页...");
				break;
			}
		}
	}
	
	private boolean parseHtml(Document doc, List<String> urlList, String inserttime, List<FBasicModel> infoList){
		Elements els = doc.getElementsByClass("font02");
		
		if(els == null || els.size() == 0){
			logger.info("非招标公告，["+ company +"]当前没有数据！！！");
			return false;
		}
		
		els = els.get(0).getElementsByTag("li");
		for(int j = 0; j < els.size(); j++){
			
			FBasicModel bm = new FBasicModel();
			
			Elements eles = els.get(j).children();
			Element aEle = eles.get(0).select("a").get(0);
			String title = aEle.attr("title");
			bm.setValue("title", title);
			String text = aEle.text();
			String company = extractCompany(text);
			bm.setValue("company", company);
			String url = extractDetailUrl(aEle.attr("onclick"));
			bm.setValue("url", url);
			bm.setValue("gongg", "非招标公告");
			String pubdate = eles.get(1).text();
			bm.setValue("pubdate", pubdate);
			
			if(StringUtils.isNotEmpty(deadline)){
				if(deadline.compareTo(pubdate) > 0){
					logger.info("非招标公告，设置截止日期内数据已抓取完毕，[" + this.company + "]停止抓取...");
					return false;
				}
			}else{
				if(yesterday.compareTo(pubdate) > 0){
					logger.info("非招标公告，满足条件数据已抓取完毕，[" + this.company + "]停止抓取...");
					return false;
				}
			}
			
			
			if(jset.sismember(pubdate, url)){
				//该条数据已抓取
				logger.info("非招标公告，该条数据已存在，title：" + title);
				continue;
			}else{
				jset.sadd(pubdate, url);
			}
			
			if(jset.ttl(pubdate) == -1){
				//设置过期时间
				jset.expire(pubdate, 3*24*3600);
			}
			
			String storeTitle = title.replaceAll("[a-zA-Z]", "").replaceAll("[一二三]次公告", "").replaceAll("\\p{Punct}|\\pP", "");
			storeTitle = storeTitle.replace("投标提示", "").replace("谈判公告", "").replace("非物资", "").replace("招标采购项目", "").replace("招标采购", "").replace("服务项目", "").
					replace("招标采购", "").replace("招标公告", "").replace("流标项目", "").replace("项目", "").replace("招标", "").replace("采购", "").replace("公告", "");
			System.out.println(storeTitle);
			jstr.set(1, storeTitle, url);
			//设置过期时间 5天
			jstr.expire(1, storeTitle, 5*24*3600);
			
			urlList.add(url);
			String uuid = UUID.randomUUID().toString();
			bm.setValue("id", uuid);
			bm.setValue("inserttime", inserttime);
			infoList.add(bm);
		}
		return true;
	}
	
	/**
	 * 
	 * @param input
	 * @return
	 */
	private String extractCompany(String input){
		String regex = "\\[(.*)\\]";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if(m.find()){
			return m.group(1);
		}
		return null;
	}
	
	/**
	 * 提取详细信息url
	 * @param input 输入字符串
	 * @return
	 */
	private String extractDetailUrl(String input){
		//showProjectDetail('014001001','9900000000004050171');
		input = input.replaceAll("\\s", "");
		String regex = "showNewsDetail\\(\\s*'(\\w+)','(\\w+)'\\)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(input);
		if(m.find()){
			String before = m.group(1);
			String after = m.group(2);
			StringBuffer sb = new StringBuffer(preDetailUrl);
			sb.append(before);
			sb.append("/");
			sb.append(after);
			sb.append(".html");
			return sb.toString();
		}
		return null;
	}
	
	/**
	 * 解析详细信息页面
	 * @param url
	 * @param method
	 * @return
	 */
	private FDetailModel parseDetailHtml(String url, String method){
		FDetailModel dm = new FDetailModel();
		Document doc = CrawlerUtils.crawler(url, method);
		Elements els = doc.select("div.bot_list");
		String content = els.toString();
		dm.setValue("content", content);
		String uuid = UUID.randomUUID().toString();
		dm.setValue("id", uuid);
		Elements eles = doc.select("p.bot_list");
		//附件数据封装
		Map<String, String> resultMap = new HashMap<>();
		List<Map<String, String>> fileList = new ArrayList<>();
		String id = dm.getValue("taskid") + "-" + dm.getValue("ruleid") + "-" + uuid;
		for(int i = 0; i < eles.size(); i++){
			eles = eles.get(i).select("a");
			for(Element ele : eles){
				String _url = ele.absUrl("href");
				//本地文件
				if(!fileStoreType.equalsIgnoreCase("local")){
					URL URL = null;
					try {
						URL = new URL(_url);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
					String fileid = FastDFSClient.uploadFile(URL);
					String filename = ele.text().trim();
					
					Map<String, String> map = new HashMap<>();
					map.put("fileid", fileid);
					map.put("filename", filename);
					fileList.add(map);
				}
			}
		}
		
		if(fileList.size() > 0){
			resultMap.put("dataid", id + "-001");
			resultMap.put("attachment", JSON.toJSONString(fileList));
			String result = JSON.toJSONString(resultMap);
			logger.info(result);
			QueueSender.sendFilesMsg(result);
		}
		logger.info("非招标公告，抓取附件：" + fileList.size() + "个；url：" + url);
		
		return dm;
	}
	
	
	public static void main(String[] args) {
		Document doc = CrawlerUtils.crawler("http://www.bidding.csg.cn/zbgg/1200043936.jhtml", "GET");
		Elements els = doc.select(".Contnet").get(0).children();
		for(Element ele : els){
			if("a".equalsIgnoreCase(ele.tagName())){
				System.out.println(ele.absUrl("href"));
			}
		}
	}
}
