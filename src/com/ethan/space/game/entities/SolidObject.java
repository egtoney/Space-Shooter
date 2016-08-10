/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.game.entities;

import java.awt.Dimension;
import java.awt.Graphics2D;

import com.ethan.space.input.ControllerState;

/**
 * 
 * 
 * @author Ethan Toney
 */
public abstract class SolidObject {
	
	private double x, y;

	/**
	 * TODO
	 * @param x2
	 * @param y2
	 */
	public SolidObject(double x2, double y2) {
		x = x2;
		y = y2;
	}

	public abstract void tick( double delta_time, ControllerState controller );
	
	public abstract void draw( Graphics2D g2d, Dimension dim );
	
	public void setLocation( double x, double y ){
		this.x = x;
		this.y = y;
	}
	
	public double getX(){ return x; }
	
	public double getY(){ return y; }
	
}
