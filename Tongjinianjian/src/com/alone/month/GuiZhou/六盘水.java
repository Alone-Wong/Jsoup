package com.alone.month.GuiZhou;

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
public class 六盘水 {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 18; i++) {
			number = i + "";
			url = "http://tjj.gzlps.gov.cn/tjzl/tjjb/index_" + number + ".html";
			charset = "utf-8";
			selector = ".lb ul li";
			selector02 = ".nr_con";

			// 获取发布时间
			// dateSelector = "td.pubtimecontentstyle77255 > span";

			String hrefSelector = "body > div.main > div.content > div > div:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > a[href]";

			filepath = "G:\\数据\\贵州\\地级市\\六盘水\\统计简报\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);

			
			for (Element element : eles) {
				String name = element.select("a[href]").text();
				// String name = name02.replace(" ", "%20");
				// System.err.println(name);
				String href = element.select("a[href]").attr("abs:href");
				String spanDate = element.select("span").text();
				String date = spanDate.substring(0, 4);
				// System.err.println(href);
				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					if (html02!=null && !"".equals(html02)) {
						
						Elements elements = html02.select(selector02);
						if (elements.size() != 0) {
							CrawlerUtil.dirCheck(filepath + date + "\\");
							String content = "<table>" + elements + "</table>";
							writeXls(filepath + date + "\\" + name + ".xls", content, charset);
							System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
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
