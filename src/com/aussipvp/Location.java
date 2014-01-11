package com.aussipvp;

public class Location {
	
	private int x;
	private int y;
	
	public Location(int x, int y, String world) {
		world = null;
		this.x = x << 4;
		this.y = y << 4;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
}
