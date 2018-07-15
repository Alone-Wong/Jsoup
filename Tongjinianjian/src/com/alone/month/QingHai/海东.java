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
public class 海东 {
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
			url = "http://www.qhhn.gov.cn/xxgk/zfxxgk/216/476/index1.html";
			charset = "utf-8";
			selector = "table.grid>tbody tr";
			selector02 = "#pline > table > tbody > tr:nth-child(1) > td";
			selector03 = ".Vent";

			filepath = "G:\\数据\\青海\\地级市\\海东州\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements elements = doc.select(selector);

			for (Element element : elements) {
				// 获取发布时间
				String releaseDate = "";
				String date = element.select(".rq").text();
				if (date != null && !"".equals(date)) {
					releaseDate = date.substring(1, 5);
				} else {
					continue;
				}
				// 获取链接绝对地址
				String link = element.select("a[href]").attr("abs:href");
				// 获取名称
				// Elements scripts = element.select("script");
				// for (Element script : scripts) {
				// String totalPage = "var title=";
				// if (script.html().contains(totalPage)) {
				// String totalPageValue = CrawlerUtil.getPageByVar(script,
				// totalPage);
				// System.err.println();
				// }

				String name = "";
				// 创建文件夹--发布时间
				CrawlerUtil.dirCheck(filepath + releaseDate + "\\");

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
						String title = htmlDoc.select(selector02).text();
						name = title.substring(title.indexOf("统计信息") + 5);
						Elements htmlElements = htmlDoc.select(selector03);
						/**
						 * 判断是否是表格形式
						 */
						if (htmlElements.size() != 0 && !htmlElements.isEmpty()) {
							// 流 写入xls
							String contents = "<table>" + htmlElements + "</table>";
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
