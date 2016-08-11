/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.HashMap;
import java.util.Set;

import org.json.simple.JSONObject;

import com.ethan.space.util.OutputHandler;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class ControllerStateUpdater implements KeyListener, MouseListener, MouseMotionListener{
	
	private final HashMap<String, Double> controller_state;
	private final JSONObject key_mapping;
	
	public ControllerStateUpdater( JSONObject key_mapping_settings ){
		key_mapping = key_mapping_settings;
		
		controller_state = new HashMap<>();
		controller_state.put("a", 0.0);
		controller_state.put("b", 0.0);
		controller_state.put("x", 0.0);
		controller_state.put("y", 0.0);
		
		controller_state.put("left", 0.0);
		controller_state.put("right", 0.0);
		controller_state.put("up", 0.0);
		controller_state.put("down", 0.0);
		
		controller_state.put("start", 0.0);
		controller_state.put("select", 0.0);
	}
	
	public ControllerState getState(){
		return new ControllerState(controller_state);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseDragged(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseDragged(MouseEvent e) {	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseMotionListener#mouseMoved(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
	 */
	@Override
	public void mousePressed(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyPressed(KeyEvent e) {
		String key_code = ""+e.getKeyCode();
		controller_state.put((String) key_mapping.get(key_code), 1.0);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyReleased(KeyEvent e) {
		String key_code = ""+e.getKeyCode();
		controller_state.put((String) key_mapping.get(key_code), 0.0);
	}

}
