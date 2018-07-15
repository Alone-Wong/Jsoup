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
public class 昭通 {
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
			url = "http://www.zt.gov.cn/lanmu/zwgk/503_4.html";
			charset = "utf-8";
			htmlselector = ".la-r-text-no-image>h3 a[href]";
			htmlSelector02 = ".la-l-img img[src]";
			imgSelector = "#artibody > p > img[src]";
			// 获取发布时间
			// dateSelector = "td.pubtimecontentstyle77255 > span";

			String hrefSelector = "body > div.main > div.content > div > div:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > a[href]";

			List<String> list = new ArrayList<String>();

			filepath = "G:\\数据\\云南\\地级市\\昭通\\2017\\";
			// 创建文件路径
			CrawlerUtil.dirCheck(filepath);

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(htmlselector);
			// <a href="/lanmu/zwgk/contents/503/15623.html"
			// class="htitle-color" target="_blank"> 2017年1-11月全市主要经济指标 </a>
			// System.out.println(eles);
			for (Element element : eles) {
				List<Object> list2 = new ArrayList<>();
				String name = element.text();
				String href = element.attr("abs:href");
				// System.err.println(href);
				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					Elements elements = html02.select(imgSelector);
					for (Element element2 : elements) {
						String srcUrl = element2.attr("abs:src");
						list2.add(srcUrl);
//						String content = "<table>" + elements + "</table>";
//						writeXls(filepath + name + ".xls", content, charset);
					}
					for (int j = 0; j < list2.size(); j++) {
						String string = list2.get(j).toString();
							String index = "";
							if (j!=0) {
								index=j+"";
							}
									
						CrawlerUtil.downloadFromHtml(filepath + name + index + ".png", string);
					}

					// String content = "<table>"+elements+"</table>";
					// writeXls(filepath+name+".png", content, charset);

				}
			}

			Elements eles02 = doc.select(htmlSelector02);

			if (eles02.size() != 0 && !eles02.isEmpty()) {
				for (Element element : eles02) {
					List<String> arrayList = new ArrayList<>();
					String imgUrl = element.attr("abs:src");
					String name = element.attr("alt");
				
					arrayList.add(imgUrl);
					
					for (int j = 0; j < arrayList.size(); j++) {
						String index = "";
						if (j!=0) {
							index=j+"";
						}
						CrawlerUtil.downloadFromHtml(filepath + name + index + ".png", arrayList.get(j));
					}
					
				}
				
				
			}

			// for (Element element : eles) {
			// Elements imgTag = element.select("img");
			// if (imgTag.size()==0&&imgTag.isEmpty()) {
			// String href = element.attr("abs:href");
			// System.err.println(href);
			// }
			//
			// }

			// for (Element element : eles) {
			// // 获取绝对路径,判断是否是以htm结尾,如是,解析 String
			// href = element.attr("abs:href");
			// // System.out.println(href);
			// if (href.endsWith(".htm")) {
			// // 获取月报名称 String name = element.text();
			// Document document = CrawlerUtil.getFromHtml02(href, charset);
			// // 获取月报下载附件路径
			// Elements ele = document.select(hrefSelector);
			// // 判断是否有下载路径
			// if (ele.size() != 0) {
			// String downUrl = ele.attr("abs:href");
			// if (downUrl != null && !"".equals(downUrl)) {
			// System.out.println("下载路径==>>" + downUrl);
			// CrawlerUtil.downloadFromHtml(filepath + name + ".xls", downUrl);
			// }
			// // 如果没有下载路径,解析文本
			// } else {
			// System.err.println("下载路径为null");
			// Elements ele02 = document.select("#vsb_content");
			// String content = "<table>" + ele02 + "</table>";
			// writeXls(filepath + name + ".xls", content, "utf-8");
			// System.out.println("文件<=====" + name + "======>>" + "写入到" +
			// filepath);
			// }
			//
			// }
			//
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
