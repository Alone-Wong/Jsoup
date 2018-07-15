package com.alone.Test;

public class test02 {

	public static void main(String[] args) {

		String str = "1   2 3           4";
		String[] split = str.split(" +");
		print(split);
		
		
	}

	public static void print(String[] str) {

		if (str != null && !"".equals(str)) {
			for (String string : str) {
				System.out.println(string);
			}
		}else{
			System.err.println("Sorry,Sir!");
		}

	}

}
