package com.alone.month;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ddd {

	/**
	 * 输入键值对input，判断其值是否同时存在A、B
	 */
	public static boolean evaluate(Map<String, String> input) {
		// TODO 请实现，并考虑你的程序的效率和健壮性

		Iterator entries = input.entrySet().iterator();

		List<String> list = new ArrayList<>();

		while (entries.hasNext()) {

			Map.Entry entry = (Map.Entry) entries.next();

			String key = (String) entry.getKey();

			String value = (String) entry.getValue();

			list.add(value);
		}

		if (list.contains("A") && list.contains("B")) {
			System.out.println("OK");
		}

		return false;
	}

	public static void main(String[] args) {
		// ****** Testcase 1 ******
		// 输入：
		Map<String, String> input = new HashMap<String, String>();
		input.put("A", "A");
		input.put("B", "B");
		// 输出：
		assert evaluate(input) == true;

		// ****** Testcase 2 ******
		// 输入：
		Map<String, String> input2 = new HashMap<String, String>();
		input2.put("A", "A");
		input2.put("B", "C");
		// 输出：
		assert evaluate(input2) == false;

	}

}
