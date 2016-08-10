/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.graphics.menus;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import com.ethan.space.GameSystems;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class EndlessMode extends JPanel{

	private final GameSystems game_system;
	
	/**
	 * TODO
	 * @param game_systems
	 */
	public EndlessMode(GameSystems game_system) {
		super();
		
		this.game_system = game_system;
		
		setOpaque(false);
	}
	
	@Override
	public void paint(Graphics g){
		Graphics2D g2d = (Graphics2D) g.create();
		Dimension dim = getSize();
		
		game_system.endless_game.drawGame(g2d, dim);
	}

}
