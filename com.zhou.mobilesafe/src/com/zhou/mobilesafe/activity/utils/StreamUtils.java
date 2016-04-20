package com.zhou.mobilesafe.activity.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamUtils {
	
	
	public static String readFromStream(InputStream in){
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		int len=0;
		byte[] buffer=new byte[1024];
		try {
			while((len=in.read(buffer))!=-1){
				out.write(buffer,0,len);
			}
			String result=out.toString();
			return result;
		} catch (IOException e) {

			e.printStackTrace();
		}finally{
			if(in!=null){
				try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			if(out!=null){
				
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}			
		}
		
		
		return null;
	}
}
