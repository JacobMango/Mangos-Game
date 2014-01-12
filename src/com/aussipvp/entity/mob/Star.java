package com.aussipvp.entity.mob;

import java.util.List;

import com.aussipvp.graphics.AnimatedSprite;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.SpriteSheet;
import com.aussipvp.level.Node;
import com.aussipvp.util.Vector2i;

public class Star extends Mob {

	private AnimatedSprite down = new AnimatedSprite(SpriteSheet.basic_down, 32, 32, 3);
	private AnimatedSprite up = new AnimatedSprite(SpriteSheet.basic_up, 32, 32, 3);
	private AnimatedSprite left = new AnimatedSprite(SpriteSheet.basic_left, 32, 32, 3);
	private AnimatedSprite right = new AnimatedSprite(SpriteSheet.basic_right, 32, 32, 3);

	private AnimatedSprite animSprite;

	private int timeRandom = 0;
	private int timeFinder = 0;
	private int xa = 0;
	private int ya = 0;
	private List<Node> path = null;

	public Star(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		animSprite = down;
	}

	private void move() {
		xa = 0;
		ya = 0;
		int px = (int) level.getPlayers().get(0).getX();
		int py = (int) level.getPlayers().get(0).getY();
		Vector2i start = new Vector2i((int) getX() >> 4, (int) getY() >> 4);
		Vector2i destination = new Vector2i((int) px >> 4, (int) py >> 4);
		timeFinder++;
		if (timeFinder % 20 == 0) path = level.findPath(start, destination);
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (x < vec.getX() << 4) xa++;
				if (x > vec.getX() << 4) xa--;
				if (y < vec.getY() << 4) ya++;
				if (y > vec.getY() << 4) ya--;
			}
		} else {
			timeRandom++;
			if (timeRandom % (random.nextInt(50) + 30) == 0) {
				xa = random.nextInt(3) - 1;
				ya = random.nextInt(3) - 1;
				if (random.nextInt(5) == 0) {
					xa = 0;
					ya = 0;
				}
			}
		}
		
		if (xa != 0 || ya != 0) {
			move(xa, ya);
			walking = true;
		} else {
			walking = false;
		}
	}

	public void update() {
		move();
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
		screen.renderMob((int) x, (int) y, this);
	}
}
