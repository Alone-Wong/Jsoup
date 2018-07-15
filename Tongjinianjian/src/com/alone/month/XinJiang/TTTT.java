package com.alone.month.XinJiang;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class TTTT {

	public static void main(String[] args) throws URISyntaxException {
		String url = "http://www.xjtj.gov.cn/sjcx/ydsj_3329/ydsj.html";
		String charset = "utf-8";
		String selector = "select option[parentid]";
		String selector02 = ".r_detial_title";
		String selector03 = ".r_detail_content";

		String filepath = "G:\\数据\\新疆\\省\\";

		// 抓取页面数据
		Document doc = CrawlerUtil.getFromHtml02(url, charset);

		Elements elements = doc.select(selector);

		for (Element element : elements) {
//			System.out.println(element);
			// 获取月份数据 代号
			String date = element.attr("value");
			String id = element.attr("parentid");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("titleword", date));
			params.add(new BasicNameValuePair("channelids", id));
			String param = URLEncodedUtils.format(params, "UTF-8");
			URI uri = URIUtils.createURI("http", "www.xjtj.gov.cn/sjcx/ydsj_3329", -1, "jieguo.html", param, null);
			System.out.println(uri);
			//
			Document html = CrawlerUtil.getFromHtml02(uri.toString(), charset);
			System.err.println(html);
			// String name = html.select(selector02).get(0).toString();
			Elements name = html.select(selector02);
			Elements elements2 = html.select(selector03);

			// 创建文件夹
			CrawlerUtil.dirCheck(filepath + date + "\\");
			Elements htmlElements = html.select(selector03);

			// 流 写入xls
			//writeXls(filepath + date + "\\" + name + ".xls", htmlElements.toString(), charset);
		//	System.out.println("文件<=====" + name + "=====>>" + "写入到" + '\t' + filepath + date);

		}
	}
}
