/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.game.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.LinkedList;
import java.util.List;

import org.json.simple.JSONObject;

import com.ethan.space.input.ControllerState;
import com.ethan.space.util.OutputHandler;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class Player extends SolidObject{

	private final double BASE_HEALTH = 100;
	private final double BASE_DAMAGE = 50;
	private final double BASE_ARMOR = 0.0; // % reduction
	private final double BASE_WEAPON_RECHARGE_RATE = 5; // shots/second
	
	private final double HEALTH_MODIFIER;
	private final double DAMAGE_MODIFIER;
	private final double ARMOR_MODIFIER;
	private final double WEAPON_RECHARGE_MODIFIER; 
	
	private double health;
	private JSONObject player_settings;
	
	double radius = 10;
	private GeneralPath ship_model;
	
	private final double WEAPON_RECHARGE_RATE; // rounds/second
	
	private double main_weapon_recharge_timer = 1;
	private List<Bullet> bullet_container;
	
	/**
	 * TODO
	 * @param player_settings
	 */
	public Player(JSONObject player_settings) {
		super(0, 0);
		
		this.player_settings = player_settings;
		
		// Load player blueprint
		HEALTH_MODIFIER = (double) player_settings.get("health-modifier");
		health = HEALTH_MODIFIER * BASE_HEALTH;
		DAMAGE_MODIFIER = (double) player_settings.get("damage-modifier");
		ARMOR_MODIFIER = (double) player_settings.get("defense-modifier");
		WEAPON_RECHARGE_MODIFIER = (double) player_settings.get("recharge-modifier");
		
		// Set blueprint paramaters
		WEAPON_RECHARGE_RATE = BASE_WEAPON_RECHARGE_RATE * WEAPON_RECHARGE_MODIFIER;
		
		// Construct player model
		int dx[] = { -20, -30, 20, -30 };
		int dy[] = { 0, 10, 0, -10 };
		
		ship_model = new GeneralPath( GeneralPath.WIND_EVEN_ODD, dx.length );
		ship_model.moveTo(dx[0], dy[0]);
		for( int i=1 ; i<dx.length ; i++ )
			ship_model.lineTo(dx[i], dy[i]);
		ship_model.closePath();
	}

	/**
	 * TODO
	 * @return
	 */
	public Player copy() {
		return new Player(player_settings);
	}

	/* (non-Javadoc)
	 * @see com.ethan.space.game.entities.SolidObject#tick(com.ethan.space.input.ControllerState)
	 */
	@Override
	public void tick( double delta_time, ControllerState controller ) {
		// temp constants
		double speed = 0.5; // units / sec
		
		double px = getX(), py = getY();
		
		/*
		 * MOVE THE PLAYER 
		 */
		double dx = 0, dy = 0;

		if( controller.isUpPressed() ) dy--;
		if( controller.isDownPressed() ) dy++;
		if( controller.isLeftPressed() ) dx--;
		if( controller.isRightPressed() ) dx++;
		
		px += dx*speed*delta_time;
		py += dy*speed*delta_time;
		
		if( py < 0.05 ) py = 0.05;
		if( py > 0.95 ) py = 0.95;
		
		if( px < 0.05 ) px = 0.05;
		if( px > 0.95 ) px = 0.95;
		
		setLocation( px, py );
		
		/*
		 * TRY TO SHOOT WEAPONS
		 */
		if( controller.isAPressed() && main_weapon_recharge_timer <= 0 ){
			main_weapon_recharge_timer = 1.0/WEAPON_RECHARGE_RATE;
			List<Bullet> bullets = fireWeapon();
			synchronized( bullet_container ){
				bullet_container.addAll(bullets);
			}
			bullets.clear();
		}
		
		if( main_weapon_recharge_timer > 0 )
			main_weapon_recharge_timer -= delta_time;
	}

	/**
	 * TODO
	 * @return
	 */
	private List<Bullet> fireWeapon() {
		List<Bullet> new_bullets = new LinkedList<>();
		
		double bullet_damage = BASE_DAMAGE * DAMAGE_MODIFIER;
		new_bullets.add(new Bullet(getX(), getY(), 1.0, 0.0, DAMAGE_MODIFIER ));
		
		return new_bullets;
	}

	/* (non-Javadoc)
	 * @see com.ethan.space.game.entities.SolidObject#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g2d, Dimension dim) {
		int px = (int) (dim.width*getX()), py = (int) (dim.height*getY());
		
		g2d.setColor(new Color(79, 75, 73));
		g2d.translate(px, py);
		g2d.fill(ship_model);
		g2d.translate(-px, -py);
	}

	/**
	 * TODO
	 * @param bullets
	 */
	public void setBulletContainer(List<Bullet> bullets) {
		bullet_container = bullets;
	}

	/**
	 * TODO
	 * @param damage
	 * @param armor_pen
	 */
	public void takeDamage(double damage, double armor_pen) {
		health -= damage * (1 - (BASE_ARMOR * armor_pen));
	}

	/**
	 * TODO
	 * @return
	 */
	public double getHealth() {
		return health;
	}
	
	
	
}
