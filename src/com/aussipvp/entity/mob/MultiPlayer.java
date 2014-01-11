package com.aussipvp.entity.mob;

import com.aussipvp.graphics.AnimatedSprite;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.SpriteSheet;

public class MultiPlayer extends Mob {

	private AnimatedSprite test = new AnimatedSprite(SpriteSheet.player_down, 32, 32, 2);
	private int x;
	private int y;
	private boolean moving = false;
	private int dir = 0;
	private String userName = "";
	
	public MultiPlayer(int x, int y, String userName) {
		this.x = x;
		this.y = y;
		setUsername(userName);
	}

	public void setUsername(String userName) {
		this.userName = userName;
	}
	
	public void update() {
		
	}

	public void render(Screen screen) {
		screen.renderMob(x, y, test, 0);
	}

	public String getUsername() {
		return userName;
	}

	public void setLocation(int x, int y, boolean moving, int dir) {
		this.x = x;
		this.y = y;
		this.moving = moving;
		this.dir = dir;
	}

}
