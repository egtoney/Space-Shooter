/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.game;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import com.ethan.space.game.entities.Enemy;
import com.ethan.space.input.ControllerState;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class EnemyManager {
	
	/*
	 * - - - - -
	 * 
	 * - - - - -
	 */
	private final int SPAWN_PATTERN_1 = 0;
	private final double SPAWN_PATTERN_1_SPAWN_DELAY = 4;
	
	/*
	 *   -
	 * -
	 * 
	 * -
	 *   -
	 */
	private final int SPAWN_PATTERN_2 = 1;
	private final double SPAWN_PATTERN_2_SPAWN_DELAY = 4;
	
	/*
	 *     - -
	 *    - -
	 *   - -
	 *  - -
	 * - -
	 */
	private final int SPAWN_PATTERN_3 = 2;
	private final double SPAWN_PATTERN_3_SPAWN_DELAY = 6;
	
	/*
	 * -   -
	 *   -
	 *     -
	 *       -
	 *   -    
	 */
	private final int SPAWN_PATTERN_4 = 3;
	private final double SPAWN_PATTERN_4_SPAWN_DEALY = 5;
	
	private final Random rnjesus = new Random();
	
	private final double ENEMY_SPAWN_TIMER = 0.1; // enemies/second
	
	private double current_spawn_timer = 2;

	private List<Enemy> enemies;
	
	/**
	 * TODO
	 * @param enemies 
	 * @param score
	 */
	public EnemyManager(List<Enemy> enemies, long[] score) {
		this.enemies = enemies;
	}

	/**
	 * TODO
	 */
	public void reset() {
		// TODO Implement method reset
		
	}

	/**
	 * TODO
	 * @param delta_time
	 * @param event
	 */
	public void tick(double delta_time, ControllerState event) {
		/*
		 * TRY TO SPAWN STUFF
		 */
		if( current_spawn_timer <= 0 ){
			current_spawn_timer = 1.0/ENEMY_SPAWN_TIMER;
			
			List<Enemy> new_enemies = spawnEnemies(delta_time);
			synchronized( enemies ){
				enemies.addAll(new_enemies);
			}
		}
		
		current_spawn_timer -= delta_time;
	}
	
	private List<Enemy> spawnEnemies(double delta_time){
		List<Enemy> new_enemies = new LinkedList<>();
		
		int enemy_wave_code = rnjesus.nextInt(4);
		switch( enemy_wave_code ){
		case( SPAWN_PATTERN_1 ):
			for( double x=1.1 ; x<1.5 ; x+=0.1 ){
				new_enemies.add(new Enemy(x, 0.25, 0.1));
				new_enemies.add(new Enemy(x, 0.75, 0.1));
			}
			current_spawn_timer = SPAWN_PATTERN_1_SPAWN_DELAY;
			break;
			
		case( SPAWN_PATTERN_2 ):
			for( double x=1.1, y=0.40 ; x<1.5 ; x+=0.1, y-=0.05 ){
				new_enemies.add(new Enemy(x, y));
				new_enemies.add(new Enemy(x, 1.0-y));
			}
			current_spawn_timer = SPAWN_PATTERN_2_SPAWN_DELAY;
			break;
			
		case( SPAWN_PATTERN_3 ):
			for( double x=1.1, y=0.95 ; y>0 ; x+=0.08, y-=0.2 ){
				new_enemies.add(new Enemy(x, y));
				new_enemies.add(new Enemy(x+0.2, y));
			}
			current_spawn_timer = SPAWN_PATTERN_3_SPAWN_DELAY;
			break;
			
		case( SPAWN_PATTERN_4 ):
			new_enemies.add(new Enemy(1.1, 0.1));
			new_enemies.add(new Enemy(1.3, 0.1));
			new_enemies.add(new Enemy(1.2, 0.3));
			new_enemies.add(new Enemy(1.3, 0.5));
			new_enemies.add(new Enemy(1.4, 0.7));
			new_enemies.add(new Enemy(1.2, 0.9));
			current_spawn_timer = SPAWN_PATTERN_4_SPAWN_DEALY;
			break;
		}
		
//		double dy = rnjesus.nextDouble()-0.1+0.05;
		
		return new_enemies;
	}

	
	
}
