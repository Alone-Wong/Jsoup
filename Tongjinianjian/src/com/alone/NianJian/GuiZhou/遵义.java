package com.alone.NianJian.GuiZhou;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 遵义 {

	public static void main(String[] args) throws IOException {
		String url = "http://www.zunyi.gov.cn/zwgk/jcxxgk/tjxx/tjnj/nj2011.htm";
		String filepath = "G:/数据/统计年鉴/贵州/遵义/2011/";

		// String selector = "p[style=text-indent:0;line-height:2;padding-left:
		// 60px;] a[href]";
		String selector = "tr[style=cursor:hand]";

		String charset = "gb2312";

		String titleName = "";
		Document doc = CrawlerUtil.getFromHtml02(url, charset);

		Elements elements = doc.select(selector);

		for (Element element : elements) {
			String name = element.text();
			if (name != null && !"".equals(name)) {
				titleName = name;
				CrawlerUtil.dirCheck(filepath + titleName + "/");
				Elements aElements = element.nextElementSibling().select("a[href]");
				for (Element aElement : aElements) {
					String href = aElement.attr("abs:href");
					String title = aElement.text().replace(Jsoup.parse("&nbsp;").text(), "");
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					Elements elements2 = html02.select("table");
					String contents = "<table>" + elements2 + "</table>";
					// CrawlerUtil.downloadFromHtml(filepath+titleName+"/"+title+".png",
					// href);
					writeXls(filepath + titleName + "/" + title + ".xls", contents, charset);
					System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
				}
			}
			// for (Element aElement : aElements) {
			// String href = aElement.attr("abs:href");
			// String name = aElement.text();
			// CrawlerUtil.downloadFromHtml(filepath+name+".png", href);
			// }
		}

		/*
		 * for (Element element : elements) { String href =
		 * element.attr("abs:href"); String name =
		 * element.text().replace(Jsoup.parse("&nbsp;").text(), "");
		 * CrawlerUtil.downloadFromHtml(filepath + name +".pdf", href); }
		 */

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
