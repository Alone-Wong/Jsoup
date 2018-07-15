package com.meritit.common;

public class test {
public static void main(String args[]){
	String info="1511753198975-001-58326";
	String taskid=info.substring(0, info.lastIndexOf("-"));
	String areaCode=info.substring( info.lastIndexOf("-")+1, info.length());
	System.out.println(taskid);
	System.out.println(areaCode);
	if(!areaCode.equals("5832")){
		System.out.println("ok");
	}
}
}
