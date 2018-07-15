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
public class 咸宁 {
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
		for (int i = 1; i < 4; i++) {
			number = i + "";
			url = "http://tjj.enshi.gov.cn/ztjj/sjcx/tjyb/"+number+".shtml";
			charset = "utf-8";
			selector = ".clear.list-dot li";
			selector02 = "#article >dddddiv.article-content.mtb20.clear > p > a[href]";
			selector03 = "#shLink";
			selector04 = "body > table > tbody";

			filepath = "G:\\数据\\湖北\\地级市\\恩施\\";

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements elements = doc.select(selector);

			for (Element element : elements) {
				// 获取发布时间
				String releaseDate  = "";
				//String releaseDate = element.select("td[width=100]").text().substring(0,4);
//				String releaseDate = element.select("time").text().substring(0, 4);
//				if (releaseDate == null || "".equals(releaseDate)) {
//					continue;
//				}
				// 获取链接绝对地址
				String link = element.select("a[href]").attr("abs:href");
				// 获取名称
				String name= element.select("a[href]").text();
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
							String picUrl = htmlElements.attr("abs:href");
							CrawlerUtil.downloadFromHtml(filepath + releaseDate + "\\" + name + ".pdf", picUrl);

						}
						/**
						 * 文字 文字,图片混合
						 */
						else {
							// 获取每个bar的所有链接
							Elements textElements = htmlDoc.select(selector03);
							for (Element textElement : textElements) {
								//获取每个sheet的链接
								String href = textElement.attr("abs:href");
								if (href==null || "".equals(href)) {
									continue;
								}
								
								
								String hrefname = href.substring(href.lastIndexOf(".")-8,href.lastIndexOf(".") );
								Document html02 = CrawlerUtil.getFromHtml02(href, "gb2312");
								Elements elements2 = html02.select(selector04);
								Elements imgElements = html02.select(selector04).select("img[src]");
								
								/**
								 * 如果文字和图片混合的情况
								 */
								if (imgElements.size() != 0 ) {

									for (int j = 0; j < imgElements.size(); j++) {
										String fixIndex = "";
										String imgLink = imgElements.get(j).attr("abs:src");
										if (j == 0) {
											elements2.select("img").get(j).attr("src", "./" +hrefname+ fixIndex + ".png");
										} else {
											fixIndex = (j + 1) + "";
											elements2.select("img").get(j).attr("src", "./" +hrefname+ fixIndex + ".png");
										}
										if (imgLink.startsWith("file") || imgLink.endsWith("W020170527340676987330.jpg")
												|| imgLink.endsWith("2011年乌市公报_files/image001.jpg"))
											continue;
										imgList.add(imgLink);

									}

									// 遍历下载图片 保存图片至文本目录
									for (int j = 0; j < imgList.size(); j++) {
										String picIndex = "";
										if (j == 0) {
											picIndex = "";
											CrawlerUtil.dirCheck(filepath  + name + "\\"+hrefname+"\\");
											CrawlerUtil.downloadFromHtml(
													filepath + name + "\\"+hrefname+"\\" +hrefname + picIndex + ".png",
													imgList.get(j));
										} else {
											picIndex = (j + 1) + "";
											// CrawlerUtil.dirCheck(filepath +
											// releaseDate + "\\" +name+"\\");
											CrawlerUtil.downloadFromHtml(
													filepath  + name + "\\" +hrefname+"\\"+hrefname+ picIndex + ".png",
													imgList.get(j));
										}
									}
									// 下载文本 此时的textElements其中路径已经修改
									String contents = "<table>" + elements2 + "</table>";
									CrawlerUtil.dirCheck(filepath + name+ "\\" +hrefname+"\\");
									writeXls(filepath  + name + "\\" + hrefname +"\\"+hrefname+ ".xls", contents,
											charset);
									System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);

								} // 如果只是文字
								else if (elements2.size() != 0 && imgElements.size() == 0) {
									CrawlerUtil.dirCheck(filepath + name+ "\\");
									String contents = "<table>" + elements2 + "</table>";
									writeXls(filepath + name +"\\"+hrefname+ ".xls", contents, charset);
									System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath + releaseDate);
								}
								
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
