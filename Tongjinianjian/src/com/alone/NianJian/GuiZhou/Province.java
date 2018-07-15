package com.alone.NianJian.GuiZhou;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class Province {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		String baseUrl = "http://www.gz.stats.gov.cn/tjsj_35719/sjcx_35720/gztjnj_40112/2014n/";
		String url = "";
		String charset = "";
		String selector = "";
		String titleSelector = "";
		String filepath = "";
		for (int i = 1; i < 19; i++) {
			System.err.println("------------------------"+i);
			int index = i;
			url = "http://www.gz.stats.gov.cn/tjsj_35719/sjcx_35720/gztjnj_40112/2014n/index_"+index+".html";
			charset = "utf-8";
			selector = "[style=font-size:16px; color:#0066cc;]";
			titleSelector = "div[style=font-size:25px; text-align:center; color:#000000; font-weight:800;]";
			filepath = "G:\\数据\\统计年鉴\\贵州\\省\\2014\\";

			CrawlerUtil.dirCheck(filepath);

			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements scripts = doc.select("script");

			for (Element script : scripts) {
				String totalPage = "var str_1 =";

				if (script.html().contains(totalPage)) // 注意这里一定是html(),
														// 而不是text()
				{
					String totalPageValue = CrawlerUtil.getPageByVar(script, totalPage); // 获取总页数

					String finalUrl = baseUrl + totalPageValue;

					Document doc02 = CrawlerUtil.getFromHtml02(finalUrl, charset);
					Elements element = doc02.select(selector);
					
					if (element.size()!=0 && !element.isEmpty()) {
						
						String href = element.attr("abs:href");
						if (href.contains("U020171123600167522840")||href.contains("U020171123603997745349")||href.contains("U020171123604251833788")||href.contains("U020171123604526197217")||href.contains("U020171123606936200901")||href.contains("U020171205622379242658")||href.contains("U020171201364585872076")||href.contains("U020171205620952499960")||href.contains("U020171205627232776130")) {
							continue;
						}
						String name = element.text().replace(Jsoup.parse("&nbsp;").text(), "");

						CrawlerUtil.downloadFromHtml(filepath + name, href);
						
					}else{
						
						Elements elements = doc02.select("div[style=font-size:14px;width:920px;]");
						String contents = "<table>"+elements+"</table>";
						String name02=doc02.select(titleSelector).text().replace(Jsoup.parse("&nbsp;").text(), "");
						String name = name02.substring(name02.indexOf("》")+1).trim();
						//String name = name02.substring(name02.indexOf("《2016年贵州统计年鉴》"));
						writeXls(filepath + name + ".xls", contents, charset);
						System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
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
