package com.alone.month.GanSu;

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
public class 嘉峪关市 {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String selector03 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://www.xgtj.gov.cn/tjsj/index.html";
			charset = "utf-8";
			selector = ".gl-main-itm .list li a[href]";
			selector02 = ".TRS_Editor:eq(1) img[src]";
			selector03 = "td#ArticleBody";

			filepath = "G:\\数据\\甘肃\\地级市\\嘉峪关市\\2017\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);

			

			for (Element element : eles) {
				String name = element.text();
				String href = element.attr("abs:href");
				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					if (html02 != null && !"".equals(html02)) {
						Elements elements = html02.select(selector02);
						
						// 存储图片Url
						List<String> list = new ArrayList<String>();
						
						if (elements.size() != 0) {
							for (Element element2 : elements) {
								String imgUrl = element2.attr("abs:src");
								list.add(imgUrl);

							}
							// 遍历下载图片URL
							for (int j = 0; j < list.size(); j++) {
								int index = j + 1;
								CrawlerUtil.dirCheck(filepath + name + "\\");
								CrawlerUtil.downloadFromHtml(filepath + name + "\\" + name + index + ".png",
										list.get(j));
							}

						} else {
							Elements eles02 = html02.select(selector03);
							CrawlerUtil.dirCheck(filepath);
							// String content = "<table>" + elements +
							// "</table>";
							writeXls(filepath + name + ".xls", eles02.toString(), charset);
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
