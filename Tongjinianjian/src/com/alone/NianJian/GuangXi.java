package com.alone.NianJian;

import com.alone.utils.CrawlerUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.UnsupportedEncodingException;

public class GuangXi {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String url = "http://www.gxtj.gov.cn/tjsj/tjnj/2017/lefte.htm";

        String charset = "gb2312";

        String selector = "#foldheader";

        String filefolder = "G:/数据/广西/统计年鉴/2017/";

        Document doc = CrawlerUtil.getFromHtml02(url, charset);

        //获取指定元素集
        Elements elements = doc.select(selector);

        for (Element element : elements) {

            String title = element.text().replaceAll("[a-zA-Z0-9,-’‘]", "").trim() + "/";

            Elements nextElements = element.nextElementSibling().select("li>a[href]");
            for (Element nextElement : nextElements) {
                String path = nextElement.text().replaceAll(Jsoup.parse("&nbsp;").text(), "");
                String href = nextElement.attr("abs:href");
                if (href.endsWith(".xls")) {
                    CrawlerUtil.dirCheck(filefolder + title + "\\");
                    CrawlerUtil.downloadFromHtml(filefolder + title + "\\" + path + ".xls", href);
                }
            }
        }
    }
}
