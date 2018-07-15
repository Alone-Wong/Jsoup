package com.alone.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class JsoupPostTest {
	
	// 程序主入口
	public static void main(String[] args) {
		// 获取配置参数
		String cookie = getProperties("cookie");
		String __EVENTTARGET = getProperties("__EVENTTARGET");
		String __VIEWSTATE = getProperties("__VIEWSTATE");
		String __VIEWSTATEGENERATOR = getProperties("__VIEWSTATEGENERATOR");
		String __EVENTVALIDATION = getProperties("__EVENTVALIDATION");
		try {
			// jsoup向指定页面发送post请求
			postOk(cookie, __EVENTTARGET, __VIEWSTATE, __VIEWSTATEGENERATOR, __EVENTVALIDATION);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/* * jsoup向指定的页面发送post请求 */
	public static String postOk(String cookie, String __EVENTTARGET, String __VIEWSTATE, String __VIEWSTATEGENERATOR, String __EVENTVALIDATION) throws IOException {
		// 获取请求连接
		Connection con = Jsoup.connect("http://ez.hb.stats.cn/(X(1)S(keczcjd5iqurbad5mk23yy0j))/ilist.aspx?5&AspxAutoDetectCookieSupport=1")
				.userAgent(
						"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.64 Safari/537.31");
		// 发送参数
		con.data("cookie", cookie);
		con.data("__EVENTTARGET", __EVENTTARGET);
		con.data("__VIEWSTATE", __VIEWSTATE);
		con.data("__VIEWSTATEGENERATOR", __VIEWSTATEGENERATOR);
		con.data("__EVENTVALIDATION", __EVENTVALIDATION);
		
		Document doc = con.post();
		// 将获取到的内容打印出来
		System.out.println(doc);
		return doc.toString();
	}

	/* * 读取配置信息 */
	public static String getProperties(String key) {
		Properties props = new Properties();
		try {
			// 读取配置文件
			FileInputStream in = new FileInputStream("src/config.properties");
			props.load(in);
			
			// 关闭资源
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props.getProperty(key);
	}
}