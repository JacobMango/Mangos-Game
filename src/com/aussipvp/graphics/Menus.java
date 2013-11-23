package com.aussipvp.graphics;

import com.aussipvp.Game;
import com.aussipvp.input.Mouse;

public class Menus {

	public void mainMenu(Screen screen) {
		screen.renderSprite(0, 0, new Sprite(300, 300, 0xFF002241), false);
		Sprite noclick = new Sprite(100, 10, 0xFF635B02);
		Sprite hover = new Sprite(100, 10, 0xFF03758C);
		Sprite yesclick = new Sprite(100, 10, 0xFF03758C);

		int x = (((300 * 3) / 2) - 250) / 2;
		int y = 90;

		if (Mouse.getButton() == 0 || Mouse.getButton() == -1 || Mouse.getButton() == 2 || Mouse.getButton() == 3) screen.renderSprite(x, y - 1, noclick, false);
		else if (Mouse.getX() > 300 && Mouse.getX() < 600 && Mouse.getY() > 260 && Mouse.getY() < 295) {
			screen.renderSprite(x, y, hover, false);
			if (Mouse.getButton() == 1) {
				screen.renderSprite(x, y, yesclick, false);
				Game.loading = true;
				Game.menuEnable = false;
			}
		} else screen.renderSprite(x, y - 1, noclick, false);
	}
}
