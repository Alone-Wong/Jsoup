package com.alone.month.YunNan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 丽江 {
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://www.zt.gov.cn/";
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String dateSelector = "";
		String htmlselector = "";
		String htmlSelector02 = "";
		String imgSelector = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://www.puershi.gov.cn/gkml_list.jsp?ainfolist1813t=4&ainfolist1813p=4&ainfolist1813c=15&urltype=egovinfo.EgovTreeURl&wbtreeid=1066&type=egovinfosubcattree2&sccode=TJSJ&subtype=1&gilevel=1";
			charset = "UTF-8";
			htmlselector = "#wrap > div.main.container.rel > div.content > div.body > ul > li a[href]";
			htmlSelector02 = "#wrap > div.main.container.rel > div.content > table > tbody > tr:nth-child(3) > td > table > tbody > tr:nth-child(1) > td:nth-child(1) > table > tbody";
			imgSelector = "#artibody > p > img[src]";
			// 获取发布时间
			// dateSelector = "td.pubtimecontentstyle77255 > span";

			String hrefSelector = "body > div.main > div.content > div > div:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > a[href]";

			List<String> list = new ArrayList<String>();

			filepath = "G:\\数据\\云南\\地级市\\普洱\\2019\\";
			// 创建文件路径
			CrawlerUtil.dirCheck(filepath);

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(htmlselector);

			for (Element element : eles) {
				List<Object> list2 = new ArrayList<>();
				String name = element.text();
				String href = element.attr("abs:href");
				// System.err.println(href);
				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02("http://www.puershi.gov.cn/gkml_info.jsp?urltype=egovinfo.EgovInfoContent&wbtreeid=1066&indentifier=1030-%2F2017-0822001", charset);
					Elements elements = html02.select(htmlSelector02);
					writeXls(filepath + name + ".xls", elements.toString(), charset);
					System.out.println("文件<=====" + name + "======>>" + "写入到" + filepath);

				}
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
