package com.alone.utils;

import java.io.IOException;
import java.util.HashMap;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * 抓取网页 http://www.qhdtjj.gov.cn/html/jjsj/index.html
 * 
 * @author 74707 更改map中的 currPage 键值 实现翻页
 */
public class PostUtils {
	public static void main(String[] args) {
		String url = "http://ez.hb.stats.cn/ilist.aspx?5&AspxAutoDetectCookieSupport=2";
		// double random = Math.random();
		// url = url + random;// 模拟链接上随机码
		HashMap<String, String> map = new HashMap<>();
		map.put("__EVENTTARGET", "SqlPager1$Last");
		map.put("__EVENTVALIDATION	",
				"/wEdAAcsHBRVBEE5Vc/Kfr7mZPupnbYBsnhL17heE9TTGQD+ZaTsZ0WjvF8kqBEQdp11vMyYQeLa52MLS80TELPTu7psJlnrFTcKXxnRKVbIeG4M9mNjfux9ozFpEUPuHr3UjBVtL/MKrKB9mzaT4Bej1Mq+zG8J/QLbmaKuqNQmQ1vBCw9mVLfJEt2Jtwv9ceavK0Q=");
		map.put("__VIEWSTATE",
				"/wEPDwUKLTc1NDU0NjMwOQ9kFgQCAQ9kFgICAg8WAh4EVGV4dAXGATxtZXRhIG5hbWU9ImtleXdvcmRzIiBjb250ZW50PSLphILlt57luILnu5/orqHlsYA76YSC5bee57uf6K6h5L+h5oGv572RO+mEguW3nue7n+iuoeS/oeaBr+e9kSIgLz4KPG1ldGEgbmFtZT0iZGVzY3JpcHRpb24iIGNvbnRlbnQ9IumEguW3nuW4gue7n+iuoeWxgDvphILlt57nu5/orqHkv6Hmga/nvZE76YSC5bee57uf6K6h5L+h5oGv572RIiAvPmQCAw9kFhICAQ8WAh8AZWQCAw8WAh8ABbcGPGRpdiBpZD0iZl9iZyI+DQo8ZGl2IGlkPSJmX2JnX2IiPg0KPGRpdiBpZD0iZl9iZ19pbiI+DQo8ZGl2IGlkPSJmX3RvcCI+DQo8ZGl2IGlkPSJmX3RvcF9pbiI+DQo8ZGl2IGlkPSJ0b3BkOCI+PGVtYmVkIGhl…2ZvbnQ+PC9kaXY+DQogICAgICAgICAgICA8ZGl2IHN0eWxlPSJ0ZXh0LWFsaWduOiBjZW50ZXIiPjxzcGFuIGlkPSJMYWJlbFJUUzAiPiZuYnNwOyA8L3NwYW4+PC9kaXY+DQogICAgICAgICAgICA8L2Rpdj4NCiAgICAgICAgICAgIDwvdGQ+DQogICAgICAgICAgICA8dGQgd2lkdGg9IjE1JSI+Jm5ic3A7DQo8c2NyaXB0IGlkPSJfaml1Y3VvXyIgc2l0ZWNvZGU9JzQyMDcwMDAwMTUnIHNyYz0naHR0cDovL3B1Y2hhLmthaXB1eXVuLmNuL2V4cG9zdXJlL2ppdWN1by5qcyc+PC9zY3JpcHQ+DQo8L3RkPg0KICAgICAgICA8L3RyPg0KICAgIDwvdGJvZHk+DQo8L3RhYmxlPg0KPC9kaXY+DQo8L2Rpdj5kAhEPFgIfAGVkZOVf2G6HmAuFdypFFXI3mmlRmNPzwvfu7X2C5cc4XCBk");
		map.put("__VIEWSTATEGENERATOR", "E277F190");
		map.put("Cookie",
				"yunsuo_session_verify=4256f70d267d7e57c256874148ab6c04; AspxAutoDetectCookieSupport=1; ASP.NET_SessionId=1cgkhs1nfdb5lnwwtjycowpw; safedog-flow-item=");
		// "/wEPDwUJNzg5MDQ0NzMxD2QWAgIDD2QWCGYPZBYWAgEPFgIeC18hSXRlbUNvdW50AgEWAmYPZBYCZg8VAWbjgIrkuK3lm73lsIbnu6fnu63lj5HmjKXlhajnkIPnu4/mtY7igJznqLPlrprlmajigJ3kvZznlKjigJTigJTlm73pmYXmnLrmnoTnnIvlpb3kuK3lm73nu4/mtY7liY3mma/jgItkAgMPFgIfAAILFhZmD2QWAmYPFQIkZjdmZmJlNDAtOTUwOS00Yjc1LWJiYzUtODExZjMzNjMwMTcyEuS/oeaBr+WFrOW8gOebruW9lWQCAQ9kFgJmDxUCJDc4Y2ZhMDIwLWQzOTgtNDRkNy1iMzRiLTIzYzBjMGMwOGUwZgzmnLrmnoTnroDku4tkAgIPZBYCZg8VAiQ0NjU0MmRiYy00MGZhLTQxNWMtYjY0Yy1kNjNiNWZjMzg3ZjMM6aKG5a+8566A5LuLZAIDD2QWAmYPFQIkZjlkZWRlMzMt…TFmZWIxZmQtZTU3NS00NTVkLTljNjQtZjQwYmFmYWFmZjM3NDIwMTflubTkuIDlraPluqbmpZrpm4Tlt57nlZzniafkuJrlrp7njrDlubPnqLPlvIDlsYBkAhMPZBYCZg8VBAoyMDE3LTA0LTI4KuS4gOWto+W6pualmumbhOW3nueVnOemveS6p+WTgeS+m+e7meWFhei2syRiYjIzN2Q1OC05YzQ4LTRiYTMtOGZhYi01ZDQ4YWJmMTYzOGEq5LiA5a2j5bqm5qWa6ZuE5bee55Wc56a95Lqn5ZOB5L6b57uZ5YWF6LazZAIDD2QWEAIBDw8WAh8BBQM0ODlkZAIDDw8WAh8BBQIyMWRkAgUPDxYCHwEFAjQwZGQCBw8PFgIeB0VuYWJsZWRnZGQCCQ8PFgIfAmdkZAILDw8WAh8BBQEyZGQCDQ8PFgIfAQUCMjVkZAITDxBkZBYBZmRkH+2AM6lWdb5dAWvKuJlHQ8jAx3CjLueTSG+RgG6R/BA=");
		// map.put("_VIEWSTATEGENERATOR", "14DD91A0");
		// map.put("_EVENTVALIDATION",
		// "/wEdAAnrFY5HGThbXRZVzT0iAqFXjxBXHyyiTC4AWyKDQB4zsw2lwNv39If4Dv/p043oiLJ3UoK7M7Vz7LBgazHnFhVfnkEvxAcI98ngl7Z+E7kTILNxPnnN++qvKnBxHQGzId5nkyyJMl7HuBgM+l7AB5rmLJ5RgXPMcYRWmev+QTC1mNwaGLsXFOYCz319GosUIC7PYyHDOfduFfFuxGRmdK5kc+n3jSnlfkj965FvNHu1Bg==");
		// map.put("Keyword", "关键词");
		// map.put("PageControl$ddlpageList", "20");
		// map.put("str_isSub", "true");
		// map.put("str_DescType", "pop");
		// map.put("str_isDiv", "true");
		// map.put("str_bfStr", "0|5|lastnews");
		// map.put("str_TitleNumer", "45");
		// map.put("str_PageStyle", "0$$20$plist");
		Document postToHtml = postToHtml(url, map);
		System.err.println();
	}

	/**
	 * 
	 * @param url
	 *            链接
	 * @param map
	 *            post请求参数列表
	 * @author 74707
	 */
	public static Document postToHtml(String url, HashMap<String, String> map) {
		Connection conn = null;
		Document doc = null;
		try {
			conn = Jsoup.connect(url)
					.userAgent(
							"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.84 Safari/537.36")
					.timeout(10000000);
			// 传参
			conn.data(map);
			// 使用post请求
			doc = conn.post();
			System.out.println(doc);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return doc;
	}

}
