package com.meritit.customize.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meritit.common.QueueSender;
import com.meritit.common.util.CrawlerUtil;
import com.meritit.common.util.PropertyUtils;
import com.meritit.customize.model.RKZZLModel;
import com.meritit.customize.model.SCCYGCModel;
/**
 * 三次产业构成
 * @author viki
 *
 */
public class SCCYGCThread implements Runnable {
	private String url;
	
	protected static Logger logger = Logger.getLogger(SCCYGCThread.class);
	
	static Properties id=PropertyUtils.loadProp("id");
	
	private static final String RULEID = id.getProperty("SCCYGCrule_id");
	
	private static final String TASK_ID = id.getProperty("SCCYGCtask_id");
	
	public SCCYGCThread(){};
	public SCCYGCThread(String url) {
		this.url =url;
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("正在抓取：全国数据");
		initPageCrawler(url);
		
	}
	
	/**
	 * 抓取所有信息
	 */
	public void initPageCrawler(String url) {
		
		JSONObject json=CrawlerUtil.crawler(url);
		
		logger.info("获取到数据json串："+json);
		
		int num=json.getJSONObject("returndata").getJSONArray("wdnodes").getJSONObject(1).getJSONArray("nodes").size();
			
		logger.info("共"+num+"条数据");
		
		logger.info("开始解析数据...");
		CrawlerSender(json,num);
		
	}
	
	/**
	 * 获取数据详细内容，并发送到ActiveMQ
	 */
	private void CrawlerSender(JSONObject json,int num){

		//遍历获取数据并发送到ActiveMQ
		for(int i = 0; i < num; i++){
			String inserttime = new Date().getTime() + "";
			Long time = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insertdate = sdf.format(time);
			
			String uuid = UUID.randomUUID().toString();
			String id = TASK_ID + "-" + RULEID + "-" + uuid;

			/**
			 * 解析详细信息
			 */
			SCCYGCModel sccy=new SCCYGCModel();
			
			//爬取属性并设置实体属性值
			if(parseDetailHtml(sccy,json,i,num)==null);	
			
			//设置格式属性
			
			sccy.setTaskid(TASK_ID);
			sccy.setRuleid(RULEID);
			
			sccy.setInserttime(inserttime);
			sccy.setInsertdate(insertdate);
			sccy.setId(id);
			
			sccy.setUrl(sccy.getStatdate()+sccy.getDim_1ind()+sccy.getDim_2ind()+sccy.getDim_3ind()+"-"+url);
			
			
			/**
			 * 发送数据到ActiveMQ
			 */
			String JContent = JSON.toJSONString(sccy);
			System.out.println(JContent);
			logger.info("已成功解析第"+(i+1)+"条数据");
			QueueSender.sendMsg(JContent);	
			logger.info("已成功发送第"+i+"条数据");
			
			}
	}	
		
		/**
		 * 解析详细信息页面
		 * @param dd
		 * @param json
		 * @param i
		 * @param are
		 * @return dd
		 */
		private SCCYGCModel parseDetailHtml(SCCYGCModel sccy,JSONObject json,int i,int num){

			
			//解析节点值
			JSONObject returndata = json.getJSONObject("returndata");
			JSONArray datanodes = returndata.getJSONArray("datanodes");
			JSONObject wdnodes0 = (JSONObject) returndata.getJSONArray("wdnodes").get(0);
			
			//指标值
			String strdata_1 = datanodes.getJSONObject(i+num).getJSONObject("data").getString("strdata");
			String strdata_2 = datanodes.getJSONObject(i+2*num).getJSONObject("data").getString("strdata");
			String strdata_3 = datanodes.getJSONObject(i+3*num).getJSONObject("data").getString("strdata");	

			
          	JSONArray wds=datanodes.getJSONObject(i+num).getJSONArray("wds");
          	String sj=wds.getJSONObject(1).getString("valuecode");
       
			String unit = wdnodes0.getJSONArray("nodes").getJSONObject(0).getString("unit");
	
			
			//设置实体属性值
			setValueByreflect(sccy,"statdate",sj);
			setValueByreflect(sccy,"datefreq","年");
			setValueByreflect(sccy,"country","中国");
			setValueByreflect(sccy,"province","");
			setValueByreflect(sccy,"city","");
			setValueByreflect(sccy,"district","");
			setValueByreflect(sccy,"areacode","10000");
			
			setValueByreflect(sccy,"dim_1ind",strdata_1);
			setValueByreflect(sccy,"dim_2ind",strdata_2);
			setValueByreflect(sccy,"dim_3ind",strdata_3);
			setValueByreflect(sccy,"unit",unit);

			return sccy;
		}
		
		
		/**
		 * 根据反射原理设置详细信息实体字段
		 * @param t 实体对象
		 * @param name	实体字段
		 * @param value	字段值
		 */
		@SuppressWarnings({ "unchecked", "unused" })
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

		
	  /**
	   * 根据城市编码获取对应的值
	   */
	   private String getCity(String code){
		   Properties cityCode=PropertyUtils.loadProp("code");
		   String name=cityCode.getProperty(code);
		   return name;
	   }
	   
}

