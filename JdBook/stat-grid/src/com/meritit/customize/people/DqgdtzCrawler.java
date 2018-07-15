package com.meritit.customize.people;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meritit.customize.StatGridCrawler;
import com.meritit.customize.util.CrawlerUtils;
import com.meritit.customize.util.PropertyUtils;

public class DqgdtzCrawler {

	static Logger logger = Logger.getLogger(PeopleCrawler.class);

	private static Properties prop;

	private Document doc = null;

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
				+ code
				+ "\"}]&dfwds=[{\"wdcode\":\"zb\",\"valuecode\":\"A0501\"},{\"wdcode\":\"sj\",\"valuecode\":\"LAST5\"}]&k1=1513230219640";
		// http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsnd&rowcode=zb&colcode=sj&wds=[{"wdcode":"reg","valuecode":"110000"}]&dfwds=[{"wdcode":"zb","valuecode":"A0501"}]&k1=1513300828879

		String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");

		Document document = CrawlerUtils.crawler(initUrl, "GET");

		// System.err.println(document);
		List countList = obtainPeopleSalaryCount(document, cityName);
		return countList;
	}

	/**
	 * 爬虫入口--获取人口年份信息
	 * 
	 * @param doc
	 * @return
	 */
	private List obtainPeopleSalaryCount(Document doc, String cityName) {
		List list = new ArrayList();
		String html = doc.body().html();

		JSONObject parse = (JSONObject) JSONArray.parse(html);
		String string = parse.get("returndata").toString();
		JSONObject parse02 = (JSONObject) JSONArray.parse(string);
		String object = parse02.get("datanodes").toString();

		List dataList = (List) JSONArray.parse(object);

		// System.out.println(dataList);
		for (Object datas : dataList) {

			HashMap<Object, Object> map01 = new HashMap<>();
			HashMap<Object, Object> map02 = new HashMap<>();
			HashMap<Object, Object> map03 = new HashMap<>();

			String sData = datas.toString();
			JSONObject sDataObj = (JSONObject) JSONArray.parse(sData);
			// ss---zb.A030203_reg.220000_sj.2011
			String ss = sDataObj.get("code").toString();
			// System.err.println(ss);
			/**
			 * 获取省份年-第一产业人数
			 */
			if (ss.startsWith("zb.A050101")) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map01.put("省份", cityName);
				map01.put("年份", year);
				//map01.put("投资", "全社会固定资产投资(亿元)");
				map01.put("全社会固定资产投资(亿元)", data);

				list.add(map01);
			}

/*			if (ss.startsWith("zb.A050102") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map02.put("省份", cityName);
				map02.put("年份", year);
				map02.put("投资", "城镇固定资产投资(亿元)");
				map02.put("金额", data);

				list.add(map02);
			}

			if (ss.startsWith("zb.A050103") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map03.put("省份", cityName);
				map03.put("年份", year);
				map03.put("投资", "房地产开发投资(亿元)");
				map03.put("金额", data);

				list.add(map03);
			}*/

			//return list;
		}
		return list;

	}

	public static void main(String[] args) {
		DqgdtzCrawler peopleCountCrawler = new DqgdtzCrawler();

		// 获取省份信息
		Map map = ProvinceCrawler.obtainProvince();
		
		List<Map> list02 = new ArrayList<Map>();
		list02.add(map);
		
		List<Map> list = new ArrayList<Map>();

		String code = "";
		String cityName = "";

		Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

		while (entries.hasNext()) {

			Entry<String, String> entry = entries.next();

			cityName = entry.getKey().toString();
			code = entry.getValue().toString();
			// 调用爬虫入口,获取统计数据countList
			List countList = peopleCountCrawler.crawlerHandler(code, cityName);
			list.addAll(countList);

		}

		// 导出数据到Excel中
		// 表头
//		String[] headers = { "省份", "年份", "全社会固定资产投资(亿元)" };
//		String[] Col = { "省份", "年份", "全社会固定资产投资(亿元)"};
		
		String[] headers = {"编号"};
		String[] Col = {"cityName"};

//		ExportData.export(headers, Col, list);
		ExportData.export(headers, Col, list02);

	}

}
