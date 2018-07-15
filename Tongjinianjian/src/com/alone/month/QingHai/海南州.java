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
public class 海南州 {
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
		for (int i = 1; i < 30; i++) {
			number = i + "";
			// url = "http://www.qhtjj.gov.cn/tjData/ydsj/index_" + number +".html";
			url = "http://www.qhtjj.gov.cn/tjData/ydsj/index.html";
			charset = "utf-8";
			selector = ".gl_r_ul li";
			selector02 = ".TRS_PreExcel.TRS_PreAppend>table";
			// selector03 = "#ContentDetail";
			// selector04 = ".Pages a[href]";

			filepath = "G:\\数据\\青海\\省\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements elements = doc.select(selector);

			for (Element element : elements) {
				// 获取发布时间
				String releaseDate = element.select("span").text().substring(1, 5);
				// 获取链接绝对地址
				String link = element.select("a[href]").attr("abs:href");
				// 获取名称
				String name = element.select("a[href]").text();
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
