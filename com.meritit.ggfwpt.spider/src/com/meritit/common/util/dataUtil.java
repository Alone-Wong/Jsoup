package com.meritit.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class dataUtil {
	public static boolean mkDirectory(String path) {
		File file = null;
		try {
			file = new File(path);
			if (!file.exists()) {
				return file.mkdirs();
			} else {
				return false;
			}
		} catch (Exception e) {
		} finally {
			file = null;
			
		}
		return false;
	}

	public static void main(String args[]) {
		
		
		
		for(int i=1;i<20;i++){
//			String urls = "http://tjj.hefei.gov.cn/8688/8689/n/";
			
			String urls = "http://tjj.hefei.gov.cn/8688/8689/8691/index_"+i+".html";
			System.out.println("PAGE:"+i);
		
			Document doc = CrawlerUtil.getHtml(urls);
			Elements links = doc.getAllElements();
			Elements titles= links.select("a[href]");
			
	//		List<String> cNames=new ArrayList<String>();
			
			for (Element e : titles) {
				//201311
				if(e.attr("href").contains("201311")){
					
					String url="http://tjj.hefei.gov.cn/8688/8689/8691/"+e.attr("href").substring(2);
					
					String context=e.text().replace(" ", "");
					System.out.println(context);
					String name=context.substring(0,2);
					System.out.println(name+"--"+context+"---"+url);
				
					DownlodFile.download(name,context,url);
					
//					cNames.add(context+"+"+"http://tjj.yangzhou.gov.cn/nj2012/"+e.attr("href"));
					
					
				}
			}
			
			
			
		}
		
		

		
//		for(int i=0;i<cNames.size();i++){
//			if(cNames.get(i).length()>1){
//				//标题页
//				String url=cNames.get(i).substring(cNames.get(i).indexOf("+")+1);
//				String name=cNames.get(i).substring(0,cNames.get(i).indexOf("+"));
//				
//				System.out.println(url+"-----"+name);
//				
//				Document docP = CrawlerUtil.getHtml(url);
//				Elements linkP = docP.getAllElements();
//				Elements titleP= linkP.select("a[href]");
//				List<String> tit=new ArrayList<String>();
//				
//				
//				//遍历各个表
//				for (Element e : titleP) {
//					if(e.attr("href").contains("p")){
//						String context=e.text();
//						String page="http://tjj.yangzhou.gov.cn/nj2012/"+e.attr("href");
//						DownlodFile.download(name,context,page);
//						
//					}
//				}
//				
//				
//			}
//			
//		}
		
		
		
		
//		int num=0;
//		String title=null;
//		int t=1;
//		for (Element e : links) {
//			if(num>0){
//				break;
//			}
//			//Elements elements = e.select("li").select("a[href]");
//			
//			Elements elements = e.select("a[href]");
//			for (Element element : elements) {
//				
//				System.out.println(element.attr("href"));
//
//				String x=element.attr("href");
//				if(x.contains("-01.xls")){
//					
//					String str=cNames.get(t++);
//					//title=str;
//					title=str.substring(str.indexOf("篇")+2);
//					//System.out.println("title"+title);
//				}
//				
//				if(x.contains("/extra/col20/2012/")){
//					
//					if(x.contains("note.htm")){
//						continue;
//					}
//					
//					String href = "http://www.bjstats.gov.cn/nj/main/2012_ch/content/"
//							+ x.substring(x.indexOf("_")+1,x.indexOf(".htm"))+".xls";
//					
//					System.out.println("href:"+href);
//					
//					
//					//文件名
//					String str = element.text();
//					String name = str.substring(str.indexOf(" ") + 1);
//					
//					//文件夹名
//					String cName = title;
//					System.out.println("cName:"+cName);
//					
//					//处理续表覆盖
//					if(name.contains("续表")){
//						name=str;
//						System.out.println(name);
//					}
//					
//					try {
//						URL url = new URL(href);
//						URLConnection con= url.openConnection();
//						// 创建目录
//						String dirName = "C:\\Users\\merit\\Desktop\\河北省\\河北省石家庄市\\统计年鉴\\2016"
//								+ cName;
//						if (mkDirectory(dirName)) {
//							System.out.println(dirName + "建立完毕");
//						} 
////						else {
////							System.out.println(dirName + "建立失败！此目录或许已经存在！");
////						}
//
//						// 创建文件
//						File file = new File(dirName+"\\"+name + ".xls");
//						FileOutputStream out = new FileOutputStream(file);
//						
//					    InputStream ins = con.getInputStream();
//						byte[] b = new byte[1024];
//						int i = 0;
//						while ((i = ins.read(b)) != -1) {
//							out.write(b, 0, i);
//						}
//						ins.close();
//						
//						out.close();
//						
//					} catch (IOException e1) {
//						// TODO 自动生成的 catch 块
//						System.out.println("404:"+href);
//						//e1.printStackTrace();
//						continue;
//					}
//				}
//			
//			}
//			num++;
//		}
//		
		

	}
}
