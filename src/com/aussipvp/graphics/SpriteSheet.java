package com.aussipvp.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {

	private String path;
	public final int SIZE;
	public final int WIDTH, HEIGHT;
	public final int SPRITESIZE;
	public int[] pixels;

	public static SpriteSheet tiles;

	public static SpriteSheet player;
	public static SpriteSheet player_down;
	public static SpriteSheet player_up;
	public static SpriteSheet player_right;
	public static SpriteSheet player_left;

	public static SpriteSheet basic;
	public static SpriteSheet basic_down;
	public static SpriteSheet basic_up;
	public static SpriteSheet basic_right;
	public static SpriteSheet basic_left;

	public static SpriteSheet font;

	public Sprite[] sprites;

	static int time = 999;
	static int rate = 120;

	public static void update() {
		if (time++ % rate == 0) {
			tiles = new SpriteSheet("/textures/textures.png", 256);
			player = new SpriteSheet("/textures/player.png", 128, 96);
			player_down = new SpriteSheet(player, 0, 0, 1, 3, 32);
			player_up = new SpriteSheet(player, 1, 0, 1, 3, 32);
			player_right = new SpriteSheet(player, 2, 0, 1, 3, 32);
			player_left = new SpriteSheet(player, 3, 0, 1, 3, 32);
			basic = new SpriteSheet("/textures/player.png", 128, 96);
			basic_down = new SpriteSheet(basic, 0, 0, 1, 3, 32);
			basic_up = new SpriteSheet(basic, 1, 0, 1, 3, 32);
			basic_right = new SpriteSheet(basic, 2, 0, 1, 3, 32);
			basic_left = new SpriteSheet(basic, 3, 0, 1, 3, 32);
			font = new SpriteSheet("/textures/font.png", 600, 24);
		}
	}

	public SpriteSheet(SpriteSheet sheet, int x, int y, int width, int height, int spriteSize) {
		this.SPRITESIZE = spriteSize;
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int w = width * spriteSize;
		int h = height * spriteSize;
		this.SIZE = (width == height) ? width : -1;
		this.WIDTH = w;
		this.HEIGHT = h;
		this.pixels = new int[w * h];
		for (int y0 = 0; y0 < h; y0++) {
			int yp = yy + y0;
			for (int x0 = 0; x0 < w; x0++) {
				int xp = xx + x0;
				pixels[x0 + y0 * w] = sheet.pixels[xp + yp * sheet.WIDTH];
			}
		}

		int frame = 0;
		sprites = new Sprite[width * height];
		for (int ya = 0; ya < height; ya++) {
			for (int xa = 0; xa < width; xa++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (y0 + ya * spriteSize) * WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}

	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SPRITESIZE = 32;
		this.SIZE = size;
		this.WIDTH = size;
		this.HEIGHT = size;
		this.pixels = new int[this.SIZE * this.SIZE];
		load();
	}

	public SpriteSheet(String path, int width, int height) {
		this.path = path;
		this.SIZE = -1;
		this.SPRITESIZE = 32;
		this.WIDTH = width;
		this.HEIGHT = height;
		this.pixels = new int[this.WIDTH * this.HEIGHT];
		load();
	}

	public Sprite[] getSprites() {
		return sprites;
	}

	private void load() {
		try {
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			int w = image.getWidth();
			int h = image.getHeight();
			image.getRGB(0, 0, w, h, pixels, 0, w);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
