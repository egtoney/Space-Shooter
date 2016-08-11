package com.ethan.space.util;

public class Caster {

	public static int castToInteger( Object obj ){
		if( obj instanceof Long ){
			long tmp = (long) obj;
			return (int) tmp;
		}else if( obj instanceof Integer ){
			return (int) obj;
		}
		return 0;
	}
	
}
