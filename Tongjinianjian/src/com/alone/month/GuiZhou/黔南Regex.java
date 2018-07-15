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

public class 黔南Regex {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
//		http://www.qdn.gov.cn/xxgk/zdgk/tjxx/tjfx/201607/t20160721_1041684.html
		String baseUrl = "http://www.qdn.gov.cn/xxgk/zdgk/tjxx/tjnb/";
		String url = "http://www.qdn.gov.cn/xxgk/zdgk/tjxx/tjnb/";
		String charset = "utf-8";
		String selector = "#main > div.wsbspage.mb5 > div.w705.fr > div > div:nth-child(6) > div.zw-list.mb5 > ul > li:nth-child(2) > a";
		// > div.xxjs_form_tl > div.w384.left > p > a[href]
		String htmlselector = "#Zoom > div > table";
		String filepath = "G:\\数据\\贵州\\地级市月度数据\\黔南\\统计年报\\";
		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);

		Elements scripts = doc.select("script");

		for (Element script : scripts) {
			String totalPage = "var str_1 =";

			if (script.html().contains(totalPage)) // 注意这里一定是html(), 而不是text()
			{
				String totalPageValue = CrawlerUtil.getPageByVar(script, totalPage); // 获取总页数

				String finalUrl = baseUrl + totalPageValue;
				
				Document finalDoc = Jsoup.parse(new URL(finalUrl).openStream(), charset, finalUrl);
				Elements select = finalDoc.select("body > div.main-box > div > div.box > div.xxgk_xl");
				
				String name = finalDoc.select("body > div.main-box > div > div.box > div.xxgk_xl > div.bt > span").text();
				
				CrawlerUtil.dirCheck(filepath);
				
				writeXls(filepath + name + ".xls", select.toString(), "utf-8");
				System.out.println("文件" + name + "写入到" + filepath);
				//CrawlerUtil.downloadFromHtml(filepath, downloadurl);
				//CrawlerUtil.downloadFromHtml(filepath + name + ".xls", );

			//	 System.out.println(totalPageValue);
			}

			// Elements elements2 = doc.select("#main");
			// String name = doc.select("#main > div.view-box.b-ltrb.mb5 >
			// div.title > h1").text();
			// CrawlerUtil.dirCheck(filepath);
			// writeXls(filepath + name + ".xls", elements2.toString(),
			// "utf-8");
			// System.out.println("文件" + name + "写入到" + filepath);
		}
		// Elements scripts = document.select("script");

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
