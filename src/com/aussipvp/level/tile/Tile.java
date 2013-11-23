package com.aussipvp.level.tile;

import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;

public class Tile {

	public int x, y;
	public Sprite sprite;

	public static Tile nothing = new VoidTile(Sprite.nothing);
	public static Tile grass = new GrassTile(Sprite.grass);
	public static Tile wall = new WallTile(Sprite.wall);
	public static Tile floor = new FloorTile(Sprite.floor);
	public static Tile gravel = new GravelTile(Sprite.gravel);
	public static Tile sand = new SandTile(Sprite.sand);

	public static final int col_grass = 0xFF00FF00;
	public static final int col_void = 0;
	public static final int col_wall = 0xFF783A12;
	public static final int col_floor = 0xFFC48738;
	public static final int col_gravel = 0xFF00FF00;
	public static final int col_sand = 0xFF808080;

	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}

	public void render(int xScroll, int yScroll, Screen screen) {

	}

	public boolean isSolid() {
		return false;
	}
}
