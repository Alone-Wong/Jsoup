package com.alone.month.XiZang;

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
public class 拉萨 {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://www.lasa.gov.cn/lasa/zwgk/jbxxgk/tjxx_2.shtml";
			charset = "utf-8";
			selector = "span ~ a[href]";
			selector02 = ".text_content";

			filepath = "G:\\数据\\西藏\\地级市\\拉萨\\2017\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);

			for (Element element : eles) {
				String name = element.text();
				String href = element.attr("abs:href");
				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					if (html02 != null && !"".equals(html02)) {
						Elements elements = html02.select(selector02);
						if (elements.size() != 0) {
							CrawlerUtil.dirCheck(filepath);
							String content = "<table>" + elements + "</table>";
							writeXls(filepath + name + ".xls", content, charset);
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
