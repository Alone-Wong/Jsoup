package com.alone.NianJian.XinJiang;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class Province {

	public static void main(String[] args) throws IOException {
		String charset = "utf-8";
		String url = "http://www.xjtj.gov.cn/sjcx/tjnj_3415/";

		String selector = "ul[parentid=\"7090\"] a[href]";
		String selector02 = ".rightlist.tjnj_bg li a[href]";
		String selector03 = "#table1";

		String filefolder = "G:/数据/统计年鉴/新疆/省/2016/";

		Document doc = CrawlerUtil.getFromHtml02(url, charset);
		// 获取指定元素集
		Elements elements = doc.select(selector);

		for (Element element : elements) {
			String title = element.text();
			CrawlerUtil.dirCheck(filefolder + title + "/");

			String link = element.attr("totarget");

			Document doc02 = CrawlerUtil.getFromHtml02(link, charset);
			Elements elements02 = doc02.select(selector02);
			for (Element element02 : elements02) {
				String name = element02.text();
				String orgHref = element02.attr("href");
				String href = orgHref.substring(orgHref.indexOf("('") + 2, orgHref.lastIndexOf("')"));
				System.err.println(href);
				Document doc03 = CrawlerUtil.getFromHtml02(href, charset);

				Elements elements2 = doc03.select(selector03);

				writeXls(filefolder + title + "\\" + name + ".xls", elements2.toString(), charset);
				System.out.println("文件<=====" + name + "=====>>" + "写入到" + filefolder + title);

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
