package org.gugino.gamemanager.gfx.ui.uiitems;

import java.awt.Font;
import java.awt.Graphics;

import org.gugino.gamemanager.GameManager;
import org.gugino.gamemanager.gfx.ui.UIRenderLayer;

public abstract class UIItem {

	protected String uiID;
	protected double uiX, uiY;
	protected int uiWidth, uiHeight;
	protected int parentStateID = -1;
	
	protected Font uiItemFont;
	
	private Font defaultFont = new Font("TimesRoman", Font.PLAIN, 18);
	
	private UIRenderLayer uiRenderLayer = UIRenderLayer.BACKGROUND;
	
	protected boolean isEnabled = false;
	
	public UIItem(String _id, double _x, double _y, int _width, int _height) {
		this.uiID = _id;
		this.uiX = _x;
		this.uiY = _y;
		this.uiWidth = _width;
		this.uiHeight = _height;
	}

	
	public abstract void update(double _delta);
	
	public abstract void render(Graphics _g);

	protected boolean mouseInsideButton() {
		double mX = GameManager.mouseHandler.getMouseX();
		double mY = GameManager.mouseHandler.getMouseY();
		
		if((mX >= uiX && mX <= uiX + uiWidth) && (mY >= uiY && mY <= uiY + uiHeight)) return true;
		
		return false;
	}
	
	public void setFont(Font _font) {
		this.uiItemFont = _font;
	}
	
	public void setEnabled(boolean _enabled) {
		this.isEnabled = _enabled;
	}
	
	public void setParentID(int _id) {
		this.parentStateID = _id;
	}
	
	public void setRenderLayer(UIRenderLayer _layer) {
		this.uiRenderLayer = _layer;
	}
	
	public UIRenderLayer getRenderLayer() {
		return uiRenderLayer;
	}
	
	public String getUiID() {
		return uiID;
	}

	public int getParentID() {
		return parentStateID;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public double getUiX() {
		return uiX;
	}

	public double getUiY() {
		return uiY;
	}

	public int getUiWidth() {
		return uiWidth;
	}

	public int getUiHeight() {
		return uiHeight;
	}

	public Font getFont() {
		return this.uiItemFont;
	}


	public Font getDefaultFont() {
		return defaultFont;
	}
}