package com.alone.month.XinJiang;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class Province {
	@SuppressWarnings("deprecation")
	public static void main(String[] args) throws IOException, URISyntaxException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String selector03 = "";
		String selector04 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://www.xjtj.gov.cn/sjcx/ydsj_3329/ydsj.html";
			// url = "http://www.xjtj.gov.cn/sjcx/ydsj_3329/gmjjhs_951/";
			// http://www.xjtj.gov.cn/sjcx/ydsj_3329/jieguo.html?titleword=2017%E5%B9%B45%E6%9C%88&channelids=4802
			charset = "utf-8";
			selector = "select option[parentid]";
			selector02 = ".r_detial_title";
			selector03 = ".r_detail_content";

			filepath = "G:\\数据\\新疆\\省\\";

			// jsoupGet(url, charset, selector04, selector02, selector03,
			// filepath);

			String cookie = "_trs_uv=jczzpjwg_477_kl2y; _trs_ua_s_1=jd01l31w_477_74ti";
			String responseBody = method1(url);
			Document document = Jsoup.parse(responseBody.toString());
			Elements elements = document.select("a");
			for (Element element : elements) {
				System.out.println(element);
			}

			// System.err.println(responseBody);
		}

	}

	/**
	 */
	// public String testHttpClientPost() throws IOException {
	// //定义uri
	// String uri="http://www.xjtj.gov.cn/sjcx/ydsj_3329/ydsj.html";
	// //需要传入的参数
	// String responseBody = method1(uri);
	// return responseBody;
	// }

	public static String method1(String uri) throws IOException, ClientProtocolException {
		Map<String, String> map = new HashMap<String, String>();
		map.put("code", "js");
		map.put("day", "0");
		map.put("city", "上海");
		map.put("dfc", "1");
		map.put("charset", "utf-8");
		String encoding = "utf-8";
		// 创建默认的httpclient
		CloseableHttpClient httpClient = HttpClients.createDefault();
		// 创建post请求对象
		HttpPost httpPost = new HttpPost(uri);
		// 装填请求参数
		// List<NameValuePair> list = new ArrayList<NameValuePair>();
		// for (Map.Entry<String, String> entry : map.entrySet()) {
		// list.add(new BasicNameValuePair(entry.getKey(),entry.getValue()));
		// }
		// 设置参数到请求对象中
		// httpPost.setEntity(new UrlEncodedFormEntity(list,encoding));

		// System.out.println("请求地址："+uri);
		// System.out.println("请求参数："+list.toString());

		// 设置header信息
		// 指定报文头【Content-type】、【User-Agent】
		httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
		httpPost.setHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

		// 执行请求操作，并拿到结果（同步阻塞）
		CloseableHttpResponse response = httpClient.execute(httpPost);
		// 获取所有的请求头信息
		Header[] allHeaders = response.getAllHeaders();
		for (Header allHeader : allHeaders) {
			// System.out.println(allHeader.toString());
		}
		// 获取结果实体
		HttpEntity entity = response.getEntity();

		String responseBody = "";

		if (entity != null) {
			// System.out.println(EntityUtils.toString(entity,encoding));
			responseBody = EntityUtils.toString(entity, encoding);
		}
		// 关流
		EntityUtils.consume(entity);
		response.close();

		return responseBody;
	}

	@Test
	private static void jsoupGet() throws URISyntaxException, IOException {

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
			System.out.println(element);
			// 获取月份数据 代号
			String date = element.attr("value");
			String id = element.attr("parentid");

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("titleword", date));
			params.add(new BasicNameValuePair("channelids", id));
			String param = URLEncodedUtils.format(params, "UTF-8");
			URI uri = URIUtils.createURI("http", "www.xjtj.gov.cn/sjcx/ydsj_3329", -1, "jieguo.html", param, null);
			//
			Document html = CrawlerUtil.getFromHtml02(uri.toString(), charset);
			// String name = html.select(selector02).get(0).toString();
			String name = html.select(selector02).select("h1").text();
			Elements elements2 = html.select(selector03);

			// 创建文件夹
			CrawlerUtil.dirCheck(filepath + date + "\\");
			Elements htmlElements = html.select(selector03);

			// 流 写入xls
			writeXls(filepath + date + "\\" + name + ".xls", htmlElements.toString(), charset);
			System.out.println("文件<=====" + name + "=====>>" + "写入到" + '\t' + filepath + date);

		}
	}

	public static void writeXls(String path, String content, String encoding) throws IOException {
		File file = new File(path);
		file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		writer.write(content);
		writer.close();
	}
}
