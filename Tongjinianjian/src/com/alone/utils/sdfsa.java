package com.alone.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

public class sdfsa {

	public static void main(String[] args) throws IOException, DocumentException {
		PostParams();
	}

	@Test
	private static void PostParams() throws IOException, DocumentException {
		String charset = "gb2312";
		String selector = ".inside_news ul li";
		String selector02 = "#content > p > img";
		String selector03 = ".am-article-bd ab";
		String filepath = "G:\\数据\\湖北\\地级市\\武汉\\";
		String baseUrl = "http://www.whtj.gov.cn/";

		String url = "http://www.whtj.gov.cn/newslist.aspx?id=2012111010455712";
		List<NameValuePair> values = new ArrayList<NameValuePair>();
		values.add(new BasicNameValuePair("__EVENTTARGET", "ctl00$ContentPlaceHolder1$AspNetPager1"));
		values.add(new BasicNameValuePair("__EVENTARGUMENT", "3"));
		values.add(new BasicNameValuePair("__VIEWSTATE","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NeW5tDEtM+aciOatpuaxieW4guWbveawkee7j+a1juS4u+imgeaMh+agh2QCCw9kFgJmDxUDCjIwMTUtMDMtMTcUZGV0YWlscy5hc3B4P2lkPTI1ODMuMjAxNeW5tDEtMuaciOatpuaxieW4guWbveawkee7j+a1juS4u+imgeaMh+agh2QCDA9kFgJmDxUDCjIwMTUtMDMtMTYUZGV0YWlscy5hc3B4P2lkPTI1MzIoMjAxNOW5tOatpuaxieW4guWbveawkee7j+a1juS4u+imgeaMh+agh2QCDQ9kFgJmDxUDCjIwMTUtMDMtMTYUZGV0YWlscy5hc3B4P2lkPTI1MzEvMjAxNOW5tDEtMTHmnIjmrabmsYnluILlm73msJHnu4/mtY7kuLvopoHmjIfmoIdkAg4PZBYCZg8VAwoyMDE1LTAzLTEyFGRldGFpbHMuYXNweD9pZD0yNTE0LzIwMTTlubQxLTEw5pyI5q2m5rGJ5biC5Zu95rCR57uP5rWO5Li76KaB5oyH5qCHZAIPD2QWAmYPFQMKMjAxNC0xMS0wNBRkZXRhaWxzLmFzcHg/aWQ9MjM2NS4yMDE05bm0MS055pyI5q2m5rGJ5biC5Zu95rCR57uP5rWO5Li76KaB5oyH5qCHZAIQD2QWAmYPFQMKMjAxNC0xMC0yNBRkZXRhaWxzLmFzcHg/aWQ9MjM1Mi4yMDE05bm0MS045pyI5q2m5rGJ5biC5Zu95rCR57uP5rWO5Li76KaB5oyH5qCHZAIRD2QWAmYPFQMKMjAxNC0wOC0yORRkZXRhaWxzLmFzcHg/aWQ9MjI4NC4yMDE05bm0MS035pyI5q2m5rGJ5biC5Zu95rCR57uP5rWO5Li76KaB5oyH5qCHZAISD2QWAmYPFQMKMjAxNC0wOC0wMRRkZXRhaWxzLmFzcHg/aWQ9MjI0Mi4yMDE05bm0MS025pyI5q2m5rGJ5biC5Zu95rCR57uP5rWO5Li76KaB5oyH5qCHZAITD2QWAmYPFQMKMjAxNC0wNi0yNRRkZXRhaWxzLmFzcHg/aWQ9MjIxNS4yMDE05bm0MS015pyI5q2m5rGJ5biC5Zu95rCR57uP5rWO5Li76KaB5oyH5qCHZAIFDw8WBh4OQ3VzdG9tSW5mb1RleHQFN+WFsTxzcGFuPlszXTwvc3Bhbj7pobUv5YWxICA8c3Bhbj5bNTJdPHNwYW4+5paH56ugICAgICAeEEN1cnJlbnRQYWdlSW5kZXgCAh4LUmVjb3JkY291bnQCNGRkAg0PFgIfAgIEFghmD2QWAmYPFQMbaHR0cDovL3d3dy5zdGF0cy1oYi5nb3YuY24vGmltYWdlcy9mb29ydGVyX2xpbmtfMDIuanBnEua5luWMl+ecgee7n+iuoeWxgGQCAQ9kFgJmDxUDGGh0dHA6Ly93d3cuc3RhdHMuZ292LmNuLxppbWFnZXMvZm9vcnRlcl9saW5rXzAxLmpwZyTkuK3ljY7kurrmsJHlhbHlkozlm73lm73lrrbnu5/orqHlsYBkAgIPZBYCZg8VAxhodHRwOi8vdGpqLnd1aGFuLmdvdi5jbi8aaW1hZ2VzL2Zvb3J0ZXJfbGlua18wMy5qcGcY5q2m5rGJ5biC57uf6K6h5L+h5oGv572RZAIDD2QWAmYPFQMXaHR0cDovL3d3dy53dWhhbi5nb3YuY24aaW1hZ2VzL2Zvb3J0ZXJfbGlua18wNC5qcGcM5Lit5Zu95q2m5rGJZGTGJ2np9UXdX8s2cgY3PmOlDEPu9A=="));
		values.add(new BasicNameValuePair("cookie", "ASP.NET_SessionId=zuv4z455f3yjk5blu50oi5em"));

		String html = post(url, values);

		Document document = Jsoup.parse(html);
		Elements elements = document.select(".inside_news ul li");
		for (Element element : elements) {
			// 获取发布时间
			String releaseDate = element.select("span").text().substring(0, 4);
			if (releaseDate == null || "".equals(releaseDate)) {
				continue;
			}
			// 获取链接绝对地址
			String link = element.select("a[href]").attr("href");
			// 获取名称
			String name = element.select("a[href]").text();
			// 创建文件夹--发布时间
			CrawlerUtil.dirCheck("G:\\数据\\湖北\\地级市\\武汉\\" + releaseDate + "\\");
			if (link != null && !"".equals(link)) {
				/**
				 * 判断是否以.xls结尾--可直接下载
				 */
				if (link.contains(".xls") || link.contains(".xlsx")) {
					// 下载
					CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".xls", link);
				}
				/**
				 * 判读是否以.htm结尾
				 */
				else {
					// 获取document
					Document htmlDoc = CrawlerUtil.getFromHtml02(baseUrl + link, charset);
					Elements htmlElements = htmlDoc.select(selector02);
					/**
					 * 判断是否是PIC
					 */
					if (htmlElements.size() != 0 && !htmlElements.isEmpty()) {
						String picUrl = htmlElements.attr("src");
						if (!picUrl.contains("���")&&!picUrl.contains("QQ�剧�")) {
							CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".png",
									baseUrl + picUrl);
						}

					}
					/**
					 * 文字 文字,图片混合
					 */
					else {
						// 获取文本信息
						Elements textElements = htmlDoc.select(selector03);
						// 获取图片信息
						Elements imgElements = htmlDoc.select(selector03).select("img[src]");

						/**
						 * 如果文字和图片混合的情况
						 */
						if (imgElements.size() != 0 && textElements.size() != 0) {

							for (int j = 0; j < imgElements.size(); j++) {
								String fixIndex = "";
								String imgLink = imgElements.get(j).attr("abs:src");
								if (j == 0) {
									textElements.select("img").get(j).attr("src", "./" + name + fixIndex + ".png");
								} else {
									fixIndex = (j + 1) + "";
									textElements.select("img").get(j).attr("src", "./" + name + fixIndex + ".png");
								}
								if (imgLink.startsWith("file") || imgLink.endsWith("W020170527340676987330.jpg")
										|| imgLink.endsWith("2011年乌市公报_files/image001.jpg"))
									continue;

							}


						} // 如果只是文字
						else if (textElements.size() != 0 && imgElements.size() == 0) {
							String contents = "<table>" + textElements + "</table>";
							System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);
						}

					}

				}

			}

		}

	}

	public static String post(String url, List<NameValuePair> params) {
		String body = null;

		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			// 设置客户端编码
			if (httpClient == null) {
				// Create HttpClient Object
				httpClient = new DefaultHttpClient();
			}

			// Post请求
			HttpPost httppost = new HttpPost(url);

			// 设置参数
			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			// 发送请求
			HttpResponse httpresponse = httpClient.execute(httppost);
			// 获取返回数据
			HttpEntity entity = httpresponse.getEntity();
			body = EntityUtils.toString(entity, "UTF-8");
			if (entity != null) {
				entity.consumeContent();
			}
		} catch (Exception e) {
			e.printStackTrace();

		}

		return body;
	}

}
