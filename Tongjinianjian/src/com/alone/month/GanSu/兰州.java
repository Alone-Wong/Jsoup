package com.alone.month.GanSu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

import static com.alone.month.GanSu.Province.writeXls;

@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public class 兰州 {
    public static void main(String[] args) throws IOException {
        String number = "";
        String url = "";
        String charset = "";
        String selector = "";
        String selector02 = "";
        String filepath = "";
        String baseUrl = "http://tjj.lanzhou.gov.cn/";
        // 循环页码
        for (int i = 4; i < 6; i++) {
            number = i + "";
            url = "http://tjj.lanzhou.gov.cn/col/col4851/index.html?uid=18204&pageNum=" + number;
            charset = "utf-8";
            selector = "div#zoom a[href]";
            selector02 = ".MsoNormalTable";

            filepath = "G:\\数据\\甘肃\\地级市\\兰州\\月报\\20179\\";
            List<Map> mapList = new ArrayList<Map>();
            CrawlerUtil.dirCheck(filepath);

            // 抓取页面数据
            Document doc = CrawlerUtil.getFromHtml02(url, charset);

            String html = doc.getElementById("18204").getElementsByTag("script").html();

            try {
                org.dom4j.Document parseText = DocumentHelper.parseText(html);
                Element root = parseText.getRootElement();
                Element element = root.element("recordset");
                List<Element> elements = element.elements();
                for (Element e : elements) {
                    if (!"style".equals(e.getName())) {
                        String text = e.getText();
                        Document doc1 = Jsoup.parse(text);
                        String secondUrl = doc1.select("a[href]").attr("href");
                        String name = doc1.select("a[href]").text();
                        String finalUrl = baseUrl + secondUrl;

                        Document html02 = CrawlerUtil.getFromHtml02(finalUrl, charset);
                        Elements elements2 = html02.select(selector);
                        if (!elements2.isEmpty() && elements2.size() != 0) {
                            String pdfUrl = elements2.attr("abs:href");
                            String suf = pdfUrl.substring(pdfUrl.lastIndexOf("."));

                            CrawlerUtil.downloadFromHtml(filepath + name + suf, pdfUrl);
                        } else {

                            Elements elements3 = html02.select(selector02);

                            writeXls(filepath + name + ".xls", elements3.toString(), charset);
                            System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
                            System.err.println("woaini ");
                        }
                    }

                }

            } catch (DocumentException e) {
                e.printStackTrace();
            }

        }

    }


}
