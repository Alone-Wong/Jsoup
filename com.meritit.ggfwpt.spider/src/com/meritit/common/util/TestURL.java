package com.meritit.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * 网站连通性测试
 * 
 * @viki
 */

public class TestURL {
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
	
	public static void main(String args[]){
		
//		String urls = "http://tjj.yangzhou.gov.cn/nj2016/nj19.htm";
//		Document doc = null;
//		
//		try {
//			doc = Jsoup.parse(new URL(urls).openStream(), "GBK", urls);
//		} catch (MalformedURLException e2) {
//			// TODO 自动生成的 catch 块
//			e2.printStackTrace();
//		} catch (IOException e2) {
//			// TODO 自动生成的 catch 块
//			e2.printStackTrace();
//		}
//		System.out.println(doc);
		
//		
		
		String href="http://tjj.hefei.gov.cn/8688/8689/2017nj/201712/t20171221_2427916.html";
		String name="tesdt";
		String cName="fzss12";
		
		try {
			
			URL url = new URL(href);
			URLConnection con= url.openConnection();
			
			
			// 创建目录
			String dirName = "C:\\Users\\merit\\Desktop\\TEST\\"+cName;
				
			if (mkDirectory(dirName)) {
					System.out.println(dirName + "建立完毕");
				} 

				// 创建文件
				File file = new File(dirName+"\\"+name+ ".xls");
				FileOutputStream out = new FileOutputStream(file,true);
				InputStream ins = con.getInputStream();
				byte[] b = new byte[1024];
				int i = 0;
				while ((i = ins.read(b)) != -1) {
					out.write(b, 0, i);
				}
				ins.close();
						
				out.close();
			} catch (IOException e1) {

				e1.printStackTrace();
			}
	}
}
