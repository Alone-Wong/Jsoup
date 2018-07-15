package com.meritit.customize;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.meritit.customize.util.ExportExcel;

public class TestExcel {
	
	
	public static void main(String[] args) throws IOException{
		
		//表头
		String[] headers = {"姓名","年龄"};  
		//数据键名或者MODEL类字段名
		String[] Col = {"姓名","age"};

		ExportExcel<Map> ex = new ExportExcel<Map>();
		//这是model类型的数据  写的例子  暂时不添加数据
		
		List<Map> excelList  = new ArrayList<Map>();
		for(int i=0; i < 4;i++){
			
			
			Map map = new HashMap();
			
			map.put("姓名", i);
			
			map.put("age", 30);
			
			excelList.add(map);
		}
		
		
		System.out.println(excelList);
		
		//生成Excel
		HSSFWorkbook workbook = ex.exportExcel("sheet2", headers,Col, excelList,null);
		
		/*FileOutputStream stream = null;
        
		//创建数据流，把改好的数据放入excel中
        stream = new FileOutputStream("G:\\test\\test.xlsx");*/
        
        OutputStream stream = new FileOutputStream("G:\\test\\test1.xls");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		
        Date date = new Date();
        String path ="G:\\test\\";
        String fileName =  "test_"+sdf.format(date)+".xls";
        
        String baseuploadpath = path+fileName;
        
        File f = new File(baseuploadpath);
	    if(!f.getParentFile().exists()){
	 	   f.getParentFile().mkdirs();
	    }
	    try {
			f.createNewFile();
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	    OutputStream out = null;
		try {
			out = new FileOutputStream(f);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}  
        
        
        //使用workbook写入
        workbook.write(out);
        
	 
        stream.flush();   
        stream.close(); 
		
        System.out.println("完成");
	}

}
