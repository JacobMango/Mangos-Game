package com.aussipvp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.security.auth.login.Configuration;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;

import com.aussipvp.input.Keyboard;
import com.aussipvp.input.Mouse;

public class Launcher extends JFrame implements Runnable {
	private static final long serialVersionUID = 1L;

	protected JPanel window = new JPanel();
	protected JFrame frame = new JFrame();
	// private JButton play, options, help, quit;

	private int width = 600;
	private int height = 300;

	protected int button_width = 80;
	protected int button_height = 30;
	boolean running = false;
	Thread thread;
	Keyboard kb;
	Mouse m;
	Image image;

	public Launcher() {
		kb = new Keyboard();
		m = new Mouse();

		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		frame.setUndecorated(true);
		frame.setTitle("Minefront Lanucher");
		frame.setSize(new Dimension(width, height));
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// getContentPane().add(window);
		// add(this);
		frame.setLocationRelativeTo(null);

		frame.setResizable(false);
		frame.setVisible(true);
		window.setLayout(null);

		frame.addKeyListener(kb);
		frame.addFocusListener(m);
		frame.addMouseListener(m);
		frame.addMouseMotionListener(m);
		startMenu();

		repaint();

	}

	public void updateFrame() {
		if (Mouse.dragged) {
			Point p = frame.getLocation();
			frame.setLocation(p.x + Mouse.mouseX - Mouse.mouseY, p.y + Mouse.mousePX - Mouse.mousePY);
		}
	}

	private void renderMenu() throws IllegalStateException {
		BufferStrategy bs = frame.getBufferStrategy();
		if (bs == null) {
			frame.createBufferStrategy(3);
			return;
		}

		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, 600, 300);
		try {
			g.drawImage(ImageIO.read(Game.class.getResource("/launcher/launcher.png")), 0, 0, 600, 300, null);

			if (Mouse.mouseX > 475 && Mouse.mouseX < 475 + 102 && Mouse.mouseY > 70 && Mouse.mouseY < 70 + 35) {

				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/button_play_on.png")), 479, 70, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/arrow.png")), 562, 75, 22, 20, null);
				if (Mouse.mouseB == 1) {
					frame.dispose();
					try {
						stopMenu();
						new Game(this);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}

			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/button_play.png")), 480, 70, 80, 30, null);
			}
			if (Mouse.mouseX > 482 && Mouse.mouseX < 482 + 80 && Mouse.mouseY > 130 && Mouse.mouseY < 130 + 35) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/button_help_on.png")), 483, 130, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/arrow.png")), 562, 135, 22, 20, null);
				if (Mouse.mouseB == 1) {
					System.out.println("Help");
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/button_help.png")), 483, 130, 80, 30, null);
			}
			if (Mouse.mouseX > 482 && Mouse.mouseX < 482 + 80 && Mouse.mouseY > 160 && Mouse.mouseY < 160 + 35) {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/button_quit_on.png")), 485, 160, 80, 30, null);
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/arrow.png")), 562, 165, 22, 20, null);

				if (Mouse.mouseB == 1) {
					System.exit(0);
				}
			} else {
				g.drawImage(ImageIO.read(Launcher.class.getResource("/launcher/button_quit.png")), 485, 160, 80, 30, null);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		g.dispose();
		bs.show();
	}

	public void startMenu() {
		running = true;
		thread = new Thread(this, "menu");
		thread.start();
	}

	@SuppressWarnings("deprecation")
	public void stopMenu() throws Exception {
		try {
			thread.join();
			thread.stop();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		while (running) {
			try {
				renderMenu();

			} catch (IllegalStateException e) {

			}
			updateFrame();

		}

	}

	public static void main(String[] args) {
		new Launcher();
	}

}
