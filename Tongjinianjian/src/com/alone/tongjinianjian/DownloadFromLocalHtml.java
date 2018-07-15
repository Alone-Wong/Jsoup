package com.alone.tongjinianjian;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alone.utils.CrawlerUtil;

public class DownloadFromLocalHtml {
	public static void main(String[] args){
		String htmlPath = "C:/Users/74707/Desktop/谷歌下载/2017nj/lefte.htm";
		String basePath = "C:/Users/74707/Desktop/谷歌下载/2017nj/";
		String aimPath  = "C:/Users/74707/Desktop/test/";
		Elements lis = CrawlerUtil.getFromLocalHtml(htmlPath, "li","gb2312");
		//System.out.println(lis);
		String folder=null;
		for (Element li : lis) {
			if(li.attr("id").equals("foldheader")){
				folder = li.text();
			}else{
				Elements a = li.select("a[href]");
				String downloadurl = basePath+a.attr("href");//文件原路径
				String filename = a.text()+".xls";
				String filefolder = aimPath+folder+"/";
				String filepath = filefolder+filename;//目标路径
				if(downloadurl.endsWith("xls")){
					CrawlerUtil.dirCheck(filefolder);
					CrawlerUtil.removeFile(downloadurl, filepath);
				}
			}
			
		}
	}
	
	
	
	
}
