/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space;

import com.ethan.space.game.EndlessGame;
import com.ethan.space.graphics.GraphicsEngine;
import com.ethan.space.input.InputEngine;
import com.ethan.space.util.GameSettings;
import com.ethan.space.util.OutputHandler;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class GameSystems {

	private final GameSettings current_game_settings;
	
	public final GraphicsEngine graphics_engine;
	public final InputEngine input_engine;
	public final EndlessGame endless_game;
	
	public GameSystems(){
		current_game_settings = GameSettings.getSettings();
		
		graphics_engine = new GraphicsEngine(this, current_game_settings);
		input_engine = new InputEngine(this, current_game_settings);
		endless_game = new EndlessGame(this, current_game_settings);
	}
	
	public void start(){
		OutputHandler out = new OutputHandler();
		out.startTimedPrintln("Starting Game");
		
		graphics_engine.start();
		input_engine.start();
		
		out.endTimedPrintln("Finished Starting Game");
	}
	
	public void stop(){
		OutputHandler out = new OutputHandler();
		out.startTimedPrintln("Stopping Game");
		
		graphics_engine.stop();
		input_engine.stop();
		
		out.endTimedPrintln("Finished Stopping Game");
		System.exit(0);
	}
	
}
