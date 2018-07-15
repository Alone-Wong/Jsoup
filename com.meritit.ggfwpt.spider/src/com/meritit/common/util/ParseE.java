package com.meritit.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 图片转excel文件下载
 * @author viki
 *
 */
public class ParseE{
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

	
	
	
	public static void download(Map<String,String> cNames){
		
		
		Set<String> Treeset = cNames.keySet();
		Iterator<String> it = Treeset.iterator();
		
		while(it.hasNext()){
			
			String urlN=it.next();
			String cname=cNames.get(urlN);
			//http://www.ahtjj.gov.cn/tjjweb/tjnj/2017/cn/9/cn9-9.files/sheet001.htm
			//:www.ahtjj.gov.cn/tjjweb/tjnj/2015/2015/13/cn13-2.htm
			//http://www.stats-sh.gov.cn/tjnj/2016tjnj/C0101.htm
			//http://www.ahtjj.gov.cn/tjjweb/tjnj/2013/2013cn/6/cn6-2.htm
			String href = "http://www.stats-sh.gov.cn/tjnj/2016tjnj/"+urlN+".htm";
			System.out.println(href);
			
			//System.out.println(cname.substring(0,cname.indexOf("<->"))+"_@_"+cname.substring(cname.indexOf("<->")+3));
	
			try {
				
				URL url = new URL(href);
				URLConnection con= url.openConnection();
				
				
				// 创建目录
				String dirName = "C:\\Users\\merit\\Desktop\\上海市\\上海市\\统计年鉴\\2012\\"+cname.substring(0,cname.indexOf("<->"));
					
				if (mkDirectory(dirName)) {
						System.out.println(dirName + "建立完毕");
					} 
	
					// 创建文件
					File file = new File(dirName+"\\"+cname.substring(cname.indexOf("<->")+3) + ".xlsx");
					FileOutputStream out = new FileOutputStream(file);
							
				    InputStream ins = con.getInputStream();
					byte[] b = new byte[1024];
					int i = 0;
					while ((i = ins.read(b)) != -1) {
						out.write(b, 0, i);
					}
					ins.close();
							
					out.close();
							
				} catch (IOException e1) {
					System.out.println("404:"+href);
					//e1.printStackTrace();
					continue;
				}
			
			
		}//遍历url

	}
	public static Map<String,String> getURLS() throws MalformedURLException, IOException{
		String encoding = "GBK";
	    File file = new File("C:\\Users\\merit\\Desktop\\___left2012.html");
		
	    Map<String,String> urls=new TreeMap<String,String>();
	    String url=null;
	    String sTitle=null;
	   
	    if (file.isFile() && file.exists()) {
	        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
	        BufferedReader bufferedReader = new BufferedReader(read);
	        String lineTxt = null;
	        int t=0;
	        String cName=null;
	        int index=0;
	        String title=null;
	        while ((lineTxt = bufferedReader.readLine()) != null) {
	        	//System.out.println("----->"+lineTxt);
	        	//part-1 记录文件夹名
	        	if(lineTxt.contains("title")){
	        		
	        		int i=lineTxt.indexOf("、")+1;
	        		int j=lineTxt.indexOf("\",");
        			title=lineTxt.substring(i,j);
        			System.out.println("Stitle:"+title);
        			index++;
	        	     		
	        	}
	         
	        	//记录url
	        	if(lineTxt.contains(".htm")){
	        		
	        		sTitle=title+"<->"+lineTxt.substring(lineTxt.indexOf(" "),lineTxt.indexOf("\",\""));
	        		url=lineTxt.substring(lineTxt.indexOf("\",\"")+3,lineTxt.lastIndexOf("."));
	        		//System.out.println(sTitle+"-->"+url);
	        		urls.put(url, sTitle);
	        		
	        	}
	        }
	        
	        read.close();
	        
	        
	    } else {
	        System.out.println("找不到指定的文件");
	       
	    }
		
		
		
		return urls;
	}
	
	public static void main(String args[]) throws IOException{
		download(getURLS());
	}
}
