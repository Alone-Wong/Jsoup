package com.alone.month.GuiZhou;

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
public class 贵阳2 {
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://tjj.gygov.gov.cn/c8026/";
		String number = "";
		String url = "";
		String charset = "";
		String dateSelector = "";
		String selector = "";
		String selector02 = "";
		String selector03 = "";
		String selector04 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://tjj.gygov.gov.cn/c8026/pages/4.html";
			charset = "utf-8";
			selector = "body > table:nth-child(4) > tbody > tr > td:nth-child(2) > table:nth-child(4) > tbody > tr:nth-child(1) a[href]";
			// selector02 = ".content a[href]";
			selector02 = ".content .MsoNormalTable";
			selector03 = "#shLink";
			selector04 = ".brokenLine";

			// 获取发布时间
			// dateSelector = "td.pubtimecontentstyle77255 > span";

			String hrefSelector = "body > div.main > div.content > div > div:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > a[href]";

			List<String> list = new ArrayList<String>();

			filepath = "G:\\数据\\贵州\\地级市\\贵阳\\2015\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);

			for (Element element : eles) {
				String name02 = element.text();
				String name = name02.replace(" ", "%20");
				// System.err.println(name);
				String href = element.attr("abs:href");
				// System.err.println(href);
				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					Elements elements = html02.select(selector02);
					if (elements.size() != 0) {
						CrawlerUtil.dirCheck(filepath);
						writeXls(filepath + name + ".xls", elements.toString(), charset);
						System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
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
