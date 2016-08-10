/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.input;

import java.util.HashMap;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class ControllerState {

	public final double a, b, x, y, start, select, left, right, up, down;
	
	/**
	 * TODO
	 * @param controller_state
	 */
	public ControllerState(HashMap<String, Double> controller_state) {
		a = (double) controller_state.get("a");
		b = (double) controller_state.get("b");
		x = (double) controller_state.get("x");
		y = (double) controller_state.get("y");

		left = (double) controller_state.get("left");
		right = (double) controller_state.get("right");
		up = (double) controller_state.get("up");
		down = (double) controller_state.get("down");
		
		start = (double) controller_state.get("start");
		select = (double) controller_state.get("select");
	}
	
	public boolean isAPressed(){
		return a != 0;
	}
	
	public boolean isBPressed(){
		return b != 0;
	}
	
	public boolean isXPressed(){
		return x != 0;
	}
	
	public boolean isYPressed(){
		return y != 0;
	}
	
	public boolean isLeftPressed(){
		return left != 0;
	}
	
	public boolean isRightPressed(){
		return right != 0;
	}
	
	public boolean isUpPressed(){
		return up != 0;
	}
	
	public boolean isDownPressed(){
		return down != 0;
	}
	
	public boolean isStartPressed(){
		return start != 0;
	}
	
	public boolean isSelectPressed(){
		return select != 0;
	}

}
