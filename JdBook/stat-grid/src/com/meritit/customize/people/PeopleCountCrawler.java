package com.meritit.customize.people;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meritit.customize.StatGridCrawler;
import com.meritit.customize.util.CrawlerUtils;
import com.meritit.customize.util.PropertyUtils;

public class PeopleCountCrawler {

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

		String initUrl = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsnd&rowcode=zb&colcode=sj&wds=[{\"wdcode\":\"reg\",\"valuecode\":\""+code+"\"}]&dfwds=[{\"wdcode\":\"zb\",\"valuecode\":\"A0301\"}]&k1=1512540125561";
	
		String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");

		Document document = CrawlerUtils.crawler(initUrl, "GET");
		
		List countList = obtainPeopleCount(document, cityName);

		return countList;
	}


	/**
	 * 爬虫入口--获取人口年份信息
	 * 
	 * @param doc
	 * @return
	 */
	private List obtainPeopleCount(Document doc, String cityName) {
		List list = new ArrayList();
		String html = doc.body().html();

		JSONObject parse = (JSONObject) JSONArray.parse(html);
		String string = parse.get("returndata").toString();
		JSONObject parse02 = (JSONObject) JSONArray.parse(string);
		String object = parse02.get("datanodes").toString();

		List dataList = (List) JSONArray.parse(object);

		for (Object datas : dataList) {
			
			HashMap<Object, Object> firstMap = new HashMap<>();
			HashMap<Object, Object> secondMap = new HashMap<>();
			HashMap<Object, Object> thirdMap = new HashMap<>();
			
			String sData = datas.toString();
			JSONObject sDataObj = (JSONObject) JSONArray.parse(sData);
			//ss---zb.A030203_reg.220000_sj.2011
			String ss = sDataObj.get("code").toString();
			//System.err.println(ss);
			/**
			 * 获取省份年-第一产业人数
			 */
			if (ss.startsWith("zb.A030101") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				//double保留两位小数点
				String data = String.format("%.2f", parseDouble);
				
				firstMap.put("省份", cityName);
				firstMap.put("年份", year);
				firstMap.put("年末常住人口(万人)", data);

				
				list.add(firstMap);
			}
			
//				if (ss.startsWith("zb.A030102") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
//						|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
//					String year = ss.substring(ss.lastIndexOf(".") + 1);
//					String stringData = sDataObj.get("data").toString();
//					Map mapData = (Map) JSON.parse(stringData);
//					double parseDouble = Double.parseDouble(mapData.get("data").toString());
//
//					String data = String.format("%.2f", parseDouble);
//					
//					secondMap.put("省份", cityName);
//					secondMap.put("年份", year);
//					secondMap.put("人口", "城镇人口(万人)");
//					secondMap.put("数量", data);
//
//
//					list.add(secondMap);
//				}
//
//				if (ss.startsWith("zb.A030103") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
//						|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
//					String year = ss.substring(ss.lastIndexOf(".") + 1);
//					String stringData = sDataObj.get("data").toString();
//					Map mapData = (Map) JSON.parse(stringData);
//					double parseDouble = Double.parseDouble(mapData.get("data").toString());
//
//					String data = string.format("%.2f", parseDouble);
//					
//					thirdMap.put("省份", cityName);
//					thirdMap.put("年份", year);
//					thirdMap.put("人口", "乡村人口(万人)");
//					thirdMap.put("数量", data);
//
//					list.add(thirdMap);
//				}
//			

			}
		
		return list;

	}

	public static void main(String[] args) {
		PeopleCountCrawler peopleCountCrawler = new PeopleCountCrawler();

		//获取省份信息
		Map map = ProvinceCrawler.obtainProvince();

		List<Map> list = new ArrayList<Map>();

		String code = "";
		String cityName = "";

		Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

		while (entries.hasNext()) {

			Entry<String, String> entry = entries.next();

			cityName = entry.getKey().toString();
			code = entry.getValue().toString();
			//调用爬虫入口,获取统计数据countList
			List countList = peopleCountCrawler.crawlerHandler(code, cityName);
			list.addAll(countList);

		}

		//导出数据到Excel中
		// 表头
		String[] headers = { "省份", "年份", "年末常住人口(万人)" };
		String[] Col = { "省份", "年份", "年末常住人口(万人)" };

		ExportData.export(headers, Col, list);

	}
}
