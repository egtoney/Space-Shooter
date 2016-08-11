/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.game.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import java.util.Random;

import com.ethan.space.input.ControllerState;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class Enemy extends SolidObject{

	private static final Random rnjesus = new Random();
	
	private final double BASE_SPEED = 0.2;
	private final double BASE_HEALTH = 100;
	private final double BASE_DAMAGE = 50;
	private final double BASE_ARMOR = 0; // % damage reduction

	private double dx=-1, dy=0, drunk_driving_constant;
	private double health;
	private double damage;
	private double armor_pen = 0;
	private double time_alive = 0;
	private final double timer_offset;
	private long score = 100;
	
	double radius = 15;
	private static final GeneralPath SHIP_MODEL = new GeneralPath( GeneralPath.WIND_EVEN_ODD, 4 );
	private static boolean init_model = false;

	public Enemy(double tx, double ty, double dd){
		this(tx, ty);
		drunk_driving_constant = dd;
	}
	
	public Enemy(double tx, double ty){
		super(tx, ty);
		
		// Set the stats
		health = BASE_HEALTH;
		damage = BASE_DAMAGE;
		
		timer_offset = rnjesus.nextDouble()*3;
		
		// Construct the ship
		int dx[] = { 15, 8, -45, 8 };
		int dy[] = { 0, -15, 0, 15 };
		
		if( !init_model ){
			synchronized( SHIP_MODEL ){
				if( !init_model ){
					init_model = true;
					SHIP_MODEL.moveTo(dx[0], dy[0]);
					for( int i=1 ; i<dx.length ; i++ )
						SHIP_MODEL.lineTo(dx[i], dy[i]);
					SHIP_MODEL.closePath();
				}
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see com.ethan.space.game.entities.SolidObject#tick(com.ethan.space.input.ControllerState)
	 */
	@Override
	public void tick( double delta_time, ControllerState controller ) {
		double sx = getX(), sy = getY();
		sx += delta_time*BASE_SPEED*dx;
		sy += delta_time*BASE_SPEED*0.5*Math.cos(timer_offset+time_alive*2);
		setLocation( sx, sy );
		
		time_alive += delta_time;
	}

	/* (non-Javadoc)
	 * @see com.ethan.space.game.entities.SolidObject#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g2d, Dimension dim) {
		int px = (int) (dim.width*getX()), py = (int) (dim.height*getY());
		
		g2d.setColor(new Color(79, 75, 73));
		g2d.translate(px, py);
		g2d.fill(SHIP_MODEL);
		g2d.translate(-px, -py);
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

	/**
	 * TODO
	 * @param main_player
	 * @param game_dim 
	 * @return
	 */
	public boolean collidesWith(Player main_player, Dimension game_dim) {
		double dx = game_dim.width * (getX() - main_player.getX());
		double dy = game_dim.height * (getY() - main_player.getY());
		double dr = radius+main_player.radius;
		dr *= dr;
		if( dx*dx + dy*dy <= dr ){
			main_player.takeDamage( damage, armor_pen );
			return true;
		}
		return false;
	}

	/**
	 * TODO
	 * @return
	 */
	public long getScore() {
		return score;
	}

}
