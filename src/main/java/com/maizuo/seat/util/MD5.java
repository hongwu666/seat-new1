package com.maizuo.seat.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public static String getVal(String plainText){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			
			int i;
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			
			return buf.toString();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getValUTF(String plainText) {
		
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes("UTF-8"));
			byte b[] = md.digest();
			
			int i;
			StringBuffer buf = new StringBuffer(""); 
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if(i<0) i+= 256;
				if(i<16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			return buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static String getHEXVal(String plainText){
		String str = MD5.getValUTF(plainText);
		return str.substring(8, 24);
	}
}
