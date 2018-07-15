package com.meritit.customize.people;

import java.io.FileNotFoundException;
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
import com.meritit.customize.util.ExportData;
import com.meritit.customize.util.PropertyUtils;

public class MoneyCrawler2 {

	static Logger logger = Logger.getLogger(MoneyCrawler2.class);

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

		String initUrl = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsyd&rowcode=zb&colcode=sj&wds=[{\"wdcode\":\"reg\",\"valuecode\":\"110000\"}]&dfwds=[{\"wdcode\":\"zb\",\"valuecode\":\"A030103\"},{\"wdcode\":\"sj\",\"valuecode\":\"LAST86\"}]&k1=1521076771963";
		String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");

		Document document = CrawlerUtils.crawler(initUrl, "GET");

		List countList = obtainPeopleSalaryCount(document, cityName);
		return countList;
	}

	/**	
	 * 爬虫入口
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

		for (Object datas : dataList) {

			HashMap<Object, Object> map = new HashMap<>();

			String sData = datas.toString();
			JSONObject sDataObj = (JSONObject) JSONArray.parse(sData);
			// ss---zb.A030203_reg.220000_sj.2011
			String ss = sDataObj.get("code").toString();
			// System.err.println(ss);
			/**
			 * 获取省份年-第一产业人数
			 */
			if (ss.startsWith("zb.A03010304")) {
				String year = ss.substring(ss.lastIndexOf(".") + 1);
				String stringData = sDataObj.get("data").toString();
				Map mapData = (Map) JSON.parse(stringData);
				double parseDouble = Double.parseDouble(mapData.get("data").toString());

				// double保留两位小数点
				String data = String.format("%.2f", parseDouble);

				map.put("省份", cityName);
				map.put("年份", year);
				map.put("指标", "水力发电量_同比增长(%)");
				map.put("data", data);

				list.add(map);
			}


		}
		return list;

	}

	public static void main(String[] args) throws FileNotFoundException, IOException {
		MoneyCrawler2 peopleCountCrawler = new MoneyCrawler2();

		// 获取省份信息
		Map map = ProvinceCrawler.obtainProvince();
		
		List<Map> list02 = new ArrayList<Map>();
		list02.add(map);
		
		List<Map> list = new ArrayList<Map>();

		String code = "";
		String cityName = "";

		Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();

				// 调用爬虫入口,获取统计数据countList
			List countList = peopleCountCrawler.crawlerHandler(code, cityName);
			list.addAll(countList);


		// 导出数据到Excel中
		// 表头
		String[] headers = { "省份", "年份", "指标","data" };
		String[] Col = {  "省份", "年份", "指标","data" };
		
		ExportData.export(headers, Col, list);

	}

}
