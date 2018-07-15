package com.alone.month.YunNan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class 昆明 {
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://www.stats.yn.gov.cn/TJJMH_Model/";
		String url = "http://tjj.km.gov.cn/c/2014-04-01/1516613.shtml";
		String charset = "utf-8";
		String selector = "body > div:nth-child(9) > div > div.right > div > div.right_down > h1";
		String htmlselector = "body > div:nth-child(9) > div > div.right > div > div.right_down p";

		List<String> list = new ArrayList<String>();
		String filepath = "G:\\数据\\云南\\地级市\\昆明\\2011\\";
		
		Document doc = Jsoup.parse(new URL(url).openStream(), charset, url);
		String text = "2014"+doc.select(selector).text();
		Elements elements2 = doc.select(htmlselector);
		
		writeXls(filepath + text + ".htm", elements2.toString(), "utf-8");

		System.out.println("文件<=====" + text + "======>>" + "写入到" + filepath);


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
