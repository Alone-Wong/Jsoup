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

public class 曲靖 {
	public static void main(String[] args) throws IOException {
		/**
		 * 月度经济数据
		 */
		String baseUrl = "http://www.qjf.cn/";
		String url = "http://www.qjf.cn/list_104.xhtml?0=0&page=3";
		/**
		 * 年度经济数据
		 */
		String yearUrl = "http://www.qj.gov.cn/index.php?m=content&c=index&a=lists&catid=241&page=6";
		String charset = "gb2312";

		String yearCharset = "utf-8";
		String selector = ".list_wrap a[href]";

		String yearSelector = ".col-left a[href]";

		String htmlselector = "body > div.container > div:nth-child(5)";
		String filepath = "G:\\数据\\云南\\地级市\\曲靖\\要闻\\2014\\";
		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);

		Document yearDoc = Jsoup.parse(new URL(yearUrl).openStream(), yearCharset, yearUrl);
		Elements elements3 = yearDoc.select(yearSelector);
		for (Element element : elements3) {
			String yearName = element.text();
			String yearHref = element.attr("abs:href");
			if (!yearHref.contains("page") && yearHref.contains("&id=")) {
				Document yearDoc02 = Jsoup.parse(new URL(yearHref).openStream(), yearCharset, yearHref);
				Elements select = yearDoc02.select("#Article > div.content");
				if (select != null && !select.equals("")) {
					CrawlerUtil.dirCheck(filepath);
					writeXls(filepath + yearName + ".xls", "<table>" + select + "</table>", "utf-8");
					System.out.println("文件" + yearName + "写入到" + filepath);
				}
			}

		}

		List<String> list = new ArrayList<String>();
		Elements elements = doc.select(selector);
		// System.err.println(elements);
		Document doc02 = null;
		for (Element element : elements) {
			String path = element.text();
			String href = element.attr("abs:href");

			doc02 = Jsoup.parse(new URL(href).openStream(), charset, href);
			Elements elements2 = doc02.select("iframe");
			String select = elements2.attr("src");
			String finalPath = baseUrl + select;

			// CrawlerUtil.dirCheck(filepath);
			// CrawlerUtil.downloadFromHtml(filepath +path+ ".xls", finalPath);
			// String content = "<table>" + elements2 + "</table>";

			// CrawlerUtil.dirCheck(filepath);
			// writeXls(filepath + path + ".htm", content, "utf-8");
			// System.out.println("文件" + "<<====" + path + "======>>" + "写入到" +
			// filepath);

			// CrawlerUtil.downloadFromHtml(filepath +path+ ".html", href);

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

	public static void copyHtml(String url, String selector, String filename, String filepath) {
		try {
			Element xls = CrawlerUtil.getFromHtml02(url, "utf-8");
			CrawlerUtil.dirCheck(filepath);
			writeXls(filepath + filename + ".xls", xls.toString(), "utf-8");
			System.out.println("文件" + filename + "写入到" + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
