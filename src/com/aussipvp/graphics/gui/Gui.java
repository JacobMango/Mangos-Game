package com.aussipvp.graphics.gui;

import java.awt.Graphics2D;
import java.awt.Image;

import com.aussipvp.Game;
import com.aussipvp.graphics.Screen;

public abstract class Gui extends GuiInit {
	
	public Game game = var1;
	public Screen screen = var2;
	public int[] pixels = var3;
	public Image image = var4;
	public Graphics2D graphics2d = var5;
	
	public Gui() {
	}
	
	public abstract void render();
}
