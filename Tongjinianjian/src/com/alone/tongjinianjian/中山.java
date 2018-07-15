package com.alone.tongjinianjian;

import java.util.HashMap;
import java.util.Set;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class 中山 {

	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<>();
		String url = "http://www.zsstats.gov.cn/tjzl/tjnj/2012nj/ziliao1.htm";
		String selector = "a[href]";
		String charset = "gb2312";
		String baseurl = "http://www.zsstats.gov.cn/tjzl/tjnj/2012nj/";
		String filefolder = "C:/Users/74707/Desktop/数据/广东/中山/统计年鉴/2012/";
		Elements els = CrawlerUtil.getFromHtml(url, selector, charset, baseurl);
//		System.out.println(els);
		for (Element li : els) {
			String htmdurl = li.attr("abs:href");
			String filename = li.text();
			String filepath = filefolder + filename+"/";
			CrawlerUtil.dirCheck(filepath);
			if(filename!=null&&!filename.equals("")){
				//System.out.println(filename+"=====>"+htmdurl);
				String detailselector = "a[href]";
				Elements fromHtml = CrawlerUtil.getFromHtml(htmdurl, detailselector, charset, "http://www.zsstats.gov.cn/tjzl/tjnj/2012nj/ziliao/");
				//System.out.println(fromHtml);
				for (Element a : fromHtml) {
					String name = a.text();
					String htmurl = a.attr("abs:href");
					if(name!=null&&name.length()>0){
						System.out.println(filepath+name+"======>"+htmurl);
						String imgname = filepath+name;
						travelHtml(htmurl,imgname,map,0,imgname.length());//是否有下一页
					}
				}
			}

		}
		System.out.println(map.size());
		Set<String> key = map.keySet();
		for (String downloadpath : key) {
			String downloadurl = map.get(downloadpath);
			CrawlerUtil.downloadFromHtml(downloadpath+".jpg", downloadurl);
		}
		
	}
	
	public static void travelHtml(String url,String filename,HashMap<String, String> map,int i,int length){
		Elements imgsrc=null;
		Document doc = CrawlerUtil.getHtml(url);
		imgsrc = doc.select("img");//图片
		for (Element img : imgsrc) {
			String imgurl = img.attr("abs:src");
			if(imgurl.endsWith("img/g01.jpg"))
				continue;
//			System.out.println(filename+"====>"+imgurl);
			if(map.get(filename)==null||map.get(filename).equals(""))
				map.put(filename, imgurl);
		}
		
		Elements nextPage = doc.select("a[href]");//下一页
		for (Element a : nextPage) {
			String htmurl = a.attr("abs:href");
			String next = a.text();
			if(!htmurl.equals("")&&next.equals("下一页")){
				//System.out.println(htmurl);
				i++;
				String imgname = filename.substring(0, length)+i;
//				System.out.println(htmurl);
				if(htmurl.equals("http://www.zsstats.gov.cn/tjzl/tjnj/2012nj/ziliao/10_05_02.htm"))
					continue;
				travelHtml(htmurl,imgname,map,i,length);
			}
		}
	}

	public static void downloadxls() {
		String url = "http://www.stats-zh.gov.cn/tjsj/tjnj/201712/t20171221_378263.htm";
		String selector = "#content > div > div > div > p > a";
		String charset = "utf-8";
		String baseurl = "http://www.stats-zh.gov.cn/tjsj/tjnj/201712/";
		String filefolder = "C:/Users/74707/Desktop/数据/广东/珠海/统计年鉴/2017/";
		String folder = null;
		Elements els = CrawlerUtil.getFromHtml(url, selector, charset, baseurl);
		System.out.println(els);
		for (Element li : els) {
			if (li.attr("id").equals("foldheader")) {
				folder = li.text();
			} else {
				Elements a = li.select("a[href]");
				String downloadurl = a.attr("abs:href");// 文件下载路径
				String filename = a.text() + ".xls";
				String parentFolder = filefolder + folder + "/";
				String filepath = parentFolder + filename;// 目标路径
				if (downloadurl.endsWith("xls")) {
					System.out.println(filepath);
					//int dirCheck = CrawlerUtil.dirCheck(parentFolder);
					CrawlerUtil.downloadFromHtml(filepath, downloadurl);
				}
			}

		}
	}

}
