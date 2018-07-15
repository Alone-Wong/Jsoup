package com.alone.month.HuBei;

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
public class 神农架 {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String selector03 = "";
		String selector04 = "";
		String filepath = "";
		// 循环页码
		for (int i = 1; i < 3; i++) {
			System.err.println("第" + '\t' + i + '\t' + "页");
			number = i + "";
			url = "http://www.snj.gov.cn/snj_xxgk/xxgkzl/xxgkml/tjgb/list_4612_"+number+".shtml";
			charset = "utf-8";
			selector = ".xxgkml_table table tr:has(a[href])";
			selector02 = ".am-article-bd abc";
			selector03 = ".TRS_Editor";

			filepath = "G:\\数据\\湖北\\地级市\\神农架\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements elements = doc.select(selector);

			for (Element element : elements) {
				String releaseDate = element.select("td:eq(3)").text().substring(0, 4);
				// 获取链接绝对地址
				String link = element.select("a[href]").attr("abs:href");
				// 获取名称
				String name = element.select("a[href]").text();
				// 创建文件夹--发布时间
				CrawlerUtil.dirCheck(filepath + releaseDate + "\\");
				// 创建图片集合
				List<String> imgList = new ArrayList<String>();

				if (link != null && !"".equals(link)) {
					/**
					 * 判断是否以.xls结尾--可直接下载
					 */
					if (link.contains(".xls") || link.contains(".xlsx")) {
						// 下载
						CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".xls", link);
					}
					/**
					 * 判读是否以.htm结尾
					 */
					else {
						// 获取document
						Document htmlDoc = CrawlerUtil.getFromHtml02(link, charset);
						Elements htmlElements = htmlDoc.select(selector02);
						/**
						 * 判断是否是PIC
						 */
						if (htmlElements.size() != 0 && !htmlElements.isEmpty()) {
							String picUrl = htmlElements.attr("abs:src");
							CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".png", picUrl);

						}
						/**
						 * 文字 文字,图片混合
						 */
						else {
							// 获取文本信息
							Elements textElements = htmlDoc.select(selector03);
							// 获取图片信息
							Elements imgElements = htmlDoc.select(selector03).select("img[href]");

							/**
							 * 如果文字和图片混合的情况
							 */
							if (imgElements.size() != 0) {

								for (int j = 0; j < imgElements.size(); j++) {
									String fixIndex = "";
									String imgLink = imgElements.get(j).attr("abs:src");
									if (imgLink != null && !"".equals(imgLink)) {

										imgList.add(imgLink);
									}

								}

								// 遍历下载图片 保存图片至文本目录
								for (int j = 0; j < imgList.size(); j++) {
									String picIndex = "";
									if (j == 0) {
										picIndex = "";
										CrawlerUtil.dirCheck(filepath + releaseDate + "\\" + name + "\\");
										CrawlerUtil.downloadFromHtml(
												filepath + releaseDate + "\\" + name + "\\" + name + picIndex + ".png",
												imgList.get(j));
									} else {
										picIndex = (j + 1) + "";
										// CrawlerUtil.dirCheck(filepath +
										// releaseDate + "\\" +name+"\\");
										CrawlerUtil.downloadFromHtml(
												filepath + releaseDate + "\\" + name + "\\" + name + picIndex + ".png",
												imgList.get(j));
									}
								}
								// 下载文本 此时的textElements其中路径已经修改
								String contents = "<table>" + textElements + "</table>";
								CrawlerUtil.dirCheck(filepath + releaseDate + "\\" + name + "\\");
								writeXls(filepath + releaseDate + "\\" + name + "\\" + name + ".xls", contents,
										charset);
								System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);

							} // 如果只是文字
							else if (textElements.size() != 0 && imgElements.size() == 0) {
								String contents = "<table>" + textElements + "</table>";
								writeXls(filepath + releaseDate + "\\" + name + ".xls", contents, charset);
								System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);
							}

						}

					}

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
