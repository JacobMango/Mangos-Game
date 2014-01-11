package com.aussipvp.entity.projectile;

import com.aussipvp.entity.spawner.ParticleSpawner;
import com.aussipvp.entity.spawner.Spawner;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;

public class PlayerProjectile extends Projectile {

	public static final int FIRE_RATE = 10;
	
	public PlayerProjectile(double x, double y, double dir) {
		super(x, y, dir);
		this.range = random.nextInt((int) 100.0) + 150.0;
		this.speed = 2;
		this.damage = 20;
		sprite = Sprite.projectile;
		this.nx = speed * Math.cos(this.angle);
		this.ny = speed * Math.sin(this.angle);
	}

	public void update() {
		if (level.tileCollision((int) x + (int) nx, (int) y + (int)ny, 7, 5, 5)) {
			level.add(new ParticleSpawner((int) x,(int) y, 44, 50, level));
			remove();
		}
		move();
	}

	protected void move() {
		x += nx;
		y += ny;
		if (distance() > range) remove();
	}

	private double distance() {
		double dist = 0;
		dist = Math.hypot(xOrigin - x, yOrigin - y);
		return dist;
	}

	public void render(Screen screen) {
		screen.renderProjectile((int) x - 8, (int) y - 5, this);
		//this.p.render(screen);
	}

}
