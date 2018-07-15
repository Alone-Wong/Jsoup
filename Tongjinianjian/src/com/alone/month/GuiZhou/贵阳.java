package com.alone.month.GuiZhou;

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
public class 贵阳 {
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://tjj.gygov.gov.cn/c8026/";
		String number = "";
		String url = "";
		String charset = "";
		String dateSelector = "";
		String selector = "";
		String selector02 = "";
		String selector03 = "";
		String selector04 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://tjj.gygov.gov.cn/c8026/pages/2.html";
			selector = "body > table:nth-child(4) > tbody > tr > td:nth-child(2) > table:nth-child(4) > tbody > tr:nth-child(1) a[href]";
			selector02 = ".content a[href]";
			selector03 = "#shLink";
			 charset = "utf-8";
			// 获取发布时间
			// dateSelector = "td.pubtimecontentstyle77255 > span";

			String hrefSelector = "body > div.main > div.content > div > div:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > a[href]";

			List<String> list = new ArrayList<String>();

			filepath = "G:\\数据\\贵州\\地级市\\贵阳\\2016\\";
			// 创建文件路径
			// CrawlerUtil.dirCheck(filepath);

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(selector);
			// <a href="/lanmu/zwgk/contents/503/15623.html"
			// class="htitle-color" target="_blank"> 2017年1-11月全市主要经济指标 </a>
			// System.out.println(eles);
			for (Element element : eles) {
				List<Object> list2 = new ArrayList<>();
				String name02 = element.text();
				String name = name02.replace(" ", "%20");
				String href = element.attr("abs:href");
				// System.err.println(href);
				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					Elements elements = html02.select(selector02);
					if (elements.size() != 0) {

						String href02 = elements.attr("abs:href");
						if (href02 != null && !"".equals("href02")) {
							
							String decode02 = href02.replace(" ", "%20");
							//String decode = URLDecoder.decode(href02,"UTF-8");
							
							
							
							Document html03 = CrawlerUtil.getFromHtml02(decode02, "gb2312");
							Elements elements2 = html03.select(selector03);
							if (elements2.size() != 0 && !elements2.isEmpty()) {

								for (Element element2 : elements2) {
									String sheetUrl = element2.attr("abs:href");
									if (sheetUrl != null && !sheetUrl.equals("")) {
//										String sheetUrl02 =UrlEncoded.encodeString(sheetUrl);
										String sheetUrl02 = sheetUrl.replace(" ", "%20");
										// String
										// sheetUrl02=URLDecoder.decode(replace,"gb2312");
										System.err.println(sheetUrl02);

										String finalUrl = sheetUrl.substring(sheetUrl02.lastIndexOf(".") - 3,
												sheetUrl02.lastIndexOf("."));

										if (!"001".equals(finalUrl) && !"002".equals(finalUrl)) {

											// 创建文件路径
											CrawlerUtil.dirCheck(filepath + name + "\\");

											CrawlerUtil.downloadFromHtml(
													filepath + name + "\\" + name + finalUrl + ".xls", sheetUrl02);
										}
									}
								}

							}

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
