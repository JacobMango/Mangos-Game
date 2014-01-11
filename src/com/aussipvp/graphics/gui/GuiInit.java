package com.aussipvp.graphics.gui;

import java.awt.Graphics2D;
import java.awt.Image;

import com.aussipvp.Game;
import com.aussipvp.graphics.Screen;

public class GuiInit {

	protected Game var1;
	protected Screen var2;
	protected int[] var3;
	protected Image var4;
	protected Graphics2D var5;

	public void init(Game game) {
		var1 = game;
		var2 = game.screen;
		var3 = game.pixels;
		var4 = game.image;
		var5 = game.g;
	}
}
