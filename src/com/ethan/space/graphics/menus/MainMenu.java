/**
 * Created on Aug 9, 2016 by Ethan Toney
 */
package com.ethan.space.graphics.menus;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.ethan.space.GameSystems;
import com.ethan.space.graphics.FrameManager;
import com.ethan.space.util.OutputHandler;
import com.ethan.space.util.Pair;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class MainMenu extends JPanel{

	private final int BUTTON_WIDTH = 300;
	private final int BUTTON_HEIGHT = 50;
	
	private final JButton play_button;
	private final JButton exit_button;
	private final JLabel game_logo;

	// Stuff used to draw stars
	private Random rnjesus = new Random();
	private final List<Pair<Double, Double>> stars = new LinkedList<>();
	private final List<Pair<Double, Double>> delete_buffer = new LinkedList<>();

	public MainMenu( GameSystems game_system ){
		super(null);
		
		setOpaque(false);
		
		game_logo = new JLabel("GENERIC SPACE SHOOTER", SwingConstants.CENTER);
		game_logo.setForeground(new Color(255, 255, 255));
		game_logo.setFont(new Font("Nasalization",Font.PLAIN,44));
		add(game_logo);
		
		play_button = new JButton("Play!");
		play_button.setFont(new Font("Nasalization",Font.PLAIN,26));
		play_button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				game_system.graphics_engine.changeRooms(FrameManager.ENDLESS_MODE);
				// Show cool loading animation
				game_system.endless_game.startGame();
			}
			
		});
		add(play_button);
		
		exit_button = new JButton("Exit");
		exit_button.setFont(new Font("Nasalization",Font.PLAIN,26));
		exit_button.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				game_system.stop();
			}
			
		});
		add(exit_button);
	}
	
	@Override
	public void setBounds(int x, int y, int width, int height){
		super.setBounds(x, y, width, height);

		game_logo.setBounds(0, 50, width, BUTTON_HEIGHT);
		play_button.setBounds((int) ((width-BUTTON_WIDTH)/2.0), (int) (120+1.5*BUTTON_HEIGHT), BUTTON_WIDTH, BUTTON_HEIGHT);
		exit_button.setBounds((int) ((width-BUTTON_WIDTH)/2.0), (int) (120+3*BUTTON_HEIGHT), BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	@Override
	public void setSize(int width, int height){
		super.setSize(width, height);

		game_logo.setBounds(0, 50, width, BUTTON_HEIGHT);
		play_button.setBounds((int) ((width-BUTTON_WIDTH)/2.0), (int) (120+1.5*BUTTON_HEIGHT), BUTTON_WIDTH, BUTTON_HEIGHT);
		exit_button.setBounds((int) ((width-BUTTON_WIDTH)/2.0), (int) (120+3*BUTTON_HEIGHT), BUTTON_WIDTH, BUTTON_HEIGHT);
	}
	
	@Override
	public void paint(Graphics g){
		int width = getWidth(), height = getHeight();
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(0, 0, width, height);
		
		g2d.setColor(new Color(255, 255, 255));
		
		int dx = 90, dy = 50;
		
		g2d.setColor(new Color(31, 40, 45));
		g2d.drawLine(0, 0, dx, dy);
		g2d.drawLine(0, height, dx, height-dy);
		g2d.drawLine(width, 0, width-dx, dy);
		g2d.drawLine(width-dx, height-dy, width, height);
		
		double curr_time = 1;//1.0 - (System.currentTimeMillis()/200.0 % 1);
		
		int tdx = (int) (curr_time * dx), tdy = (int) (curr_time * dy);

		g2d.drawLine(tdx, tdy, tdx, height-tdy);
		g2d.drawLine(width-tdx, tdy, width-tdx, height-tdy);
		g2d.drawLine(tdx, tdy, width-tdx, tdy);
		g2d.drawLine(tdx, height-tdy, width-tdx, height-tdy);
		
		// How to draw stars
		while( stars.size() < 150 ){
			double x = rnjesus.nextDouble() * width/4 + 3*width/8;
			double y = rnjesus.nextDouble() * height/4 + 3*height/8;
			Pair<Double, Double> star = new Pair<>(x, y);
			stars.add(star);
		}
		
		int x, y;
		double hw = width/2.0;
		double hh = height/2.0;
		g2d.setColor(new Color(255, 255, 255));
		for( Pair<Double, Double> star : stars ){
			double x1 = star.left - hw;
			double y1 = star.right - hh;
			double x2 = x1 * 1.1;
			double y2 = y1 * 1.1;
			star.left = x1*1.08+hw;
			star.right = y1*1.08+hh;
			g2d.drawLine((int) (x1+hw), (int) (y1+hh), (int) (x2+hw), (int) (y2+hh));
			if( star.left < 0 || star.left > width || star.right < 0 || star.right > height ){
				delete_buffer.add(star);
			}
		}
		
		stars.removeAll(delete_buffer);
		delete_buffer.clear();
		
		super.paint(g);
	}
	
}
