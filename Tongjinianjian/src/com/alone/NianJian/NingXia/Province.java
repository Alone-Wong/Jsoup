package com.alone.NianJian.NingXia;

import java.io.UnsupportedEncodingException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class Province {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String charset = "gb2312";
		String url = "http://www.nxtj.gov.cn/nxtjjxbww/tjsj/ndsj/2016/indexfiles/lefte.htm";

		String selector = "li#foldheader:gt(2)";

		String filefolder = "G:/数据/统计年鉴/宁夏/省/2016/";

		Document doc = CrawlerUtil.getFromHtml02(url, charset);

		// 获取指定元素集
		Elements elements = doc.select(selector);

		for (Element element : elements) {
			String title = element.text();
			CrawlerUtil.dirCheck(filefolder + title + "/");
			Elements linkElements = element.nextElementSibling().select("a[href]");
			if (linkElements.size() != 0 && !linkElements.isEmpty()) {
				for (Element linkElement : linkElements) {
					String href = linkElement.attr("abs:href");
					String name = linkElement.text().replaceAll("[a-zA-Z0-9,-’‘()]", "").trim();
					CrawlerUtil.downloadFromHtml(filefolder + title + "/" + name + ".xls", href);
				}
			}

		}
	}
}
