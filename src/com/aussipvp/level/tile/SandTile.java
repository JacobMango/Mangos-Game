package com.aussipvp.level.tile;

import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;

public class SandTile extends Tile {

	public SandTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}

}