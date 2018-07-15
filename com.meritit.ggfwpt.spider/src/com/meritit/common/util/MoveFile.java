package com.meritit.common.util;

import java.io.File;

public class MoveFile {
	
	public static void moveTotherFolders(String cName ,String name){
	  
		String title=cName.substring(0,cName.indexOf("+"));
		cName=cName.substring(cName.indexOf("+")+1);
//		String title=name.substring(0,name.lastIndexOf(".htm"));
//		name=cName+"\\"+name;

		String startPath = "C:\\Users\\merit\\Desktop\\viki\\e\\"+name;
	    String endPath = "C:\\Users\\merit\\Desktop\\南通市\\统计年鉴\\2016\\"+cName+"\\";
	   
	    try {
	        File startFile = new File(startPath);
	        File tmpFile = new File(endPath);//获取文件夹路径
	       
	        if(!tmpFile.exists()){//判断文件夹是否创建，没有创建则创建新文件夹
	            tmpFile.mkdirs();
	        }
	        
	        System.out.println(startPath);
	        System.out.println(endPath + title+".jpg");
	       
	        if (startFile.renameTo(new File(endPath + title+".jpg"))) {
//	            System.out.println("File is moved successful!");
//	            System.out.println("文件移动成功！");
	        } else {
	          //  System.out.println("File is failed to move!");
	            System.out.println("文件移动失败！");
	        }
	    } catch (Exception e) {
	       e.printStackTrace();

	    }
	}
	public static void main(String args[]){
		for(int i=1;i<25;i++){
			for(int j=1;j<26;j++){
				moveTotherFolders(String.valueOf(i),String.valueOf(i)+"-"+j+".htm");
			}
		}
		
	}
}
