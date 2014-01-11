package com.aussipvp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.aussipvp.entity.mob.BasicMob;
import com.aussipvp.entity.mob.Mob;
import com.aussipvp.entity.mob.MultiPlayer;
import com.aussipvp.entity.mob.Player;
import com.aussipvp.graphics.gui.Chat;
import com.aussipvp.graphics.text.Font;
import com.aussipvp.graphics.text.FontRenderer;
import com.aussipvp.graphics.Colours;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.graphics.SpriteSheet;
import com.aussipvp.graphics.gui.Gui;
import com.aussipvp.graphics.gui.GuiInit;
import com.aussipvp.graphics.gui.InGame;
import com.aussipvp.graphics.gui.Menu;
import com.aussipvp.input.Keyboard;
import com.aussipvp.input.Mouse;
import com.aussipvp.level.Level;
import com.aussipvp.level.SpawnLevel;
import com.aussipvp.level.TileCoord;
import com.aussipvp.server.Connection;

public class Game extends Canvas implements Runnable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Screen resolution.
	 */
	public static int width = 300;
	private static int height = width / 16 * 9;
	private static int scale = 3;

	public static int w = width * scale;
	public static int h = height * scale;

	/**
	 * game threads and graphics.
	 */
	private Thread thread;
	public JFrame frame;
	private Keyboard key;
	public Level level;
	private Mob mob;
	private Launcher launcher;
	public Player player;
	private Menu menu;
	private InGame ingame;
	private Font font;
	private FontRenderer fr;
	private Chat chat;
	private Connection connection;
	private boolean running = false;
	public boolean f3menu = false;
	public static boolean dead = false;
	public static double angle;

	public String userName = "Jacob";

	public int zoom = 1;

	public int fps = 0;
	public int ups = 0;

	public static String TITLE = "Mangos-Game";

	public Screen screen;

	public static boolean DEBUG = false;

	public static boolean menuEnable = true;
	public static boolean loading = false;
	public static boolean game = false;
	public static boolean pauseMenu = false;

	/**
	 * Final image
	 */
	public BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	public int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

	public Graphics2D g;

	public static int getWindowWidth() {
		return width * scale;
	}

	public static int getWindowHeight() {
		return height * scale;
	}

	/**
	 * Constructor
	 */
	public Game() {
		Dimension size = new Dimension(getWindowWidth(), getWindowHeight());
		this.setPreferredSize(size);
		screen = new Screen(width, height);
		key = new Keyboard();
		frame = new JFrame();
		level = level.spawn;
		level.init(screen, this);
		level.add(player = new Player(new Location(4, 3, "spawn"), key));
		player.init(level, screen, this);
		font = new Font(screen);
		fr = new FontRenderer(screen);
		this.addKeyListener(key);
		Mouse mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);

		/*
		 * new GuiInit().init(this); menu = new Menu(); ingame = new InGame();
		 */
	}

	/**
	 * Start method
	 */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Game");
		thread.start();
	}

	/**
	 * Stop method
	 * 
	 * @throws InterruptedException
	 */
	public synchronized void stop() {
		try {
			running = false;
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Run method
	 */
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		int updates = 0;
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames;
				ups = updates;
				frames = 0;
				updates = 0;
			}
		}
		if (running == false) stop();
	}

	/**
	 * Update method
	 */
	public void update() {
		key.update();
		level.update();
		screen = new Screen(width, height);
		if (key.f3 == true) {
			if (f3menu == true) {
				f3menu = false;
			} else {
				f3menu = true;
			}
		}
		if (key.escape) {
			if (pauseMenu == true) {
				pauseMenu = false;
				game = true;
			} else {
				pauseMenu = true;
			}
		}
	}

	private int time = 0;
	private int rate = 60;

	/**
	 * Render method
	 */
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		this.g = g;

		if (menuEnable) {
			Sprite s = new Sprite(100, 35, 0xFF002241);
			game = false;
			screen.clear();
			screen.renderSprite(0, 0, new Sprite(300, 300, 0xFF002241), false);

			int x = (((300 * scale) / 2) - 250) / 2;
			int y = 90;

			screen.renderSprite(x, y, s, false);
			if (Mouse.getX() > x && Mouse.getX() < 400 * scale && Mouse.getY() > y && Mouse.getY() < 175 * scale) {
				screen.renderSprite(x, y, s, false);
				if (Mouse.getButton() == 1) {
					screen.renderSprite(x, y, s, false);
					System.out.println("IT WORKS HERE!");
					menuEnable = false;
					loading = true;
				}
			} else {
				screen.renderSprite(x, y, s, false);
			}

			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		} else if (loading) {
			game = false;
			screen.clear();
			screen.renderSprite(0, 0, new Sprite(300, 300, 0xFF002241), false);
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.setColor(new Color(0xFF993311));
			// g.setFont(new Font("Calibra", 1, 50));
			g.drawString("Loading...", 325, 200);
			new Timer().schedule(new TimerTask() {

				@Override
				public void run() {
					Game.loading = false;
					Game.game = true;
				}
			}, 100);
		} else if (dead) {
			game = false;
			screen.clear();
			int xScroll = (int) (player.getX() - screen.width / 2);
			int yScroll = (int) (player.getY() - screen.height / 2);

			if (time % (rate * 600) == 0) {}

			if (xScroll >= 210) {
				xScroll = 210;
			} else if (xScroll <= 0) {
				xScroll = 0;
			}
			if (yScroll >= 350) {
				yScroll = 350;
			} else if (yScroll <= 0) {
				yScroll = 0;
			}

			level.render(xScroll, yScroll, screen);

			Sprite s = new Sprite(100, 35, 0xFF002241);
			int x = (((300 * scale) / 2) - 250) / 2;
			int y = 90;
			screen.renderSprite(x, y, s, false);
			if (Mouse.getX() > x && Mouse.getX() < 400 * scale && Mouse.getY() > y && Mouse.getY() < 175 * scale) {
				screen.renderSprite(x, y, s, false);
				if (Mouse.getButton() == 1) {
					screen.renderSprite(x, y, s, false);
					System.out.println("IT WORKS HERE!");
					dead = false;
					game = true;
					player.remove();
					level.add(player);
				}
			} else {
				screen.renderSprite(x, y, s, false);
			}

			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.setColor(new Color(45, 45, 45, 200));
			g.fillRect(0, 0, w, h);
		} else if (pauseMenu) {
			screen.clear();
			int xScroll = (int) (player.getX() - screen.width / 2);
			int yScroll = (int) (player.getY() - screen.height / 2);

			if (time % (rate) == 0) {
				if (xScroll >= 210) {
					xScroll = 210;
				} else if (xScroll <= 0) {
					xScroll = 0;
				}
				if (yScroll >= 350) {
					yScroll = 350;
				} else if (yScroll <= 0) {
					yScroll = 0;
				}
			}
			level.render(xScroll, yScroll, screen);

			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.setColor(new Color(45, 45, 45, 200));
			g.fillRect(0, 0, w, h);
			game = false;
			if (Mouse.getButton() == 1) {
				menuEnable = true;
				pauseMenu = false;
			}
		} else if (game) {
			// ingame.render();
			Graphics2D graphics2d = g;
			screen.clear();
			int xScroll = (int) (player.getX() - screen.width / 2) + 16;
			int yScroll = (int) (player.getY() - screen.height / 2) + 16;



			if (xScroll >= 210) {
				xScroll = 210;
			} else if (xScroll <= 0) {
				xScroll = 0;
			}
			if (yScroll >= 350) {
				yScroll = 350;
			} else if (yScroll <= 0) {
				yScroll = 0;
			}
			level.render(xScroll, yScroll, screen);

			font.render("TEST", 50, 50);
			
			screen.renderSprite(10, 10, new Sprite(8, 0, 0, SpriteSheet.font), false);
			
			Sprite sprite = null;
			Sprite noh = new Sprite(200, 10, 0xFF808080);
			if (player.getHealth() <= 0) {
				sprite = new Sprite(0, 0, 0);
			} else if (player.getHealth() == 1) {
				sprite = new Sprite(10, 10, 0xFFFF0000);
			} else if (player.getHealth() == 2) {
				sprite = new Sprite(20, 10, 0xFFFF0000);
			} else if (player.getHealth() == 3) {
				sprite = new Sprite(30, 10, 0xFFFF0000);
			} else if (player.getHealth() == 4) {
				sprite = new Sprite(40, 10, 0xFFFF0000);
			} else if (player.getHealth() == 5) {
				sprite = new Sprite(50, 10, 0xFFFF0000);
			} else if (player.getHealth() == 6) {
				sprite = new Sprite(60, 10, 0xFFFF0000);
			} else if (player.getHealth() == 7) {
				sprite = new Sprite(70, 10, 0xFFFF0000);
			} else if (player.getHealth() == 8) {
				sprite = new Sprite(80, 10, 0xFFFF0000);
			} else if (player.getHealth() == 9) {
				sprite = new Sprite(90, 10, 0xFFFF0000);
			} else if (player.getHealth() == 10) {
				sprite = new Sprite(100, 10, 0xFFFF0000);
			} else if (player.getHealth() == 11) {
				sprite = new Sprite(110, 10, 0xFFFF0000);
			} else if (player.getHealth() == 12) {
				sprite = new Sprite(120, 10, 0xFFFF0000);
			} else if (player.getHealth() == 13) {
				sprite = new Sprite(130, 10, 0xFFFF0000);
			} else if (player.getHealth() == 14) {
				sprite = new Sprite(140, 10, 0xFFFF0000);
			} else if (player.getHealth() == 15) {
				sprite = new Sprite(150, 10, 0xFFFF0000);
			} else if (player.getHealth() == 16) {
				sprite = new Sprite(160, 10, 0xFFFF0000);
			} else if (player.getHealth() == 17) {
				sprite = new Sprite(170, 10, 0xFFFF0000);
			} else if (player.getHealth() == 18) {
				sprite = new Sprite(180, 10, 0xFFFF0000);
			} else if (player.getHealth() == 19) {
				sprite = new Sprite(190, 10, 0xFFFF0000);
			} else if (player.getHealth() == 20) {
				sprite = new Sprite(200, 10, 0xFFFF0000);
			}
			screen.renderSprite((width - 200) / 2, 0, noh, false);
			screen.renderSprite((width - 200) / 2, 0, sprite, false);

			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}

			graphics2d.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			if (f3menu) {
				int sysy = 15;
				graphics2d.setColor(Color.WHITE);
				// graphics2d.setFont(new Font("Calibra", 0, 15));
				graphics2d.drawString("FPS: " + fps, 5, sysy);
				graphics2d.drawString("UPS: " + ups, 5, sysy = sysy + 15);
				graphics2d.drawString("X: " + ((double) player.getX() / 16), 5, sysy = sysy + 15);
				graphics2d.drawString("Y: " + ((double) player.getY() / 16), 5, sysy = sysy + 15);
				graphics2d.drawString(String.format("Angle: %.2f", new Object[] { Double.valueOf(angle) }), 5, sysy = sysy + 15);
				graphics2d.drawString("Projectiles: " + Level.getProjectiles().size(), 5, sysy = sysy + 15);
				graphics2d.drawString("Button: " + Mouse.getButton(), 5, sysy = sysy + 15);
			}
		}
		g.dispose();
		bs.show();
	}
}
