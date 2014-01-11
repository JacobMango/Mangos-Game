package com.aussipvp.entity;

import java.util.Random;

import com.aussipvp.Game;
import com.aussipvp.Location;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.level.Level;

public abstract class Entity {

	protected double x, y;
	protected Sprite sprite;
	private boolean removed = false;
	protected Level level;
	protected Location location;
	protected Game game;
	protected Screen screen;
	protected final Random random = new Random();
	private static Game gameStatic;

	public Entity() {
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	

	public Sprite getSprite() {
	    return sprite;
    }
	
	public void update() {
	}

	public void render(Screen screen) {
		if (sprite != null) screen.renderSprite((int)x, (int)y, sprite, true);
	}

	public void remove() {
		removed = true;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void init(Level level, Screen screen, Game game) {
		this.level = level;
		this.screen = screen;
		this.game = game;
		this.gameStatic = this.game;
	}

	public static Game getGameClass() {
	    return gameStatic;
    }
	
	public Location getLocation() {
		return location;
	}
}
