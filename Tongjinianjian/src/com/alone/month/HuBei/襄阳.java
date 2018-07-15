package com.alone.month.HuBei;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;
import com.alone.utils.HttpClientUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 襄阳 {
	public static void main(String[] args) throws IOException {
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
//			url = "http://ez.hb.stats.cn/ilist.aspx?5";
			charset = "utf-8";
			selector = "#DataList1 > tbody > tr  td > table > tbody > tr > td:nth-child(1) > a";
			selector02 = ".am-article-bd abc";
			selector03 = "#DIVContent  table > tbody";

			filepath = "G:\\数据\\湖北\\地级市\\鄂州\\";

			// 抓取页面数据
			//Document doc = CrawlerUtil.getFromHtml02(url, charset);

			//ezhou(charset, selector, selector02, selector03, filepath, doc);
			
			/**
			 * 
			 */
			
			url = "http://ez.hb.stats.cn/ilist.aspx?5&AspxAutoDetectCookieSupport=1";
			String cookie = "safedog-flow-item=; ASP.NET_SessionId=jb45cxvw5o5jor2vvredurmg; yunsuo_session_verify=4256f70d267d7e57c256874148ab6c04";
			
			Map<String, String> map = new HashMap<>();
			map.put("__EVENTTARGET", "SqlPager1$Next");
			map.put("__VIEWSTATE", "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Zm8vMjAxNC9DMDYyMDE4Mi5odG06MjAxNOW5tDEtNeaciOS7veWbveawkee7j+a1juS4u+imgee7n+iuoeaMh+agh+WujOaIkOaDheWGtToyMDE05bm0MS015pyI5Lu95Zu95rCR57uP5rWO5Li76KaB57uf6K6h5oyH5qCH5a6M5oiQ5oOF5Ya1CjIwMTQtMDYtMjBkAgsPDxYKHg1TZWxlY3RDb21tYW5kBZgCU0VMRUNUIFNCTTAwLCBTQk0wMSwgU0JNMDIsIFNCTTAzLCBTQk0wNCwgU0JNMDUsIFNCTTA2LCBTQk0wNywgU0JNMDgsIFNCTTA5LCBTQk0xMCwgU0JNMTEsIFNCTTEyLCBTQk0xMywgU0JNMTQsIFNCTTE1LCBTQk0xNixTQk0xNywgU0JNMTgsIFNCTTE5LCBTQk0yMCwgU0JNMjEsIFNCTTIyLCBTQk0yMywgU0JNMjQsIFNCTTI1LCBTQk0yNiwgU0JNMjcsIFNCTTI4LCBCVEYwLCBCVEYxMCBGUk9NIFtVU3lzdGVtQmFzaWNJbmZvVGFiXSBXSEVSRSBbU0JNMTNdID0gMSBhbmQgW1NCTTAyXSA9NR4KVG90YWxQYWdlcwIDHhBDb25uZWN0aW9uU3RyaW5nBY0BRGF0YSBTb3VyY2U9bG9jYWxob3N0O0luaXRpYWwgQ2F0YWxvZz1EMjAxNF9lenRqak9VVFNJREU7UGVyc2lzdCBTZWN1cml0eSBJbmZvPVRydWU7VXNlciBJRD1zYTtQYXNzd29yZD1udW5pb24xMDAxMTBYdDtNYXggUG9vbCBTaXplID0gMzI3Njc7HgxUb3RhbFJlY29yZHMCMh4QQ3VycmVudFBhZ2VJbmRleAIBZGQCDQ8WAh8AZWQCDw8WAh8ABYIJPGRpdiBpZD0iZl9ib3R0b20iIHN0eWxlPSJwYWRkaW5nLXRvcDogMHB4OyBtYXJnaW4tdG9wOiAwcHgiPg0KPGRpdiBpZD0iZl9ib3R0b21faW4iPg0KPHRhYmxlIGFsaWduPSJjZW50ZXIiPg0KICAgIDx0Ym9keT4NCiAgICAgICAgPHRyPg0KICAgICAgICAgICAgPHRkIHdpZHRoPSIxNSUiPjxzY3JpcHQgdHlwZT0idGV4dC9qYXZhc2NyaXB0Ij5kb2N1bWVudC53cml0ZSh1bmVzY2FwZSgiJTNDc3BhbiBpZD0nX2lkZUNvbmFjJyAlM0UlM0Mvc3BhbiUzRSUzQ3NjcmlwdCAgIHNyYz0naHR0cDovL2Rjcy5jb25hYy5jbi9qcy8xOC8yNzQvMDAwMC80MDUxNDIzOS9DQTE4Mjc0MDAwMDQwNTE0MjM5MDAwMS5qcycgdHlwZT0ndGV4dC9qYXZhc2NyaXB0JyUzRSUzQy9zY3JpcHQlM0UiKSk7PC9zY3JpcHQ+PC90ZD4NCiAgICAgICAgICAgIDx0ZCBhbGlnbj0iY2VudGVyIj48IS0t5bC+6YOo4oCU4oCU5byA5aeLLS0+DQogICAgICAgICAgICA8ZGl2IGlkPSJmX2JvdHRvbWQyIj7niYjmnYPmiYDmnInvvJrmuZbljJfnnIHphILlt57luILnu5/orqHlsYDjgIAg5Zyw5Z2A77ya5rmW5YyX55yB6YSC5bee5biC5ruo5rmW5aSn6YGT5biC5aeU5pS/5bqc5aSn5qW8IDxiciAvPg0KICAgICAgICAgICAg6YKu5pS/57yW56CBIO+8mjQzNjAwMCDnlLXor53vvJowNzExLTM4MzAxOTfjgIDjgIBF77yNbWFpbO+8mmV6c3RqakAxMjYuY29tIDxiciAvPg0KICAgICAgICAgICAgPGRpdj48L2Rpdj4NCiAgICAgICAgICAgIDxkaXY+Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7IDxmb250IGZhY2U9IkFyaWFsIj7phIJJQ1DlpIcgMDUwMDk3MDTlj7c8L2ZvbnQ+PC9kaXY+DQogICAgICAgICAgICA8ZGl2IHN0eWxlPSJ0ZXh0LWFsaWduOiBjZW50ZXIiPjxzcGFuIGlkPSJMYWJlbFJUUzAiPiZuYnNwOyA8L3NwYW4+PC9kaXY+DQogICAgICAgICAgICA8L2Rpdj4NCiAgICAgICAgICAgIDwvdGQ+DQogICAgICAgICAgICA8dGQgd2lkdGg9IjE1JSI+Jm5ic3A7DQo8c2NyaXB0IGlkPSJfaml1Y3VvXyIgc2l0ZWNvZGU9JzQyMDcwMDAwMTUnIHNyYz0naHR0cDovL3B1Y2hhLmthaXB1eXVuLmNuL2V4cG9zdXJlL2ppdWN1by5qcyc+PC9zY3JpcHQ+DQo8L3RkPg0KICAgICAgICA8L3RyPg0KICAgIDwvdGJvZHk+DQo8L3RhYmxlPg0KPC9kaXY+DQo8L2Rpdj5kAhEPFgIfAGVkZOVf2G6HmAuFdypFFXI3mmlRmNPzwvfu7X2C5cc4XCBk");
			map.put("__VIEWSTATEGENERATOR", "E277F190");
			map.put("__EVENTVALIDATION", "/wEdAAcsHBRVBEE5Vc/Kfr7mZPupnbYBsnhL17heE9TTGQD+ZaTsZ0WjvF8kqBEQdp11vMyYQeLa52MLS80TELPTu7psJlnrFTcKXxnRKVbIeG4M9mNjfux9ozFpEUPuHr3UjBVtL/MKrKB9mzaT4Bej1Mq+zG8J/QLbmaKuqNQmQ1vBCw9mVLfJEt2Jtwv9ceavK0Q=");
		
			String httpPost = HttpClientUtil.getInstance().sendHttpPost(url, map, cookie);
			
			charset = "utf-8";
			selector = "#DataList1 > tbody > tr  td > table > tbody > tr > td:nth-child(1) > a";
			selector02 = ".am-article-bd abc";
			selector03 = "#DIVContent  table > tbody";

			filepath = "G:\\数据\\湖北\\地级市\\鄂州\\";
			
			
			Document document = Jsoup.parse(httpPost);
			System.out.println(document);
			
			ezhou(charset, selector, selector02, selector03, filepath, document);
			

		}

	}

	public static void ezhou(String charset, String selector, String selector02, String selector03, String filepath,
			Document doc) throws IOException {
		Elements elements = doc.select(selector);
		
		String baseUrl = "http://ez.hb.stats.cn";

		for (Element element : elements) {
			// 获取发布时间
			String releaseDate = "";
//				String releaseDate = element.select("time").text().substring(0, 4);
//				if (releaseDate == null || "".equals(releaseDate)) {
//					continue;
//				}
			// 获取链接绝对地址
			String link = element.attr("href");
			// 获取名称
			String name = element.text();
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
					Document htmlDoc = CrawlerUtil.getFromHtml02(baseUrl+link, charset);
					Elements htmlElements = htmlDoc.select(selector02);
					/**
					 * 判断是否是PIC
					 */
					if (htmlElements.size() != 0 && !htmlElements.isEmpty()) {
						String picUrl = htmlElements.attr("src");
						CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".png", picUrl);

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

	public static void writeXls(String path, String content, String encoding) throws IOException {
		File file = new File(path);
		file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		writer.write(content);
		writer.close();
	}
}
