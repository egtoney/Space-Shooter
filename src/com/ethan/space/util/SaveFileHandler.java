/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.util;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class SaveFileHandler {

	public static String encode( String data ) throws UnsupportedEncodingException{     
		byte[] encryptArray = Base64.getEncoder().encode(data.getBytes());
		return new String(encryptArray,"UTF-8");
	}
	
	public static String decode( String data ) throws UnsupportedEncodingException{
		byte[] dectryptArray = data.getBytes();
		byte[] decarray = Base64.getDecoder().decode(dectryptArray);
		return new String(decarray,"UTF-8");
	}
	
}
