package com.aussipvp.graphics.gui;

import java.awt.Graphics2D;
import java.awt.Image;

import com.aussipvp.Game;
import com.aussipvp.graphics.Button;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.input.Mouse;

public class Menu extends Gui {

	public Menu() {
	    super();
    }
	
	public void render() {
		Sprite s = new Sprite(100, 35, 0xFF002241);
		game.game = false;
		screen.clear();
		screen.renderSprite(0, 0, new Sprite(300, 300, 0xFF002241), false);

		int x = (((300 * 3) / 2) - 250) / 2;
		int y = 90;
		
		screen.renderSprite(x, y, s, false);
		if (Mouse.getX() > x && Mouse.getX() < 400 && Mouse.getY() > y && Mouse.getY() < 175) {
			screen.renderSprite(x, y, s, false);
			if (Mouse.getButton() == 1) {
				screen.renderSprite(x, y, s, false);
				System.out.println("IT WORKS HERE!");
				game.menuEnable = false;
				game.game = true;
			}
		} else {
			screen.renderSprite(x, y, s, false);
		}

		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		graphics2d.drawImage(image, 0, 0, game.getWidth(), game.getHeight(), null);
	}

}
