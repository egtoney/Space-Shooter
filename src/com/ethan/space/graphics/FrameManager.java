/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.graphics;

import java.awt.Component;

import com.ethan.space.GameSystems;
import com.ethan.space.graphics.menus.EndlessMode;
import com.ethan.space.graphics.menus.MainMenu;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class FrameManager {

	public static final int MAIN_MENU = 100;
	
	public static final int ENDLESS_MODE = 200;

	private final GameSystems game_systems;
	
	private MainMenu main_menu = null;
	private EndlessMode endless_mode = null;
	
	public FrameManager( GameSystems game_systems ){
		this.game_systems = game_systems;
		main_menu = new MainMenu(game_systems);
		endless_mode = new EndlessMode(game_systems);
	}

	/**
	 * TODO
	 * @return
	 */
	public MainMenu getMainMenu() {
		return main_menu;
	}

	/**
	 * TODO
	 * @return
	 */
	public EndlessMode getEndlessMode() {
		return endless_mode;
	}
	
}
