package com.alone.month.YunNan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 德宏州 {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		// String dateSelector = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://xxgk.yn.gov.cn/Z_M_001/Info_More.aspx?int_PageNow=8&ClassID=102415&departmentid=9384";
			charset = "gb2312";
			selector = "div#main-cnt ul li a[href]";
			selector02 = "#form1 > div:nth-child(4) > div > div .ArticleBody";

			// 获取发布时间
			// dateSelector = "td.pubtimecontentstyle77255 > span";

			filepath = "G:\\数据\\云南\\地级市\\迪庆藏族自治州\\2011\\";
			// 创建文件路径
			CrawlerUtil.dirCheck(filepath);

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);
			System.err.println(eles.size());
			for (Element element : eles) {
				String name = element.text();
				String href = element.attr("abs:href");

				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					Elements elements = html02.select(selector02);

					String content = "<table>" + elements + "</table>";
					writeXls(filepath + name + ".xls", content, charset);
					System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
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
