package com.aussipvp.graphics;

public class AnimatedSprite extends Sprite {

	private int frame = 0;
	private Sprite sprite;
	private int rate = 5;
	private int length = -1;
	private int time = 0;

	public AnimatedSprite(SpriteSheet sheet, int width, int height, int length) {
		super(sheet, width, height);
		this.length = length;
		sprite = sheet.sprites[0];
		if (length > sheet.getSprites().length) System.err.println("Error: Index Out Of Bounds Exception");
	}

	public void update() {
		time++;
		if (time % rate == 0) {
			if (frame >= length) frame = 0;
			else frame++;
			//sprite = sheet.getSprites()[frame];
		}
	}

	public Sprite getSprite() {
		return sprite;
	}

	public void setFrameRate(int rate) {
		this.rate = rate;
	}
}
