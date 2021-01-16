package org.gugino.gamemanager.input;


import java.awt.MouseInfo;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.awt.event.MouseWheelEvent;

public class MouseHandler extends MouseAdapter{
	
	private boolean[] buttonsPressed = new boolean[MouseInfo.getNumberOfButtons()];
	
	private int scrollWheelRotation = 0;
	
	private double mouseX, mouseY;
	private double mouseScreenX, mouseScreenY;
	
	//Override Methods

	@Override
    public void mousePressed(MouseEvent e) {
		if(!buttonsPressed[e.getButton()]) buttonsPressed[e.getButton()] = true;
	}

	@Override
    public void mouseReleased(MouseEvent e) {
		if(buttonsPressed[e.getButton()]) buttonsPressed[e.getButton()] = false;
	}

	@Override
    public void mouseEntered(MouseEvent e) {}

	@Override
    public void mouseExited(MouseEvent e) {}

	@Override
    public void mouseWheelMoved(MouseWheelEvent e){
		scrollWheelRotation = e.getWheelRotation();
	}

	@Override
    public void mouseDragged(MouseEvent e){}

	@Override
    public void mouseMoved(MouseEvent e){
		mouseX = e.getX();
		mouseY = e.getY();
		mouseScreenX = e.getXOnScreen();
		mouseScreenY = e.getYOnScreen();
	}

	
	//Check input methods
	public boolean mouseButtonPressed(int _button) {
		return buttonsPressed[_button];
	}
	
	//Getters
	
	public double getMouseX() {
		return mouseX;
	}
	
	public double getMouseY() {
		return mouseY;
	}
	
	public double getMouseScreenX() {
		return mouseScreenX;
	}
	
	public double getMouseScreenY() {
		return mouseScreenY;
	}
	
	public int getScrollRotation() {
		return scrollWheelRotation;
	}
	
	//Setters
	
	public void setScrollWheelRotation(int _rot) {
		scrollWheelRotation = _rot;
	}
}
