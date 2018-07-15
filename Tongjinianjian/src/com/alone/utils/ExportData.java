package com.alone.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Created by Black on 2018/3/14.
 */
public class ExportData {

    public static void export(String[] headers, String[] Col, List<Map> list) {
        ExportExcel<Map> ex = new ExportExcel<Map>();
        // 生成Excel
        HSSFWorkbook workbook = ex.exportExcel("sheet2", headers, Col, list, null);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        Date date = new Date();
        String path = "D:\\test\\";
        String fileName = "test_" + sdf.format(date) + ".xls";

        String baseuploadpath = path + fileName;

        File f = new File(baseuploadpath);
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdirs();
        }
        try {
            f.createNewFile();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        OutputStream out = null;
        try {
            out = new FileOutputStream(f);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }

        // 使用workbook写入
        try {
            workbook.write(out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("完成");
    }

}
