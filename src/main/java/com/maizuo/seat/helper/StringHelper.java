package com.maizuo.seat.helper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class StringHelper {

	/**
	 * 把str字符串拼成length位字符，不够位前面不0
	 * 
	 * @param str
	 * @param length
	 * @return
	 */
	public static String get(String str, int length) {
		int count = length - str.length();
		if (count > 0) {
			StringBuffer buf = new StringBuffer();
			for (int i = 0; i < count; i++) {
				buf.append("0");
			}
			buf.append(str);
			return buf.toString();
		}
		return str;
	}
	/**
	 * 获取现在时间转换datefrom格式
	 * @param dateformat
	 * @return
	 */
	public static String getCurTime(String dateformat) {
		Calendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat(dateformat);
		return format.format(date);
	}

	public static boolean timeCompare(String date, String time) {
		if (time.length() == 2) {// 处理30情况，测试环境出现
			time = "00" + time;
		}
		String curdate = getCurTime("yyyy-MM-dd HH:mm:ss");
		String comdate = date + " " + time.substring(0, 2) + ":" + time.substring(2, 4) + ":00";

		if (curdate.compareTo(comdate) > 0) {
			return false;
		}
		return true;

	}

	public static void main(String[] args) {
		System.out.println(getCurTime("mm"));
	}
}
