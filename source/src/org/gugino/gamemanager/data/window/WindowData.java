package org.gugino.gamemanager.data.window;

import javax.swing.WindowConstants;

public class WindowData {
	public String windowTitle;
	public int windowWidth;
	public int windowHeight;
	public int windowCurrentWidth;
	public int windowCurrentHeight;
	public boolean resizable;
	public int closeOperation;
	
	public WindowData() {
		this.windowTitle = "Game Window";
		this.windowWidth = 800;
		this.windowHeight = 600;
		this.resizable = false;
		this.closeOperation = WindowConstants.EXIT_ON_CLOSE;
	}
	
	public WindowData(String _windowTitle, int _windowWidth, int _windowHeight, boolean _resizable, int _closeOperation) {
		this.windowTitle = _windowTitle;
		this.windowWidth = _windowWidth;
		this.windowHeight = _windowHeight;
		this.windowCurrentWidth = this.windowWidth;
		this.windowCurrentHeight = this.windowHeight;
		this.resizable = _resizable;
		this.closeOperation = _closeOperation;
	}
}
