package com.meritit.customize.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class FileUtils {

	/**
	 * 
	 * @param url 下载链接地址
	 * @param dir 文件存放目录
	 */
	public static void downloadFile(String url, String dir){
		URL _url = null;
		try {
			_url = new URL(url);
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		URLConnection conn = getConn(_url);
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		InputStream is = null;
		OutputStream out = null;
		try {
			String path = _url.getFile();
			String filename = path.substring(path.lastIndexOf("/") + 1);
			file = new File(dir, filename);
			is = conn.getInputStream();
			out =new FileOutputStream(file);
			int len = 0;
			byte[] buff = new byte[1024];
			while((len = is.read(buff)) != -1){
				out.write(buff, 0, len);
			}
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out != null){
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/**
	 * 获取连接
	 * @param spec
	 * @return
	 */
	public static HttpURLConnection getConn(URL url){
		try {
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.106 Safari/537.36");
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5000);
			conn.connect();
			return conn;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static void main(String[] args)throws Exception {
		String url = "http://ecp.sgcc.com.cn/structData/900000000000048168990000000000089141.zip";
		downloadFile(url, "E://");
	}
}
