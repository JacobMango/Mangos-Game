package com.aussipvp.entity.spawner;

import com.aussipvp.entity.particles.Particle;
import com.aussipvp.level.Level;

public class ParticleSpawner extends Spawner {

	private int life;

	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		for (int i = 0; i < amount - 1; i++) {
			level.add(new Particle(x, y, life));
		}
	}

}
