package com.meritit.customize.thread;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
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
import com.meritit.customize.model.BasicTender;
import com.meritit.customize.model.DetailTender;
import com.meritit.customize.redis.JRedisSet;
import com.meritit.customize.redis.JRedisString;
import com.meritit.customize.util.CrawlerUtils;
import com.meritit.customize.util.FastDFSClient;
import com.meritit.customize.util.FileUtils;
import com.meritit.customize.util.PropertyUtils;

public class ZhBThread implements Runnable{

	private String initUrl;
	private String company;
	private String fileStoreType;
	private Properties prop;
	private JRedisSet jset;
	private JRedisString jstr;
	private String yesterday;
	private String deadline;
	
	private static final String TASK_ID = "1510044073797";
	
	private static final String RULE_ID1 = "001";
	
	private static final String RULE_ID2 = "002";
	
	private static String rootUrl = "http://ecp.sgcc.com.cn/";
	
	private static String preDetailUrl = "http://ecp.sgcc.com.cn/html/project/";
	
	protected static Logger logger = Logger.getLogger(ZhBThread.class);
	
	public ZhBThread(){}
	
	public ZhBThread(Properties prop, String initUrl, String fileStoreType, String company, JRedisSet jset, 
			JRedisString jstr, String yesterday, String deadline) {
		this.prop = prop;
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
		logger.info("招标公告，正在抓取：" + company);
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
		logger.info("招标公告，总共页数: " + num);
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
			logger.info("招标公告，开始抓取第" + i + "页数据！URL==>" + url);
			Document doc = CrawlerUtils.crawler(url, "GET");
			List<BasicTender> infoList = new ArrayList<>();
			boolean isRollPage = parseHtml(doc, urlList, inserttime, infoList);
			
			logger.info("招标公告，["+ company +"]共抓取数据" + infoList.size() + "条！！！");
			
			/**
			 * 解析详细信息
			 */
			List<DetailTender> detailList = new ArrayList<DetailTender>();
			for(String detailUrl : urlList){
				logger.info("招标公告，正在解析详细信息URL==>" + detailUrl);
				DetailTender dt = parseDetailHtml(detailUrl, "GET");
				dt.setTaskid(TASK_ID);
				dt.setRuleid(RULE_ID2);
				dt.setUrl(detailUrl);
				dt.setInserttime(inserttime);
				detailList.add(dt);
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
				for(BasicTender bt : infoList){
					String json = JSON.toJSONString(bt);
					System.out.println(json);
					QueueSender.sendMsg(json);
				}
				logger.info("招标公告，[" + company + "]推送成功，第"+ i +"页推送概要信息" + infoList.size() + "条数据！");
			}
			if(detailList != null && detailList.size() > 0){
				for(DetailTender dt : detailList){
					QueueSender.sendMsg(JSON.toJSONString(dt));
				}
				logger.info("招标公告，[" + company + "]推送成功，第"+ i +"页推送详细信息" + detailList.size() + "条数据！");
			}
			
			if(!isRollPage){
				logger.info("招标公告，["+ company +"]满足条件数据已抓取完毕，停止翻页...");
				break;
			}
		}
	}
	
	private boolean parseHtml(Document doc, List<String> urlList, String inserttime, List<BasicTender> infoList){
		Elements els = doc.getElementsByClass("font02");
		
		if(els == null || els.size() == 0){
			logger.info("招标公告，["+ company +"]当前没有数据！！！");
			return false;
		}
		
		els = els.get(0).getElementsByTag("tr");
		for(int j = 1; j < els.size(); j++){
			Elements eles = els.get(j).children();
			String projectState = eles.get(0).text().trim();
			String projectNum = eles.get(1).text();
			Elements e = eles.get(2).select("a");
			String text = eles.get(2).text();
			String publishCompany = extractCompany(text);
			String title = e.attr("title");
			String url = extractDetailUrl(e.attr("onclick"));
			String createtime = eles.get(3).text();
			
			if(StringUtils.isNotEmpty(deadline)){
				if(deadline.compareTo(createtime) > 0){
					logger.info("招标公告，设置截止日期内数据已抓取完毕，[" + company + "]停止抓取...");
					return false;
				}
			}else{
				if(yesterday.compareTo(createtime) > 0){
					logger.info("招标公告，满足条件数据已抓取完毕，[" + company + "]停止抓取...");
					return false;
				}
			}
			
			
//			if(jset.sismember(createtime, url)){
//				//该条数据已抓取
//				logger.info("招标公告，该条数据已存在，title：" + title);
//				continue;
//			}else{
//				jset.sadd(createtime, url);
//			}
//			
//			if(jset.ttl(createtime) == -1){
//				//设置过期时间
//				jset.expire(createtime, 3*24*3600);
//			}
			
			String storeTitle = title.replaceAll("[a-zA-Z]", "").replaceAll("[一二三]次公告", "").replaceAll("\\p{Punct}|\\pP", "");
			storeTitle = storeTitle.replace("投标提示", "").replace("谈判公告", "").replace("非物资", "").replace("招标采购项目", "").replace("招标采购", "").replace("服务项目", "").
					replace("招标采购", "").replace("招标公告", "").replace("流标项目", "").replace("项目", "").replace("招标", "").replace("采购", "").replace("公告", "");
			System.out.println(storeTitle);
			jstr.set(1, storeTitle, url);
			//设置过期时间 5天
			jstr.expire(1, storeTitle, 5*24*3600);
			
			urlList.add(url);
			String uuid = UUID.randomUUID().toString();
			BasicTender bt = new BasicTender(uuid, TASK_ID, RULE_ID1, projectNum, "招标公告", projectState, publishCompany, title, url, createtime, inserttime, this.company);
			infoList.add(bt);
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
		String regex = "showProjectDetail\\('(\\w+)','(\\w+)'\\)";
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
	private DetailTender parseDetailHtml(String url, String method){
		Document doc = CrawlerUtils.crawler(url, method);
		Elements els = doc.getElementsByClass("font02");
		els = els.get(0).getElementsByTag("tr");
		DetailTender dt = new DetailTender();
		for(Element ele : els){
			for(Object key : prop.keySet()){
				String value = prop.get(key).toString();
				String detailName = ele.child(0).text().replaceAll("[：，。\\p{Punct}]", "");
				if(value.equals(detailName)){
					String detailValue = ele.child(1).text();
					if("项目公告文件".equals(detailName)){
						dt.setTenderDownloadName(detailValue);
						String downloadUrl = ele.child(1).children().attr("href");
						String _url = rootUrl + downloadUrl;
						setValueByreflect(dt, "tenderDownloadUrl", _url);
						String uuid = UUID.randomUUID().toString();
						String id = TASK_ID + "-" + RULE_ID2 + "-" + uuid;
						setValueByreflect(dt, "id", uuid);
						//文件处理方式
						if(fileStoreType != null){
							//本地文件
							if(fileStoreType.equalsIgnoreCase("local")){
								//
								FileUtils.downloadFile(_url, PropertyUtils.getValueByKey("rootDir") + File.separator + TASK_ID + File.separator + id);
							}
							//分布式文件系统
							else{
								URL URL = null;
								try {
									URL = new URL(_url);
								} catch (MalformedURLException e) {
									e.printStackTrace();
								}
								String fileid = FastDFSClient.uploadFile(URL);
								String path = URL.getFile();
								String filename = path.substring(path.lastIndexOf("/") + 1);
								
								
								//附件数据封装
								Map<String, String> resultMap = new HashMap<>();
								Map<String, String> map = new HashMap<>();
								List<Map<String, String>> fileList = new ArrayList<>();
								map.put("fileid", fileid);
								map.put("filename", filename);
								fileList.add(map);
								resultMap.put("dataid", id + "-001");
								resultMap.put("attachment", JSON.toJSONString(fileList));
								String result = JSON.toJSONString(resultMap);
								QueueSender.sendFilesMsg(result);
							}
						}
					}else{
						setValueByreflect(dt, key.toString(), detailValue);
					}
					break;
				}
			}
		}
		return dt;
	}
	
	
	/**
	 * 根据反射原理设置详细信息实体字段
	 * @param t 实体对象
	 * @param name	实体字段
	 * @param value	字段值
	 */
	private <T> void setValueByreflect(T t, String name, String value){
		Class clazz = t.getClass();
		String newName = Character.toUpperCase(name.charAt(0)) + name.substring(1);
		try {
			Method method = clazz.getDeclaredMethod("set" + newName, String.class);
			method.invoke(t, value);
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		Document doc = CrawlerUtils.crawler("http://ecp.sgcc.com.cn/topic_project_list.jsp?columnName=topic10&company_id=34&site=global&status=00&project_name=all&pageSize=200&pageNo=1", "GET");
		Elements els = doc.getElementsByClass("font02");
		System.out.println(els.size());
		els = els.get(0).getElementsByTag("tr");
	}
}
