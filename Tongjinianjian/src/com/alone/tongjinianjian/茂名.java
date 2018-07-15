package com.alone.tongjinianjian;

import com.alone.utils.CrawlerUtil;

public class 茂名 {

	public static void main(String[] args) {
		String firsturl = "http://tjj.maoming.gov.cn/dznj/ebook/2012/UpImages/2012(";
		String lasturl  = ").jpg";
		String filepath = "C:/Users/74707/Desktop/数据/广东/茂名/2012/";
		CrawlerUtil.dirCheck(filepath);
		for (int i=1;i<=321;i++) {
			String url = firsturl+i+lasturl;
			CrawlerUtil.downloadFromHtml(filepath+i+".jpg", url);
		}
	}
	
	
	

}
