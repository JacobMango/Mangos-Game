package com.aussipvp.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.aussipvp.Game;

public class Keyboard implements KeyListener {

	public boolean[] keys = new boolean[120];
	public boolean up, down, left, right, f3, escape, y;

	private int time = 0;
	private int rate = 10;

	public void update() {
		time++;
		up = keys[KeyEvent.VK_W];
		down = keys[KeyEvent.VK_S];
		left = keys[KeyEvent.VK_A];
		right = keys[KeyEvent.VK_D];
		f3 = keys[KeyEvent.VK_F3];
		escape = keys[KeyEvent.VK_ESCAPE];
		y = keys[KeyEvent.VK_Y];
		if (Game.DEBUG == true) {
			System.out.println("UP: " + up + ", DOWN: " + down + ", LEFT: " + left + ", RIGHT: " + right);
		}

		if (f3) {
			if (time % rate == 0) {
				boolean f = Game.f3menu;
				f = (Game.f3menu) ? false : true;
				Game.f3menu = f;
				time = 0;
			}
		}
		if (escape) {
			if (time % rate == 0) {
				boolean f = Game.pauseMenu;
				f = (Game.pauseMenu) ? false : true;
				Game.pauseMenu = f;
				if (f) Game.game = false;
				else Game.game = true;
				time = 0;
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {

	}
}
