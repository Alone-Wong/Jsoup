package com.alone.NianJian.GuiZhou;

import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;
@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 贵阳 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String url = "http://218.201.254.101:8082/abc/left.htm";
		String filepath = "G:/数据/统计年鉴/贵州/贵阳/2014/";
		
		String selector = "a:has(li)";
		
		String titleName = "";

		String charset = "gb2312";

		Document doc = CrawlerUtil.getFromHtml02(url, charset);

		Elements elements = doc.select(selector);

		for (Element element : elements) {
			String name = element.text().replace(Jsoup.parse("&nbsp;").text(), "").trim();
			if (name!=null && !"".equals(name)) {
				titleName = name;
				CrawlerUtil.dirCheck(filepath+titleName+"/");
				Elements aElements = element.nextElementSibling().select("a[href]");
				for (Element aElement : aElements) {
					String href = aElement.attr("abs:href");
					String title = aElement.text();
					CrawlerUtil.downloadFromHtml(filepath+titleName+"/"+title+".jpg", href);
				}
			}
			
			
			
		
		}
		

	/*	for (int i = 0; i < elements.size(); i++) {

			Elements select = elements.get(i).select("span[style=font-size:9.0pt;mso-bidi-font-size:12.0pt;font-family:宋体;mso-ascii-font-family:\"Times New Roman\";mso-hansi-font-family:\"Times New Roman]");
			// System.out.println(elements.get(i) + "--------" + i);

			if (i % 2 != 0) {
				String title = elements.get(i).text().replace(Jsoup.parse("&nbsp;").text(), "");
				titleName = title;
				CrawlerUtil.dirCheck(filepath + titleName + "\\");
			} else {
				Elements aElements = elements.get(i).select("a");
				// System.err.println(elements.get(11));

				for (Element element : aElements) {
					String href = element.attr("abs:href");
					String name = element.text().replace(Jsoup.parse("&nbsp;").text(), "");
					CrawlerUtil.downloadFromHtml(filepath + titleName + "/" + name + ".xls", href);
				}
			}

		}*/

		/*
		 * for (Element element : elements) {
		 * 
		 * String title =
		 * element.select("[style=font-size:14.0pt;font-family:黑体]").text();
		 * 
		 * if (title != null && !"".equals(title)) { titleName =
		 * element.select(".MsoNormal").text().replace(Jsoup.parse("&nbsp;").
		 * text(), ""); System.err.println(titleName);
		 * CrawlerUtil.dirCheck(filepath + titleName + "\\"); continue; }
		 * 
		 * String href = element.select("a[href]").attr("abs:href"); if
		 * (href!=null && !"".equals(href)) { String name =
		 * element.select("a[href]").text().replace(Jsoup.parse("&nbsp;").text()
		 * , ""); CrawlerUtil.dirCheck(filepath + titleName + "\\");
		 * CrawlerUtil.downloadFromHtml(filepath + titleName + "/" + name +
		 * ".xls", href);
		 * 
		 * }
		 * 
		 * 
		 * }
		 */
		// String folderPath = element.text().replaceAll("[a-zA-Z0-9,-’‘]",
		// "").trim() + "/";
		// String select =
		// element.select("a[href]").attr("abs:href").replace("\\", "/");
		// System.out.println(select);

		// String replace = element.attr("abs:href").replace("\\", "/");
		// CrawlerUtil.dirCheck(filefolder + path+"/" );
		// CrawlerUtil.downloadFromHtml(filefolder + path+"/" +path +
		// ".pdf", select);

		// Elements nextElements =
		// element.nextElementSibling().select("li>a[href]");
		// for (Element nextElement : nextElements) {
		// String path = nextElement.text();
		// Pattern p = Pattern.compile("[a-zA-z]");
		// if (!p.matcher(path).find()) {
		// String text = nextElement.attr("abs:href").replace("\\",
		// "/").replace(" ", "%20");
		// //URL转码
		// path=URLDecoder.decode(path,"utf-8");
		// //创建文件夹
		// CrawlerUtil.dirCheck(filefolder + folderPath);
		// //文件下载
		// CrawlerUtil.downloadFromHtml(filefolder + folderPath + path + ".xls",
		// path);
		// }
	}
}
