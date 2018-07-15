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
import com.meritit.customize.model.JYPersonModel;
/**
 * 就业人口
 * @author viki
 *
 */
public class JYPersonNumThread implements Runnable {
	private String url;
	private String url2;
	
	protected static Logger logger = Logger.getLogger(JYPersonNumThread.class);
	
	static Properties id=PropertyUtils.loadProp("id");
	
	private static final String RULEID = id.getProperty("JYrule_id");
	
	private static final String TASK_ID = id.getProperty("JYtask_id");
	
	public JYPersonNumThread(){};
	public JYPersonNumThread(String url,String url2) {
		this.url =url;
		this.url2 = url2;
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		logger.info("正在抓取：全国数据");
		initPageCrawler(url,"qg");
		logger.info("正在抓取：省份数据");
		initPageCrawler(url2,"sf");
	}
	
	/**
	 * 抓取所有信息
	 */
	public void initPageCrawler(String url,String are) {
		
		JSONObject json=CrawlerUtil.crawler(url);
		
		logger.info("获取到数据json串："+json);
		
		int num=0;
		
		if(are.equals("qg")){
			
			num=json.getJSONObject("returndata").getJSONArray("wdnodes").getJSONObject(1).getJSONArray("nodes").size();
			
		}else num=json.getJSONObject("returndata").getJSONArray("datanodes").size();
		
		logger.info("共"+num+"条数据");
		
		logger.info("开始解析数据...");
		CrawlerSender(json,num,are);
		
	}
	
	/**
	 * 获取数据详细内容，并发送到ActiveMQ
	 */
	private void CrawlerSender(JSONObject json,int num,String are){

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
			JYPersonModel jyn=new JYPersonModel();
			
			//爬取属性并设置实体属性值
			if(parseDetailHtml(jyn,json,i,num,are)==null);	
			
			//设置格式属性
			
			jyn.setTaskid(TASK_ID);
			jyn.setRuleid(RULEID);
			
			jyn.setInserttime(inserttime);
			jyn.setInsertdate(insertdate);
			jyn.setId(id);
			
			jyn.setUrl(jyn.getStatdate()+jyn.getValue()+"-"+url);
			
			
			/**
			 * 发送数据到ActiveMQ
			 */
			String JContent = JSON.toJSONString(jyn);
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
		private JYPersonModel parseDetailHtml(JYPersonModel jyn,JSONObject json,int i,int num,String are){

			
			//解析节点值
			JSONObject returndata = json.getJSONObject("returndata");
			JSONArray datanodes = returndata.getJSONArray("datanodes");
			JSONObject wdnodes0 = (JSONObject) returndata.getJSONArray("wdnodes").get(0);
			
			JSONObject jsonObject = datanodes.getJSONObject(i).getJSONObject("data");
            
			
			//指标值
			String strdata = datanodes.getJSONObject(i).getJSONObject("data").getString("data");
			String strdata_1="";
			String strdata_2="";
			String strdata_3="";
			
			if(are.equals("qg")) {
				strdata_1 = datanodes.getJSONObject(i+num).getJSONObject("data").getString("data");
				strdata_2 = datanodes.getJSONObject(i+2*num).getJSONObject("data").getString("data");
				strdata_3 = datanodes.getJSONObject(i+3*num).getJSONObject("data").getString("data");	
			}

			
          	String info = datanodes.getJSONObject(i).getString("code");
          	JSONArray wds=datanodes.getJSONObject(i).getJSONArray("wds");
          	
          	String regCode=null;
          	String sj=null;
          	
          	if(are.equals("qg")){ 
          		regCode="10000";	
          		sj=wds.getJSONObject(1).getString("valuecode");
          	}else{
          		regCode = info.split("_")[1].split("\\.")[1];
         		logger.info(regCode);
         		sj=wds.getJSONObject(2).getString("valuecode");
          	}
          	
          
			String unit = wdnodes0.getJSONArray("nodes").getJSONObject(0).getString("unit");
	
			
			//设置实体属性值
			setValueByreflect(jyn,"statdate",sj);
			setValueByreflect(jyn,"datefreq","年");
			setValueByreflect(jyn,"country","中国");
			
			//根据获取的邮政编码，从配置表获取邮编名
			String reg=getCity(regCode);
			setValueByreflect(jyn,"areacode",regCode);
			setValueByreflect(jyn,"province",reg);
			if(regCode.equals("110000")||regCode.equals("120000")||regCode.equals("500000")||regCode.equals("310000")){
				
				setValueByreflect(jyn,"city",reg);
			}else{
			setValueByreflect(jyn,"city","");
			}
			
			setValueByreflect(jyn,"district","");
			setValueByreflect(jyn,"value",strdata);
			setValueByreflect(jyn,"dim_1ind",strdata_1);
			setValueByreflect(jyn,"dim_2ind",strdata_2);
			setValueByreflect(jyn,"dim_3ind",strdata_3);
			setValueByreflect(jyn,"unit",unit);

			return jyn;
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

