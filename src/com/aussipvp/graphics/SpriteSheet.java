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

	public static SpriteSheet player = new SpriteSheet("/textures/player.png", 128, 96);
	public static SpriteSheet player_down = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet player_up = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet player_right = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet player_left = new SpriteSheet(player, 3, 0, 1, 3, 32);

	public static SpriteSheet basic = new SpriteSheet("/textures/player.png", 128, 96);
	public static SpriteSheet basic_down = new SpriteSheet(basic, 0, 0, 1, 3, 32);
	public static SpriteSheet basic_up = new SpriteSheet(basic, 1, 0, 1, 3, 32);
	public static SpriteSheet basic_right = new SpriteSheet(basic, 2, 0, 1, 3, 32);
	public static SpriteSheet basic_left = new SpriteSheet(basic, 3, 0, 1, 3, 32);
	
	public static SpriteSheet font = new SpriteSheet("/textures/font.png", 576, 16);;
	public static SpriteSheet A = new SpriteSheet(font, 0, 0, 0, 0, 8);
	public static SpriteSheet B = new SpriteSheet(font, 1, 0, 0, 0, 8);
	public static SpriteSheet C = new SpriteSheet(font, 2, 0, 0, 0, 8);
	public static SpriteSheet D = new SpriteSheet(font, 3, 0, 0, 0, 8);
	public static SpriteSheet E = new SpriteSheet(font, 4, 0, 0, 0, 8);
	public static SpriteSheet F = new SpriteSheet(font, 5, 0, 0, 0, 8);
	public static SpriteSheet G = new SpriteSheet(font, 6, 0, 0, 0, 8);
	public static SpriteSheet H = new SpriteSheet(font, 7, 0, 0, 0, 8);
	public static SpriteSheet I = new SpriteSheet(font, 8, 0, 0, 0, 8);
	public static SpriteSheet J = new SpriteSheet(font, 9, 0, 0, 0, 8);
	public static SpriteSheet K = new SpriteSheet(font, 10, 0, 0, 0, 8);
	public static SpriteSheet L = new SpriteSheet(font, 11, 0, 0, 0, 8);
	public static SpriteSheet M = new SpriteSheet(font, 12, 0, 0, 0, 8);
	public static SpriteSheet N = new SpriteSheet(font, 13, 0, 0, 0, 8);
	public static SpriteSheet O = new SpriteSheet(font, 14, 0, 0, 0, 8);
	public static SpriteSheet P = new SpriteSheet(font, 15, 0, 0, 0, 8);
	public static SpriteSheet Q = new SpriteSheet(font, 16, 0, 0, 0, 8);
	public static SpriteSheet R = new SpriteSheet(font, 17, 0, 0, 0, 8);
	public static SpriteSheet S = new SpriteSheet(font, 18, 0, 0, 0, 8);
	public static SpriteSheet T = new SpriteSheet(font, 19, 0, 0, 0, 8);
	public static SpriteSheet U = new SpriteSheet(font, 20, 0, 0, 0, 8);
	public static SpriteSheet V = new SpriteSheet(font, 21, 0, 0, 0, 8);
	public static SpriteSheet W = new SpriteSheet(font, 22, 0, 0, 0, 8);
	public static SpriteSheet X = new SpriteSheet(font, 23, 0, 0, 0, 8);
	public static SpriteSheet Y = new SpriteSheet(font, 24, 0, 0, 0, 8);
	public static SpriteSheet Z = new SpriteSheet(font, 25, 0, 0, 0, 8);
	public static SpriteSheet Space = new SpriteSheet(font, 26, 1, 0, 0, 8);
	
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
