package com.aussipvp.graphics;

import com.aussipvp.input.Keyboard;

public class Chat {
	
	public String message = "";

	private Keyboard key;
	
	public void init(Keyboard key) {
		this.key = key;
	}
	
	public void type() {
		if (key.y) {
			message = key.keys.toString();
		}
	}

	public void update() {
		type();
		//System.out.println(message + "");
	}
}
