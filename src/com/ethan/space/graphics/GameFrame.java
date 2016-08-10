/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.json.simple.JSONObject;

import com.ethan.space.GameSystems;
import com.ethan.space.graphics.menus.MainMenu;
import com.ethan.space.util.GameSettings;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class GameFrame extends JFrame{
	
	private final JPanel content_pane;
	
	public GameFrame( GameSystems game_system, JSONObject graphics_settings ){
		super();
		
		content_pane = new JPanel(null);
		content_pane.setLayout(new GridLayout(0,1));
		content_pane.setBackground(new Color(0,0,255));
		
		JSONObject resolution = (JSONObject) graphics_settings.get("resolution");
		setBackground(new Color(0,0,0));
		setResizable(false);
		setNativeLookAndFeel();
		setSize(new Dimension((int) resolution.get("width"), (int) resolution.get("height")));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setIgnoreRepaint(true);
		setContentPane(content_pane);
		
		addWindowListener( new WindowListener() {

			boolean close_toggle = true;
			
			@Override
			public void windowOpened(WindowEvent e) {}

			@Override
			public void windowClosing(WindowEvent e) {
				if( close_toggle ){
					close_toggle = false;
					game_system.stop();
				}
			}

			@Override
			public void windowClosed(WindowEvent e) {}

			@Override
			public void windowIconified(WindowEvent e) {}

			@Override
			public void windowDeiconified(WindowEvent e) {}

			@Override
			public void windowActivated(WindowEvent e) {}

			@Override
			public void windowDeactivated(WindowEvent e) {}
			
		});
		
		setVisible(true);
		requestFocus();
	}
	
	/**
	 * TODO
	 */
	private void setNativeLookAndFeel() {
		try {// Set System L&F
		    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch (UnsupportedLookAndFeelException e) {
		   // handle exception
		}catch (ClassNotFoundException e) {
		   // handle exception
		}catch (InstantiationException e) {
		   // handle exception
		}catch (IllegalAccessException e) {
		   // handle exception
		}
	}

	public void setDisplayPanel( JPanel panel ){
		int width = content_pane.getWidth(), height = content_pane.getHeight();
		
		content_pane.removeAll();
		content_pane.add(panel);
		panel.setSize(width, height);
		panel.setPreferredSize(new Dimension(width, height));
		content_pane.validate();
		requestFocus();
	}
	
}
