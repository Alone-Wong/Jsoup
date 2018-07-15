package com.meritit.common.util;

import java.util.Properties;

import org.apache.log4j.Logger;

public class GetCity {
	
	protected static Logger logger = Logger.getLogger(GetCity.class);
	/*
	 * 获取省市县属性
	 */
	public static String getProvince(String code){
		 
		Properties pop=PropertyUtils.loadProp("city");
		String qx=pop.getProperty(code);
		
		String city=pop.getProperty(qx.substring(qx.lastIndexOf("-")+1,  qx.length()));
		
		String prov=city.substring(0,3);
		
		
		String qx0=qx.substring(0,qx.lastIndexOf("-"));
		
		if(qx.equals(city)){
			qx0=null;
		}
		
		String city0=city.substring(prov.length(),city.lastIndexOf("-"));
		
	//	System.out.println(prov+city0+qx0);
		
		if(qx0==null){
			qx0="";
		}
		
		return prov;
	 }
	 
	public static String getCity(String code){
		 
			Properties pop=PropertyUtils.loadProp("city");
			String qx=pop.getProperty(code);
			
			String city=pop.getProperty(qx.substring(qx.lastIndexOf("-")+1,  qx.length()));
			
			String prov=city.substring(0,3);
			
			
			String qx0=qx.substring(0,qx.lastIndexOf("-"));
			
			if(qx.equals(city)){
				qx0=null;
			}
			
			String city0=city.substring(prov.length(),city.lastIndexOf("-"));
			
			return city0;
	 }
	 
	public static String getQX(String code){
		 
			Properties pop=PropertyUtils.loadProp("city");
			String qx=pop.getProperty(code);
			
			String city=pop.getProperty(qx.substring(qx.lastIndexOf("-")+1,  qx.length()));
			
			String prov=city.substring(0,3);
			
			
			String qx0=qx.substring(0,qx.lastIndexOf("-"));
			
			if(qx.equals(city)){
				qx0=null;
			}
			
			String city0=city.substring(prov.length(),city.lastIndexOf("-"));
			
			return qx0;
	}
}
