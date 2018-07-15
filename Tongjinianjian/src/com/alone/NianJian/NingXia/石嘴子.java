package com.alone.NianJian.NingXia;


import java.io.UnsupportedEncodingException;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class 石嘴子 {

    public static void main(String[] args) throws UnsupportedEncodingException {
        String charset = "gb2312";
        String url = "http://szstjj.nxszs.gov.cn/11tjnj1.html";

        String selector = "table a[href]";

        String filefolder = "G:/数据/统计年鉴/宁夏/石嘴子/201111/";
        CrawlerUtil.dirCheck(filefolder);
        Document doc = CrawlerUtil.getFromHtml02(url, charset);
        // 获取指定元素集
        Elements elements = doc.select(selector);
        for (Element element : elements) {
            String name = element.text();
            String link = element.attr("abs:href");
            if (name != null && !"".equals(name) && name.length() > 1) {
                if (link != null && !"".equals(link)) {
                    CrawlerUtil.downloadFromHtml(filefolder + name + ".xls", link);
                }
            }

        }
    }
}
