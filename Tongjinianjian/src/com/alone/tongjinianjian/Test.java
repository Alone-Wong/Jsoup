package com.alone.tongjinianjian;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.ScriptResult;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class Test {

	public static void main(String[] args) throws IOException {
		WebClient wc = new WebClient(BrowserVersion.CHROME);
		wc.getOptions().setCssEnabled(false);// 禁用css
		wc.getOptions().setJavaScriptEnabled(true);// 启用js
		wc.getOptions().setRedirectEnabled(true);// 启用客户的重定向
		wc.setAjaxController(new NicelyResynchronizingAjaxController());// 设置Ajax异步
		wc.getOptions().setThrowExceptionOnScriptError(false);// 运行错误时，不抛出异常
		HtmlPage page = wc.getPage("http://www.bijie.gov.cn/bm/bjstjj/gk/xxgkml/tjsj/tjkb/index.shtml");
		String asxml = page.asXml();
		System.out.println(asxml);
		// Charset charset = page.getCharset();
		// System.out.println(charset);

		Document parse = Jsoup.parse(asxml);
		Elements select = parse.select("a[href]");
		System.out.println(select);
		ScriptResult exe = page.executeJavaScript("javascript:paddingData(1);");
		HtmlPage newPage = (HtmlPage) exe.getNewPage();
		String asXml2 = newPage.asXml();
		System.out.println(asXml2);
		wc.close();
	}

}