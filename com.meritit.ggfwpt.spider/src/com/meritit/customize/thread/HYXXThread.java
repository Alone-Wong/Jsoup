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
import com.meritit.customize.model.HYXXModel;
import com.meritit.customize.model.JYPersonModel;
/**
 * 分产业GDP增长情况（国内生产总值）
 * @author viki
 *
 */
public class HYXXThread implements Runnable {
	private String url;
	
	protected static Logger logger = Logger.getLogger(HYXXThread.class);
	
	static Properties id=PropertyUtils.loadProp("id");
	
	private static final String RULEID = id.getProperty("HYXXrule_id");
	
	private static final String TASK_ID = id.getProperty("HYXXtask_id");
	
	public HYXXThread(){};
	
	public HYXXThread(String url) {
		this.url =url;
	}
	
	
	@Override
	public void run() {
		logger.info("正在抓取：全国年度数据");
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
			HYXXModel hyxx=new HYXXModel();
			
			//爬取属性并设置实体属性值
			if(parseDetailHtml(hyxx,json,i,num)==null);	
			
			//设置格式属性
			hyxx.setTaskid(TASK_ID);
			hyxx.setRuleid(RULEID);
			
			hyxx.setInserttime(inserttime);
			hyxx.setInsertdate(insertdate);
			hyxx.setId(id);
			
			hyxx.setUrl(hyxx.getStatdate()+hyxx.getAreacode()+hyxx.getFr_jr_num()+hyxx.getFr_jyfr_num()+"-"+url);
			
			
			/**
			 * 发送数据到ActiveMQ
			 */
			String JContent = JSON.toJSONString(hyxx);
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
		private HYXXModel parseDetailHtml(HYXXModel hyxx,JSONObject json,int i,int num){

			
			//解析节点值
			JSONObject returndata = json.getJSONObject("returndata");
			JSONArray datanodes = returndata.getJSONArray("datanodes");
			JSONObject wdnodes0 = (JSONObject) returndata.getJSONArray("wdnodes").get(0);
			
			//获取指标值
			String strdata=datanodes.getJSONObject(i).getJSONObject("data").getString("strdata");
			String strdata_1 = datanodes.getJSONObject(i+1*num).getJSONObject("data").getString("strdata");
			String strdata_2 = datanodes.getJSONObject(i+2*num).getJSONObject("data").getString("strdata");
			String strdata_3 = datanodes.getJSONObject(i+3*num).getJSONObject("data").getString("strdata");	
			String strdata_4 = datanodes.getJSONObject(i+4*num).getJSONObject("data").getString("strdata");
			String strdata_5 = datanodes.getJSONObject(i+5*num).getJSONObject("data").getString("strdata");
			String strdata_6 = datanodes.getJSONObject(i+6*num).getJSONObject("data").getString("strdata");	
			String strdata_7 = datanodes.getJSONObject(i+7*num).getJSONObject("data").getString("strdata");
			String strdata_8 = datanodes.getJSONObject(i+8*num).getJSONObject("data").getString("strdata");
			String strdata_9 = datanodes.getJSONObject(i+9*num).getJSONObject("data").getString("strdata");	
			String strdata_A = datanodes.getJSONObject(i+10*num).getJSONObject("data").getString("strdata");
			String strdata_B = datanodes.getJSONObject(i+11*num).getJSONObject("data").getString("strdata");
			String strdata_C = datanodes.getJSONObject(i+12*num).getJSONObject("data").getString("strdata");	
			String strdata_D = datanodes.getJSONObject(i+13*num).getJSONObject("data").getString("strdata");
			String strdata_E = datanodes.getJSONObject(i+14*num).getJSONObject("data").getString("strdata");
			String strdata_F = datanodes.getJSONObject(i+15*num).getJSONObject("data").getString("strdata");	
			String strdata_G = datanodes.getJSONObject(i+16*num).getJSONObject("data").getString("strdata");
			String strdata_H = datanodes.getJSONObject(i+17*num).getJSONObject("data").getString("strdata");
			String strdata_I = datanodes.getJSONObject(i+18*num).getJSONObject("data").getString("strdata");	
			String strdata_J = datanodes.getJSONObject(i+19*num).getJSONObject("data").getString("strdata");
			
			setValueByreflect(hyxx,"fr_num",strdata);
			setValueByreflect(hyxx,"fr_nlmy_num",strdata_1);
			setValueByreflect(hyxx,"fr_cky_num",strdata_2);
			setValueByreflect(hyxx,"fr_zzy_num",strdata_3);
			setValueByreflect(hyxx,"fr_dlrqsc_num",strdata_4);
			setValueByreflect(hyxx,"fr_jzy_num",strdata_5);
			setValueByreflect(hyxx,"fr_jtccyz_num",strdata_6);
			setValueByreflect(hyxx,"fr_xxjsjrj_num",strdata_7);
			setValueByreflect(hyxx,"fr_pfls_num",strdata_8);
			setValueByreflect(hyxx,"fr_zscy_num",strdata_9);
			setValueByreflect(hyxx,"fr_jr_num",strdata_A);
			setValueByreflect(hyxx,"fr_fdc_num",strdata_B);
			setValueByreflect(hyxx,"fr_zlswfu_num",strdata_C);
			setValueByreflect(hyxx,"fr_kxjidz_num",strdata_D);
			setValueByreflect(hyxx,"fr_slhjgg_num",strdata_E);
			setValueByreflect(hyxx,"fr_jmqt_num",strdata_F);
			setValueByreflect(hyxx,"fr_jyfr_num",strdata_G);
			setValueByreflect(hyxx,"fr_wssbsf_num",strdata_H);
			setValueByreflect(hyxx,"fr_whtyyl_num",strdata_I);
			setValueByreflect(hyxx,"fr_ggsh_num",strdata_J);
			
	        JSONArray wds=datanodes.getJSONObject(i).getJSONArray("wds");
	        String sj=wds.getJSONObject(1).getString("valuecode");
			String unit = wdnodes0.getJSONArray("nodes").getJSONObject(0).getString("unit");
				
			//设置实体属性值
			setValueByreflect(hyxx,"statdate",sj);
			setValueByreflect(hyxx,"datefreq","年");
			setValueByreflect(hyxx,"areacode","10000");
			setValueByreflect(hyxx,"country","中国");
			setValueByreflect(hyxx,"province","");
			setValueByreflect(hyxx,"city","");
			setValueByreflect(hyxx,"district","");
			setValueByreflect(hyxx,"unit",unit);
			return hyxx;
			
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

