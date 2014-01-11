package com.aussipvp;

import javax.swing.JFrame;

public class Launcher {

	public static void start() {
		Game g = new Game();
		g.frame.setResizable(false);
		g.frame.setTitle(g.TITLE);
		g.frame.add(g);
		g.frame.pack();
		g.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		g.frame.setLocationRelativeTo(null);
		g.frame.setVisible(true);
		g.DEBUG = false;
		g.start();
	}

}
