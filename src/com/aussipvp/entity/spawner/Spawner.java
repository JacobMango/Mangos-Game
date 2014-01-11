package com.aussipvp.entity.spawner;

import com.aussipvp.entity.Entity;
import com.aussipvp.entity.particles.Particle;
import com.aussipvp.level.Level;

public class Spawner extends Entity {

	public enum Type {
		PARTICLE, MOB;
	}
	
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		this.init(level, screen, game);
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
}
