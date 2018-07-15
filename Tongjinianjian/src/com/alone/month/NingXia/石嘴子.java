package com.alone.month.NingXia;

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
public class 石嘴子 {
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
		for (int i = 1; i < 2; i++) {
			number = i + "";
			url = "http://szstjj.nxszs.gov.cn/tjkx1.html";
			charset = "gb2312";
			selector = "table tr";
			selector02 = ".TRS_Editor img[src]";
			selector03 = "body > table:nth-child(2) > tbody > tr > td > table > tbody > tr:nth-child(5)";
			// selector04 = ".Pages a[href]";

			filepath = "G:\\数据\\宁夏\\地级市\\石嘴子\\222";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements elements = doc.select(selector);

			for (Element element : elements) {
				// 获取发布时间
				String releaseDate = "";
				String date = element.select("td.kkll:eq(1)").text();
				if (date.length()>5) {
					 releaseDate = date.substring(0, 4);
				}
				// 获取链接绝对地址
				String link = element.select("td.kkll:eq(0) a[href]").attr("abs:href");
				// 获取名称
				String name = element.select("td.kkll:eq(0) a[href]").text();
				// 创建文件夹--发布时间
				CrawlerUtil.dirCheck(filepath + releaseDate + "\\");
				// 创建图片集合
				List<String> imgList = new ArrayList<String>();

				if (link != null && !"".equals(link)) {
					/**
					 * 判断是否以.xls结尾--可直接下载
					 */
					if (link.contains(".xls")) {
						// 下载
						CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".xls", link);
					}
					/**
					 * 判读是否以.htm结尾
					 */
					else {
						// 获取document
						Document htmlDoc = CrawlerUtil.getFromHtml02("http://szstjj.nxszs.gov.cn/html/2015011692458.html", charset);
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
							Elements imgElements = htmlDoc.select(selector03).select("img[src]");

							/**
							 * 如果文字和图片混合的情况
							 */
							if (imgElements.size() != 0 && textElements.size() != 0) {

								for (int j = 0; j < imgElements.size(); j++) {
									String fixIndex = "";
									String imgLink = imgElements.get(j).attr("abs:src");
									if (j == 0) {
										textElements.select("img").get(j).attr("src", "./" + name + fixIndex + ".png");
									} else {
										fixIndex = (j + 1) + "";
										textElements.select("img").get(j).attr("src", "./" + name + fixIndex + ".png");
									}
									imgList.add(imgLink);
								}

								// 遍历下载图片 保存图片至文本目录
								for (int j = 0; j < imgList.size(); j++) {
									String picIndex = "";
									String imgName = name + picIndex + ".png";
									if (j == 0) {
										picIndex = "";
										CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + imgName,
												imgList.get(j));
									} else {
										picIndex = (j + 1) + "";
										CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + imgName,
												imgList.get(j));
									}
								}
								// 下载文本 此时的textElements其中路径已经修改
								String contents = "<table>" + textElements + "</table>";
								writeXls(filepath + releaseDate + "\\" + name + ".xls", contents, charset);
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
