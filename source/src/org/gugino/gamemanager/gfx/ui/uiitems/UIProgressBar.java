package org.gugino.gamemanager.gfx.ui.uiitems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIProgressBar extends UIItem {

	private Color backgroundColor = Color.darkGray;
	private Color fillColor = Color.green;
	
	private float maxValue = 100, currentValue = 100;
	
	private BufferedImage backgroundImage;
	private BufferedImage fillImage;
	private boolean hasBackgroundImage = false;
	
	public UIProgressBar(String _id, double _x, double _y, int _width, int _height, Color _backgroundColor, Color _fillColor) {
		super(_id, _x, _y, _width, _height);
		this.backgroundColor = _backgroundColor;
		this.fillColor = _fillColor;
		hasBackgroundImage = false;
	}
	
	public UIProgressBar(String _id, double _x, double _y, int _width, int _height, BufferedImage _backgroundImage, BufferedImage _fillImage) {
		super(_id, _x, _y, _width, _height);
		this.backgroundImage = _backgroundImage;
		this.fillImage = _fillImage;
		hasBackgroundImage = true;
	}

	@Override
	public void update(double _delta) {}

	@Override
	public void render(Graphics _g) {
		if(hasBackgroundImage) {
			if((backgroundImage != null) && (fillImage != null)) {
				_g.drawImage(backgroundImage, (int)uiX, (int)uiY, uiWidth, uiHeight, null);
				_g.drawImage(fillImage, (int)uiX, (int)uiY, (int)((currentValue/maxValue) * uiWidth), uiHeight, null);
			}
		}else{
			_g.setColor(backgroundColor);
			_g.fillRoundRect((int)uiX, (int)uiY, uiWidth, uiHeight, 2, 2);
			_g.setColor(fillColor);
			_g.fillRoundRect((int)uiX, (int)uiY, (int)((currentValue/maxValue) * uiWidth), uiHeight, 2, 2);
			System.out.println((currentValue/maxValue) * uiWidth);
			_g.setColor(Color.black);
		}
	}
	
	public void setValues(int _maxValue, int _currentValue) {
		if(_maxValue >= 0) {
			maxValue = _maxValue;
		}else {
			maxValue = 0;
		}
		
		if(_currentValue >= 0) {
			currentValue = _currentValue;
		}else {
			currentValue = 0;
		}
	}
	
	public float getMaxValue() {
		return maxValue;
	}
	
	public float getCurrentValue() {
		return currentValue;
	}
}
