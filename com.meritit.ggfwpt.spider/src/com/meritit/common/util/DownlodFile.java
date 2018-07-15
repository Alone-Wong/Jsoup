package com.meritit.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownlodFile {
	public static void main(String args[]){
		String href="http://www.ha.stats.gov.cn/hntj/lib/tjnj/2017/zk/html/0412.xls";
		String name="TEST";
		String cName="TEST";
		
		download(cName ,name,href);
	}
	
	//创建文件
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
	
	//下载文件
	public static void download(String cName ,String name,String href){

		try {
			System.out.println(href);
			URL url1 = new URL(href);
			URLConnection con= url1.openConnection();
			con.setReadTimeout(300000);
		
			// 创建目录
			String dirName = "C:\\Users\\merit\\Desktop\\合肥市\\统计年鉴\\2013\\"+ cName;
			if (mkDirectory(dirName)) {
				System.out.println(dirName + "建立完毕");
			} 

			//创建文件
			File file2 = new File(dirName+"\\"+name.substring(name.indexOf(".")+1) + ".xls");
			FileOutputStream out = new FileOutputStream(file2);
			
		    InputStream ins = con.getInputStream();
			byte[] b = new byte[1024];
			int j = 0;
			while ((j = ins.read(b)) != -1) {
				out.write(b, 0, j);
			}
			ins.close();
			
			out.close();
			
			
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			System.out.println("404:"+href);
			e1.printStackTrace();
		}		
    	
	}	
}
