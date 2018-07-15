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
import com.meritit.customize.model.HYXXModel;

/**
 * 分产业GDP增长情况（国内生产总值）
 * 
 * @author viki
 * 
 */
public class HYXX_FSThread implements Runnable {
	
	private String url2_0;
	private String url2_1;
	private String url2_2;
	private String url2_3;
	private String url2_4;
	private String url2_5;
	private String url2_6;
	private String url2_7;
	private String url2_8;
	private String url2_9;
	private String url2_A;
	private String url2_B;
	private String url2_C;
	private String url2_D;
	private String url2_E;
	private String url2_F;
	private String url2_G;
	private String url2_H;
	private String url2_I;
	private String url2_J;
	

	protected static Logger logger = Logger.getLogger(HYXX_FSThread.class);

	static Properties id = PropertyUtils.loadProp("id");

	private static final String RULEID = id.getProperty("HYXXrule_id");

	private static final String TASK_ID = id.getProperty("HYXXtask_id");

	public HYXX_FSThread() {};
	public HYXX_FSThread(String url2_0, String url2_1, String url2_2,
			String url2_3, String url2_4, String url2_5, String url2_6,
			String url2_7, String url2_8, String url2_9, String url2_A,
			String url2_B, String url2_C, String url2_D, String url2_E,
			String url2_F, String url2_G, String url2_H, String url2_I,
			String url2_J) {
		super();
		this.url2_0 = url2_0;
		this.url2_1 = url2_1;
		this.url2_2 = url2_2;
		this.url2_3 = url2_3;
		this.url2_4 = url2_4;
		this.url2_5 = url2_5;
		this.url2_6 = url2_6;
		this.url2_7 = url2_7;
		this.url2_8 = url2_8;
		this.url2_9 = url2_9;
		this.url2_A = url2_A;
		this.url2_B = url2_B;
		this.url2_C = url2_C;
		this.url2_D = url2_D;
		this.url2_E = url2_E;
		this.url2_F = url2_F;
		this.url2_G = url2_G;
		this.url2_H = url2_H;
		this.url2_I = url2_I;
		this.url2_J = url2_J;
	}
	

	@Override
	public void run() {
		logger.info("正在抓取：省份年度数据");
		initPageCrawler(url2_0,url2_1,url2_2,url2_3,url2_4,url2_5,url2_6,url2_7,url2_8,url2_9,url2_A,url2_B,url2_C,url2_D,url2_E,url2_F,url2_G,url2_H,url2_I,url2_J);
	}

	/**
	 * 抓取所有信息
	 *
	 */
	public void initPageCrawler(String url2_02, String url2_12, String url2_22, String url2_32, String url2_42, String url2_52, String url2_62, String url2_72, String url2_82, String url2_92, String url2_A2, String url2_B2, String url2_C2, String url2_D2, String url2_E2, String url2_F2, String url2_G2, String url2_H2, String url2_I2, String url2_J2) {

		JSONObject json = CrawlerUtil.crawler(url2_02);
		JSONObject json1 = CrawlerUtil.crawler(url2_12);
		JSONObject json2 = CrawlerUtil.crawler(url2_22);
		JSONObject json3 = CrawlerUtil.crawler(url2_32);
		JSONObject json4 = CrawlerUtil.crawler(url2_42);
		JSONObject json5 = CrawlerUtil.crawler(url2_52);
		JSONObject json6 = CrawlerUtil.crawler(url2_62);
		JSONObject json7 = CrawlerUtil.crawler(url2_72);
		JSONObject json8 = CrawlerUtil.crawler(url2_82);
		JSONObject json9= CrawlerUtil.crawler(url2_92);
		JSONObject jsonA = CrawlerUtil.crawler(url2_A2);
		JSONObject jsonB = CrawlerUtil.crawler(url2_B2);
		JSONObject jsonC = CrawlerUtil.crawler(url2_C2);
		JSONObject jsonD= CrawlerUtil.crawler(url2_D2);
		JSONObject jsonE = CrawlerUtil.crawler(url2_E2);
		JSONObject jsonF = CrawlerUtil.crawler(url2_F2);
		JSONObject jsonG = CrawlerUtil.crawler(url2_G2);
		JSONObject jsonH= CrawlerUtil.crawler(url2_H2);
		JSONObject jsonI = CrawlerUtil.crawler(url2_I2);
		JSONObject jsonJ = CrawlerUtil.crawler(url2_J2);
		
		logger.info("获取到数据json串：" + json);
		
		int num = json.getJSONObject("returndata").getJSONArray("datanodes").size();
		logger.info("共" + num + "条数据");

		logger.info("开始解析数据...");
		CrawlerSender(json,json1,json2, json3, json4, json5,json6,json7,json8,json9,jsonA,jsonB,jsonC,jsonD,jsonE,jsonF,jsonG,jsonH,jsonI,jsonJ,num);

	}

	/**
	 * 获取数据详细内容，并发送到ActiveMQ
	 *
	 */
	private void CrawlerSender(JSONObject json, JSONObject json1, JSONObject json2, JSONObject json3, JSONObject json4, JSONObject json5, JSONObject json6, JSONObject json7, JSONObject json8, JSONObject json9, JSONObject jsonA, JSONObject jsonB, JSONObject jsonC, JSONObject jsonD, JSONObject jsonE, JSONObject jsonF, JSONObject jsonG, JSONObject jsonH, JSONObject jsonI, JSONObject jsonJ, int num) {

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
			HYXXModel hyxx = new HYXXModel();

			// 爬取属性并设置实体属性值
			if (parseDetailHtml(hyxx,json,json1,json2, json3, json4, json5,json6,json7,json8,json9,jsonA,jsonB,jsonC,jsonD,jsonE,jsonF,jsonG,jsonH,jsonI,jsonJ, i, num) == null);

			// 设置格式属性
			hyxx.setTaskid(TASK_ID);
			hyxx.setRuleid(RULEID);

			hyxx.setInserttime(inserttime);
			hyxx.setInsertdate(insertdate);
			hyxx.setId(id);

			hyxx.setUrl(hyxx.getStatdate() + hyxx.getAreacode()+ hyxx.getFr_jr_num() + "-" + url2_0);

			/**
			 * 发送数据到ActiveMQ
			 */
			String JContent = JSON.toJSONString(hyxx);
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
	private HYXXModel parseDetailHtml(HYXXModel hyxx, JSONObject json, JSONObject json1, JSONObject json2, JSONObject json3, JSONObject json4, JSONObject json5, JSONObject json6, JSONObject json7, JSONObject json8, JSONObject json9, JSONObject jsonA, JSONObject jsonB, JSONObject jsonC, JSONObject jsonD, JSONObject jsonE, JSONObject jsonF, JSONObject jsonG, JSONObject jsonH, JSONObject jsonI, JSONObject jsonJ, int i, int num) {

		// 解析节点值
		JSONObject returndata = json.getJSONObject("returndata");
		
		JSONArray datanodes = returndata.getJSONArray("datanodes");
		JSONArray datanodes1 = json1.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes2 = json2.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes3 = json3.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes4 = json4.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes5 = json5.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes6 = json6.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes7 = json7.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes8 = json8.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodes9 = json9.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesA = jsonA.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesB = jsonB.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesC = jsonC.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesD = jsonD.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesE = jsonE.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesF = jsonF.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesG = jsonG.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesH = jsonH.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesI = jsonI.getJSONObject("returndata").getJSONArray("datanodes");
		JSONArray datanodesJ = jsonJ.getJSONObject("returndata").getJSONArray("datanodes");
		

		//获取指标值
		String strdata=datanodes.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_1 = datanodes1.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_2 = datanodes2.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_3 = datanodes3.getJSONObject(i).getJSONObject("data").getString("strdata");	
		String strdata_4 = datanodes4.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_5 = datanodes5.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_6 = datanodes6.getJSONObject(i).getJSONObject("data").getString("strdata");	
		String strdata_7 = datanodes7.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_8 = datanodes8.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_9 = datanodes9.getJSONObject(i).getJSONObject("data").getString("strdata");	
		String strdata_A = datanodesA.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_B = datanodesB.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_C = datanodesC.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_D = datanodesD.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_E = datanodesE.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_F = datanodesF.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_G = datanodesG.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_H = datanodesH.getJSONObject(i).getJSONObject("data").getString("strdata");
		String strdata_I = datanodesI.getJSONObject(i).getJSONObject("data").getString("strdata");	
		String strdata_J = datanodesJ.getJSONObject(i).getJSONObject("data").getString("strdata");
		
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
		
		
		JSONObject wdnodes0 = (JSONObject) returndata.getJSONArray("wdnodes").get(0);
		JSONArray wds = datanodes.getJSONObject(i).getJSONArray("wds");
		
		String sj = wds.getJSONObject(2).getString("valuecode");
		
		String regCode = wds.getJSONObject(1).getString("valuecode");
		String reg = getCity(regCode);
		if (regCode.equals("110000") || regCode.equals("120000")
				|| regCode.equals("500000") || regCode.equals("310000")) {

			setValueByreflect(hyxx, "city", reg);
		}
		
		String unit = wdnodes0.getJSONArray("nodes").getJSONObject(0).getString("unit");


		// 设置实体属性值
		setValueByreflect(hyxx, "statdate", sj);
		setValueByreflect(hyxx, "datefreq", "年");
		setValueByreflect(hyxx, "country", "中国");

		setValueByreflect(hyxx, "province", reg);
		setValueByreflect(hyxx, "city", "");
		setValueByreflect(hyxx, "district", "");
		setValueByreflect(hyxx, "areacode", regCode);
		setValueByreflect(hyxx, "unit", unit);

		return hyxx;
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
