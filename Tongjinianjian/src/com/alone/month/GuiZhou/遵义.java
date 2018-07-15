package com.alone.month.GuiZhou;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class 遵义 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String index = "";
		for (int i = 1; i < 20; i++) {
			index = i+"";
			String url = "http://tjj.zunyi.gov.cn/xxgk/zpfl/tjxx/tjsj/list_"+index+".html";
			String charset = "utf-8";
			String selector = "#documentContainer > div.xxjs_form_tl > div.w384.left > p > a[href]";
			//> div.xxjs_form_tl > div.w384.left > p > a[href]
			String htmlselector = "tbody > tr > td:nth-child(2) > div > ul > li > span.lbx > a[href]";
			String filepath = "G:\\数据\\贵州\\地级市\\遵义\\";
			Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);
			Elements elements = doc.select(selector);
			for (Element element : elements) {
				String path = element.text();
				String href = element.attr("abs:href");
				CrawlerUtil.dirCheck(filepath);
				CrawlerUtil.downloadFromHtml(filepath + path + ".xls", href);
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
