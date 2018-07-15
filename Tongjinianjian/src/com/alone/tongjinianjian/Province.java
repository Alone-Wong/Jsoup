package com.alone.tongjinianjian;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class Province {

	public static void main(String[] args) {
		String url = "http://www.gxtj.gov.cn/tjsj/tjnj/2012/lefte.htm";
		String selector = "li";
		String charset = "gb2312";
		String baseurl = "http://www.gxtj.gov.cn/tjsj/tjnj/2012/";
		String filepath = "I:/数据/广西/统计年鉴/2012/";

		Elements lis = CrawlerUtil.getFromHtml(url, selector, charset, baseurl);
		System.out.println(lis);
		//System.out.println(lis);
		String filefolder = null;
		for (Element li : lis) {
			if (li.id().equals("foldheader")) {
				filefolder = li.text() + "/";
			}
			Elements a = li.select("a[href]");
			if(a.hasText()){
				String downloadurl = a.attr("abs:href");
				String filename = a.text().replace("\\", "每").replace("/","每");
				String filedst = null;
				CrawlerUtil.dirCheck(filepath + filefolder);
				if (downloadurl.endsWith(".xls")) {
					filedst = filepath + filefolder + filename + ".xls";
				} else if (downloadurl.endsWith(".jpg")) {
					filedst = filepath + filefolder + filename + ".jpg";
				} else if (downloadurl.endsWith(".htm")) {
					filedst = filepath + filefolder + filename + ".htm";
				} else {
					//continue;
				}
				CrawlerUtil.downloadFromHtml(filedst, downloadurl);
			}
		}
	}

}
