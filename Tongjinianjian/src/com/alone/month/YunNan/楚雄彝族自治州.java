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
public class 楚雄彝族自治州 {
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
			url = "http://www.cxtj.gov.cn/NewsList.aspx?Classid=394FBAE9-FA0D-43DF-AAF4-D772ED79B261";
			charset = "utf-8";
			htmlselector = ".class_list li a[href]";
			htmlSelector02 = ".c_content_text";
			imgSelector = "#artibody > p > img[src]";
			// 获取发布时间
			// dateSelector = "td.pubtimecontentstyle77255 > span";

			String hrefSelector = "body > div.main > div.content > div > div:nth-child(3) > table > tbody > tr:nth-child(2) > td > table > tbody > tr:nth-child(2) > td > a[href]";

			List<String> list = new ArrayList<String>();

			filepath = "G:\\数据\\云南\\地级市\\楚雄彝族自治州\\统计信息\\2017\\";
			// 创建文件路径
			CrawlerUtil.dirCheck(filepath);

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements eles = doc.select(htmlselector);
			System.err.println(eles.size());
			for (Element element : eles) {
				List<Object> list2 = new ArrayList<>();
				String name = element.text();
				String href = element.attr("abs:href");

				if (href != null && !"".equals(href)) {
					Document html02 = CrawlerUtil.getFromHtml02(href, charset);
					Elements elements = html02.select(htmlSelector02);

					String content = "<table>" + elements + "</table>";
					writeXls(filepath + name + ".xls", content, charset);
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
