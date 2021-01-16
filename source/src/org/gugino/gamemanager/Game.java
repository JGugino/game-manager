package org.gugino.gamemanager;

import java.awt.Graphics;

public abstract class Game {
	
	public abstract void update(double _deltaTime);
	
	public abstract void render(Graphics _graphics);
	
	public void windowResize() {}
}
