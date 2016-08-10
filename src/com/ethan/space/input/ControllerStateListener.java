/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.input;

/**
 * 
 * 
 * @author Ethan Toney
 */
public interface ControllerStateListener {

	public void fireOnControllerState( double delta_time, ControllerState event );
	
}
