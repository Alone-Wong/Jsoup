package com.alone.month.HuBei;

import java.io.File;

public class DeleteBatch {

	public static void main(String[] args) {
		String dir = "G:\\数据\\贵州\\地级市\\毕节\\";
		delete(dir);
	}

	public static void delete(String dir) {
		File file = new File(dir);// 里面输入特定目录
		File temp = null;
		File[] filelist = file.listFiles();

		for (int i = 0; i < filelist.length; i++) {
			if (filelist[i].isDirectory()) {
				delete(dir + filelist[i].getName() + "/");
			} else {

				temp = filelist[i];
				if (temp.getName().endsWith(".xls")) // 获得文件名，如果后缀为“”，这个你自己写，就删除文件
				{
					temp.delete();// 删除文件}

				}
			}

		}
	}
}
