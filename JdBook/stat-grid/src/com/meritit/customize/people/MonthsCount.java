package com.meritit.customize.people;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * count
 * 
 * @author Black
 *
 */
public class MonthsCount {
	
	public static void main(String[] args) throws ParseException {
		List<String> list = getMonthBetween("2011-01","2012-03");
		System.err.println(list);
	}

	public static int countMonths(String date1, String date2, String pattern) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));

		int year = c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR);

		// 开始日期若小月结束日期
		if (year < 0) {
			year = -year;
			return year * 12 + c1.get(Calendar.MONTH) - c2.get(Calendar.MONTH);
		}

		return year * 12 + c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
	}

	/**
	 * 	
	 */

	private static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
		ArrayList<String> result = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

		Calendar min = Calendar.getInstance();
		Calendar max = Calendar.getInstance();

		min.setTime(sdf.parse(minDate));
		min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

		max.setTime(sdf.parse(maxDate));
		max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

		Calendar curr = min;
		while (curr.before(max)) {
			result.add(sdf.format(curr.getTime()));
			curr.add(Calendar.MONTH, 1);
		}

		return result;
	}

}
