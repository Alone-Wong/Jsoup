package com.alone.month.QingHai;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 西宁 {
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
		for (int i = 1; i < 5; i++) {
			number = i + "";
			url = "http://xntjj.xining.gov.cn/webaspx/view_list.aspx?portalid=26&lmid=873&pages=" + number;
			charset = "utf-8";
			selector = "#Label1 > table > tbody > tr:nth-child(9) > td > table > tbody > tr > td:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(4) > td > table > tbody > tr:nth-child(1) > td > table > tbody td a[href]";
			selector02 = "body > table > tbody > tr:nth-child(4) > td > table > tbody > tr:nth-child(2) > td > table";
			// selector03 = "#ContentDetail";
			// selector04 = ".Pages a[href]";

			filepath = "G:\\数据\\青海\\地级市\\西宁市\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements elements = doc.select(selector);

			for (Element element : elements) {
				// 获取发布时间
				String releaseDate="";
				// 获取链接绝对地址
				String link = element.attr("abs:href");
				// 获取名称
				String name = element.text();
				// 创建文件夹--发布时间
				CrawlerUtil.dirCheck(filepath );

				if (link != null && !"".equals(link)) {
					/**
					 * 判断是否以.xls结尾--可直接下载
					 */
					if (link.contains(".xls")) {
						// 下载
						CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".xls", link);
					}
					/**
					 * 判读是否以.htm结尾
					 */
					else {
						// 获取document
						Document htmlDoc = CrawlerUtil.getFromHtml02(link, charset);
						Elements htmlElements = htmlDoc.select(selector02);
						/**
						 * 判断是否是表格形式
						 */
						if (htmlElements.size() != 0 && !htmlElements.isEmpty()) {
							// 流 写入xls
							writeXls(filepath + releaseDate + "\\" + name + ".xls", htmlElements.toString(), charset);
							System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);
						}
						/**
						 * Js:正则匹配--??
						 */
						else {
							Elements scriptElements = htmlDoc.select("script");
							String script = "var fj = '<a href=\"";
							for (Element scriptElement : scriptElements) {
								if (scriptElement.html().contains(script)) {
									String var = CrawlerUtil.getPageByVar(scriptElement, script);
									System.err.println(var);
								}
							}
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
