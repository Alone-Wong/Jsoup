package com.alone.month;

import java.io.File;

public class BatchDelete {
	public static void main(String[] args) {
		File file = new File("G:\\数据\\贵州");
		delete(file);
	}

	private static void delete(File f) {
		File[] fi = f.listFiles();
		for (File file : fi) {
			if (file.isDirectory()) {
				delete(file);
			} else if (file.getName().substring(file.getName().lastIndexOf(".") + 1).equals("css")) {
				System.out.println("成功删除" + file.getName());
				file.delete();

			}
		}
	}
}