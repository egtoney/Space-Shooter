/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.json.simple.JSONObject;

import com.ethan.space.GameSystems;
import com.ethan.space.game.entities.Bullet;
import com.ethan.space.game.entities.Enemy;
import com.ethan.space.game.entities.Player;
import com.ethan.space.graphics.FrameManager;
import com.ethan.space.input.ControllerState;
import com.ethan.space.input.ControllerStateListener;
import com.ethan.space.util.GameSettings;
import com.ethan.space.util.Pair;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class EndlessGame implements ControllerStateListener{

	private final Player DEFAULT_PLAYER;
	
	private Player main_player;
	private List<Enemy> enemies = new LinkedList<>();
	private List<Bullet> bullets = new LinkedList<>();
	
	private long[] score = {0};
	
	private boolean running = false;
	private double BUTTON_TIMER = 0.25; // seconds
	private double button_lock = 0;

	// Stuff used to draw stars
	private Random rnjesus = new Random();
	private final List<Pair<Double, Double>> stars = new LinkedList<>();
	private final List<Pair<Double, Double>> delete_buffer = new LinkedList<>();

	private GameSystems game_system;

	private EnemyManager enemy_manager;

	private Dimension game_dim;
	
	public EndlessGame( GameSystems game_system, GameSettings game_settings ){
		this.game_system = game_system;
		
		JSONObject player_settings = (JSONObject) game_settings.get("player-settings");
		DEFAULT_PLAYER = new Player(player_settings);
		
		enemy_manager = new EnemyManager(enemies, score);
	}
	
	public void resetGame(){
		main_player = DEFAULT_PLAYER.copy();
		main_player.setLocation(0.125, 0.5);
		main_player.setBulletContainer(bullets);
		enemies.clear();
		bullets.clear();
		enemy_manager.reset();
		score[0] = 0;
	}
	
	public void startGame(){
		running = true;
		resetGame();
		game_system.input_engine.bindControllerStateListener(this);
	}
	
	public void pauseGame(){
		running = !running;
	}
	
	public void stopGame(){
		running = false;
		game_system.input_engine.removeControllerStateListener(this);
		game_system.graphics_engine.changeRooms(FrameManager.MAIN_MENU);
	}

	/* (non-Javadoc)
	 * @see com.ethan.space.input.ControllerStateListener#fireOnControllerState(double, com.ethan.space.input.ControllerState)
	 */
	@Override
	public void fireOnControllerState(double delta_time, ControllerState event) {
		/*
		 * CHECK FOR PAUSE/START BUTTON
		 */
		if( event.isStartPressed() && button_lock <= 0 ){
			button_lock = BUTTON_TIMER;
			pauseGame();
		}
		
		/*
		 * UPDATE GAME STATE
		 */
		if( running ){
			/*
			 * Move all Entities
			 */
			main_player.tick( delta_time, event );
			
			List<Object> trash = new LinkedList<>();
			
			synchronized( bullets ){
				for( Bullet bullet : bullets ){
					bullet.tick(delta_time, event);
					synchronized( enemies ){
						for( Enemy enemy : enemies ){
							// Will be true if the bullet should die
							if( bullet.collidesWith(enemy, game_dim) ){
								trash.add(bullet);
								break;
							}
						}
					}
					if( bullet.getX() > 1.5 )
						trash.add(bullet);
				}
				bullets.removeAll(trash);
			}
			
			synchronized( enemies ){
				for( Enemy enemy : enemies ){
					enemy.tick(delta_time, event);
					if( enemy.getX() < -0.1 ){
						trash.add(enemy);
					}else if( enemy.getHealth() < 0 ){
						score[0] += enemy.getScore();
						trash.add(enemy);
					}else if( enemy.collidesWith(main_player, game_dim) ){
						trash.add(enemy);
					}
				}
				enemies.removeAll(trash);
			}
			
			/*
			 * Check for Game Over
			 */
			if( main_player.getHealth() <= 0 ){
				stopGame();
			}
			
			/*
			 * Spawn new Enemies
			 */
			enemy_manager.tick(delta_time, event);
		}
		
		if( button_lock > 0 )
			button_lock -= delta_time;
	}
	
	public void drawGame( Graphics2D g2d, Dimension dim ){
		game_dim = dim;
		
		/*
		 *  Draw background
		 */
		g2d.setColor(new Color(0, 0, 0));
		g2d.fillRect(0, 0, dim.width, dim.height);
		
		// Draw Stars
		g2d.setColor(new Color(255, 255, 255));
		for( Pair<Double, Double> star : stars ){
			double x1 = star.left;
			double y1 = star.right;
			g2d.drawLine((int) (x1), (int) (y1), (int) (x1-3), (int) (y1));
		}
		
		// Draw Enemies
		synchronized( enemies ){
			for( Enemy enemy : enemies )
				enemy.draw(g2d, dim);
		}
		
		// Draw Projectiles
		synchronized( bullets ){
			for( Bullet bullet : bullets )
				bullet.draw(g2d, dim);
		}
		
		// Draw Player
		main_player.draw(g2d, dim);
		
		// Draw UI
		g2d.setColor(new Color(255, 255, 255));
		g2d.drawString("score: "+score[0], 5, 15);
		g2d.drawString("enemies: "+enemies.size(), 5, 30);
		g2d.drawString("bullets: "+bullets.size(), 5, 45);
		g2d.drawString("hp: "+main_player.getHealth(), 5, 60);
		
		/*
		 *  Update Background
		 */
		if( running ){
			// Update Stars
			int i=0;
			while( stars.size() < 280 ){
				double x = dim.width+50;
				double y = rnjesus.nextDouble() * dim.height;
				Pair<Double, Double> star = new Pair<>(x, y);
				stars.add(star);
					break;
			}
			
			for( Pair<Double, Double> star : stars ){
				double x1 = star.left - 10;
				star.left = x1;
				if( star.left < 0 || star.right < 0 || star.right > dim.height ){
					delete_buffer.add(star);
				}
			}
			
			stars.removeAll(delete_buffer);
			delete_buffer.clear();
			
		/*
		 * Draw the Pause Screen
		 */
		}else{
			AlphaComposite alcom = AlphaComposite.getInstance( AlphaComposite.SRC_OVER, 0.5f );
			g2d.setComposite(alcom);
			g2d.setColor(new Color(0, 0, 0));
			g2d.fillRect(0, 0, dim.width, dim.height);
			g2d.setColor(new Color(255, 255, 255));
			g2d.setFont(new Font("Nasalization",Font.PLAIN,44));
			FontMetrics fm = g2d.getFontMetrics();
			g2d.drawString("Paused", (dim.width-fm.stringWidth("Paused"))/2, (dim.height-fm.getHeight())/2);
		}
	}
	
}
