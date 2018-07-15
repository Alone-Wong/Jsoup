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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 本地读取
 * @author viki
 *
 */

public class dataUtil2{


	
	public static void main(String args[]) throws MalformedURLException, IOException{
		getURLS();
	}
	
	
	
	
	
	public static void  getURLS() throws MalformedURLException, IOException{

		
		String encoding = "gbk";
	    File file = new File("C:\\Users\\merit\\Desktop\\viki\\lefte.htm");
		
	    List<String> cNames=new ArrayList<String>();
	    List<String> urls=new ArrayList<String>();
		
	    if (file.isFile() && file.exists()) {
	        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);//考虑到编码格式
	        BufferedReader bufferedReader = new BufferedReader(read);
	        String lineTxt = null;
	        int t=0;
	        String cName=null;
	        while ((lineTxt = bufferedReader.readLine()) != null) {
	        	// System.out.println(lineTxt);
	        	//part-1 记录文件夹名
	        	if(lineTxt.contains("foldheader")){
	        		
	        		int i=lineTxt.indexOf("篇")+2;
		        	int j=lineTxt.lastIndexOf("</li>"); 
		        	System.out.println(lineTxt.substring(i, j));
	        		cNames.add(lineTxt.substring(i, j)); 
	        	 		
	        	}
	         
	        	//记录url
	        	if(lineTxt.contains(".xls")){
	        		if(lineTxt.contains("简要说明")||lineTxt.contains("主要统计指标解释")){
	        			continue;
	        		}
	        		urls.add(lineTxt);
	        	}
	        
	        }
	        
	        read.close();
	        
	        
	    } else {
	        System.out.println("找不到指定的文件");
	       
	    }
	    
	    String cName=null;
	    int cn=0;
	   
	    //遍历url获取下载文件
	    for(int i=0;i<urls.size();i++){
	    	
	    	
		    	String url=urls.get(i);
		    	//System.out.println(url);
		    	String href=url.substring(url.indexOf("html")+5,url.indexOf("xls"))+"jpg";
		    	
		    	if(url.contains("01.xls")){
				    
	    			cName=cNames.get(cn);
	    			cn++;
	    		}
		    	
		    	String name=url.substring(url.indexOf(".xls")+6,url.indexOf("</a>")).replace(" ", "");
		    	
		    	
		    	
		    	System.out.println(cName+"->"+name+"->"+href);
		
		    	MoveFile.moveTotherFolders(name+"+"+cName,href);
		    	
		    //	//DownlodFile.download(cName ,name,href);
		    	
	    	}
	    	
	    }   

	
}
