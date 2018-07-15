package com.alone.month.GuiZhou;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class 毕节2 {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String filepath = "";
		// 循环页码
		for (int i = 21; i > 13; i--) {
			number = i + "";
			System.out.println("---------"+i);
			url = "http://www.bijie.gov.cn/gk/szfxxgkml/tjsj/tjxx/2211"+number+".shtml";
			charset = "utf-8";
			selector = ".Cnt-Article";
			selector02 = "body > div.box1 > div.icontainer > div.contTextBox > h3";
			filepath = "G:\\数据\\贵州\\地级市\\毕节\\";

			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			String name = doc.select(selector02).text();

			Elements elements = doc.select(".Cnt-Article");

			CrawlerUtil.dirCheck(filepath);

			String contents = "<table>" + elements + "</table>";

			writeXls(filepath + name + ".xls", contents, charset);

			System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);

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
