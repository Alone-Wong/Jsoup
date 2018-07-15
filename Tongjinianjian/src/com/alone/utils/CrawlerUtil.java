package com.alone.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import sun.net.www.protocol.ftp.Handler;

public class CrawlerUtil {

	public static void main(String[] args) {

	}

	public static void removeFile(String oldPath, String newPath) {
		File oldFile = new File(oldPath);
		File newFile = new File(newPath);
		oldFile.renameTo(newFile);
	}

	public static int dirCheck(String aimpath) {
		File file = new File(aimpath);
		if (file.exists()) {

			if (file.isDirectory()) {
				return 1;
			} else {
				System.out.println(file.getAbsolutePath() + "这是文件不是目录");
				return 0;

			}
		} else {
			file.mkdirs();
			return 1;
		}
	}

	public static Elements getFromLocalHtml(String htmlpath, String selector, String charset) {
		Elements eles = null;
		try {
			File in = new File(htmlpath);
			Document doc = Jsoup.parse(in, charset);
			eles = doc.select(selector);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eles;
	}

	public static Elements getBoby(String url) {
		Elements body = null;
		try {
			Document doc = Jsoup.connect(url).timeout(30000).get();
			body = doc.select("body");

		} catch (IOException e) {
			e.printStackTrace();
		}
		return body;
	}

	public static Elements getFromHtml(String url, String selector, String charset, String baseurl) {
		Elements eles = null;
		Document doc = null;
		try {
			// URL htmurl = new URL(url);
			URL htmurl = new URL(null, url, new Handler());
			// HttpURLConnection connection =
			// (HttpURLConnection)htmurl.openConnection();
			URLConnection connection = htmurl.openConnection();
			// connection.setRequestMethod("GET");
			// 是否允许缓存，默认true。
			connection.setUseCaches(Boolean.FALSE);
			// 设置连接主机超时（单位：毫秒）
			connection.setConnectTimeout(18000);
			// 设置从主机读取数据超时（单位：毫秒）
			connection.setReadTimeout(18000);
			doc = Jsoup.parse(connection.getInputStream(), charset, url);
			eles = doc.select(selector);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return eles;
	}

	public static Document getFromHtml02(String url, String charset) {
		Document doc = null;
		try {
			URL htmurl = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) htmurl.openConnection();
			connection.setRequestMethod("GET");
			// 是否允许缓存，默认true。
			connection.setUseCaches(Boolean.FALSE);
			// 设置连接主机超时（单位：毫秒）
			connection.setConnectTimeout(1800000);
			// 设置从主机读取数据超时（单位：毫秒）
			connection.setReadTimeout(1800000);
			doc = Jsoup.parse(connection.getInputStream(), charset, url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

	public static void downloadFromHtml(String filepath, String downloadurl) {
		HttpURLConnection con = null;
		InputStream inputStream = null;
		FileOutputStream fos = null;
		File file = null;
		try {
			file = new File(filepath);
			// 根据需求看看是否注释掉
			if (file.exists()) {
				System.out.println(file.getAbsolutePath() + "文件已存在");
				return;
			}
			URL url = new URL(downloadurl);
			con = (HttpURLConnection) url.openConnection();
			con.setReadTimeout(10000);
			con.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36");
			inputStream = con.getInputStream();
			// // 设定请求的方法，默认是GET
			// con.setRequestMethod("GET");
			// // 设置字符编码
			// con.setRequestProperty("Charset", "UTF-8");
			fos = new FileOutputStream(file);
			/* 循环写入 */
			byte[] b = new byte[1024];
			int i;
			while ((i = inputStream.read(b, 0, 1024)) > 0) {
				fos.write(b, 0, i);
			}
			fos.flush();
			fos.close();
			inputStream.close();
			con.disconnect();
			System.out.println(downloadurl + "下载完成==========>" + filepath);
		} catch (IOException e) {
			e.printStackTrace();
			try {
				fos.close();
				inputStream.close();
				con.disconnect();
				file.delete();
				System.err.println(downloadurl + "===>" + file.getName() + "下载失败");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public static String getJson(String url) {
		String json = null;
		try {
			Document document = Jsoup.connect(url).get();
			json = JSON.toJSONString(document);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static Document getHtml(String url) {
		Document doc = null;
		try {
			doc = Jsoup.connect(url).userAgent(
					"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
					/* .ignoreContentType(true) */
					.timeout(10000).get();

			// IE8:mozilla/4.0 (compatible; msie 8.0; windows nt 6.0;
			// trident/4.0)

		} catch (IOException e) {
			e.printStackTrace();
		}

		return doc;
	}

	public static JSONObject getContent(String url) {
		String doc = getHtml(url).toString();
		String start = "<body>";
		String end = "</body>";
		int s = doc.indexOf(start) + start.length();
		int e = doc.indexOf(end);
		String content = doc.substring(s, e);
		JSONObject json = JSON.parseObject(content);
		return json;
	}

	public static JSONObject crawler(String url) {
		JSONObject json = getContent(url);
		return json;
	}

	/**
	 * 根据参数名获取参数值
	 * 
	 * @param script
	 * @param varString
	 * @return
	 */
	public static String getPageByVar(Element script, String varString) {

		String value = "";

		String str = script.html().replace("\n", ""); // 解决 无法多行匹配的问题
		String pattern = varString + " (.*?);"; // 数字的正则表达式

		Pattern r = Pattern.compile(pattern, Pattern.MULTILINE);
		Matcher m = r.matcher(str);
		if (m.find()) {
			String var = m.group();
			// System.out.println(option_1);
			value = var.replace(varString + " ", "");

			value = value.substring(3, value.length() - 2);
		}

		return value;
	}
	
	/**
	 * post
	 */
	public static Document httpPost(String url,Map<String,String> map,String cookie) throws IOException{
        //获取请求连接
        Connection con = Jsoup.connect(url);
        //遍历生成参数
        if(map!=null){
            for (Entry<String, String> entry : map.entrySet()) {     
               //添加参数
                con.data(entry.getKey(), entry.getValue());
               } 
        }
        //插入cookie（头文件形式）
       // con.header("Cookie", cookie);
        Document doc = con.post();  
        //System.out.println(doc);
        return doc;
    }

	/**
	 * 写入文件
	 */
	public static void writeXls(String path, String content, String encoding) throws IOException {
		File file = new File(path);
		file.delete();
		file.createNewFile();
		BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), encoding));
		writer.write(content);
		writer.close();
	}


}
