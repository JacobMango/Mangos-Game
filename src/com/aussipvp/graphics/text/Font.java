package com.aussipvp.graphics.text;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.aussipvp.Game;
import com.aussipvp.graphics.SpriteSheet;

public class Font {

	public int width, height;
	public int[] pixels;
	
	public Font(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
    }
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void render(int xp, int yp, SpriteSheet sheet, int colour, Effects effect) {
		if (effect == Effects.ITALICS) {
			xp = xp - 30;
			for (int y = 0; y < sheet.HEIGHT; y++) {
				int ya = y + yp;
				for (int x = 0; x < sheet.WIDTH; x++) {
					int xa = x + xp;
					if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
					int col = sheet.pixels[x + y * sheet.WIDTH];
					if (col == 0xFFFFFFFF) continue;
					if (col == 0xFF000000) col = colour;
					if (effect == Effects.ITALICS) {
						width = width + 1;
					}
					pixels[xa + ya * (width)] = col;
				}
			}
		} else if (effect == Effects.BOLD) {
			for (int y = 0; y < sheet.HEIGHT; y++) {
				int ya = y + yp;
				for (int x = 0; x < sheet.WIDTH; x++) {
					int xa = x + xp;
					if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
					int col = sheet.pixels[x + y * sheet.WIDTH];
					if (col == 0xFFFFFFFF) continue;
					if (col == 0xFF000000) col = colour;
					pixels[xa + ya * width] = col;
				}
			}
		} else if (effect == Effects.NONE) {
			for (int y = 0; y < sheet.HEIGHT; y++) {
				int ya = y + yp;
				for (int x = 0; x < sheet.WIDTH; x++) {
					int xa = x + xp;
					if (xa < 0 || xa >= width || ya < 0 || ya >= height) continue;
					int col = sheet.pixels[x + y * sheet.WIDTH];
					if (col == 0xFFFFFFFF) continue;
					if (col == 0xFF000000) col = colour;
					pixels[xa + ya * width] = col;
				}
			}
		}
	}
	
	private String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789              " + "abcdefghijklmnopqrstuvwxyz.!?#@$%^&*()-+=:         ";

	public enum Effects {
		NONE, ITALICS, BOLD;
	}

	public int getStringWidth(String s) {
		return s.length() * 12;
	}

	public void render(String msg, int x, int y, int col, Effects effect) {
		int length = msg.length();
		for (int i = 0; i < length; i++) {
			int c = letters.indexOf(msg.charAt(i));
			if (c < 0) continue;
			int xp = (c >= 50) ? c - 50 : c;
			int yp = (c >= 50) ? 1 : 0;
			int width = 1;
			int height = 1;
			int size = 12;
			SpriteSheet spriteSheet = new SpriteSheet(SpriteSheet.font, xp, yp, width, height, size);
			if (col == 0) col = 0xFFFFFFFF;
			render(x, y, spriteSheet, col, effect);
			String l = "" + letters.charAt(c);
			if (l.equals(" ")) size -= 7;
			else if (l.equals("a")) size -= 6;
			else if (l.equals("b")) size -= 6;
			else if (l.equals("c")) size -= 7;
			else if (l.equals("d")) size -= 6;
			else if (l.equals("e")) size -= 7;
			else if (l.equals("f")) size -= 6;
			else if (l.equals("g")) size -= 6;
			else if (l.equals("h")) size -= 6;
			else if (l.equals("i")) size -= 11;
			else if (l.equals("j")) size -= 8;
			else if (l.equals("k")) size -= 8;
			else if (l.equals("l")) size -= 9;
			else if (l.equals("m")) size -= 5;
			else if (l.equals("n")) size -= 9;
			else if (l.equals("o")) size -= 8;
			else if (l.equals("p")) size -= 7;
			else if (l.equals("q")) size -= 6;
			else if (l.equals("r")) size -= 7;
			else if (l.equals("s")) size -= 7;
			else if (l.equals("t")) size -= 9;
			else if (l.equals("u")) size -= 6;
			else if (l.equals("v")) size -= 7;
			else if (l.equals("w")) size -= 5;
			else if (l.equals("x")) size -= 6;
			else if (l.equals("y")) size -= 7;
			else if (l.equals("z")) size -= 7;
			else if (l.equals("A")) size -= 5;
			else if (l.equals("B")) size -= 6;
			else if (l.equals("C")) size -= 5;
			else if (l.equals("D")) size -= 5;
			else if (l.equals("E")) size -= 5;
			else if (l.equals("F")) size -= 6;
			else if (l.equals("G")) size -= 6;
			else if (l.equals("H")) size -= 6;
			else if (l.equals("I")) size -= 11;
			else if (l.equals("J")) size -= 8;
			else if (l.equals("K")) size -= 8;
			else if (l.equals("L")) size -= 7;
			else if (l.equals("M")) size -= 5;
			else if (l.equals("N")) size -= 8;
			else if (l.equals("O")) size -= 8;
			else if (l.equals("P")) size -= 6;
			else if (l.equals("Q")) size -= 6;
			else if (l.equals("R")) size -= 7;
			else if (l.equals("S")) size -= 7;
			else if (l.equals("T")) size -= 9;
			else if (l.equals("U")) size -= 4;
			else if (l.equals("V")) size -= 7;
			else if (l.equals("W")) size -= 5;
			else if (l.equals("X")) size -= 4;
			else if (l.equals("Y")) size -= 7;
			else if (l.equals("Z")) size -= 7;
			else if (l.equals("0")) size -= 6;
			else if (l.equals("1")) size -= 11;
			else if (l.equals("2")) size -= 6;
			else if (l.equals("3")) size -= 6;
			else if (l.equals("4")) size -= 6;
			else if (l.equals("5")) size -= 6;
			else if (l.equals("6")) size -= 6;
			else if (l.equals("7")) size -= 6;
			else if (l.equals("8")) size -= 6;
			else if (l.equals("9")) size -= 6;
			else if (l.equals(".")) size -= 12;
			else if (l.equals("!")) size -= 10;
			else if (l.equals("?")) size -= 7;
			else if (l.equals("#")) size -= 4;
			else if (l.equals("@")) size -= 4;
			else if (l.equals("$")) size -= 6;
			else if (l.equals("%")) size -= 4;
			else if (l.equals("^")) size -= 7;
			else if (l.equals("&")) size -= 6;
			else if (l.equals("*")) size -= 9;
			else if (l.equals("(")) size -= 8;
			else if (l.equals(")")) size -= 8;
			else if (l.equals("-")) size -= 7;
			else if (l.equals("+")) size -= 7;
			else if (l.equals("=")) size -= 7;
			else if (l.equals(":")) size -= 11;
			x += size + 1;
		}
	}

	public void render(Graphics2D g, int[] textPixels, BufferedImage noScaleImage, Game game) {
		for (int i = 0; i < textPixels.length; i++) {
			textPixels[i] = pixels[i];
		}
		g.drawImage(noScaleImage, 0, 0, game.getWidth(), game.getHeight(), null);
    }
}
