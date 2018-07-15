package com.alone.NianJian.GuiZhou;

import com.alone.utils.CrawlerUtil;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class 毕节2 {
    public static void main(String[] args) throws IOException {
        String url = "http://tjj.gzlps.gov.cn/tjzl/tjgb_42980/201706/t20170602_1385192.html";
        String charset = "utf-8";
        String selector = ".nr_con";
        //year = "2016";
        String filepath = "G:\\数据\\统计年鉴\\贵州\\六盘水\\公报\\2011\\";
        CrawlerUtil.dirCheck(filepath);

        // 抓取页面数据
        Document doc = CrawlerUtil.getFromHtml02(url, charset);
        // String title = year + number;
        String title = "六盘水2011年国民经济和社会发展统计公报";
        Elements elements = doc.select(selector);
        Elements imgElements = doc.select(selector).select("img[src]");
        List<String> imgList = new ArrayList<>();
        if (imgElements.size() != 0) {
            for (int j = 0; j < imgElements.size(); j++) {
                String fixIndex = "";
                String imgLink = imgElements.get(j).attr("abs:src");
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
                    CrawlerUtil.downloadFromHtml(filepath + "\\" + title + picIndex + ".", imgList.get(j));
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


    public static void writeXls(String path, String content, String encoding) throws IOException {
        File file = new File(path);
        file.delete();
        file.createNewFile();
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
        writer.write(content);
        writer.close();
    }
}
