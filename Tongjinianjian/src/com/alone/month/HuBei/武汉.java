package com.alone.month.HuBei;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 武汉 {
	public static void main(String[] args) throws IOException, DocumentException {
		String number = "";
		String baseUrl = "";
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
			baseUrl = "http://www.whtj.gov.cn/";
			url = "http://www.whtj.gov.cn/newslist.aspx?id=2012111010455712";
			charset = "gb2312";
			selector = ".inside_news ul li";
			selector02 = "#content > p > img";
			selector03 = ".am-article-bd ab";

			filepath = "G:\\数据\\湖北\\地级市\\武汉\\";

			// 抓取页面数据
			// Document doc = CrawlerUtil.getFromHtml02(url, charset);

			// String html = PostParams();
			// System.out.println(html);
			Document doc = Jsoup.parse("");

			Elements elements = doc.select(selector);

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
				CrawlerUtil.dirCheck(filepath + releaseDate + "\\");
				// 创建图片集合
				List<String> imgList = new ArrayList<String>();

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
							if (!picUrl.contains("���")) {
								CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".png",
										 baseUrl+picUrl);
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
									imgList.add(imgLink);

								}

								// 遍历下载图片 保存图片至文本目录
								for (int j = 0; j < imgList.size(); j++) {
									String picIndex = "";
									if (j == 0) {
										picIndex = "";
										CrawlerUtil.dirCheck(filepath + releaseDate + "\\" + name + "\\");
										CrawlerUtil.downloadFromHtml(
												filepath + releaseDate + "\\" + name + "\\" + name + picIndex + ".png",
												imgList.get(j));
									} else {
										picIndex = (j + 1) + "";
										// CrawlerUtil.dirCheck(filepath +
										// releaseDate + "\\" +name+"\\");
										CrawlerUtil.downloadFromHtml(
												filepath + releaseDate + "\\" + name + "\\" + name + picIndex + ".png",
												imgList.get(j));
									}
								}
								// 下载文本 此时的textElements其中路径已经修改
								String contents = "<table>" + textElements + "</table>";
								// CrawlerUtil.dirCheck(filepath + releaseDate +
								// "\\" +name+"\\");
								writeXls(filepath + releaseDate + "\\" + name + "\\" + name + ".xls", contents,
										charset);
								System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);

							} // 如果只是文字
							else if (textElements.size() != 0 && imgElements.size() == 0) {
								String contents = "<table>" + textElements + "</table>";
								writeXls(filepath + releaseDate + "\\" + name + ".xls", contents, charset);
								System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);
							}

						}

					}

				}

			}

		}

	}

	@Test
	private static void PostParams() throws IOException, DocumentException {
		String url = "http://www.whtj.gov.cn/newslist.aspx?id=2012111010455712";
		List<NameValuePair> values = new ArrayList<NameValuePair>();
		values.add(new BasicNameValuePair("_EVENTTARGET", "ctl00$ContentPlaceHolder1$AspNetPager1"));
		values.add(new BasicNameValuePair("_EVENTARGUMENT", "2"));
		values.add(new BasicNameValuePair("_VIEWSTATE",
				"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"));
		values.add(new BasicNameValuePair("cookie", "ASP.NET_SessionId=zuv4z455f3yjk5blu50oi5em"));

		String html = post(url, values);

		Document document = Jsoup.parse(html);
		Elements elements = document.select(".inside_news ul li");
		for (Element element : elements) {

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

	public static void writeXls(String path, String content, String encoding) throws IOException {
		File file = new File(path);
		file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		writer.write(content);
		writer.close();
	}
}
