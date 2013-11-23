package com.aussipvp.entity;

import java.util.Random;

import com.aussipvp.graphics.Screen;
import com.aussipvp.level.Level;

public abstract class Entity {

	public double x, y;
	private boolean removed = false;
	protected Level level;
	protected Screen screen;
	protected final Random random = new Random();

	public void update() {
	}

	public void render(Screen screen) {
	}

	public void remove() {
		removed = true;
	}

	public boolean isRemoved() {
		return removed;
	}

	public void init(Level level, Screen screen) {
		this.level = level;
		this.screen = screen;
	}
}
