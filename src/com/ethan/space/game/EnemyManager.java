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
	
	private final Random rnjesus = new Random();
	
	private final double ENEMY_SPAWN_TIMER = 1; // enemies/second
	
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
		
		double dy = rnjesus.nextDouble()-0.1+0.05;
		new_enemies.add(new Enemy(1.1, dy));
		
		return new_enemies;
	}

	
	
}
