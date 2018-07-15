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

import com.alone.utils.CrawlerUtil;

public class 布依 {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// http://www.qdn.gov.cn/xxgk/zdgk/tjxx/tjfx/201607/t20160721_1041684.html
		String baseUrl = "http://www.qdn.gov.cn/xxgk/zdgk/tjxx/tjnb/";
		String url = "http://www.qiannan.gov.cn/doc/2016/03/10/554148.shtml";
		String charset = "utf-8";
		String selector = ".detail_main_content>p";
		// > div.xxjs_form_tl > div.w384.left > p > a[href]
		String htmlselector = "#Zoom > div > table";
		String filepath = "G:\\数据\\贵州\\地级市月度数据\\黔南\\统计年报\\";
		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);

		String select2 = doc.select(selector).text();
		System.err.println(select2);

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
			Element xls = CrawlerUtil.getFromHtml02(url,  "utf-8");
			CrawlerUtil.dirCheck(filepath);
			writeXls(filepath + filename + ".xls", xls.toString(), "utf-8");
			System.out.println("文件" + filename + "写入到" + filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
