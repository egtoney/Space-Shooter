/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.input;

import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;

import com.ethan.space.GameSystems;
import com.ethan.space.util.ErrorHandler;
import com.ethan.space.util.GameSettings;
import com.ethan.space.util.OutputHandler;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class InputEngine {

	private double desired_fps, current_fps;
	
	private Object running_lock = new Object();
	private boolean running = false;

	private final Thread input_thread;

	private final ControllerStateUpdater controller_state_updater;
	private final List<ControllerStateListener> controller_state_listeners;
	
	private final GameSystems game_systems;
	
	public InputEngine( GameSystems game_systems, GameSettings current_game_settings ){
		this.game_systems = game_systems;
		JSONObject input_settings = (JSONObject) current_game_settings.get("input-settings");
		
		desired_fps = (double) input_settings.get("controller-refresh-rate");
		
		JSONObject key_settings = (JSONObject) input_settings.get("key-mapping");
		controller_state_updater = new ControllerStateUpdater(key_settings);
		controller_state_listeners = new LinkedList<>();
		
		input_thread = new Thread( new ControllerUpdate(), "Input Thread" );
	}

	public void start() {
		// Bind the key/mouse listener to the frame
		game_systems.graphics_engine.bindPrimaryController( controller_state_updater );
		
		// Start firing off events
		synchronized( running_lock ){
			running = true;
			input_thread.start();
		}
	}
	
	public void stop() {
		synchronized ( running_lock ) {
			running = false;
		}
		try {
			input_thread.join();
			
		} catch (InterruptedException e) {
			ErrorHandler.catchError(e);
		}
	}
	
	public void bindControllerStateListener( ControllerStateListener listener ){
		synchronized (controller_state_listeners) {
			controller_state_listeners.add(listener);
		}
	}
	
	public void removeControllerStateListener( ControllerStateListener listener ){
		synchronized (controller_state_listeners) {
			controller_state_listeners.remove(listener);
		}
	}
	
	private class ControllerUpdate implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			long elapsted_time = 0;
			long desired_delay = (long) (1000.0/desired_fps);
			long required_delay;
			
			while( running ){
				start = System.currentTimeMillis();
				
				synchronized (running_lock) {
					if( !running )
						break;
				}
				
				// Do stuff here
				double total_elapsted_time = 1000.0/current_fps/1000.0;
				ControllerState controller_state = controller_state_updater.getState();
				synchronized( controller_state_listeners ){
					for( ControllerStateListener controller_state_listener : controller_state_listeners )
						controller_state_listener.fireOnControllerState( total_elapsted_time, controller_state );
				}
				
				// Delay for fps
				elapsted_time = System.currentTimeMillis() - start;
				required_delay = desired_delay - elapsted_time;
				if( required_delay < 0 )
					required_delay = 0;
				
				current_fps = (elapsted_time < desired_delay) ? 1000.0/desired_delay : 1000.0/elapsted_time;
				
				// Delay for accurate fps
				try {
					Thread.sleep(required_delay);
				} catch (InterruptedException e) {
					ErrorHandler.catchError(e);
				}
			}
		}
		
	}
	
}
