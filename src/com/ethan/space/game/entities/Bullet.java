/**
 * Created on Aug 10, 2016 by Ethan Toney
 */
package com.ethan.space.game.entities;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;

import com.ethan.space.input.ControllerState;

/**
 * 
 * 
 * @author Ethan Toney
 */
public class Bullet extends SolidObject{

	private final double BASE_SPEED = 2; // units/second
	private final double BASE_DAMAGE = 100;
	
	private double dx, dy; // displacement per tick
	private double damage;
	double radius = 5;
	private double piercing = 0;
	private double armor_pen = 0;
	
	/**
	 * TODO
	 * @param x
	 * @param y
	 * @param d
	 * @param e
	 * @param dAMAGE_MODIFIER
	 */
	public Bullet(double x, double y, double dx, double dy, double DAMAGE_MODIFIER) {
		super( x, y );
		this.dx = dx;
		this.dy = dy;
		damage = DAMAGE_MODIFIER * BASE_DAMAGE;
	}

	/* (non-Javadoc)
	 * @see com.ethan.space.game.entities.SolidObject#tick(com.ethan.space.input.ControllerState)
	 */
	@Override
	public void tick( double delta_time, ControllerState controller ) {
		double sx = getX(), sy = getY();
		sx += delta_time*BASE_SPEED*dx;
		sy += delta_time*BASE_SPEED*dy;
		setLocation( sx, sy );
	}

	/* (non-Javadoc)
	 * @see com.ethan.space.game.entities.SolidObject#draw(java.awt.Graphics2D)
	 */
	@Override
	public void draw(Graphics2D g2d, Dimension dim) {
		double sx = dim.width*getX(), sy = dim.height*getY();
		
		g2d.setColor(new Color(0, 255, 0));
		g2d.drawOval((int) (sx-radius), (int) (sy-radius), (int) (2*radius), (int) (2*radius));
	}

	/**
	 * TODO
	 * @param enemy
	 * @param game_dim 
	 * @return
	 */
	public boolean collidesWith(Enemy enemy, Dimension game_dim) {
		double dx = game_dim.width * (getX() - enemy.getX());
		double dy = game_dim.height * (getY() - enemy.getY());
		double dr = radius+enemy.radius;
		dr *= dr;
		if( dx*dx + dy*dy <= dr ){
			piercing--;
			enemy.takeDamage( damage, armor_pen );
		}
		return piercing < 0;
	}

}
