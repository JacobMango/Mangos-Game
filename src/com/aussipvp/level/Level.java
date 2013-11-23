package com.aussipvp.level;

import java.util.ArrayList;
import java.util.List;

import com.aussipvp.entity.Entity;
import com.aussipvp.entity.mob.MultiPlayer;
import com.aussipvp.entity.particles.Particle;
import com.aussipvp.entity.projectile.Projectile;
import com.aussipvp.graphics.Screen;
import com.aussipvp.level.tile.Tile;

public class Level {

	private int width;
	private int height;
	protected int[] tileInt;
	public int[] tiles;
	protected Screen screen;

	private static List<Entity> entities = new ArrayList<Entity>();
	private static List<Projectile> projectiles = new ArrayList<Projectile>();
	private static List<Particle> particles = new ArrayList<Particle>();
	private static List<MultiPlayer> multiplayer = new ArrayList<MultiPlayer>();

	public static Level spawn = new SpawnLevel("/levels/testspawn.png");

	public Level(String path) {
		this.tileInt = new int[getWidth() * getHeight()];
		loadLevel(path);
		generateLevel();
	}

	protected void generateLevel() {

	}

	protected void loadLevel(String path) {

	}

	public void init(Screen screen) {

	}

	public void update() {
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).update();
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).update();
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).update();
		}
		remove();
	}

	private void remove() {
		for (int i = 0; i < entities.size(); i++) {
			if (entities.get(i).isRemoved()) entities.remove(i);
		}
		for (int i = 0; i < projectiles.size(); i++) {
			if (projectiles.get(i).isRemoved()) projectiles.remove(i);
		}
		for (int i = 0; i < particles.size(); i++) {
			if (particles.get(i).isRemoved()) particles.remove(i);
		}
	}

	public static List<Projectile> getProjectiles() {
		return projectiles;
	}

	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				this.getTile(x, y).render(x, y, screen);
			}
		}
		for (int i = 0; i < projectiles.size(); i++) {
			projectiles.get(i).render(screen);
		}
		for (int i = 0; i < entities.size(); i++) {
			entities.get(i).render(screen);
		}
		for (int i = 0; i < particles.size(); i++) {
			particles.get(i).render(screen);
		}
	}

	public void addPlayer(MultiPlayer e) {
		multiplayer.add(e);
	}

	public void add(Entity e) {
		e.init(this, screen);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else {
			entities.add(e);
		}
	}

	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		for (int c = 0; c < 4; c++) {
			int xt = (x - c % 2 * size - xOffset) >> 4;
			int yt = (y - c / 2 * size + yOffset) >> 4;
			if (getTile(xt, yt).isSolid()) solid = true;
		}
		return solid;
	}

	// Grass = 0xFF00FF00
	// Stone = 0xFFFFFF00
	// Flower = 0xFF808080
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= getWidth() || y >= getHeight()) return Tile.nothing;
		if (getTiles()[x + y * getWidth()] == Tile.col_grass) return Tile.grass;
		if (getTiles()[x + y * getWidth()] == Tile.col_wall) return Tile.wall;
		if (getTiles()[x + y * getWidth()] == Tile.col_floor) return Tile.floor;
		if (getTiles()[x + y * getWidth()] == Tile.col_sand) return Tile.sand;
		if (getTiles()[x + y * getWidth()] == Tile.col_gravel) return Tile.gravel;
		return Tile.nothing;
	}

	public int[] getTiles() {
		return tiles;
	}

	public void setTiles(int[] tiles) {
		this.tiles = tiles;
	}

	public int getWidth() {
		return width;
	}

	public int setWidth(int width) {
		this.width = width;
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int setHeight(int height) {
		this.height = height;
		return height;
	}

	public MultiPlayer getPlayer(String userName) {
		for (int i = 0; i < multiplayer.size(); i++) {
			if (multiplayer.get(i).getUsername().equals(userName)) {
				return multiplayer.get(i);
			}
		}
		return null;
	}
	
	public void movePlayer(String userName, int x, int y, boolean moving, int dir) {
		getPlayer(userName).setLocation(x, y, moving, dir);
	}
}
