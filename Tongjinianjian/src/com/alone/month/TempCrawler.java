package com.alone.month;

import com.alone.utils.CrawlerUtil;
import com.alone.utils.ExportData;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class TempCrawler {
	public static void main(String[] args) throws IOException {
		String number = "";
		String url = "";
		String charset = "";
		String selector = "";
		String selector02 = "";
		String filepath = "";
		String baseUrl = "http://tjj.lanzhou.gov.cn/";


        List<Map> list = new ArrayList<>();

		// 循环页码
		for (int i = 2011; i < 2018; i++) {
			number = i + "";
			url = "https://en.tutiempo.net/climate/"+number+"/ws-538870.html";
			charset = "utf-8";
			selector = ".mlistados.mt10 ul li a[href]";
			selector02 = ".tc6";

			filepath = "G:\\数据\\山西湿度\\大同\\";
			
			CrawlerUtil.dirCheck(filepath);

			// 抓取页面数据
			Document doc = CrawlerUtil.getFromHtml02(url, charset);

			Elements elements = doc.select(selector);

            for (Element element : elements) {
            	
            	
            	 Map<String, String> map = new HashMap<>();
            	
				String href = element.attr("abs:href");
				String name = number+element.text();
				if (href != null && !"".equals(href)) {
					Document document = CrawlerUtil.getFromHtml02(href, "utf-8");

					if (document != null && !"".equals(document)) {
						Elements elements02 = document.select(selector02);
                        String him = elements02.get(0).text();

                        map.put("时间",name+"");
                        map.put("值",him);

                        list.add(map);
                        
                        System.err.println(number);

					}

				}

			}

		}

        System.out.println(list.size());
        String[] headers = { "时间", "值" };
        String[] Col = { "时间", "值"};

        // ExportData.export(headers, Col, list);
        ExportData.export(headers,Col,list);




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
