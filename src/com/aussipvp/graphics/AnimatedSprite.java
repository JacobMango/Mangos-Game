package com.aussipvp.graphics;

import com.aussipvp.Game;
import com.aussipvp.entity.mob.Player;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate = 20;
	private int length = -1;
	private int time = 0;
	
	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.sprites[0];
		if (length > sheet.getSprites().length) {
			System.err.println("Error: Index Out Of Bounds Exception: " + this);
		}
	}

	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= length - 1) frame = 0;
			else frame++;
			sprite = sheet.getSprites()[frame];
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int rate) {
		this.rate = rate;
	}

	public void setFrame(int index) {
		if (index > sheet.getSprites().length - 1) {
			System.err.println("Error: Index Out Of Bounds Exception: " + this);
			return;
		}
		sprite = sheet.getSprites()[index];
	}
}
