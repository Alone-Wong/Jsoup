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
import com.meritit.customize.model.FCYGDPModel;
import com.meritit.customize.model.JYPersonModel;
/**
 * 分产业GDP增长情况（国内生产总值）
 * @author viki
 *
 */
public class FCYGDPThread implements Runnable {
	private String url;
	private String url2;
	
	protected static Logger logger = Logger.getLogger(FCYGDPThread.class);
	
	static Properties id=PropertyUtils.loadProp("id");
	
	private static final String RULEID = id.getProperty("FCYGGDPrule_id");
	
	private static final String TASK_ID = id.getProperty("FCYGGDPtask_id");
	
	public FCYGDPThread(){};
	
	public FCYGDPThread(String url,String url2) {
		this.url =url;
		this.url2 = url2;
	}
	
	
	@Override
	public void run() {
		logger.info("正在抓取：全国年度数据");
		initPageCrawler(url,"qg");
		
		logger.info("正在抓取：全国季度数据");
		initPageCrawler(url2,"jd");
	}
	
	/**
	 * 抓取所有信息
	 */
	public void initPageCrawler(String url,String area) {
		
		JSONObject json=CrawlerUtil.crawler(url);
		
		logger.info("获取到数据json串："+json);
		
		int num=json.getJSONObject("returndata").getJSONArray("wdnodes").getJSONObject(1).getJSONArray("nodes").size();
					
		logger.info("共"+num+"条数据");
		
		logger.info("开始解析数据...");
		CrawlerSender(json,num,area);
		
	}
	

	
	/**
	 * 获取数据详细内容，并发送到ActiveMQ
	 */
	private void CrawlerSender(JSONObject json,int num,String area){

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
			FCYGDPModel fcy=new FCYGDPModel();
			
			//爬取属性并设置实体属性值
			if(parseDetailHtml(fcy,json,i,num,area)==null);	
			
			//设置格式属性
			fcy.setTaskid(TASK_ID);
			fcy.setRuleid(RULEID);
			
			fcy.setInserttime(inserttime);
			fcy.setInsertdate(insertdate);
			fcy.setId(id);
			
			fcy.setUrl(fcy.getStatdate()+fcy.getAreacode()+fcy.getDim_sszz_val()+"-"+url);
			
			
			/**
			 * 发送数据到ActiveMQ
			 */
			String JContent = JSON.toJSONString(fcy);
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
		private FCYGDPModel parseDetailHtml(FCYGDPModel fcy,JSONObject json,int i,int num,String area){

			
			//解析节点值
			JSONObject returndata = json.getJSONObject("returndata");
			JSONArray datanodes = returndata.getJSONArray("datanodes");
			JSONObject wdnodes0 = (JSONObject) returndata.getJSONArray("wdnodes").get(0);
			
			
			//指标值
			if(area.equals("jd")){
				
				String strdata1 = datanodes.getJSONObject(i).getJSONObject("data").getString("strdata");
				String strdata2 = datanodes.getJSONObject(i+1*num).getJSONObject("data").getString("strdata");
				String strdata3 = datanodes.getJSONObject(i+2*num).getJSONObject("data").getString("strdata");
				String strdata4 = datanodes.getJSONObject(i+3*num).getJSONObject("data").getString("strdata");	
				String strdata5 = datanodes.getJSONObject(i+4*num).getJSONObject("data").getString("strdata");
				String strdata6 = datanodes.getJSONObject(i+5*num).getJSONObject("data").getString("strdata");
				String strdata7 = datanodes.getJSONObject(i+6*num).getJSONObject("data").getString("strdata");
				String strdata8 = datanodes.getJSONObject(i+7*num).getJSONObject("data").getString("strdata");	
				
				setValueByreflect(fcy,"dim_sszz_val",strdata1);
				setValueByreflect(fcy,"dim_sszz_accval",strdata2);
				setValueByreflect(fcy,"dim_1ind_val",strdata3);
				setValueByreflect(fcy,"dim_1ind_accval",strdata4);
				setValueByreflect(fcy,"dim_2ind_val",strdata5);
				setValueByreflect(fcy,"dim_2ind_accval",strdata6);
				setValueByreflect(fcy,"dim_3ind_val",strdata7);
				setValueByreflect(fcy,"dim_3ind_accval",strdata8);
				
				setValueByreflect(fcy,"datefreq","季");
				
				
			}else{
				
				String strdata=datanodes.getJSONObject(i+num).getJSONObject("data").getString("strdata");
				String strdata_1 = datanodes.getJSONObject(i+2*num).getJSONObject("data").getString("strdata");
				String strdata_2 = datanodes.getJSONObject(i+3*num).getJSONObject("data").getString("strdata");
				String strdata_3 = datanodes.getJSONObject(i+4*num).getJSONObject("data").getString("strdata");	
				
				setValueByreflect(fcy,"dim_sszz_val",strdata);
				setValueByreflect(fcy,"dim_1ind_val",strdata_1);
				setValueByreflect(fcy,"dim_2ind_val",strdata_2);
				setValueByreflect(fcy,"dim_3ind_val",strdata_3);
				
				setValueByreflect(fcy,"datefreq","年");
			}
			
			
	        JSONArray wds=datanodes.getJSONObject(i).getJSONArray("wds");
	        String sj=wds.getJSONObject(1).getString("valuecode");
			String unit = wdnodes0.getJSONArray("nodes").getJSONObject(0).getString("unit");
				
			//设置实体属性值
			setValueByreflect(fcy,"statdate",sj);
			setValueByreflect(fcy,"country","中国");
			setValueByreflect(fcy,"areacode","10000");
			setValueByreflect(fcy,"province","");
			setValueByreflect(fcy,"city","");
			setValueByreflect(fcy,"district","");
			setValueByreflect(fcy,"unit",unit);
			return fcy;
			
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

		
	   
}

