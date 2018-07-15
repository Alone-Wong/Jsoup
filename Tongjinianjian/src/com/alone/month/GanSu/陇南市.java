package com.alone.month.GanSu;

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
public class 陇南市 {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://lntj.longnan.gov.cn/content/column/4640170?pageIndex=2";
			charset = "utf-8";
			selector = ".doc_list.list-4640170 li";
			selector02 = ".wzcon.j-fontContent";

			filepath = "G:\\数据\\甘肃\\地级市\\陇南市\\2017\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);

			for (Element element : eles) {
				String name = element.select("a[href] span").text();
				String href = element.select("a[href]").attr("abs:href");
				String date = "";
				String rightdate = element.select("span").text();
				if (rightdate!=null && !"".equals(rightdate)) {
					date = rightdate.substring(0, 8);
				}
				
				if (href != null && !"".equals(href) && href.endsWith(".html")) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					if (html02 != null && !"".equals(html02)) {
						Elements elements = html02.select(selector02);
						if (elements.size() != 0) {
							CrawlerUtil.dirCheck(filepath + date + "\\");
							String content = "<table>" + elements + "</table>";
							writeXls(filepath + date + "\\" + name + ".xls", content, charset);
							System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
						}
					}

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
