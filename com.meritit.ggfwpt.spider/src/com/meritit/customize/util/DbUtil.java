package com.meritit.customize.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.meritit.common.util.PropertyUtils;

public class DbUtil {
	
	private static Connection getConn() {
		Properties loadProp = PropertyUtils.loadProp("config");
	    String driver = "com.mysql.jdbc.Driver";
	    String mysqlip = loadProp.getProperty("mysqlip");
		String dbname = loadProp.getProperty("dbname");
		String url = "jdbc:mysql://"+mysqlip+":3306"+"/"+dbname+"?characterEncoding=utf-8";
	    String username = loadProp.getProperty("username");;
	    String password = loadProp.getProperty("password");;
	    Connection conn = null;
	    System.out.println(url);
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return conn;
	}
	
	
	
	public static int updateMaxDate(String areaCode,String maxDate) {
	    Connection conn = getConn();
	    int i = 0;
	    String sql = "update weather_maxDate_record set maxDate='" + maxDate + "' where areaCode='" + areaCode + "'";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        i = pstmt.executeUpdate();
	        System.out.println("result: " + i);
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return i;
	}
	
	
	public static  Map<String, String> getAll() {
		HashMap<String, String> companyMap=new HashMap<String, String>();
	    Connection conn = getConn();
	    String sql = "select * from weather_maxDate_record";
	    PreparedStatement pstmt;
	    try {
	        pstmt = (PreparedStatement)conn.prepareStatement(sql);
	        ResultSet rs = pstmt.executeQuery();
	        int col = rs.getMetaData().getColumnCount();
	        while (rs.next()) {
	            String maxDate = rs.getString("maxDate");
	            String areaCode =rs.getString("areaCode");
	            companyMap.put(areaCode,maxDate);
//	            System.out.println(companyid+":"+deadline);
	        }
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return companyMap;
	}
	
	
	
	
	public static void main(String[] args) {
		getAll();
		System.out.println(getAll());
	}
}
