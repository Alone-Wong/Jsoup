package com.alone.NianJian.QingHai;

import java.io.UnsupportedEncodingException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class Province {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String charset = "gb2312";
		String url = "http://www.qhtjj.gov.cn/nj/2012/lefte.htm";

		String selector = "#foldheader";

		String filefolder = "G:/数据/统计年鉴/青海/2012/";
		
		Document doc = CrawlerUtil.getFromHtml02(url, charset);
		
		//获取指定元素集
		Elements elements = doc.select(selector);

		for (Element element : elements) {
			String folderPath = element.text().trim();
			Elements nextElements = element.nextElementSibling().select("li>a[href]");
			if (nextElements.size()!=0)
				for (Element nextElement : nextElements) {
					String name = nextElement.text();
					String link = nextElement.attr("abs:href");
					if (!link.contains("/0324.xls")) {
						CrawlerUtil.dirCheck(filefolder + folderPath + "/");
						CrawlerUtil.downloadFromHtml(filefolder + folderPath + "/" + name + ".xls", link);
					}
				}
			
			
			
		/*	for (Element nextElement : nextElements) {
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
			}*/
		}
	}
}
