/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.util;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class ErrorHandler {

	/**
	 * Prints entire error message as normal.
	 * @param err
	 */
	public static void catchError( Throwable err ){
		OutputHandler.println("Error occured @ "+System.currentTimeMillis());
		OutputHandler.println("\t"+err.getMessage()+"\r\n");
		err.printStackTrace();
	}
	
	/**
	 * Prints entire error message but also outputs an error message header.
	 * @param err
	 * @param message
	 */
	public static void catchError( Throwable err, String message ){
		OutputHandler.println("Error occured @ "+System.currentTimeMillis());
		OutputHandler.println("\t"+message+"\r\n");
		System.err.print(message+"\n");
		err.printStackTrace();
	}

	/**
	 * Only prints the first line of the error.
	 * @param e
	 */
	public static void catchShortError( Throwable err ) {
		OutputHandler.println("Error occured @ "+System.currentTimeMillis());
		OutputHandler.println("\t"+err.getMessage());
		System.err.print(err.getMessage()+"\r\n");
	}

	/**
	 * Only prints the first line of the error.
	 * @param e
	 */
	public static void catchShortError( Throwable err, String message ) {
		OutputHandler.println("Error occured @ "+System.currentTimeMillis());
		OutputHandler.println("\t"+message+"\r\n");
		System.err.print(message+"\r\n");
	}
	
}
