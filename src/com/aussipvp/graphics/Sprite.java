package com.aussipvp.graphics;

import java.awt.Color;

import com.aussipvp.Game;
import com.aussipvp.entity.Entity;

public class Sprite {

	public final int SIZE;
	private int x, y;
	protected int width;
	protected int height;
	public int[] pixels;
	protected SpriteSheet sheet;

	public static Sprite nothing = new Sprite(16, 0xFF00FF);
	public static Sprite grass = new Sprite(16, 6, 1, SpriteSheet.tiles);
	public static Sprite projectile = new Sprite(16, 12, 0, SpriteSheet.tiles);
	public static Sprite wall = new Sprite(16, 8, 2, SpriteSheet.tiles);
	public static Sprite floor = new Sprite(16, 10, 3, SpriteSheet.tiles);
	public static Sprite sand = new Sprite(16, 1, 0, SpriteSheet.tiles);
	public static Sprite menu = new Sprite(16, 0, 1, SpriteSheet.tiles);
	public static Sprite gravel = new Sprite(16, 2, 0, SpriteSheet.tiles);

	public static Sprite player_forward = new Sprite(32, 0, 1, SpriteSheet.tiles);
	public static Sprite player_back = new Sprite(32, 1, 1, SpriteSheet.tiles);
	public static Sprite player_side = new Sprite(32, 2, 1, SpriteSheet.tiles);
	public static Sprite player_forward_1 = new Sprite(32, 0, 2, SpriteSheet.tiles);
	public static Sprite player_back_1 = new Sprite(32, 1, 2, SpriteSheet.tiles);
	public static Sprite player_side_1 = new Sprite(32, 2, 2, SpriteSheet.tiles);
	public static Sprite player_forward_2 = new Sprite(32, 0, 3, SpriteSheet.tiles);
	public static Sprite player_back_2 = new Sprite(32, 1, 3, SpriteSheet.tiles);
	public static Sprite player_side_2 = new Sprite(32, 2, 3, SpriteSheet.tiles);

	public static Sprite particle_normal = new Sprite(2, 2, 0xAAAAAAAA);

	protected Sprite(SpriteSheet sheet, int width, int height) {
		this.SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.sheet = sheet;
	}

	public Sprite(int size, int x, int y, SpriteSheet sheet) {
		this.SIZE = size;
		this.width = size;
		this.height = size;
		this.pixels = new int[this.SIZE * this.SIZE];
		this.x = x * size;
		this.y = y * size;
		this.sheet = sheet;
		this.load();
	}

	public Sprite(int width, int height, int colour) {
		SIZE = -1;
		if (width >= 0) {
			if (width < Game.getWindowWidth()) {
				if (height >= 0) {
					if (height < Game.getWindowHeight()) {
						this.width = width;
						this.height = height;
						this.pixels = new int[width * height];
					}
				}
			}
		}
		this.setColour(colour);
	}

	public Sprite(int width, int height, int r, int g, int b, int a) {
		SIZE = -1;
		if (width >= 0) {
			if (width < Game.getWindowWidth()) {
				if (height >= 0) {
					if (height < Game.getWindowHeight()) {
						this.width = width;
						this.height = height;
						this.pixels = new int[width * height];
					}
				}
			}
		}
	}

	public Sprite(int size, int colour) {
		this.SIZE = size;
		this.pixels = new int[this.SIZE * this.SIZE];
		this.setColour(colour);
	}

	public Sprite(int[] pixels, int width, int height) {
		this.SIZE = (width == height) ? width : -1;
		this.width = width;
		this.height = height;
		this.pixels = pixels;
	}

	public Sprite(int w, int h, Color color) {
		SIZE = -1;
		if (width >= 0) {
			if (width < Game.getWindowWidth()) {
				if (height >= 0) {
					if (height < Game.getWindowHeight()) {
						this.width = width;
						this.height = height;
						this.pixels = new int[width * height];
					}
				}
			}
		}
		int a = color.getAlpha();
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		
		for (int i = 0; i < width * height; i++) {
			pixels[i] = color.getRGB();
		}
    }

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	private void setColour(int colour) {
		for (int i = 0; i < width * height; i++) {
			pixels[i] = colour;
		}
	}

	private void load() {
		for (int y = 0; y < this.height; y++) {
			for (int x = 0; x < this.width; x++) {
				this.pixels[x + y * this.SIZE] = sheet.pixels[(x + this.x) + (y + this.y) * sheet.SIZE];
			}
		}
	}

	public Game getGameClass() {
	    return Entity.getGameClass();
    }
}
