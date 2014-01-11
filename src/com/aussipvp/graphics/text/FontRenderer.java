package com.aussipvp.graphics.text;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.aussipvp.graphics.Screen;

public class FontRenderer {

	public int[] pixels;

	public int width;
	public int height;

	public SpriteSheet sheet;

	public FontRenderer(Screen screen) {
		this.width = screen.width;
		this.height = screen.height;
		this.sheet = new SpriteSheet("/textures/font.png");
		pixels = screen.pixels;
	}

	public void render(int xPos, int yPos, int tile, int colour, int scale) {
		int scaleMap = scale - 1;
		int xTile = tile % 8;
		int yTile = tile / 8;
		int tileOffset = (xTile << 3) + (yTile << 3) * sheet.width;
		System.out.println(tile + ", " + (xTile << 3) + ", " + (yTile << 3) + ", " + tileOffset);
		for (int y = 0; y < 8; y++) {
			int ySheet = y;
			int yPixel = y + yPos + (y * scaleMap) - ((scaleMap << 3) / 2);
			for (int x = 0; x < 8; x++) {
				int xSheet = x;
				int xPixel = x + xPos + (x * scaleMap) - ((scaleMap << 3) / 2);
				int col = (colour >> (sheet.pixels[xSheet + ySheet * sheet.width + tileOffset] * 8)) & 255;
				if (col < 255) {
					for (int yScale = 0; yScale < scale; yScale++) {
						if (yPixel + yScale < 0 || yPixel + yScale >= height) continue;
						for (int xScale = 0; xScale < scale; xScale++) {
							if (xPixel + xScale < 0 || xPixel + xScale >= width) continue;
							pixels[(xPixel + xScale) + (yPixel + yScale) * width] = col;
						}
					}
				}
			}
		}
	}

	class SpriteSheet {

		public String path;
		public int width;
		public int height;

		public int[] pixels;

		public SpriteSheet(String path) {
			BufferedImage image = null;
			try {
				image = ImageIO.read(SpriteSheet.class.getResourceAsStream(path));
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (image == null) { return; }

			this.path = path;
			this.width = image.getWidth();
			this.height = image.getHeight();

			pixels = image.getRGB(0, 0, width, height, null, 0, width);

			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = (pixels[i] & 0xff) / 64;
			}
		}
	}
}
