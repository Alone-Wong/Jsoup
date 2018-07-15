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

public class 贵州2 {

	public static void main(String[] args) throws IOException {
		String url = "http://www.gz.stats.gov.cn/tjsj_35719/sjcx_35720/jdsj_35723/201609/t20160929_1065522.html";
		String baseurl = "http://www.gz.stats.gov.cn/tjsj_35719/sjcx_35720/ydsj_35722/index_14.html";
		String charset = "utf-8";
		String selector = "body > div:nth-child(7) > div:nth-child(2) > div > div:nth-child(1) > div > div > div";
		String htmlselector = "tbody > tr > td:nth-child(2) > div > ul > li > span.lbx > a[href]";
		String filepath = "G:/数据/贵州/季度/";
		// Elements doc = CrawlerUtil.getFromHtml02(url, selector, charset,"");
		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);
//		Elements select2 = doc.select("script[type]");
//		Map<String, Object> map = new HashMap<String, Object>();
//		for (Element element : select2) {
//
//			/* 取得JS变量数组 */
//			String[] data = element.data().toString().split("var");
//
//			/* 取得单个JS变量 */
//			for (String variable : data) {
//
//				/* 过滤variable为空的数据 */
//				if (variable.contains("=")) {
//
//					/* 取到满足条件的JS变量 */
//					if (variable.contains("str_1")) {
//					
//				}
//			}
//		}
//
//		System.out.println(map);
		Element select = doc.getElementsByTag("table").get(2);

		String name = doc.getElementsByTag("title").get(0).text();
		System.err.println(name);
		CrawlerUtil.dirCheck(filepath);
		//GuiZhou2.copyHtml(url, htmlselector, name + "", filepath);
		CrawlerUtil.downloadFromHtml(filepath + name + ".xls", url);

		// System.out.println("文件" + name + "写入到" + filepath);

		// for (Element li : doc) {
		// String download = li.attr("abs:href");
		// String filename = li.text() + ".xls";
		// System.out.println(filename + ":" + download);
		// copyHtml(download, htmlselector, filename, filepath);
		// }
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
