package com.aussipvp.entity.mob;

import java.util.List;

import com.aussipvp.entity.mob.Mob.Direction;
import com.aussipvp.graphics.AnimatedSprite;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.graphics.SpriteSheet;

public class MultiPlayer extends Player {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right, 32, 32, 3);
	private AnimatedSprite animSprite = down;
	private Sprite sprite;

	int xa = 0;
	int ya = 0;
	
	int id = 0;

	public double x = 0;
	public double y = 0;
	public boolean walking = false;
	public boolean frozen = false;

	public MultiPlayer(int x, int y) {
		super(x, y);
		this.x = x;
		this.y = y;
	}

	public void update() {
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
		if (walking) animSprite.update();
		else animSprite.setFrame(0);
		if (ya < 0) {
			animSprite = up;
			dir = Direction.UP;
		} else if (ya > 0) {
			animSprite = down;
			dir = Direction.DOWN;
		}
		if (xa < 0) {
			animSprite = left;
			dir = Direction.LEFT;
		} else if (xa > 0) {
			animSprite = right;
			dir = Direction.RIGHT;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) x, (int) y, sprite, 0);
	}
}
