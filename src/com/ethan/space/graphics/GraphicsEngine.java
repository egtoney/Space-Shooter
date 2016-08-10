/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONObject;

import com.ethan.space.GameSystems;
import com.ethan.space.graphics.menus.MainMenu;
import com.ethan.space.input.ControllerStateUpdater;
import com.ethan.space.util.ErrorHandler;
import com.ethan.space.util.GameSettings;
import com.ethan.space.util.OutputHandler;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class GraphicsEngine {

	private double desired_fps, current_fps, min_fps, max_fps;
	
	private Object running_lock = new Object();
	private boolean running = false;
	
	private GameFrame game_frame;
	private FrameManager frame_manager;
	
	private Thread screen_update_thread;
	
	public GraphicsEngine(GameSystems game_system, GameSettings current_game_settings){
		JSONObject graphics_settings = (JSONObject) current_game_settings.get("graphics-settings");
		
		desired_fps = (double) graphics_settings.get("desired-fps");
		
		frame_manager = new FrameManager(game_system);
		game_frame = new GameFrame(game_system, graphics_settings);
		changeRooms(FrameManager.MAIN_MENU);
		
		screen_update_thread = new Thread( new FPSUpdate(), "Graphics Thread" );
		
		// Load fonts
	}

	public void start() {
		synchronized( running_lock ){
			running = true;
			screen_update_thread.start();
		}
	}
	
	public void stop() {
		synchronized ( running_lock ) {
			running = false;
		}
		try {
			screen_update_thread.join();
			
		} catch (InterruptedException e) {
			ErrorHandler.catchError(e);
		}
	}
	
	private class FPSUpdate implements Runnable{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			long start = System.currentTimeMillis();
			long elapsted_time = 0;
			long desired_delay = (long) (1000.0/desired_fps);
			long required_delay;
			
			while(running){
				start = System.currentTimeMillis();
				synchronized (running_lock) {
					if(!running)
						break;
				}
				
				elapsted_time = System.currentTimeMillis() - start;
				required_delay = desired_delay - elapsted_time;
				if( required_delay < 0 )
					required_delay = 0;
				
				current_fps = (elapsted_time < desired_delay) ? 1000.0/desired_delay : 1000.0/elapsted_time;
				if( current_fps < min_fps )
					min_fps = current_fps;
				if( current_fps > max_fps )
					max_fps = current_fps;
				
				game_frame.repaint();
				
				// Delay for accurate fps
				try {
					Thread.sleep(required_delay);
				} catch (InterruptedException e) {
					ErrorHandler.catchError(e);
				}
			}
		}
		
	}

	/**
	 * TODO
	 * @param controller_state_updater
	 */
	public void bindPrimaryController(ControllerStateUpdater controller_state_updater) {
		game_frame.addMouseListener(controller_state_updater);
		game_frame.addMouseMotionListener(controller_state_updater);
		game_frame.addKeyListener(controller_state_updater);
	}

	/**
	 * TODO
	 * @param endlessMode
	 */
	public void changeRooms(int endless_mode) {
		switch( endless_mode ){
		case(FrameManager.MAIN_MENU):
			game_frame.setDisplayPanel(frame_manager.getMainMenu());
			break;
		case(FrameManager.ENDLESS_MODE):
			game_frame.setDisplayPanel(frame_manager.getEndlessMode());
			break;
		}
	}
	
}
