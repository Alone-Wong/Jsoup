package com.alone.month;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class Test {

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		Runtime rt = Runtime.getRuntime();
		String url = "http://tjj.zunyi.gov.cn/xxgk/zpfl/tjxx/tjsj/";
		String exec = "phantomjs";
		// Process p = rt.exec("‪cmd.exe /c start phantomjs");

		// InputStream is = p.getInputStream();
		//
		// Document doc = Jsoup.parse(is, "UTF-8", url);
		//
		// System.out.println(doc);

		Runtime rts = Runtime.getRuntime();
		Process ps = null;
		try {
			ps = rts.exec(
					"F:\\Java-Tools\\phantomjs-2.1.1-windows\\bin\\phantomjs F:\\Java-Tools\\phantomjs-2.1.1-windows\\bin\\test.js "
							+ url);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		InputStream is = ps.getInputStream();
		//
		Document doc = Jsoup.parse(is, "UTF-8", url);
		//
		//Elements e = doc.getElementsByClass("table").get(0).getElementsByTag("tbody");
		String selector = "#documentContainer > div.xxjs_form_tl > div.w384.left";
		Elements e = doc.select(selector);
		
		System.out.println(doc);

	}
}
