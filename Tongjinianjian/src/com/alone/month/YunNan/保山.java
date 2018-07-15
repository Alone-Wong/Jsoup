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

public class 保山 {
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://www.baoshan.gov.cn/zw_list.jsp?urltype=tree.TreeTempUrl&wbtreeid=1381";
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String dateSelector = "";
		String htmlselector = "";
		String filepath = "";
		//循环页码
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://www.baoshan.gov.cn/zw_list.jsp?ainfolist77255t=6&ainfolist77255p=6&ainfolist77255c=15&urltype=tree.TreeTempUrl&wbtreeid=1381";
			charset = "utf-8";
			htmlselector = "tbody a[href]";
			dateSelector = "td.pubtimecontentstyle77255 > span";
			String hrefSelector = "body > div.main > div.content > div > div:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > a[href]";

			List<String> list = new ArrayList<String>();
			filepath = "G:\\数据\\云南\\地级市\\保山\\2015\\";

			//抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);
			
			Elements eles = doc.select(htmlselector);

			// 创建文件路径
			CrawlerUtil.dirCheck(filepath);
			for (Element element : eles) {
				//获取绝对路径,判断是否是以htm结尾,如是,解析
				String href = element.attr("abs:href");
				if (href.endsWith(".htm")) {
					//获取月报名称
					String name = element.text();
					Document document = CrawlerUtil.getFromHtml02(href, charset);
					//获取月报下载附件路径
					Elements ele = document.select(hrefSelector);
					//判断是否有下载路径
					if (ele.size() != 0) {
						String downUrl = ele.attr("abs:href");
						if (downUrl != null && !"".equals(downUrl)) {
							System.out.println("下载路径==>>" + downUrl);
							CrawlerUtil.downloadFromHtml(filepath + name + ".xls", downUrl);
						}
						//如果没有下载路径,解析文本
					} else {
						System.err.println("下载路径为null");
						Elements ele02 = document.select("#vsb_content");
						String content = "<table>" + ele02 + "</table>";
						writeXls(filepath + name + ".xls", content, "utf-8");
						System.out.println("文件<=====" + name + "======>>" + "写入到" + filepath);
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
