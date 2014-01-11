package com.aussipvp.entity.mob;

import java.util.Random;

import com.aussipvp.graphics.AnimatedSprite;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.graphics.SpriteSheet;

public class BasicMob extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.basic_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.basic_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.basic_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.basic_right, 32, 32, 3);

	private AnimatedSprite animSprite;

	private int time = 0;
	private int xa = 0;
	private int ya = 0;

	public BasicMob(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		animSprite = down;
	}

	public void update() {
		time++;
		if (time % (random.nextInt(50) + 30) == 0) {
			xa = random.nextInt(3) - 1;
			ya = random.nextInt(3) - 1;
			if (random.nextInt(5) == 0) {
				xa = 0;
				ya = 0;
			}
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

		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int) x, (int) y, this);
	}
}
