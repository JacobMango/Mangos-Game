package com.aussipvp.entity.projectile;

import java.util.Random;

import com.aussipvp.entity.Entity;
import com.aussipvp.graphics.Sprite;

public abstract class Projectile extends Entity {

	final protected double xOrigin, yOrigin;
	protected double angle;
	protected Sprite sprite;
	protected double x, y;
	protected double nx, ny;
	protected double distance;
	protected double speed, range, damage;
	protected final Random random = new Random();

	public Projectile(double x, double y, double dir) {
		this.xOrigin = x;
		this.yOrigin = y;
		this.angle = dir;
		this.y = y;
		this.x = x;
	}

	public Sprite getSprite() {
		return sprite;
	}

	public int getSpriteSize() {
		return sprite.SIZE;
	}

	protected void move() {

	}
}
