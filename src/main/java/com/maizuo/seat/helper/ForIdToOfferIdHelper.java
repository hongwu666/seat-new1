package com.maizuo.seat.helper;
/**
 * 排期id转提供商id
 * @author Administrator
 *
 */
public class ForIdToOfferIdHelper {
	public static String bu(int length, String is) {
		String str = "0000000000000000000000000000000000";
		String substr = str.substring(0, length - is.length());
		str = substr + is;
		return str;
	}

	/**
	 * items[0]:影院ID items[1]:日期index items[2]:index
	 * */
	public static int[] getItems(int foretellId) {
		int[] items = new int[3];
		String is = Integer.toBinaryString(foretellId);
		String str = bu(31, is);
		items[0] = Integer.parseInt(str.substring(0, 13), 2) - 3000;
		items[1] = Integer.parseInt(str.substring(13, 23), 2);
		items[2] = Integer.parseInt(str.substring(23), 2);
		return items;
	}
	public static void main(String[] args) {
		long start=System.nanoTime();
		int [] i=getItems(123231);
		long end=System.nanoTime();
		System.out.println(end-start);
		System.out.println(i[0]+"."+i[1]);
	}
}
