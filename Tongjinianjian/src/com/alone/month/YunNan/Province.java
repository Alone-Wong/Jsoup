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

public class Province {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://www.stats.yn.gov.cn/TJJMH_Model/";
		String url = "http://www.stats.yn.gov.cn/TJJMH_Model/newslist_tjsj.aspx?classid=179407";
		String charset = "gb2312";
		String selector = ".newsline a";
		String htmlselector = "body > table:nth-child(2) > tbody > tr:nth-child(3) > td > table > tbody > tr > td > table > tbody > tr:nth-child(2) > td > table";

		List<String> list = new ArrayList<String>();
		String filepath = "G:\\数据\\云南\\省\\2011\\";

		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);

		Document doc02 = null;
		String href02 = "";

		Elements elements = doc.select(selector);
		for (Element element : elements) {
			String path = element.text();
			String href = element.attr("abs:href");

			doc02 = Jsoup.parse(new URL(href).openStream(), charset, href);
			Elements elements2 = doc02.select(htmlselector);
			
			CrawlerUtil.dirCheck(filepath);
			writeXls(filepath + path + ".xls", elements2.toString(), "utf-8");
			System.out.println("文件" + path + "======>>" + "写入到" + filepath);
			
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
