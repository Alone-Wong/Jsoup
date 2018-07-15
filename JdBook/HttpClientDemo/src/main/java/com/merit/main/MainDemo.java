package com.merit.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

public class MainDemo {

	public static void main(String[] args) throws ParseException {
		String str = "[{'columnId':5,'columnName':'人文历史'},{'columnId':2,'columnName':'商业视野'}]";
		JSONArray jsonArray = null;
		List parse = (List) JSONArray.parse(str);
		jsonArray = new JSONArray(parse);
		System.out.println(jsonArray.getJSONObject(0).get("columnName"));
		
		Date date = new Date();
		System.err.println(JSON.toJSONString(date));

		/**
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
	
		 * 
		 */
		String birthday="2016-09-24";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		long appleBirthday = format.parse(birthday).getTime();
		long nowTime = new Date().getTime();
		String format2 = format.format(date);
		long time = format.parse(format2).getTime();
		
		long days = (time-appleBirthday)/(1000*60*60*24);
		
		System.out.println("apple:"+days);
		
		
		
	}

}
