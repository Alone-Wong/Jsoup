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
 * 
 * @author viki
 * 
 */
public class FCYGDP_FSThread implements Runnable {
	
	private String url3_1;
	private String url3_2;
	private String url3_3;
	private String url3_4;

	protected static Logger logger = Logger.getLogger(FCYGDP_FSThread.class);

	static Properties id = PropertyUtils.loadProp("id");

	private static final String RULEID = id.getProperty("FCYGGDPrule_id");

	private static final String TASK_ID = id.getProperty("FCYGGDPtask_id");

	public FCYGDP_FSThread() {};

	public FCYGDP_FSThread(String url3_1, String url3_2, String url3_3,String url3_4) {
		this.url3_1 = url3_1;
		this.url3_2 = url3_2;
		this.url3_3 = url3_3;
		this.url3_4 = url3_4;
	}

	@Override
	public void run() {
		logger.info("正在抓取：省份年度数据");
		initPageCrawler(url3_1, url3_2, url3_3, url3_4);
	}

	/**
	 * 抓取所有信息
	 */
	public void initPageCrawler(String url, String url2, String url3,String url4) {

		JSONObject json = CrawlerUtil.crawler(url);
		JSONObject json2 = CrawlerUtil.crawler(url2);
		JSONObject json3 = CrawlerUtil.crawler(url3);
		JSONObject json4 = CrawlerUtil.crawler(url4);

		logger.info("获取到数据json1串：" + json);
		logger.info("获取到数据json2串：" + json2);
		logger.info("获取到数据json3串：" + json3);
		logger.info("获取到数据json4串：" + json4);
		
		int num = json.getJSONObject("returndata").getJSONArray("datanodes").size();
		logger.info("共" + num + "条数据");

		logger.info("开始解析数据...");
		CrawlerSender(json, json2, json3, json4, num);

	}

	/**
	 * 获取数据详细内容，并发送到ActiveMQ
	 */
	private void CrawlerSender(JSONObject json, JSONObject json2,JSONObject json3, JSONObject json4, int num) {

		// 遍历获取数据并发送到ActiveMQ
		for (int i = 0; i < num; i++) {
			String inserttime = new Date().getTime() + "";
			Long time = new Date().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String insertdate = sdf.format(time);

			String uuid = UUID.randomUUID().toString();
			String id = TASK_ID + "-" + RULEID + "-" + uuid;

			/**
			 * 解析详细信息
			 */
			FCYGDPModel fcy = new FCYGDPModel();

			// 爬取属性并设置实体属性值
			if (parseDetailHtml(fcy, json, json2, json3, json4, i, num) == null);

			// 设置格式属性
			fcy.setTaskid(TASK_ID);
			fcy.setRuleid(RULEID);

			fcy.setInserttime(inserttime);
			fcy.setInsertdate(insertdate);
			fcy.setId(id);

			fcy.setUrl(fcy.getStatdate() + fcy.getAreacode()+ fcy.getDim_sszz_val() + "-" + url3_1);

			/**
			 * 发送数据到ActiveMQ
			 */
			String JContent = JSON.toJSONString(fcy);
			System.out.println(JContent);
			logger.info("已成功解析第" + (i + 1) + "条数据");
			 QueueSender.sendMsg(JContent);
			 logger.info("已成功发送第"+i+"条数据");
			
		}
	}

	/**
	 * 解析详细信息页面
	 * 
	 */
	private FCYGDPModel parseDetailHtml(FCYGDPModel fcy, JSONObject json,JSONObject json2, JSONObject json3, JSONObject json4, int i, int num) {

		// 解析节点值
		JSONObject returndata = json.getJSONObject("returndata");
		
		JSONArray datanodes = returndata.getJSONArray("datanodes");
		JSONArray datanodes2 = json2.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes3 = json3.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes4 = json4.getJSONObject("returndata").getJSONArray("datanodes");

		// 指标值
		String strdata = datanodes.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata2 = datanodes2.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata3 = datanodes3.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata4 = datanodes4.getJSONObject(i).getJSONObject("data").getString("strdata");

		
		JSONObject wdnodes0 = (JSONObject) returndata.getJSONArray("wdnodes").get(0);
		
		JSONArray wds = datanodes.getJSONObject(i).getJSONArray("wds");
		
		String sj = wds.getJSONObject(2).getString("valuecode");
		String regCode = wds.getJSONObject(1).getString("valuecode");
		String reg = getCity(regCode);
		if (regCode.equals("110000") || regCode.equals("120000")
				|| regCode.equals("500000") || regCode.equals("310000")) {

			setValueByreflect(fcy, "city", reg);
		}
		
		String unit = wdnodes0.getJSONArray("nodes").getJSONObject(0).getString("unit");


		// 设置实体属性值
		setValueByreflect(fcy, "statdate", sj);
		setValueByreflect(fcy, "datefreq", "年");
		setValueByreflect(fcy, "country", "中国");

		setValueByreflect(fcy, "province", reg);
		setValueByreflect(fcy, "city", "");
		setValueByreflect(fcy, "district", "");
		setValueByreflect(fcy, "areacode", regCode);

		setValueByreflect(fcy, "dim_sszz_val", strdata);
		setValueByreflect(fcy, "dim_1ind_val", strdata2);
		setValueByreflect(fcy, "dim_2ind_val", strdata3);
		setValueByreflect(fcy, "dim_3ind_val", strdata4);

		setValueByreflect(fcy, "unit", unit);

		return fcy;
	}

	/**
	 * 根据反射原理设置详细信息实体字段
	 * 
	 * @param t
	 *            实体对象
	 * @param name
	 *            实体字段
	 * @param value
	 *            字段值
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	private <T> void setValueByreflect(T t, String name, String value) {
		Class clazz = t.getClass();
		String newName = Character.toUpperCase(name.charAt(0))
				+ name.substring(1);
		try {
			Method method = clazz.getDeclaredMethod("set" + newName,String.class);
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
	private String getCity(String code) {
		Properties cityCode = PropertyUtils.loadProp("code");
		String name = cityCode.getProperty(code);
		return name;
	}

}
