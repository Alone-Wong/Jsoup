package com.alone.month.YunNan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class 玉溪 {
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://www.stats.yn.gov.cn/TJJMH_Model/";

		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String dateSelector = "";
		String htmlselector = "";
		String filepath = "";
		for (int i = 11; i < 66; i++) {
			number = i + "";
			url = "http://xxgk.yuxi.gov.cn/yxszfxxgk/tjxx/index_" + number + ".html";
			charset = "utf-8";
			selector = ".newsline";
			dateSelector = ".newsline span";
			htmlselector = ".ArticleBody";

			List<String> list = new ArrayList<String>();
			filepath = "G:\\数据\\云南\\地级市\\玉溪\\2017\\";

			Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);
			Elements eles = doc.select(selector);
			for (Element element : eles) {
				Elements elements = element.select("li");
				for (Element element2 : elements) {
					String name = element2.select("a[href]").text();
					String date = element2.select("span").text();
					String href = element2.select("a[href]").attr("abs:href");
					System.err.println(href);
					//Document doc02 = Jsoup.parse(new URL(href).openStream(), charset, href);
					Document doc02 = CrawlerUtil.getFromHtml02(href, charset);
					Elements ele = doc02.select(htmlselector);

					if (ele != null && !"".equals(ele)) {

						String ele2 = "<table>" + ele + "</table>";
						CrawlerUtil.dirCheck(filepath);
						writeXls(filepath + date + name + ".xls", ele2, "utf-8");
						System.out.println("文件<=====" + date + name + "======>>" + "写入到" + filepath);
					}

				}

			}
		}

		// Elements elements2 = doc.select(htmlselector);

		// writeXls(filepath + eles + ".htm", elements2.toString(), "utf-8");

		// System.out.println("文件<=====" + text + "======>>" + "写入到" +
		// filepath);

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
