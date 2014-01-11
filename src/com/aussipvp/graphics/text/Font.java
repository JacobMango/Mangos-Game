package com.aussipvp.graphics.text;

import java.util.HashMap;

import com.aussipvp.graphics.Screen;
import com.aussipvp.graphics.Sprite;
import com.aussipvp.graphics.SpriteSheet;

public class Font {

	private Screen screen;
	private SpriteSheet ss;
	
	private HashMap<String, SpriteSheet> chars = new HashMap<String, SpriteSheet>();

	private static String charss = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890                                  " + "abcdefghijklmnopqrstuvwxyz                                           ";

	public Font(Screen screen) {
		this.screen = screen;
		chars.put("A", ss.A);
		chars.put("B", ss.B);
		chars.put("C", ss.C);
		chars.put("D", ss.D);
		chars.put("E", ss.E);
		chars.put("F", ss.F);
		chars.put("G", ss.G);
		chars.put("H", ss.H);
		chars.put("I", ss.I);
		chars.put("J", ss.J);
		chars.put("K", ss.K);
		chars.put("L", ss.L);
		chars.put("M", ss.M);
		chars.put("N", ss.N);
		chars.put("O", ss.O);
		chars.put("P", ss.P);
		chars.put("Q", ss.Q);
		chars.put("R", ss.R);
		chars.put("S", ss.S);
		chars.put("T", ss.T);
		chars.put("U", ss.U);
		chars.put("V", ss.V);
		chars.put("W", ss.W);
		chars.put("X", ss.X);
		chars.put("Y", ss.Y);
		chars.put("Z", ss.Z);
		chars.put(" ", ss.Space);
	}

	public void render(String msg, int x, int y) {
		for (int i = 0; i < msg.length(); i++) {
			SpriteSheet letter = chars.get(msg.charAt(i) + "");
			screen.renderFont(letter, x + i * 8, y);
		}
		
		screen.renderFont(ss.A, x, y);
	}
}
