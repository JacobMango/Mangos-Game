package com.aussipvp.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.aussipvp.Game;
import com.aussipvp.entity.Entity;
import com.aussipvp.entity.mob.MultiPlayer;
import com.aussipvp.entity.mob.Player;
import com.aussipvp.entity.particles.Particle;
import com.aussipvp.entity.projectile.Projectile;
import com.aussipvp.graphics.Screen;
import com.aussipvp.level.tile.Tile;
import com.aussipvp.util.Vector2i;

public class Level {

	private int width;
	private int height;
	protected int[] tileInt;
	public int[] tiles;
	private Game game;
	protected Screen screen;

	private static List<Entity> entities = new ArrayList<Entity>();
	private static List<Projectile> projectiles = new ArrayList<Projectile>();
	private static List<Particle> particles = new ArrayList<Particle>();
	private static List<MultiPlayer> multiplayer = new ArrayList<MultiPlayer>();

	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost) return +1;
			if (n1.fCost > n0.fCost) return -1;
			return 0;
		}
	};

	private static List<Player> players = new ArrayList<Player>();

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

	public void init(Screen screen, Game game) {
		this.game = game;
		this.screen = screen;
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
		for (int i = 0; i < players.size(); i++) {
			players.get(i).update();
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
		for (int i = 0; i < players.size(); i++) {
			if (players.get(i).isRemoved()) players.remove(i);
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
		for (int i = 0; i < players.size(); i++) {
			players.get(i).render(screen);
		}
	}

	public void addPlayer(MultiPlayer e) {
		multiplayer.add(e);
	}

	public void add(Entity e) {
		e.init(this, screen, game);
		if (e instanceof Particle) {
			particles.add((Particle) e);
		} else if (e instanceof Projectile) {
			projectiles.add((Projectile) e);
		} else if (e instanceof Player) {
			players.add((Player) e);
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

	public Player getPlayerAt(int index) {
		return players.get(index);
	}

	public List<Player> getPlayers() {
		return players;
	}

	public Player getClientPlayer() {
		return players.get(0);
	}

	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node current = new Node(start, null, 0, getDistance(start, goal));
		openList.add(current);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			current = openList.get(0);
			if (current.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (current.parent != null) {
					path.add(current);
					current = current.parent;
				}
				openList.clear();
				closedList.clear();
				return path;
			}
			openList.remove(current);
			closedList.add(current);
			for (int i = 0; i < 9; i++) {
				if (i == 4) continue;
				int x = current.tile.getX();
				int y = current.tile.getY();
				int xi = (i % 3) - 1;
				int yi = (i / 3) - 1;
				Tile at = getTile(x + xi, y + yi);
				if (at == null) continue;
				if (at.isSolid()) continue;
				Vector2i a = new Vector2i(x + xi, y + yi);
				double gCost = current.gCost + (getDistance(current.tile, a) == 1 ? 0.95 : 1);;
				double hCost = getDistance(a, goal);
				Node node = new Node(a, current, gCost, hCost);
				if (vecInList(closedList, a) && gCost >= node.gCost) continue;
				if (!vecInList(openList, a) || gCost < node.gCost) openList.add(node);

			}
		}
		closedList.clear();
		return null;
	}

	private boolean vecInList(List<Node> list, Vector2i vec) {
		for (Node n : list) {
			if (n.tile.equals(vec)) return true;
		}
		return false;
	}

	private double getDistance(Vector2i tile, Vector2i goal) {
		double dx = tile.getX() - goal.getX();
		double dy = tile.getY() - goal.getY();
		double distance = Math.sqrt(dx * dx + dy * dy);
		double s = distance;// == 1 ? 0.95 : 1;
		return s;
	}

	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			int x = (int) entity.getX();
			int y = (int) entity.getY();

			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(entity);
		}
		return result;
	}

	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int ex = (int) e.getX();
		int ey = (int) e.getY();
		for (int i = 0; i < players.size(); i++) {
			Player p = players.get(i);
			int x = (int) p.getX();
			int y = (int) p.getY();

			int dx = Math.abs(x - ex);
			int dy = Math.abs(y - ey);
			double distance = Math.sqrt((dx * dx) + (dy * dy));
			if (distance <= radius) result.add(p);
		}
		return result;
	}

	/*
	 * public MultiPlayer getPlayer(String userName) { for (int i = 0; i <
	 * multiplayer.size(); i++) { if
	 * (multiplayer.get(i).getUsername().equals(userName)) { return
	 * multiplayer.get(i); } } return null; }
	 * 
	 * public void movePlayer(String userName, int x, int y, boolean moving, int
	 * dir) { getPlayer(userName).setLocation(x, y, moving, dir); }
	 */
}
