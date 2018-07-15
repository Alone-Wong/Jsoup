package com.alone.NianJian.GuiZhou;

import com.alone.utils.CrawlerUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class 毕节 {
    public static void main(String[] args) throws IOException {
        String number = "";
        String url = "";
        String charset = "";
        String selector = "";
        String filepath = "";
        String year = "";
        // 循环页码
        for (int i = 1; i < 8; i++) {
            if (i == 1) {
                number = "";
            } else {
                number = "_" + i + "";
            }
            url = "http://www.tjcn.org/tjgb/25yn/23796" + number + ".html";
            charset = "gb2312";
            selector = "#text";
            year = "2011";
            filepath = "G:\\数据\\统计年鉴\\云南\\保山\\" + year + "\\";
            CrawlerUtil.dirCheck(filepath);

            // 抓取页面数据
            Document doc = CrawlerUtil.getFromHtml02(url, charset);
            String title = year + number;
            Elements elements = doc.select(selector);
            Elements imgElements = doc.select(selector).select("img[src]");
            List<String> imgList = new ArrayList<>();
            if (imgElements.size() != 0) {
                for (int j = 0; j < imgElements.size(); j++) {
                    String fixIndex = "";
                    String imgLink = imgElements.get(j).attr("abs:src");
                    if (imgLink.endsWith("20150520124438442001.png")) continue;
                    if (j == 0) {
                        elements.select("img").get(j).attr("src", "./" + title + fixIndex + ".png");
                    } else {
                        fixIndex = (j + 1) + "";
                        elements.select("img").get(j).attr("src", "./" + title + fixIndex + ".png");
                    }
                    imgList.add(imgLink);

                }

                for (int j = 0; j < imgList.size(); j++) {
                    String picIndex = "";
                    if (j == 0) {
                        picIndex = "";
                        CrawlerUtil.downloadFromHtml(filepath + "\\" + title + picIndex + ".png", imgList.get(j));
                    } else {
                        picIndex = (j + 1) + "";
                        CrawlerUtil.downloadFromHtml(filepath + "\\" + title + picIndex + ".png", imgList.get(j));
                    }
                }

                String contents = "<table>" + elements + "</table>";
                writeXls(filepath + title + ".xls", contents, charset);
                System.out.println("文件<=====" + title + "=====>>" + "写入到" + filepath);

            } else {
                String contents = "<table>" + elements + "</table>";
                writeXls(filepath + title + ".xls", contents, charset);
                System.out.println("文件<=====" + title + "=====>>" + "写入到" + filepath);
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
