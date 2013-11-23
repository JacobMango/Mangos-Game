package com.aussipvp.input;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JFrame;

public class Mouse implements MouseListener, MouseMotionListener, FocusListener {

	public static int mouseX = -1;
	public static int mouseY = -1;
	public static int mousePX = -1;
	public static int mousePY = -1;
	public static int mouseB = -1;
	public static boolean dragged = false;

	public static int getX() {
		return mouseX;
	}

	public static int getY() {
		return mouseY;
	}

	public static int getButton() {
		return mouseB;
	}
	
	public JFrame frame;
	
	public Mouse() {
		this.frame = new JFrame();
	}

	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
		dragged = true;
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		mousePX = e.getX();
		mousePY = e.getY();
		mouseB = e.getButton();
	}

	public void mouseReleased(MouseEvent e) {
		dragged = false;
		mouseB = -1;
	}

	public void focusGained(FocusEvent arg0) {
		
	}

	public void focusLost(FocusEvent arg0) {
		
	}

}
