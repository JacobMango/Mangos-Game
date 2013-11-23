package com.aussipvp.entity.mob;

import com.aussipvp.Game;
import com.aussipvp.entity.projectile.PlayerProjectile;
import com.aussipvp.entity.projectile.Projectile;
import com.aussipvp.graphics.AnimatedSprite;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.graphics.SpriteSheet;
import com.aussipvp.input.Keyboard;
import com.aussipvp.input.Mouse;
import com.aussipvp.level.Level;
import com.aussipvp.level.tile.Tile;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	private final int MAX_HEALTH = 20;
	private int health = 20;
	private final int VOID_LOSE_HEALTH_RATE = 20;
	private int voidLoseHealthRate = 20;
	private final int NATURALLY_GAIN_HEALTH_RATE = 50;
	private int naturallyGainHealthRate = 50;
	private boolean frozen = false;

	private AnimatedSprite test = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 2);

	private int fireRate = 0;

	public Player() {
		sprite = Sprite.player_forward;
	}

	public Player(Keyboard input) {
		this.input = input;
		// sprite = Sprite.player_forward;
	}

	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		// sprite = Sprite.player_forward;
		fireRate = PlayerProjectile.FIRE_RATE;
	}

	public void teleport(double x, double y) {
		this.x = x * 16;
		this.y = y * 16;
	}

	public void update() {
		if (fireRate > 0) fireRate--;

		int xa = 0, ya = 0;
		if (anim < 7500) anim++;
		else anim = 0;

		if (frozen) return;
		else {
			if (input.up) ya--;
			if (input.down) ya++;
			if (input.left) xa--;
			if (input.right) xa++;
			if (xa != 0 || ya != 0) {
				move(xa, ya);
				walking = true;
			} else {
				walking = false;
			}
		}
		clear();
		updateShooting();

		if (health == 0) {
			Game.dead = true;
		}

		if (naturallyGainHealthRate <= 0) {
			this.addHP(1);
			naturallyGainHealthRate = NATURALLY_GAIN_HEALTH_RATE;
		} else {
			naturallyGainHealthRate--;
		}

		if (voidLoseHealthRate <= 0) {
			if (this.getTileUnderPlayer((int) x / 16, (int) y / 16, Tile.col_void)) this.removeHP(2);
			voidLoseHealthRate = VOID_LOSE_HEALTH_RATE;
			if (this.x < 0.0 || this.y < 0.0) this.removeHP(2);
		} else {
			voidLoseHealthRate--;
		}
		// System.out.println("Health: " + health);
		test.update();
	}

	private void removeHP(int hp) {
		if (health <= 0) {
			return;
		} else {
			health = health - hp;
		}
	}

	private void addHP(int hp) {
		if (health >= this.MAX_HEALTH) {
			return;
		} else {
			health = health + hp;
		}
	}

	public int getHealth() {
		return health;
	}

	private void clear() {
		for (int i = 0; i < Level.getProjectiles().size(); i++) {
			Projectile p = Level.getProjectiles().get(i);
			if (p.isRemoved()) Level.getProjectiles().remove(i);
		}
	}

	private void updateShooting() {
		if (Mouse.getButton() == 1 && fireRate <= 0) {
			double dx = Mouse.getX() - Game.getWindowWidth() / 2;
			double dy = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dy, dx);
			shoot((int) x, (int) y, dir);
			fireRate = PlayerProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		//sprite = test.getSprite();
		screen.renderMob((int) x - 16, (int) y - 16, sprite.player_forward, 0);
	}

	public boolean getTileUnderPlayer(int x, int y, int tile) {
		if (x < 0 || y < 0 || x >= level.getWidth() || y >= level.getHeight()) {
			Tile.nothing.render(x, y, screen);
			return false;
		} else if (level.tiles[(x + y * level.getWidth())] == tile) return true;
		else return false;
	}

	public void freeze() {
		frozen = true;
	}

}
