package com.alone.month.GuiZhou;


import com.alone.utils.CrawlerUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class 太原2 {
    public static void main(String[] args) throws IOException {
        String number = "";
        String url = "";
        String charset = "";
        String selector = "";
        String selector02 = "";
        String filepath = "";
        String baseUrl = "http://tjj.lanzhou.gov.cn/";
        // 循环页码
        for (int i = 1; i < 2; i++) {
            number = i + "";
            url = "http://www.tianqihoubao.com/lishi/linfen.html";
            charset = "gb2312";
            selector = "div#content li a[href]";
            selector02 = ".b";

            filepath = "G:\\数据\\山西\\临汾\\";
            List<Map> mapList = new ArrayList<Map>();
            CrawlerUtil.dirCheck(filepath);

            // 抓取页面数据
            Document doc = CrawlerUtil.getFromHtml02(url, charset);

            Elements elements = doc.select(selector);

            for (Element element : elements) {
                String href = element.attr("abs:href");
                String name = element.text();
                if (href != null && !"".equals(href)) {
                    Document document = CrawlerUtil.getFromHtml02(href, "GBK");

                    if (document != null && !"".equals(document)) {
                        Elements elements1 = document.select(selector02);
                        if (elements1.size() != 0) {
                            CrawlerUtil.dirCheck(filepath);
                            String content = "<table>" + elements1 + "</table>";
                            writeXls(filepath + name + ".xls", content, "Unicode");
                            System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
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
