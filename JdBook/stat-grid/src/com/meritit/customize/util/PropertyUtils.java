package com.meritit.customize.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.meritit.customize.StatGridCrawler;

public class PropertyUtils {

	private static Properties prop = null;
	
	public static Properties loadProp(String filename){
	     try {
	    	 prop = new Properties();
	    	 InputStream is = PropertyUtils.class.getClassLoader().getResourceAsStream(filename+".properties");
	    	 prop.load(is);
	     } catch (IOException e) {
	       e.printStackTrace();
	     }
	     return prop;
	}
	
	public static String getValueByKey(String key){
		return prop.getProperty(key);
	}
}
