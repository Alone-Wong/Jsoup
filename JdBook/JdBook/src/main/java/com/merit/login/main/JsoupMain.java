package com.merit.login.main;

import java.net.URLDecoder;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.merit.login.domain.Model;
import com.merit.login.utils.URLFecter;

public class JsoupMain {
	    //log4j的是使用，不会的请看之前写的文章
	    static final Log logger = LogFactory.getLog(JsoupMain.class);
	    public static void main(String[] args) throws Exception {
	        //初始化一个httpclient
	        HttpClient client = new DefaultHttpClient();
	        //我们要爬取的一个地址，这里可以从数据库中抽取数据，然后利用循环，可以爬取一个URL队列
	        String url="https://search.jd.com/Search?keyword=java%E7%BC%96%E7%A8%8B%E6%80%9D%E6%83%B3&enc=utf-8&suggest=2.def.0.V03&wq=Java&pvid=a9cd265698cc40569915e726a07e187e";
	        String decode = URLDecoder.decode(url, "UTF-8");
	        System.out.println(decode);
	        //抓取的数据
	        List<Model> bookdatas=URLFecter.URLParser(client, url);
	        //循环输出抓取的数据
	        for (Model jd:bookdatas) {
	            logger.info("bookID:"+jd.getBookID()+"\t"+"bookPrice:"+jd.getBookPrice()+"\t"+"bookName:"+jd.getBookName());
	        }
	        //将抓取的数据插入数据库
//	        MYSQLControl.executeInsert(bookdatas);
	    }
	}