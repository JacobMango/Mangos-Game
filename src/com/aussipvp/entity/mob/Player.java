package com.aussipvp.entity.mob;

import java.util.List;

import com.aussipvp.Game;
import com.aussipvp.Location;
import com.aussipvp.entity.Entity;
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
import com.aussipvp.util.Vector2i;

public class Player extends Mob {

	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	public final int MAX_HEALTH = 20;
	public int health = 20;
	private final int VOID_LOSE_HEALTH_RATE = 20;
	private int voidLoseHealthRate = 20;
	private final int NATURALLY_GAIN_HEALTH_RATE = 50;
	private int naturallyGainHealthRate = 50;
	public boolean frozen = false;
	private boolean fjcmbcsa = false;

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);

	private AnimatedSprite animSprite = down;

	private int fireRate = 0;

	public Player(Keyboard input) {
		this.input = input;
		animSprite = down;
	}

	public Player(double x, double y) {
		this.x = x;
		this.y = y;
		animSprite = down;
		fireRate = PlayerProjectile.FIRE_RATE;
	}

	public Player(Location location, Keyboard input) {
		this.x = location.getX();
		this.y = location.getY();
		this.input = input;
		animSprite = down;
		fireRate = PlayerProjectile.FIRE_RATE;
	}

	double speed = 1.1;

	public void update() {
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (fireRate > 0) fireRate--;
		double xa = 0, ya = 0;
		if (anim < 7500) anim++;
		else anim = 0;

		if (input.up) {
			ya -= speed;
			animSprite = up;
		} else if (input.down) {
			ya += speed;
			animSprite = down;
		}
		if (input.left) {
			xa -= speed;
			animSprite = left;
		} else if (input.right) {
			xa += speed;
			animSprite = right;
		}
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
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
		animSprite.update();
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
			shoot((int) x + 16, (int) y + 16, dir);
			fireRate = PlayerProjectile.FIRE_RATE;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) x, (int) y, sprite, 0);
	}

	public boolean getTileUnderPlayer(int x, int y, int tile) {
		if (x < 0 || y < 0 || x >= level.getWidth() || y >= level.getHeight()) {
			Tile.nothing.render(x, y, screen);
			return false;
		} else if (level.tiles[(x + y * level.getWidth())] == tile) return true;
		else return false;
	}

	public void freeze() {
		frozen = !frozen;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
}
