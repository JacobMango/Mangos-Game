package com.aussipvp.graphics;

import java.awt.Color;

import com.aussipvp.Game;
import com.aussipvp.input.Mouse;

public class Button {

	private Screen screen;
	private Game game;
	private int w, h, x, y, type;

	public int width = 0;
	public int height = 0;
	public Sprite nc;
	public Sprite hc;
	public Sprite yc;

	public Button(Screen s, int type, Game game, int oX, int oY, int w, int h) {
		this.screen = s;
		this.game = game;
		this.x = oX;
		this.y = oY;
		this.w = w;
		this.h = h;
		this.width = x + w;
		this.height = y + h;
		this.type = type;
		//this.nc = new Sprite(100, 35, 0xFF002241);
		//this.yc = new Sprite(100, 35, 0xFF002241);
		//this.hc = new Sprite(100, 35, 0xFF002241);
	}

	public void render() {
		int x = (((300 * 3) / 2) - 250) / 2;
		int y = 90;
		screen.renderSprite(x, y, new Sprite(100, 35, 0xFF002241), false);
		if (Mouse.getX() > x && Mouse.getX() < width && Mouse.getY() > y && Mouse.getY() < height) {
			screen.renderSprite(x, y, new Sprite(100, 35, 0xFF002241), false);
			if (Mouse.getButton() == 1) {
				screen.renderSprite(x, y, new Sprite(100, 35, 0xFF002241), false);
				System.out.println("IT WORKS HERE!");
				update();
			}
		} else {
			screen.renderSprite(x, y, new Sprite(100, 35, 0xFF002241), false);
		}
	}

	public void update() {
		if (type == 0) {
			game.menuEnable = false;
			game.game = true;
			System.out.println("AND HERE TOO!\n\r" + game.game + game.menuEnable);
		} else return;
	}
}
