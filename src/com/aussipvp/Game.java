package com.aussipvp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

import com.aussipvp.entity.mob.Mob;
import com.aussipvp.entity.mob.MultiPlayer;
import com.aussipvp.entity.mob.Player;
import com.aussipvp.graphics.Chat;
import com.aussipvp.graphics.Menus;
import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.graphics.SpriteSheet;
import com.aussipvp.input.Keyboard;
import com.aussipvp.input.Mouse;
import com.aussipvp.level.Level;
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
	private static int width = 300;
	private static int height = width / 16 * 9;
	private static int scale = 3;

	public static int w = width * scale;
	public static int h = height * scale;

	/**
	 * game threads and graphics.
	 */
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	public Level level;
	private Mob mob;
	private Player player;
	private MultiPlayer multiplayer;
	private Menus menu;
	public static boolean menuEnable = true;
	public static boolean loading = false;
	public static boolean game = false;
	public static boolean pauseMenu = false;
	private Chat chat;
	private Connection connection;
	private boolean running = false;
	private boolean f3menu = false;
	public static boolean dead = false;
	public static double angle;
	
	public String userName = "Jacob";

	public int zoom = 1;

	public int fps = 0;
	public int ups = 0;

	public static String TITLE = "2D Game";

	private Screen screen;

	public boolean DEBUG = false;

	/**
	 * Final image
	 */
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();

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
		Dimension size = new Dimension(width * scale, height * scale);
		this.setPreferredSize(size);
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		level = level.spawn;
		TileCoord pSpawn = new TileCoord(12, 12);
		player = new Player(pSpawn.getX(), pSpawn.getY(), key);
		player.init(level, screen);
		level.init(screen);
		menu = new Menus();
		chat = new Chat();
		chat.init(key);
		this.addKeyListener(key);
		Mouse mouse = new Mouse();
		this.addMouseListener(mouse);
		this.addMouseMotionListener(mouse);
	}

	public void init(MultiPlayer multiplayer) {
		this.multiplayer = multiplayer;
	}
	
	/**
	 * Start method
	 */
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
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
		int slowupdates = 0;
		while (running == true) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1) {
				update();
				updates++;
				delta--;
			}
			while (delta >= 100) {
				slowUpdate();
				slowupdates++;
			}
			render();
			frames++;

			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				fps = frames;
				ups = updates;
				frames = 0;
				updates = 0;
				slowupdates = 0;
			}
		}
		stop();
	}

	private void slowUpdate() {

	}

	/**
	 * Update method
	 */
	public void update() {
		key.update();
		player.update();
		level.update();
		multiplayer.update();
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
		// connection = new Connection();
		// connection.update();
		/*if (game) {
			int x = frame.getX();
			int y = frame.getY();
			try {
				Robot robot = new Robot();
				robot.mouseMove(x + (w / 2), y + (h / 2));
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
		}*/
	}

	/**
	 * Render method
	 */
	@SuppressWarnings("static-access")
	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			this.createBufferStrategy(3);
			return;
		}
		Graphics g = bs.getDrawGraphics();

		if (menuEnable) {
			game = false;
			screen.clear();
			menu.mainMenu(screen);
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
			g.setFont(new Font("Calibra", 1, 50));
			g.drawString("Loading...", 325, 200);
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					Game.loading = false;
					Game.game = true;
				}
			}, 100);
		} else if (dead) {
			screen.clear();
			int xScroll = (int) (player.x - screen.width / 2);
			int yScroll = (int) (player.y - screen.height / 2);
			level.render(xScroll, yScroll, screen);
			player.render(screen);
			for (int i = 0; i < pixels.length; i++) {
				pixels[i] = screen.pixels[i];
			}
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			g.setColor(new Color(45, 45, 45, 200));
			g.fillRect(0, 0, w, h);
			game = false;
		} else if (pauseMenu) {
			screen.clear();
			int xScroll = (int) (player.x - screen.width / 2);
			int yScroll = (int) (player.y - screen.height / 2);
			level.render(xScroll, yScroll, screen);
			player.render(screen);
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
			Cursor cursor = null;
			frame.getContentPane().setCursor(cursor.getDefaultCursor());
		} else if (game) {
			screen.clear();
			int xScroll = (int) (player.x - screen.width / 2);
			int yScroll = (int) (player.y - screen.height / 2);
			level.render(xScroll, yScroll, screen);
			player.render(screen);
			Sprite sprite = null;
			Sprite noh = new Sprite(200, 10, 0xFF808080);
			screen.renderSheet(40, 40, SpriteSheet.player_down, false);
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

			multiplayer.render(screen);
			
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
			if (f3menu) {
				int sysy = 15;
				g.setColor(Color.WHITE);
				g.setFont(new Font("Calibra", 0, 15));
				g.drawString("FPS: " + fps, 5, sysy);
				g.drawString("UPS: " + ups, 5, sysy = sysy + 15);
				g.drawString("X: " + ((double) player.x / 16), 5, sysy = sysy + 15);
				g.drawString("Y: " + ((double) player.y / 16), 5, sysy = sysy + 15);
				g.drawString("Angle: " + angle, 5, sysy = sysy + 15);
				g.drawString("Projectiles: " + Level.getProjectiles().size(), 5, sysy = sysy + 15);
				g.drawString("Button: " + Mouse.getButton(), 5, sysy = sysy + 15);
			}
			BufferedImage c = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
			Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(c, new Point(10, 10), "blank");
			frame.getContentPane().setCursor(cursor);
			int mx = Mouse.getX(), my = Mouse.getY();
			screen.renderSprite(mx, my, new Sprite(mx, my, 0xFFFF0000), false);
		}
		g.dispose();
		bs.show();
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(Game.TITLE);
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);

		game.start();
	}
}
