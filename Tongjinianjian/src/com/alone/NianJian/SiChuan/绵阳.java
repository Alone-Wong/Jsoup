package com.alone.NianJian.SiChuan;

import java.io.UnsupportedEncodingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class 绵阳 {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// String url = "http://www.jxstj.gov.cn/resource/nj/2016CD/left.htm";
		// String url = "http://www.jxstj.gov.cn/resource/nj/2015CD/left.htm";
		// String url = "http://www.jxstj.gov.cn/resource/nj/2014CD/left.htm";
		// String url = "http://www.jxstj.gov.cn/resource/nj/2013CD/left.htm";
		String baseUrl = "http://tjj.my.gov.cn/bmwz/mytjnj/2012年/";
		String url = "http://tjj.my.gov.cn/bmwz/mytjnj/2011年/Left.htm";

		String titleName = "";

		String charset = "gb2312";

		String selector = "div.Section2 > p";

		String filepath = "G:/数据/统计年鉴/四川/绵阳/统计年鉴/2011/";

		Document doc = CrawlerUtil.getFromHtml02(url, charset);

		// Document doc=Jsoup.parse(doc2.toString().replace("&nbsp;"," "));

		// System.out.println(d.text());

		// 获取指定元素集
		Elements elements = doc.select(selector);

		// String replace =
		// elements.text().replace(Jsoup.parse("&nbsp;").text(), " ");

		// String temp = els.text().replace(Jsoup.parse("&nbsp;").text(), " ");

		for (int i = 0; i < elements.size(); i++) {

//			System.out.println(elements.get(i) + "--------" + i);

				if (i % 2 != 0 ) {
					String title = elements.get(i).text().replace(Jsoup.parse("&nbsp;").text(), "");
					titleName = title;
					CrawlerUtil.dirCheck(filepath + titleName + "\\");
				} else {
					Elements aElements = elements.get(i).select("a");
					// System.err.println(elements.get(11));

					for (Element element : aElements) {
						String href = element.attr("abs:href");
						String name = element.text().replace(Jsoup.parse("&nbsp;").text(), "");
					 CrawlerUtil.downloadFromHtml(filepath + titleName+"/" +name + ".xls",href);
					}
				}

		}

/*		for (Element element : elements) {
			
			String title = element.select("[style=font-size:14.0pt;font-family:黑体]").text();
			
			if (title != null && !"".equals(title)) {
				titleName = element.select(".MsoNormal").text().replace(Jsoup.parse("&nbsp;").text(), "");
				System.err.println(titleName);
				CrawlerUtil.dirCheck(filepath + titleName + "\\");
				continue;
			}

			String href = element.select("a[href]").attr("abs:href");
			if (href!=null && !"".equals(href)) {
				String name = element.select("a[href]").text().replace(Jsoup.parse("&nbsp;").text(), "");
				CrawlerUtil.dirCheck(filepath + titleName + "\\");
				CrawlerUtil.downloadFromHtml(filepath + titleName + "/" + name + ".xls", href);
				
			}
			

		}*/
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
