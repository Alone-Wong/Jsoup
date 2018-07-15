package com.alone.tongjinianjian;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class HtmlToXls {

	public static void main(String[] args) throws IOException {
		String url = "http://www.ldtj.gov.cn/tjsj/ndsj/index_1.html";
		String baseurl = "http://www.ldtj.gov.cn/tjsj/ndsj/";
		String charset = "utf-8";
		String selector = "body > section > div > div.fr.article_area > ul > li >a";
		String htmlselector = "body > section > div > article";
		String filepath = "C:/Users/74707/Desktop/数据/湖南/娄底/";
		Elements doc = CrawlerUtil.getFromHtml(url, selector, charset, baseurl);
		System.out.println(doc);
		for (Element li : doc) {
			String download = li.attr("abs:href");
			String filename = li.text() + ".xls";
			System.out.println(filename+":"+download);
			copyHtml(download, htmlselector, filename, filepath);
		}
	}

	public static void writeXls(String path, String content, String encoding)
			throws IOException {
		File file = new File(path);
		file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
				new FileOutputStream(file), encoding));
		writer.write(content);
		writer.close();
	}

	public static void copyHtml(String url, String selector, String filename,
			String filepath) {
		try {
			Elements xls = CrawlerUtil.getFromHtml(url, selector, "gb2312", "");
			CrawlerUtil.dirCheck(filepath);
			writeXls(filepath + filename, xls.toString(), "utf-8");
			System.out.println("文件"+filename+"写入到"+filepath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
