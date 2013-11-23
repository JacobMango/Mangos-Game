package com.aussipvp.entity.mob;

import java.util.List;

import com.aussipvp.Game;
import com.aussipvp.entity.Entity;
import com.aussipvp.entity.projectile.PlayerProjectile;
import com.aussipvp.entity.projectile.Projectile;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.level.Level;

public abstract class Mob extends Entity {

	protected Sprite sprite;
	protected int dir = 0;
	protected Game game = new Game();
	protected boolean moving = false;
	protected boolean walking = false;

	public void move(int xa, int ya) {
		if (xa != 0 && ya != 0) {
			move(xa, 0);
			move(0, ya);
			return;
		}
		if (ya < 0) dir = 0;
		if (xa > 0) dir = 1;
		if (xa < 0) dir = 2;
		if (ya > 0) dir = 3;

		if (!collision(xa, ya)) {
			y += ya;
			x += xa;
		} else {

		}
	}

	public abstract void update();

	public static int getProjectileSize() {
		return Level.getProjectiles().size();
	}

	public static Projectile getProjectile(int i) {
		return Level.getProjectiles().get(i);
	}

	public static List<Projectile> getProjectiles() {
		return Level.getProjectiles();
	}

	public static Projectile removeProjectiles(int i) {
		return Level.getProjectiles().remove(i);
	}

	protected void shoot(int x, int y, double direction) {
		// direction *= 180 / Math.PI;
		Game.angle = direction;
		Projectile p = new PlayerProjectile(x, y, direction);
		level.add(p);
	}

	private boolean collision(int xa, int ya) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (int) (((x + xa) + c % 2 * 12 - 7) / 16);
			int yt = (int) (((y + ya) + c / 2 * 12 + 3) / 16);
			if (level.getTile(xt, yt).isSolid()) solid = true;
		}
		return solid;
	}

	public abstract void render(Screen screen);
}
