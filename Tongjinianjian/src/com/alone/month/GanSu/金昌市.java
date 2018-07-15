package com.alone.month.GanSu;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 金昌市 {
	public static void main(String[] args) throws IOException, DocumentException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String filepath = "";
		String baseUrl = "http://tjj.jc.gansu.gov.cn/";
		// 循环页码
		for (int i = 4; i < 5; i++) {
			number = i + "";
			url = "http://tjj.jc.gansu.gov.cn/col/col18971/index.html";
			charset = "utf-8";
			selector = ".wenz";
			selector02 = ".MsoNormalTable";

			filepath = "G:\\数据\\甘肃\\地级市\\金昌\\月报\\2017\\";
			List<Map> mapList = new ArrayList<Map>();
			CrawlerUtil.dirCheck(filepath);

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);
			// datastore
			String html = doc.getElementById("80715").text();
			try {
				org.dom4j.Document parseText = DocumentHelper.parseText(html);

				Element root = parseText.getRootElement();
				Element element = root.element("recordset");
				List<Element> elements = element.elements();
				for (Element e : elements) {
					if (!"style".equals(e.getName())) {
						String text = e.getText();
						if (text!=null && !"".equals(text)) {
							Document doc1 = Jsoup.parse(text);
							String sUrl = doc1.select("li a[href]").attr("href");
							String name = doc1.select("li a[href]").text();
							if (sUrl!=null && !"".equals(sUrl)) {
								String fUrl = baseUrl+sUrl;
								Document html02 = CrawlerUtil.getFromHtml02(fUrl, charset);
								Elements select = html02.select(selector);
								writeXls(filepath + name + ".xls", select.toString(), charset);
								System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
								
							}
						}
						
						
					}

				}

			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.err.println();

			// for (Element element : eles) {
			// String name = element.text();
			// String href = element.attr("abs:href");
			// if (href != null && !"".equals(href)) {
			// Document html02 = CrawlerUtil.getFromHtml02(href, charset);
			// if (html02 != null && !"".equals(html02)) {
			// Elements elements = html02.select(selector02);
			// if (elements.size() != 0) {
			// CrawlerUtil.dirCheck(filepath);
			// String content = "<table>" + elements + "</table>";
			// writeXls(filepath + name + ".xls", content, charset);
			// System.out.println("文件<=====" + name + "=====>>" + "写入到" +
			// filepath);
			// }
			// }
			//
			// }
			// }

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
