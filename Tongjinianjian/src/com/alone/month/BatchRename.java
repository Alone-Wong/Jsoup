package com.alone.month;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * java批量修改指定文件夹下所有后缀名的文件为另外后缀名的代码
 * 
 * @author yangwenxue(vashon)
 * 
 */
public class BatchRename {
	public static void main(String args[]) {
		String dir = "G:\\数据\\贵州\\2016";
		File file = new File(dir);
		String srcSuffix = "htm";
		String dstSuffix = "xls";
		List<String> paths = listPath(file, srcSuffix);
		for (String path : paths) {
			File srcFile = new File(path);
			String name = srcFile.getName();
			int idx = name.lastIndexOf(".");
			String prefix = name.substring(0, idx);
			System.out.println(srcFile.getParent());

			File dstFile = new File(srcFile.getParent() + "/" + prefix + "." + dstSuffix);
			if (dstFile.exists()) {
				srcFile.delete();
				continue;
			}
			srcFile.renameTo(dstFile);
		}
	}

	/**
	 * 获取指定路径下的所有符合条件的路径
	 * 
	 * @param file
	 *            路径
	 * @param srcSuffix
	 *            后缀名
	 * @return
	 */
	private static List<String> listPath(File path, String srcSuffix) {
		List<String> list = new ArrayList<String>();
		File[] files = path.listFiles();
		Arrays.sort(files);
		for (File file : files) {
			if (file.isDirectory()) {// 如果是目录
				// 关键是理解以下两步操作(递归判断下级目录)
				List<String> _list = listPath(file, srcSuffix);// 递归调用
				list.addAll(_list);// 将集合添加到集合中
			} else {// 不是目录
				String name = file.getName();
				int idx = name.lastIndexOf(".");
				String suffix = name.substring(idx + 1);
				if (suffix.equals(srcSuffix)) {
					list.add(file.getAbsolutePath());// 把文件的决定路径添加到集合中
				}
			}
		}
		return list;
	}

}