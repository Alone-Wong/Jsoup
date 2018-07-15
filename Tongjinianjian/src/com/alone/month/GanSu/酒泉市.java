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
public class 酒泉市 {
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
		for (int i = 3; i < 4; i++) {
			number = i + "";
			url = "http://tjj.jiuquan.gov.cn/xinxigongkai/xinxigongkaimulu/shujufabu/Default.htm";
			charset = "utf-8";
			selector = ".DocList li";
			selector02 = "#contents img[src]";
			selector03 = "#contents";
			selector04 = "table.c_content_text > tbody";

			filepath = "G:\\数据\\甘肃\\地级市\\酒泉市\\2017\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);

			for (Element element : eles) {
				String name = element.select("a[href]").text().replace("\"", "");
				String href = element.select("a[href]").attr("abs:href");
				String date = element.select("span").text().substring(0, 7);

				if (href != null && !"".equals(href) && href.endsWith("12453318415668.htm")) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					if (html02 != null && !"".equals(html02)) {
						Elements elements = html02.select(selector02);

						// 存储图片Url
						List<String> list = new ArrayList<String>();

						if (elements.size() == 0) {
							Elements eles02 = html02.select(selector03);

							CrawlerUtil.dirCheck(filepath+date+"\\");
							String content = "<table>" + eles02 + "</table>";
							System.err.println(name + "--------------------------");
							writeXls(filepath + date + "\\" + name + ".xls", content, charset);
							System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);

						} else {

							Elements elements0 = html02.select(selector02);
							for (Element element2 : elements0) {
								String imgUrl = element2.attr("abs:src");
								if (imgUrl!=null && !"".equals(imgUrl)){

									list.add(imgUrl);
								}

							}
							
							if (list.size()!=0 && !list.isEmpty()) {
								
								// 遍历下载图片URL
								for (int j = 0; j < list.size(); j++) {
									int index = j + 1;
									CrawlerUtil.dirCheck(filepath + date + "\\" + name + "\\");
									CrawlerUtil.downloadFromHtml(
											filepath + date + "\\" + name + index + ".png", list.get(j));
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
