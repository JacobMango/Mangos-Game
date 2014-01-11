package com.aussipvp.graphics.gui;

import com.aussipvp.input.Keyboard;

public class Chat extends Gui{
	
	public String message = "";

	private Keyboard key;
	
	public void type() {
		if (key.y) {
			message = key.keys.toString();
		}
	}

	public void update() {
		type();
		System.out.println(message + "");
	}

    public void render() {
	    
    }
}
