/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space;

import java.io.UnsupportedEncodingException;

import com.ethan.space.util.OutputHandler;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class Main {

	private GameSystems game_systems;
	
	public static void main(String[] args) throws UnsupportedEncodingException{
		new Main();
	}
	
	public Main(){
		OutputHandler out = new OutputHandler();
		out.startTimedPrintln("Initializing Game");
		
		game_systems = new GameSystems();
		
		out.endTimedPrintln("Finished Initializing Game");
		
		game_systems.start();
	}
	
}
