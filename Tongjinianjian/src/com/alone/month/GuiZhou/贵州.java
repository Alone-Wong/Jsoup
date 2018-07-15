package com.alone.month.GuiZhou;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class 贵州 {

	public static void main(String[] args) throws IOException {
		String url = "";
		//1-35:标签
		String index = "";
		for (int i = 1; i < 36; i++) {
			if (i <= 9) {
				index = 0 + i + "";
			} else {
				index = "" + i;
			}

			url = "ftp://58.16.65.217:90/guiyangdq/gzstjj/20161107/tjyb201612.files/sheet0" + index + ".htm";

		}

		String baseurl = "";
		//0-7:页码
		String page = "";
		for (int ii = 0; ii < 7; ii++) {
			if (ii == 0) {
				page = "";
			} else {
				page = ii + "";
			}
			baseurl = "http://www.gz.stats.gov.cn/tjsj_35719/sjcx_35720/tjyb_35721/index_+" + page + ".html";
		}

		String charset = "gb2312";
		
		String selector = "body > div > div > div > div:nth-child(2) > "
				+ "div.ny_bg1 > div > div > table > tbody > tr > td:nth-child(2) > div > ul > " + "li:nth-child("
				+ ") > span.lbx > a";
		String selector02 = "body > table > tbody > tr:nth-child(1) > td";
		String htmlselector = "body";
	
		String filepath = "G:/数据/贵州/2017/";
		
		Elements doc = CrawlerUtil.getFromHtml(url, htmlselector, charset, url);
		Elements name = doc.select(selector02);
		//Elements eles = doc.select(htmlselector);

		贵州.copyHtml(url, htmlselector, name + ".xls", filepath+name+"/");
		// for (Element li : doc) {
		// String download = li.attr("abs:href");
		// String filename = li.text() + ".xls";
		// System.out.println(filename + ":" + download);
		// copyHtml(download, htmlselector, filename, filepath);
		// }
	}

	public static void writeXls(String path, String content, String encoding) throws IOException {
		File file = new File(path);
		file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		writer.write(content);
		writer.close();
	}

	public static void copyHtml(String url, String selector, String filename, String filepath) {
		try {
			Elements xls = CrawlerUtil.getFromHtml(url, selector, "gb2312", "");
			CrawlerUtil.dirCheck(filepath);
			writeXls(filepath + filename, xls.toString(), "utf-8");
			//CrawlerUtil.downloadFromHtml(filepath + ".xls", text);
			System.out.println("文件" + filename + "写入到" + filepath);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
