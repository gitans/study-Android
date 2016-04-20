package com.zhou.mobilesafe.activity.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
	/**
	 * md5º”√‹
	 * @param password
	 * @return
	 */
	public static String encode(String password){
	
		try {
			MessageDigest instance = MessageDigest.getInstance("MD5");
			byte[] digest = instance.digest(password.getBytes());
			StringBuffer sb=new StringBuffer();
			for(byte b:digest){
				int i=b&0xff;
				
				String hexString = Integer.toHexString(i);
				if(hexString.length()<2){
					hexString+="0";
				}
				sb.append(hexString);
				
			}
			
			return  sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
