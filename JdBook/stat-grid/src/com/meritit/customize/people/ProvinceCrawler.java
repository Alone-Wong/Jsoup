package com.meritit.customize.people;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meritit.customize.util.CrawlerUtils;
import com.meritit.customize.util.PropertyUtils;

public class ProvinceCrawler {

	static String provinceUrl = "http://data.stats.gov.cn/easyquery.htm?m=QueryData&dbcode=fsnd&rowcode=reg&colcode=sj&wds=[{\"wdcode\":\"zb\",\"valuecode\":\"A030101\"}]&dfwds=[]&k1=1512453196774";

	String fileStoreType = PropertyUtils.getValueByKey("fileStoreType");

	static Document doc = CrawlerUtils.crawler(provinceUrl, "GET");

	/**
	 * 获取省份信息
	 */
	public  static Map obtainProvince() {
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
}
