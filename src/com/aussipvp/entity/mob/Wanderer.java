package com.aussipvp.entity.mob;

import com.aussipvp.graphics.AnimatedSprite;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.SpriteSheet;

public class Wanderer extends Mob {

	public Wanderer(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		//sprite = new AnimatedSprite(SpriteSheet.player_down, x, y, 32);
	}
	
	public void update() {
		
	}

	public void render(Screen screen) {
		//screen.renderMob((int) x,(int) y, sprite, 0);
	}

}
