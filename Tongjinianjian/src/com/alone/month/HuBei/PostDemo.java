package com.alone.month.HuBei;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.alone.utils.HttpClientUtil;

@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
public class PostDemo {

	public static void main(String[] args) throws IOException {
		String url = "http://ez.hb.stats.cn/ilist.aspx?5&AspxAutoDetectCookieSupport=1";
		String cookie = "safedog-flow-item=; yunsuo_session_verify=64eca4b2562130ff18774d2fb7c64431; ASP.NET_SessionId=jb45cxvw5o5jor2vvredurmg";
		
		Map<String, String> map = new HashMap<>();
		map.put("__EVENTTARGET", "SqlPager1$Next");
		map.put("__VIEWSTATE", "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ICAgICAgICAgPHRkIHdpZHRoPSIxNSUiPjxzY3JpcHQgdHlwZT0idGV4dC9qYXZhc2NyaXB0Ij5kb2N1bWVudC53cml0ZSh1bmVzY2FwZSgiJTNDc3BhbiBpZD0nX2lkZUNvbmFjJyAlM0UlM0Mvc3BhbiUzRSUzQ3NjcmlwdCAgIHNyYz0naHR0cDovL2Rjcy5jb25hYy5jbi9qcy8xOC8yNzQvMDAwMC80MDUxNDIzOS9DQTE4Mjc0MDAwMDQwNTE0MjM5MDAwMS5qcycgdHlwZT0ndGV4dC9qYXZhc2NyaXB0JyUzRSUzQy9zY3JpcHQlM0UiKSk7PC9zY3JpcHQ+PC90ZD4NCiAgICAgICAgICAgIDx0ZCBhbGlnbj0iY2VudGVyIj48IS0t5bC+6YOo4oCU4oCU5byA5aeLLS0+DQogICAgICAgICAgICA8ZGl2IGlkPSJmX2JvdHRvbWQyIj7niYjmnYPmiYDmnInvvJrmuZbljJfnnIHphILlt57luILnu5/orqHlsYDjgIAg5Zyw5Z2A77ya5rmW5YyX55yB6YSC5bee5biC5ruo5rmW5aSn6YGT5biC5aeU5pS/5bqc5aSn5qW8IDxiciAvPg0KICAgICAgICAgICAg6YKu5pS/57yW56CBIO+8mjQzNjAwMCDnlLXor53vvJowNzExLTM4MzAxOTfjgIDjgIBF77yNbWFpbO+8mmV6c3RqakAxMjYuY29tIDxiciAvPg0KICAgICAgICAgICAgPGRpdj48L2Rpdj4NCiAgICAgICAgICAgIDxkaXY+Jm5ic3A7Jm5ic3A7Jm5ic3A7Jm5ic3A7IDxmb250IGZhY2U9IkFyaWFsIj7phIJJQ1DlpIcgMDUwMDk3MDTlj7c8L2ZvbnQ+PC9kaXY+DQogICAgICAgICAgICA8ZGl2IHN0eWxlPSJ0ZXh0LWFsaWduOiBjZW50ZXIiPjxzcGFuIGlkPSJMYWJlbFJUUzAiPiZuYnNwOyA8L3NwYW4+PC9kaXY+DQogICAgICAgICAgICA8L2Rpdj4NCiAgICAgICAgICAgIDwvdGQ+DQogICAgICAgICAgICA8dGQgd2lkdGg9IjE1JSI+Jm5ic3A7DQo8c2NyaXB0IGlkPSJfaml1Y3VvXyIgc2l0ZWNvZGU9JzQyMDcwMDAwMTUnIHNyYz0naHR0cDovL3B1Y2hhLmthaXB1eXVuLmNuL2V4cG9zdXJlL2ppdWN1by5qcyc+PC9zY3JpcHQ+DQo8L3RkPg0KICAgICAgICA8L3RyPg0KICAgIDwvdGJvZHk+DQo8L3RhYmxlPg0KPC9kaXY+DQo8L2Rpdj5kAhEPFgIfAGVkZDB6W5IQYKhCo3ccU7Epbin5CWexR4BgiECdDuUZMsb1");
		map.put("__VIEWSTATEGENERATOR", "E277F190");
		map.put("__EVENTVALIDATION", "/wEdAAXqWGgqTpqTmOqlkg0nS+iumEHi2udjC0vNExCz07u6bCZZ6xU3Cl8Z0SlWyHhuDPZjY37sfaMxaRFD7h691IwVbS/zCqygfZs2k+AXo9TKvoTXy6Ilauo4kKhfsYF33qa7f2AU9u+TCQScCByZ9zKO");
	
		String httpPost = HttpClientUtil.getInstance().sendHttpPost(url, map, cookie);
		
		Document document = Jsoup.parse(httpPost);
		
		String selector = "";
		String selector02 = "";
		
		
	}
}