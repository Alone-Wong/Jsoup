package com.alone.month.GanSu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 甘南州 {
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
		for (int i = 1; i < 11; i++) {
			number = i + "";
			url = "http://www.gnztj.gov.cn/htm/list/25_" + number + ".htm";
			charset = "gb2312";
			selector = ".ClsListTb  tr";
			selector02 = "#ContentDetail img[src]";
			selector03 = "#ContentDetail";
			selector04 = ".Pages a[href]";

			filepath = "G:\\数据\\甘肃\\地级市\\甘南藏族自治州\\统计分析\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);

			for (Element element : eles) {
				String date = "";
				String href = element.select("a[href]").attr("abs:href");
				String name = element.select(".ftStrong").text();
				String fDate = element.select("td.daten").text();
				if (fDate.length() > 5) {
					date = fDate.substring(0, 4);
				}

				if (href != null && !"".equals(href) && href.contains(".htm")) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					if (html02 != null && !"".equals(html02)) {
						// 获取图片元素
						Elements elements = html02.select(selector02);

						// 存储图片Url
						List<String> list = new ArrayList<String>();

						// 续表
						List<String> list2 = new ArrayList<String>();
						list2.add(href);

						if (elements.size() != 0) {

							Elements eles02 = html02.select(selector04);
							for (Element element2 : eles02) {
								String href02 = element2.attr("abs:href");
								list2.add(href02);
							}

							for (int j = 0; j < list2.size(); j++) {

								if (j == 0) {
									CrawlerUtil.dirCheck(filepath + date + "\\" + name + "\\");
									Document document = CrawlerUtil.getFromHtml02(list2.get(j), charset);
									Elements elements2 = document.select(selector03);
									String content = "<table>" + elements2 + "</table>";
									writeXls(filepath + date + "\\" + name + "\\" + name + ".xls", content, charset);
									System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + date);
								} else {
									int index = j + 1;
									CrawlerUtil.dirCheck(filepath + date + "\\" + name + "\\");
									Document document = CrawlerUtil.getFromHtml02(list2.get(j), charset);
									Elements elements2 = document.select(selector03);
									String content = "<table>" + elements2 + "</table>";
									writeXls(filepath + date + "\\" + name + "\\" + name + index + ".xls", content,
											charset);
									System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + date);
								}

							}

						} else {

							for (Element element2 : elements) {
								String imgUrl = element2.attr("abs:src");
								if (imgUrl != null && !"".equals(imgUrl)) {

									list.add(imgUrl);
								}

							}
							// 遍历下载图片URL
							for (int j = 0; j < list.size(); j++) {
								if (j != 0) {
									int index = j + 1;
									CrawlerUtil.dirCheck(filepath + date + "\\" + name + "\\");
									CrawlerUtil.downloadFromHtml(
											filepath + date + "\\" + name + "\\" + name + index + ".jpg", list.get(j));

								} else {
									CrawlerUtil.dirCheck(filepath + date + "\\" + name + "\\");
									CrawlerUtil.downloadFromHtml(filepath + date + "\\" + name + "\\" + name + ".jpg",
											list.get(j));

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
