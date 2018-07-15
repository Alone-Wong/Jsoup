package com.alone.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.DocumentException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alone.month.YunNan.楚雄彝族自治州;

public class PostDemo {

	public static void main(String[] args) throws IOException {
		try {
			PostParams();
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	private static void PostParams() throws IOException, DocumentException {
		String baseUrl = "http://www.cxtj.gov.cn";
		String url = "http://www.cxtj.gov.cn/NewsList.aspx?Classid=7021E0AB-B1E9-453C-B5FD-F24810BC02AC";
		String cookie = "B75F275AF8C2104EDDEF842F574EA3F0";
		List<NameValuePair> values = new ArrayList<NameValuePair>();
		values.add(new BasicNameValuePair("_EVENTTARGET", "PageControl$hlkNext"));
		values.add(new BasicNameValuePair("__VIEWSTATE", "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"));
		values.add(new BasicNameValuePair("__VIEWSTATEGENERATOR", "14DD91A0"));
		values.add(new BasicNameValuePair("__EVENTVALIDATION", "/wEdAAkqe6LNUVutTObsRuLYN7C1jxBXHyyiTC4AWyKDQB4zsw2lwNv39If4Dv/p043oiLJ3UoK7M7Vz7LBgazHnFhVfnkEvxAcI98ngl7Z+E7kTILNxPnnN++qvKnBxHQGzId5nkyyJMl7HuBgM+l7AB5rmLJ5RgXPMcYRWmev+QTC1mNwaGLsXFOYCz319GosUIC6w7ZDL2fd2ffWBEs7tErEVnxYnY0paRi/YWL0RFzCX0w=="));
		values.add(new BasicNameValuePair("PageControl$ddlpageList", "60"));
//		values.add(new BasicNameValuePair("sourceContentType", "1"));
//		values.add(new BasicNameValuePair("unitid", "18204"));
//		values.add(new BasicNameValuePair("webname", "兰州市统计局"));
//		values.add(new BasicNameValuePair("permissiontype", "0"));
		values.add(new BasicNameValuePair("cookie", "ASP.NET_SessionId=jekb3kmv5lytuaacxwt4iz1e"));
	
		String html = post(url, values);
		
		String selector = ".class_list li";
		String selector02 = ".c_content_text";
		String filepath ="G:\\数据\\云南\\地级市\\楚雄彝族自治州\\统计信息\\2000000000000\\";
//		CrawlerUtil.dirCheck(filepath);
		Document document = Jsoup.parse(html);
		
		Elements select = document.select(selector);
		
		for (org.jsoup.nodes.Element element : select) {
			String date = element.select("span").text().substring(0,4);
			String href = element.select("a[href]").attr("href");
			String name = element.select("a[href]").text();
			if (href!=null && !"".equals(href)) {
				String fUrl = baseUrl+href;
				Document html02 = CrawlerUtil.getFromHtml02(fUrl, "utf-8");
				Elements select2 = html02.select(selector02);
				CrawlerUtil.dirCheck(filepath+date+"\\");
				String content = "<table>" + select2+ "</table>";
				楚雄彝族自治州.writeXls(filepath +date+"\\"+ name + ".xls", content, "utf-8");
				System.out.println("文件<=====" + name + "======>>" + "写入到" + filepath);
			}
		}
		
		System.out.println();
		
		
		
		/**
		 * lllll
		 */
//		org.dom4j.Document parseText = DocumentHelper.parseText(html);
//		Element root = parseText.getRootElement();
//		Element element = root.element("recordset");
//		List<Element> elements = element.elements();
//		for (Element e : elements) {
//			if (!"style".equals(e.getName())) {
//				String text = e.getText();
//				Document doc1 = Jsoup.parse(text);
//				String secondUrl = doc1.select("a[href]").attr("href");
//				String name = doc1.select("a[href]").text();
//				String finalUrl = "http://tjj.lanzhou.gov.cn/" +secondUrl;
//				
//				
//				Document html02 = CrawlerUtil.getFromHtml02(finalUrl, "utf-8");
//				Elements elements2 = html02.select(selector);
//				if (!elements2.isEmpty()&& elements2.size()!=0) {
//					String pdfUrl = elements2.attr("abs:href");
//					if (pdfUrl!=null && !"".equals(pdfUrl)) {
//						
//						String suf = pdfUrl.substring(pdfUrl.lastIndexOf("."));
//						
//						CrawlerUtil.downloadFromHtml(filepath+name+suf, pdfUrl);
//					}
//					
//				}else{
//					
//					Elements elements3 = html02.select(selector02);
//					
//					兰州.writeXls(filepath + name + ".xls", elements3.toString(), "utf-8");
//					System.out.println("文件<=====" + name + "=====>>" + "写入到" + filepath);
//					
//				}
//			}
//
//		}
		
		
		
	}
	
	
	  /**
     * 发送 Post请求
     * 
     * @param url
     * @param reqXml
     * @return
     */
    public static String post(String url, List<NameValuePair> params) {
        String body = null;
        
       DefaultHttpClient httpClient = new DefaultHttpClient();
        try {
            // 设置客户端编码
            if (httpClient == null) {
                // Create HttpClient Object
                httpClient = new DefaultHttpClient();
            }

            // Post请求
            HttpPost httppost = new HttpPost(url);

            // 设置参数
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            // 发送请求
            HttpResponse httpresponse = httpClient.execute(httppost);
            // 获取返回数据
            HttpEntity entity = httpresponse.getEntity();
            body = EntityUtils.toString(entity,"UTF-8");
            if (entity != null) {
                entity.consumeContent();
            }
        } catch (Exception e) {
            e.printStackTrace();
      
        }

        return body;
    }
	
	

}
