package com.alone.month.GuiZhou;

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

public class 安顺 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		String url = "http://www.anshun.gov.cn/sj2/sjkf/tjsj_72987/index.html";
		String charset = "utf-8";
		String selector = "body > div.content-main > div.yb_nr.f_r > div.NewsList > ul > li> a[href]";
		// > div.xxjs_form_tl > div.w384.left > p > a[href]
		String htmlselector = "#Zoom > div > table";
		String filepath = "G:\\数据\\贵州\\地级市\\安顺\\";
		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);
		List<String> list = new ArrayList<String>();
		Elements elements = doc.select(selector);
		for (Element element : elements) {
			 String path = element.text();
			String href = element.attr("abs:href");
			list.add(href);

//			CrawlerUtil.dirCheck(filepath);
//			CrawlerUtil.downloadFromHtml(filepath + path + ".xls", href);
		}
		Document doc02 = null;
		String href02 = "";
		for (int i = 0; i < list.size(); i++) {
			href02 = list.get(i);
			doc02 = Jsoup.parse(new URL(href02).openStream(), charset, href02);
			Elements elements2 = doc02.select("#c");
			String name = doc02.select("#c > div.title > h1").text();
			CrawlerUtil.dirCheck(filepath);
			writeXls(filepath + name + ".xls", elements2.toString(), "utf-8");
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
			Element xls = CrawlerUtil.getFromHtml02(url,  "utf-8");
			CrawlerUtil.dirCheck(filepath);
			writeXls(filepath + filename + ".xls", xls.toString(), "utf-8");
			System.out.println("文件" + filename + "写入到" + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
