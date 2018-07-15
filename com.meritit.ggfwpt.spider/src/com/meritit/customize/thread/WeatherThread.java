package com.meritit.customize.thread;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import javax.jms.JMSException;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meritit.common.QueueReceiver;
import com.meritit.common.QueueSender;
import com.meritit.common.util.CrawlerUtil;
import com.meritit.common.util.GetCity;
import com.meritit.common.util.PropertyUtils;
import com.meritit.common.util.WCrawlerUtils;
import com.meritit.customize.model.WeatherModel;
import com.meritit.customize.util.DbUtil;
/**
 * 天气数据
 * 
 * @author viki
 *
 */
public class WeatherThread implements Runnable {
	private String url;
	
	
	protected static Logger logger = Logger.getLogger(WeatherThread.class);
	
	static Properties id=PropertyUtils.loadProp("id");
	private String maxTime;
	
	private static final String RULEID = id.getProperty("Weatherrule_id");
	private static final String TASK_ID = id.getProperty("Weathertask_id");
	
	public WeatherThread(){};
	
	

	@Override
	public void run() {
		
		logger.info("开始抓取数据");
		initPageCrawler();
	
	}
	
	/**
	 * 抓取所有信息
	 * @throws JMSException 
	 */
	public void initPageCrawler(){
		
	//	Properties city=PropertyUtils.loadProp("city2");
		
		//获取所要爬取的城市列表
		Properties properties=PropertyUtils.loadProp("city");
		Set keyValue = properties.keySet();	 
		logger.info("共"+keyValue.size()+"个地区");		
		
		String lastTime=null;
		Map dates=DbUtil.getAll();
		logger.info("大小"+dates.size());
		if(dates==null||dates.size()==0){
			logger.info("无数据");
		}
		
		int nc=1;
		//按地区开始遍历
		for (Iterator it = keyValue.iterator(); it.hasNext();){	 
		
			String key = (String) it.next();
			System.out.println("开始爬取第"+nc+"个城市->"+key);
			nc++;
			
//			//二次爬虫
//			if(city.containsKey(key)){
//				logger.info("已存在");
//				continue;
//			}
//			
//			//获取hbase中获取该地区最新数据日期
//			QueueSender.sendTMsg(TASK_ID+"-"+RULEID+"-"+key);
//			
//			QueueReceiver receiver=new QueueReceiver();
//			
//			String lastTime=null;
//			String info=null;
//			String area=null;
//			
//			try {
//				info = receiver.getMsg();
//				System.out.println(info);
//				lastTime=info.substring(0,info.lastIndexOf("-"));
//				area=info.substring(info.lastIndexOf("-")+1, info.length());
//				logger.info("地区："+area);
//				logger.info("数据库中最新日期："+lastTime);
//			
//			} catch (JMSException e1) {
//				e1.printStackTrace();
//			} 
//			
//			
//			//消息队列匹配
//			while(!area.equals(key)){
//				logger.info("消息队列不匹配");
//				//QueueSender.sendTMsg(TASK_ID+"-"+RULEID+"-"+key);
//				
//				//读取消息队列数据
//				try {
//					logger.info("等待中："+key);
//					//Thread.sleep(3000);
//					//QueueSender.sendTMsg(TASK_ID+"-"+RULEID+"-"+key);
//					receiver=new QueueReceiver();
//					info = receiver.getMsg();
//					lastTime=info.substring(0,info.lastIndexOf("-"));
//					area=info.substring(info.lastIndexOf("-")+1, info.length());
//					logger.info("地区："+area);
//					logger.info("数据库中最新日期："+lastTime);
//					
//				} catch (JMSException e1) {
//					e1.printStackTrace();
//				}
//			}
//			
//			if(lastTime.equals("noData")){
//				lastTime="20110101";
//			}
			
			lastTime=(String) dates.get(key);
			
			if(lastTime==null)
				lastTime="20110101";
			
			logger.info("lastTime"+lastTime);
			getContent(lastTime,key);
			
 		  }//end city
			 		 
	}
		
		
		
	public void getContent(String lastTime,String key){
		
		//获取初始时间
				Long time = new Date().getTime();
				//年
				SimpleDateFormat dateCode = new SimpleDateFormat("yyyy");
				String insertdate = dateCode.format(time);
				//月
				SimpleDateFormat dateCode1 = new SimpleDateFormat("MM");
				String insertdate1 = dateCode1.format(time);
				//日 
				SimpleDateFormat dateCode2 = new SimpleDateFormat("dd");
				String insertdate2 = dateCode2.format(time);
				
				logger.info("当前日期为："+insertdate+"-"+insertdate1+"-"+insertdate2);	 
		
		
		
		//解析历史时间
		int lyear=Integer.parseInt(lastTime.substring(0, 4));
		int lmonth=Integer.parseInt(lastTime.substring(4, 6));
		int lday=Integer.parseInt(lastTime.substring(6, 8));
		
		
		//当前时间
		int year=Integer.parseInt(insertdate);
		int month=Integer.parseInt(insertdate1);
		int date=Integer.parseInt(insertdate2);
		
		
		int tyear=year;
		int tmonth=month;
		int tdate=date;
		
		

		JSONObject json=null;
		//记录数据条数
		int count=1;

		logger.info("开始抓取"+GetCity.getProvince(key)+" 历史数据");
		

		//如果当前为月初，历史记录中无当月数据or历史记录无当天数据
		//1月1日则历史数据截至上一年12月
		if(date==1){
			if(month==1){
				year--;
			}
			month--;
		}
		
		
		
		
		
		//从最近一个月开始倒序爬取（lyearlmonth-now）
		labe: while(lyear<=year){
				  while(lmonth<=12){
					 
					  //当前月
					 if(lyear-year==0&&lmonth-month==0){
							
						   logger.info("开始抓取第"+count+"条数据："+year+"年"+month+"月数据");
						    
							//解析上次查询最近月数据
							 url=WCrawlerUtils.initURL(key,lyear,lmonth);
							 
							 try {
								 
								System.out.println("开始解析url");
								json=WCrawlerUtils.getJSPage(url);
								count++;
								CrawlerSender(json,key);
								//System.out.println("lastMonth:"+json);
							 
							 } catch (MalformedURLException e) {
								e.printStackTrace();
							 }
							 	
							break labe;
					 }
					  
					logger.info("开始抓取第"+count+"条数据："+lyear+"年"+lmonth+"月数据");
					 
					 //解析页面内容并发送至消息队列
					 url=WCrawlerUtils.initURL(key,lyear,lmonth++);
					 try {
						// logger.info("开始解析url");
						json=WCrawlerUtils.getJSPage(url);
						count++;
					 } catch (MalformedURLException e) {
						e.printStackTrace();
					 }
					 
					 
					 //如果数据不存在，结束当前地区的循环爬取 
					 if(json==null){
						count--;
						continue;
					 }
					 
					 CrawlerSender(json,key);
					// System.out.println(json);
		
				  }//end month
				  lmonth=1;
				  lyear++;
				  
	 
		 	  }//end year
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.info("已成功发送历史天气数据");
		logger.info("开始抓取"+GetCity.getProvince(key)+" 当日[-"+tyear+tmonth+tdate+"-]据");
		 
		//解析当天天气内容并发送至消息队列
		tCrawlerSender(key,tmonth,tdate);

	}
		
	
	/**
	 * 获取改地区当天数据详细内容，并发送到ActiveMQ
	 */
	public void tCrawlerSender(String key,int year,int month){
	
		String url="http://tianqi.2345.com/today-"+key+".htm";
		Document doc=CrawlerUtil.getHtml(url);
		
		String inserttime = new Date().getTime() + "";
		Long time = new Date().getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String insertdate = sdf.format(time);
		
		
		Long time2 = new Date().getTime();
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		String today = sdf2.format(time2);
		
		
		String uuid = UUID.randomUUID().toString();
		

		/**
		 * 解析详细信息
		 */
		WeatherModel weather=new WeatherModel();
		
	
		//设置格式属性
		
		weather.setTaskid(TASK_ID);
		weather.setRuleid(RULEID);
		
		weather.setInserttime(inserttime);
		weather.setInsertdate(insertdate);
		
		
		String province=GetCity.getProvince(key);
		String city=GetCity.getCity(key);

		String district=GetCity.getQX(key);
		if(district==null){
			district="";
		}

		String statdate=today;
		setValueByreflect(weather,"statdate",statdate);
	    weather.setCountry("中国");
	    weather.setProvince(province);
	    weather.setCity(city);
	    
	    weather.setAreacode(key);
	    
	    String id = TASK_ID + "-" + RULEID + "-" + uuid;
//	    String id = TASK_ID + "-" + RULEID + "-" +weather.getAreacode()+"-"+ uuid;
		weather.setId(id);
	    
	    weather.setDistrict(district);
		weather.setUrl(weather.getStatdate()+"-"+weather.getAreacode());
		
		String param=doc.getElementsByClass("parameter").text();
		int qw=param.indexOf("当前气温");
		int fx=param.indexOf("风向");
		int fl=param.indexOf("风力");
		int sd=param.indexOf("湿度");
		String qw2=param.substring(qw+5, fx);
		String fx2=param.substring(fx+3, fl);
		String fl2=param.substring(fl+3, sd);
		
		String param2=doc.getElementsByClass("day").text();
		int day=param2.indexOf("今日白天");
		int high=param2.indexOf("最高");
		String dayW=param2.substring(day+5, high);
		String high2=param2.substring(high+3);
		
		String param3=doc.getElementsByClass("night").text();
		System.out.println("2:"+param3);
		int night=param3.indexOf("今天夜间");
		int low=param3.indexOf("最低");
		String nightW=param3.substring(day+5, high);
		String low2=param3.substring(low+3);
	
		String d_n_weather = dayW +"~"+nightW;
		
	
		//设置实体属性值
		
		setValueByreflect(weather,"datefreq","日");
		
		setValueByreflect(weather,"dim_maxt",high2);
		setValueByreflect(weather,"dim_mint",low2);
		setValueByreflect(weather,"dim_weather",d_n_weather);
		setValueByreflect(weather,"dim_windd",fx2);
		setValueByreflect(weather,"dim_windp",fl2);
		
		/**
		 * 发送数据到ActiveMQ
		 */
		String JContent = JSON.toJSONString(weather);
		logger.info("Today天气："+JContent);

		QueueSender.sendMsg(JContent);
		logger.info("已成功发送今日天气数据");
		DbUtil.updateMaxDate(key, weather.getStatdate().replace("-", ""));
		
	}
	
	/**
	 * 获取改地区某月数据详细内容，并发送到ActiveMQ
	 */
	private void CrawlerSender(JSONObject json,String key){

		//获取当月有效信息条数
		int num=json.getJSONArray("tqInfo").size()-1;
		
		//遍历获取数据并发送到ActiveMQ
		for(int i = 0; i < num; i++){
			String inserttime = new Date().getTime() + "";
			Long time = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insertdate = sdf.format(time);
			
			String uuid = UUID.randomUUID().toString();
			
			/**
			 * 解析详细信息
			 */
			WeatherModel weather=new WeatherModel();
			
			//爬取属性并设置实体属性值
			if(parseDetailHtml(weather,json,i)==null);	
			
			//设置格式属性
			
			weather.setTaskid(TASK_ID);
			weather.setRuleid(RULEID);
			
			weather.setInserttime(inserttime);
			weather.setInsertdate(insertdate);
			
			String province=GetCity.getProvince(key);
			String city=GetCity.getCity(key);

			String district=GetCity.getQX(key);
			if(district==null){
				district="";
			}
		    
			
		    weather.setCountry("中国");
		    weather.setProvince(province);
		    weather.setCity(city);
		    
		    weather.setAreacode(key);
		    
		    String id = TASK_ID + "-" + RULEID + "-"+ uuid;
			weather.setId(id);
			
		    weather.setDistrict(district);
		    weather.setUrl(weather.getStatdate()+"-"+weather.getAreacode());
			
			
			/**
			 * 发送数据到ActiveMQ
			 */
			String JContent = JSON.toJSONString(weather);
			//System.out.println(JContent);
			//logger.info("已成功解析第"+(i+1)+"条数据");
			QueueSender.sendMsg(JContent);	
			//logger.info("已成功发送第"+i+"条数据");
			
			//DbUtil.updateMaxDate(key, weather.getStatdate().replace("-", ""));
			
			}
	}	
		
		/**
		 * 解析详细信息页面
		 *
		 */
		private WeatherModel  parseDetailHtml(WeatherModel weather,JSONObject json,int i){

			
			//解析节点值
			JSONArray tqInfo =json.getJSONArray("tqInfo");
			
			String statdate=tqInfo.getJSONObject(i).getString("ymd");;
			String dim_maxt=tqInfo.getJSONObject(i).getString("bWendu");
			String dim_mint=tqInfo.getJSONObject(i).getString("yWendu");
			String dim_weather=tqInfo.getJSONObject(i).getString("tianqi");;
			String dim_windd=tqInfo.getJSONObject(i).getString("fengxiang");
			String dim_windp=tqInfo.getJSONObject(i).getString("fengli");
			
			
			
			//设置实体属性值
			setValueByreflect(weather,"statdate",statdate);
			setValueByreflect(weather,"datefreq","日");
			
			setValueByreflect(weather,"dim_maxt",dim_maxt);
			setValueByreflect(weather,"dim_mint",dim_mint);
			setValueByreflect(weather,"dim_weather",dim_weather);
			setValueByreflect(weather,"dim_windd",dim_windd);
			setValueByreflect(weather,"dim_windp",dim_windp);

			return weather;
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

