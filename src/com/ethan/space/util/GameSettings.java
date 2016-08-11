/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.util;

import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class GameSettings extends JSONObject{
	
	private static final String VERSION_STRING = "0.1.0";
	
	public static GameSettings getDefaultSettings(){
		return new GameSettings();
	}
	
	private GameSettings(JSONObject data){
		super();
		putAll(data);
	}

	@SuppressWarnings("unchecked")
	public GameSettings(){
		super();
		
		put("version", VERSION_STRING);
		
		// init the different categories
		JSONObject graphics_settings = new JSONObject();
		
		// default graphics settings
		JSONObject resolution = new JSONObject();
		resolution.put("width", 800);
		resolution.put("height", 600);
		graphics_settings.put("resolution", resolution);
		
		graphics_settings.put("desired-fps", 60.0);
		
		put("graphics-settings", graphics_settings);
		
		// default input settings
		JSONObject input_settings = new JSONObject();
		
		JSONObject key_map = new JSONObject();
		key_map.put(""+KeyEvent.VK_LEFT, "left");
		key_map.put(""+KeyEvent.VK_RIGHT, "right");
		key_map.put(""+KeyEvent.VK_UP, "up");
		key_map.put(""+KeyEvent.VK_DOWN, "down");
		
		key_map.put(""+KeyEvent.VK_Z, "a");
		key_map.put(""+KeyEvent.VK_X, "b");
		key_map.put(""+KeyEvent.VK_A, "x");
		key_map.put(""+KeyEvent.VK_S, "y");
		
		key_map.put(""+KeyEvent.VK_ENTER, "start");
		key_map.put(""+KeyEvent.VK_SHIFT, "select");
		
		input_settings.put("key-mapping", key_map);
		
		input_settings.put("controller-refresh-rate", 100.0);
		
		put("input-settings", input_settings);
		
		// default player settings
		JSONObject player_settings = new JSONObject();

		player_settings.put("health-modifier", 1.0);
		player_settings.put("damage-modifier", 1.0);
		player_settings.put("defense-modifier", 1.0);
		player_settings.put("recharge-modifier", 1.0);
		
		put("player-settings", player_settings);
	}

	/**
	 * TODO
	 * @return
	 */
	public static GameSettings getSettings() {
		File savefile = new File("./.savefile");
		if( savefile.exists() ){
			try {
				BufferedReader in = new BufferedReader( new FileReader( savefile ));
				
				String data = "", line = "";
				while( (line = in.readLine()) != null )
					data += line;
				
				data = SaveFileHandler.decode(data);
				JSONObject save_data = (JSONObject) new JSONParser().parse(data);
				
				if( save_data.get("version").equals(VERSION_STRING) )
					return new GameSettings(save_data);
				
			} catch (IOException | ParseException e) {
				ErrorHandler.catchError(e, "Failed to load savefile.");
			}
		}
		return getDefaultSettings();
	}

	public void save() {
		try {
			BufferedWriter out = new BufferedWriter( new FileWriter( new File("./.savefile") ));
			
			String save_data = SaveFileHandler.encode(toJSONString());
			out.write(save_data);
			out.flush();
			out.close();
			
		} catch (IOException e) {
			ErrorHandler.catchError(e, "Failed saving game state.");
		}
	}
	
}
