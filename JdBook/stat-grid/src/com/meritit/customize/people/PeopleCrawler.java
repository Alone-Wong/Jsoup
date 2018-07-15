package com.meritit.customize.people;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meritit.customize.StatGridCrawler;
import com.meritit.customize.util.CrawlerUtils;
import com.meritit.customize.util.ExportExcel;
import com.meritit.customize.util.PropertyUtils;


public class PeopleCrawler {

	static Logger logger = Logger.getLogger(PeopleCrawler.class);

	private static Properties prop;

	private Document doc = null;

	/**
	 * 第二产业各年总额
	 */
	static double count2015 = 0;
	static double count2014 = 0;
	static double count2013 = 0;
	static double count2012 = 0;
	static double count2011 = 0;

	static double sum2015 = 0;
	static double sum2014 = 0;
	static double sum2013 = 0;
	static double sum2012 = 0;
	static double sum2011 = 0;

	static {
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
	public List crawlerHandler(String code, String cityName) {

		String initUrl = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsnd&rowcode=zb&colcode=sj&wds=[{\"wdcode\":\"reg\",\"valuecode\":\""
				+ code + "\"}]&dfwds=[{\"wdcode\":\"zb\",\"valuecode\":\"A0401\"}]&k1=1512442817233";
		String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");

		Document document = CrawlerUtils.crawler(initUrl, "GET");

		List countList = obtainPeople(document, cityName);

		// System.out.println(countList);

		return countList;
	}


	/**
	 * 爬虫入口--获取省份 名称 编号
	 * 
	 * @return
	 */
	public Map crawlerHandlerProvince() {
		String provinceUrl = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsnd&rowcode=reg&colcode=sj&wds=[{\"wdcode\":\"zb\",\"valuecode\":\"A040101\"}]&dfwds=[]&k1=1512453196774";

		String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");

		Document provinceDocument = CrawlerUtils.crawler(provinceUrl, "GET");

		Map provinceMap = obtainProvince(provinceDocument);

		return provinceMap;
	}

	/**
	 * 爬虫入口--获取人口年份信息
	 * 
	 * @param doc
	 * @return
	 */
	private List obtainPeople(Document doc, String cityName) {
		List list = new ArrayList();
		// 第一产业
		List<Double> firstIndustry = new ArrayList<Double>();
		HashMap<Object, Object> firstMap = new HashMap<>();
		
		// 第二产业
		List<Double> secondIndustry = new ArrayList<Double>();

		// 第三产业
		List<Double> thirdIndustry = new ArrayList<Double>();

		String html = doc.body().html();

		JSONObject parse = (JSONObject) JSONArray.parse(html);
		String string = parse.get("returndata").toString();
		JSONObject parse02 = (JSONObject) JSONArray.parse(string);
		String object = parse02.get("datanodes").toString();

		List dataList = (List) JSONArray.parse(object);

		for (Object datas : dataList) {
			String sData = datas.toString();
			JSONObject sDataObj = (JSONObject) JSONArray.parse(sData);
			String ss = sDataObj.get("code").toString();
			/**
			 * 获取省份年-第一产业人数
			 */
			if (!ss.startsWith("zb.A040101")) {
				if (ss.startsWith("zb.A040102") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
						|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
					String year = ss.substring(ss.lastIndexOf(".") + 1);
					String stringData = sDataObj.get("data").toString();
					Map mapData = (Map) JSON.parse(stringData);
					double parseDouble = Double.parseDouble(mapData.get("data").toString());
					firstIndustry.add(parseDouble);

					firstMap.put("省份", cityName);
					firstMap.put("年份", year);
					firstMap.put("产业结构", "第一产业");
					firstMap.put("人口数量", parseDouble);


				}
			}
			
			list.add(firstMap);

			/**
			 * 获取省份年-第二产业人数
			 */

			if (!ss.startsWith("zb.A040101")) {
				if ((ss.startsWith("zb.A040103") || ss.startsWith("zb.A040104") || ss.startsWith("zb.A040105")
						|| ss.startsWith("zb.A040106") || ss.startsWith("zb.A040107"))
						&& 
						(ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013") || ss.endsWith(".2014")|| ss.endsWith(".2015"))) {
					//获取年份
					String year = ss.substring(ss.lastIndexOf(".") + 1);
					String stringData = sDataObj.get("data").toString();
					Map mapData = (Map) JSON.parse(stringData);
					double parseDouble = Double.parseDouble(mapData.get("data").toString());
					secondIndustry.add(parseDouble);

				}
			}
			
			//遍历第二产业
			for (int i = 0; i < secondIndustry.size(); i++) {
				if (i % 5 == 0) {
					count2015 += secondIndustry.get(i);
				}
				if (i % 5 == 1) {
					count2014 += secondIndustry.get(i);
				}
				if (i % 5 == 2) {
					count2013 += secondIndustry.get(i);
				}
				if (i % 5 == 3) {
					count2012 += secondIndustry.get(i);
				}
				if (i % 5 == 4) {
					count2011 += secondIndustry.get(i);
				}

			}
			//将第二产业获取的数据加至Map中,封装到List中
			for (int i = 2011; i < 2016; i++) {
				HashMap<Object, Object> secondMap = new HashMap<>();
				secondMap.put("省份", cityName);
				secondMap.put("年份", i);
				secondMap.put("产业结构", "第二产业");
				//通过反射获取静态成员变量
				secondMap.put("人口数量", reflect("count" + i));
				//封转Map至List中
				list.add(secondMap);

			}

			/**
			 * 获取省份年-第三产业人数
			 */
			if ((!ss.startsWith("zb.A040101") && !ss.startsWith("zb.A040102") && !ss.startsWith("zb.A040103")&& 
					!ss.startsWith("zb.A040104") && 
					!ss.startsWith("zb.A040105") && !ss.startsWith("zb.A040106")&& !ss.startsWith("zb.A040107"))
					&& 
					(ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013") 
							|| ss.endsWith(".2014")|| ss.endsWith(".2015"))) {
				// 截取年份
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				// 获取data节点 转换为map
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());
				thirdIndustry.add(parseDouble);

			}

		}

		
		//遍历第三产业 获取每年人口总数
		for (int i = 0; i < thirdIndustry.size(); i++) {
			if (i % 5 == 0) {
				sum2015 += thirdIndustry.get(i);
			}
			if (i % 5 == 1) {
				sum2014 += thirdIndustry.get(i);
			}
			if (i % 5 == 2) {
				sum2013 += thirdIndustry.get(i);
			}
			if (i % 5 == 3) {
				sum2012 += thirdIndustry.get(i);
			}
			if (i % 5 == 4) {
				sum2011 += thirdIndustry.get(i);
			}
		}

		//封装Map至List中
		for (int i = 2011; i < 2016; i++) {
			HashMap<Object, Object> thirdMap = new HashMap<>();
			thirdMap.put("省份", cityName);
			thirdMap.put("年份", i);
			thirdMap.put("产业结构", "第三产业");
			thirdMap.put("人口数量", reflect("sum" + i));
			//
			list.add(thirdMap);

		}

		return list;
	}

	/**
	 * 获取省份信息
	 */
	private Map obtainProvince(Document doc) {
		Map<Object, Object> map = new HashMap<>();

		String html = doc.body().html();

		JSONObject parse = (JSONObject) JSONArray.parse(html);
		String string = parse.get("returndata").toString();
		JSONObject parse02 = (JSONObject) JSONArray.parse(string);

		// 所有的省市
		String string2 = parse02.get("wdnodes").toString();
		List provinceList = (List) JSONArray.parse(string2);
		String object = provinceList.get(1).toString();
		Map parse2 = (Map) JSONObject.parse(object);
		List nodesList = (List) parse2.get("nodes");
		for (Object node : nodesList) {
			Map nodeMap = (Map) JSONObject.parse(node.toString());
			String cityName = nodeMap.get("cname").toString();
			String code = nodeMap.get("code").toString();
			map.put(cityName, code);
		}

		return map;
	}

	/**
	 * 通过反射获取静态成员变量
	 * @param string
	 * @return
	 */
	public static String reflect(String string) {
		double d = 0;
		String s = "";
		Field fieldPassword;
		try {
			fieldPassword = PeopleCrawler.class.getDeclaredField(string);

			PeopleCrawler peopleCrawler = new PeopleCrawler();

			d = (double) fieldPassword.get(peopleCrawler);
			//取小数点后两位
			s = String.format("%.2f", d);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}

		return s;
	}
	/**
	 * 导出数据到Excel
	 * @param list
	 */
		public static void exportExcel(List list) {
			// 表头
			String[] headers = {"省份", "年份", "产业结构", "人口数量" };
			String[] Col = { "省份", "年份", "产业结构", "人口数量" };

			ExportExcel<Map> ex = new ExportExcel<Map>();
			// 生成Excel
			HSSFWorkbook workbook = ex.exportExcel("sheet2", headers, Col, list, null);

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

			Date date = new Date();
			String path = "E:\\test\\";
			String fileName = "test_" + sdf.format(date) + ".xls";

			String baseuploadpath = path + fileName;

			File f = new File(baseuploadpath);
			if (!f.getParentFile().exists()) {
				f.getParentFile().mkdirs();
			}
			try {
				f.createNewFile();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
			OutputStream out = null;
			try {
				out = new FileOutputStream(f);
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}

			// 使用workbook写入
			try {
				workbook.write(out);
			} catch (IOException e) {
				e.printStackTrace();
			}

			System.out.println("完成");
		}

	public static void main(String[] args) {
		PeopleCrawler peopleCrawler = new PeopleCrawler();
		/**
		 * 各省对应的编号 北京:110000,天津:120000,河北:130000,山西:140000,内蒙古:150000,
		 * 辽宁:210000,吉林:220000,黑龙江:230000
		 * 上海:310000,江苏:320000,浙江:330000,安徽:340000,福建:350000,江西:360000,山东:
		 * 370000, 河南:410000,湖北:420000,湖南:430000,广东:440000,广西:450000,海南:460000,
		 * 重庆:500000,四川;510000,贵州:520000,云南:530000,西藏:540000,
		 * 山西:610000,甘肃:620000,青海:630000,宁夏:640000,新疆;650000,
		 * 
		 */
		Map map = peopleCrawler.crawlerHandlerProvince();

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		String code = "";
		String cityName = "";

		Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

		while (entries.hasNext()) {

			Entry<String, String> entry = entries.next();

			cityName = entry.getKey().toString();
			code = entry.getValue().toString();
			//调用爬虫入口,获取统计数据countList
			List countList = peopleCrawler.crawlerHandler(code, cityName);
			list.addAll(countList);

		}
		//System.out.println(list);
		//导出数据到Excel中
		exportExcel(list);

	}
}
