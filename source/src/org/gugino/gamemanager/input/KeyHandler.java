package org.gugino.gamemanager.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{

	private boolean[] pressedKeys = new boolean[256];
	
	public String typedString = "";
	
	@Override
	public void keyPressed(KeyEvent _key) {
		if(_key.getKeyCode() <= pressedKeys.length) {
			if(!pressedKeys[_key.getKeyCode()]) pressedKeys[_key.getKeyCode()] = true;	
		}
	}

	@Override
	public void keyReleased(KeyEvent _key) {
		if(_key.getKeyCode() <= pressedKeys.length) {
			if(pressedKeys[_key.getKeyCode()]) pressedKeys[_key.getKeyCode()] = false;	
		}
	}
	
	@Override
	public void keyTyped(KeyEvent _key) {
		if(_key.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
			typedString += _key.getKeyChar();	
		}
	}
	
	public boolean isKeyPressed(int _keyCode) {
		return pressedKeys[_keyCode];
	}
}
