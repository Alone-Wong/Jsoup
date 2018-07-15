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

public class DqsczzCrawler {

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
				+ code + "\"}]&dfwds=[{\"wdcode\":\"zb\",\"valuecode\":\"A0201\"}]&k1=1513230219640";

		String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");

		Document document = CrawlerUtils.crawler(initUrl, "GET");

		//System.err.println(document);
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

		//System.out.println(dataList);
		for (Object datas : dataList) {

			HashMap<Object, Object> map01 = new HashMap<>();
			HashMap<Object, Object> map02 = new HashMap<>();
			HashMap<Object, Object> map03 = new HashMap<>();
			HashMap<Object, Object> map04 = new HashMap<>();
			HashMap<Object, Object> map05 = new HashMap<>();
			HashMap<Object, Object> map06 = new HashMap<>();
			HashMap<Object, Object> map07 = new HashMap<>();
			HashMap<Object, Object> map08 = new HashMap<>();
			HashMap<Object, Object> map09 = new HashMap<>();
			HashMap<Object, Object> map10 = new HashMap<>();
			HashMap<Object, Object> map11 = new HashMap<>();
			HashMap<Object, Object> map12 = new HashMap<>();
			HashMap<Object, Object> map13 = new HashMap<>();
			HashMap<Object, Object> map14 = new HashMap<>();
			HashMap<Object, Object> map15 = new HashMap<>();
			HashMap<Object, Object> map16 = new HashMap<>();

			String sData = datas.toString();
			JSONObject sDataObj = (JSONObject) JSONArray.parse(sData);
			// ss---zb.A030203_reg.220000_sj.2011
			String ss = sDataObj.get("code").toString();
			// System.err.println(ss);
			/**
			 * 获取省份年-第一产业人数
			 */
			if (ss.startsWith("zb.A020101") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map01.put("省份", cityName);
				map01.put("年份", year);
				map01.put("行业", "地区生产总值(亿元)");
				map01.put("工资", data);

				list.add(map01);
			}

			if (ss.startsWith("zb.A020102") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map02.put("省份", cityName);
				map02.put("年份", year);
				map02.put("行业", "第一产业增加值(亿元)");
				map02.put("工资", data);

				list.add(map02);
			}

			if (ss.startsWith("zb.A020103") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map03.put("省份", cityName);
				map03.put("年份", year);
				map03.put("行业", "第二产业增加值(亿元)");
				map03.put("工资", data);

				list.add(map03);
			}

			if (ss.startsWith("zb.A020104") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map04.put("省份", cityName);
				map04.put("年份", year);
				map04.put("行业", "第三产业增加值(亿元)");
				map04.put("工资", data);

				list.add(map04);
			}
			if (ss.startsWith("zb.A020105") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map05.put("省份", cityName);
				map05.put("年份", year);
				map05.put("行业", "农林牧渔业增加值(亿元)");
				map05.put("工资", data);

				list.add(map05);
			}

			if (ss.startsWith("zb.A020106") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map06.put("省份", cityName);
				map06.put("年份", year);
				map06.put("行业", "工业增加值(亿元)");
				map06.put("工资", data);

				list.add(map06);
			}
			if (ss.startsWith("zb.A020107") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map07.put("省份", cityName);
				map07.put("年份", year);
				map07.put("行业", "建筑业增加值(亿元)");
				map07.put("工资", data);

				list.add(map07);
			}

			if (ss.startsWith("zb.A020108") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map08.put("省份", cityName);
				map08.put("年份", year);
				map08.put("行业", "批发和零售业增加值(亿元)");
				map08.put("工资", data);

				list.add(map08);
			}
			if (ss.startsWith("zb.A020109") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map09.put("省份", cityName);
				map09.put("年份", year);
				map09.put("行业", "批发和零售贸易餐饮业增加值(亿元)");
				map09.put("工资", data);

				list.add(map09);
			}

			if (ss.startsWith("zb.A02010A") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map10.put("省份", cityName);
				map10.put("年份", year);
				map10.put("行业", "交通运输、仓储和邮政业增加值(亿元)");
				map10.put("工资", data);

				list.add(map10);
			}
			if (ss.startsWith("zb.A02010B") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map11.put("省份", cityName);
				map11.put("年份", year);
				map11.put("行业", "交通运输、仓储和邮电通信业增加值(亿元)");
				map11.put("工资", data);

				list.add(map11);
			}

			if (ss.startsWith("zb.A02010C") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map12.put("省份", cityName);
				map12.put("年份", year);
				map12.put("行业", "住宿和餐饮业增加值(亿元)");
				map12.put("工资", data);

				list.add(map12);
			}
			if (ss.startsWith("zb.A02010D") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map13.put("省份", cityName);
				map13.put("年份", year);
				map13.put("行业", "金融业增加值(亿元)");
				map13.put("工资", data);

				list.add(map13);
			}

			if (ss.startsWith("zb.A02010E") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map14.put("省份", cityName);
				map14.put("年份", year);
				map14.put("行业", "房地产业增加值(亿元)");
				map14.put("工资", data);

				list.add(map14);
			}
			if (ss.startsWith("zb.A02010F") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map15.put("省份", cityName);
				map15.put("年份", year);
				map15.put("行业", "其他行业增加值(亿元)");
				map15.put("工资", data);

				list.add(map15);
			}

			if (ss.startsWith("zb.A02010G") && (ss.endsWith(".2011") || ss.endsWith(".2012") || ss.endsWith(".2013")
					|| ss.endsWith(".2014") || ss.endsWith(".2015"))) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				String data = String.format("%.2f", parseDouble);

				map16.put("省份", cityName);
				map16.put("年份", year);
				map16.put("行业", "人均地区生产总值(元/人)");
				map16.put("工资", data);

				list.add(map16);
			}

		}

		return list;

	}

	public static void main(String[] args) {
		DqsczzCrawler peopleCountCrawler = new DqsczzCrawler();

		// 获取省份信息
		Map map = ProvinceCrawler.obtainProvince();

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
		String[] headers = { "省份", "年份", "行业", "工资" };
		String[] Col = { "省份", "年份",  "行业", "工资" };

		ExportData.export(headers, Col, list);

	}

}
