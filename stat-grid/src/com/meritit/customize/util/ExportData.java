package com.meritit.customize.util;


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

public class ExportData {
	

	public static void export(String[] headers, String[] Col, List<Map> excelList) throws FileNotFoundException, IOException {
		ExportExcel<Map> ex = new ExportExcel<Map>();
		
		System.out.println(excelList);
		
		//生成Excel
		HSSFWorkbook workbook = ex.exportExcel("sheet2", headers,Col, excelList,null);
		
		/*FileOutputStream stream = null;
        
		//创建数据流，把改好的数据放入excel中
        stream = new FileOutputStream("G:\\test\\test.xlsx");*/
        
//		OutputStream stream = new FileOutputStream("G:\\test\\test1.xls");
        
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss"); 
		
        Date date = new Date();
        String path ="G:\\test\\";
        String fileName =  "test_"+sdf.format(date)+".xls";
        
		OutputStream stream = new FileOutputStream(path + fileName);
        
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
