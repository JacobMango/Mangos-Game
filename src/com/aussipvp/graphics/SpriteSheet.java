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

	public static SpriteSheet tiles = new SpriteSheet("/textures/textures.png", 256);

	public static SpriteSheet player = new SpriteSheet(tiles, 0, 1, 2, 3, 32);
	
	public static SpriteSheet player_down = new SpriteSheet(player, 1, 0, 1, 2, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 0, 0, 0, 2, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 2, 0, 2, 2, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 2, 0, 2, 2, 32);
	
	public Sprite[] sprites;

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
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xa * spriteSize) + (x0 + xa * spriteSize) * WIDTH];
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
