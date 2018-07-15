package com.alone.NianJian;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class JiangXi {

	public static void main(String[] args) throws UnsupportedEncodingException {
		// String url = "http://www.jxstj.gov.cn/resource/nj/2016CD/left.htm";
		// String url = "http://www.jxstj.gov.cn/resource/nj/2015CD/left.htm";
		// String url = "http://www.jxstj.gov.cn/resource/nj/2014CD/left.htm";
		// String url = "http://www.jxstj.gov.cn/resource/nj/2013CD/left.htm";
		String url = "http://www.jxstj.gov.cn/resource/nj/2012cd/left.htm";

		String selector = "#foldheader";

		// String filefolder = "G:/数据/江西/统计年鉴/2016/";
		// String filefolder = "G:/数据/江西/统计年鉴/2015/";
		// String filefolder = "G:/数据/江西/统计年鉴/2014/";
		// String filefolder = "G:/数据/江西/统计年鉴/2013/";
		String filefolder = "G:/数据/江西/统计年鉴/2012/";
		
		// GBK编码
		// Document doc = CrawlerUtil.getHtml(url);
		Document doc = null;
		
		//解析URL
		try {
			doc = Jsoup.parse(new URL(url).openStream(), "Unicode", url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//获取指定元素集
		Elements elements = doc.select(selector);

		for (Element element : elements) {
			String folderPath = element.text().replaceAll("[a-zA-Z0-9,-’‘]", "").trim() + "/";
			// String select = element.select("a[href]").attr("abs:href").replace("\\", "/");
			// System.out.println(select);

			// String replace = element.attr("abs:href").replace("\\", "/");
			// CrawlerUtil.dirCheck(filefolder + path+"/" );
			// CrawlerUtil.downloadFromHtml(filefolder + path+"/" +path +
			// ".pdf", select);

			Elements nextElements = element.nextElementSibling().select("li>a[href]");
			for (Element nextElement : nextElements) {
				String path = nextElement.text();
				Pattern p = Pattern.compile("[a-zA-z]");
				if (!p.matcher(path).find()) {
					String text = nextElement.attr("abs:href").replace("\\", "/").replace(" ", "%20");
					//URL转码
					path=URLDecoder.decode(path,"utf-8");
					//创建文件夹
					CrawlerUtil.dirCheck(filefolder + folderPath);
					//文件下载
					CrawlerUtil.downloadFromHtml(filefolder + folderPath + path + ".xls", path);
				}
			}
		}
	}
}
