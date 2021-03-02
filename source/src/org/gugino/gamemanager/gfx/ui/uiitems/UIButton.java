package org.gugino.gamemanager.gfx.ui.uiitems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import org.gugino.gamemanager.GameManager;
import org.gugino.gamemanager.gfx.ui.uiitems.interfaces.IClickable;
import org.gugino.gamemanager.statemanagement.StateManager;

public class UIButton extends UIItem{

	private Color clickColor = Color.lightGray;
	private Color hoverColor = Color.darkGray;
	private Color backgroundColor = Color.gray;
	private Color buttonTextColor = Color.black;
	private BufferedImage backgroundImage = null;
	private BufferedImage hoverImage = null;
	private BufferedImage clickImage = null;
	
	private String buttonText = "Button";
	
	private IClickable buttonListener;
	
	private boolean isHovering = false;
	private boolean isClicked = false;
	
	private boolean hasBackgroundImage = false;
	
	public UIButton(String _id, double _x, double _y, int _width, int _height) {
		super(_id, _x, _y, _width, _height);
	}
	public UIButton(String _id, double _x, double _y, int _width, int _height, Color _backgroundColor, Color _hoverColor, Color _clickColor) {
		super(_id, _x, _y, _width, _height);
		this.backgroundColor = _backgroundColor;
		this.hoverColor = _hoverColor;
		this.clickColor = _clickColor;
		this.hasBackgroundImage = false;
	}
	
	public UIButton(String _id, double _x, double _y, int _width, int _height, Color _backgroundColor, Color _hoverColor, Color _clickColor, Color _buttonTextColor) {
		super(_id, _x, _y, _width, _height);
		this.backgroundColor = _backgroundColor;
		this.hoverColor = _hoverColor;
		this.clickColor = _clickColor;
		this.hasBackgroundImage = false;
		this.buttonTextColor = _buttonTextColor;
	}
	
	public UIButton(String _id, double _x, double _y, int _width, int _height, BufferedImage _backgroundImage, BufferedImage _hoverImage, BufferedImage _clickImage) {
		super(_id, _x, _y, _width, _height);
		this.backgroundImage = _backgroundImage;
		this.hoverImage = _hoverImage;
		this.clickImage = _clickImage;
		this.hasBackgroundImage = true;
	}
	
	public UIButton(String _id, double _x, double _y, int _width, int _height, BufferedImage _backgroundImage, BufferedImage _hoverImage, BufferedImage _clickImage, Color _buttonTextColor) {
		super(_id, _x, _y, _width, _height);
		this.backgroundImage = _backgroundImage;
		this.hoverImage = _hoverImage;
		this.clickImage = _clickImage;
		this.hasBackgroundImage = true;
		this.buttonTextColor = _buttonTextColor;
	}

	@Override
	public void update(double _delta) {
		if(isEnabled) {
			if(!mouseInsideButton()) {
				if(isHovering) isHovering = false;
			}else if(mouseInsideButton()) {
				isHovering = true;
				if(getParentID() != -1) {
					if(!StateManager.updatingUIItems) {
						if(isHovering && GameManager.mouseHandler.mouseButtonPressed(MouseEvent.BUTTON1)) {
							if(!isClicked) {
								isClicked = true;
								if(buttonListener != null) buttonListener.onClick(this.uiID);
								return;
							}			
						}	
					}		
				}else {
					if(isHovering && GameManager.mouseHandler.mouseButtonPressed(MouseEvent.BUTTON1)) {
						if(!isClicked) {
							isClicked = true;
							if(buttonListener != null) buttonListener.onClick(this.uiID);
							return;
						}			
					}	
				}
			}
			
			if(!GameManager.mouseHandler.mouseButtonPressed(MouseEvent.BUTTON1)) if(isClicked) isClicked = false;
		}
	}
	
	@Override
	public void render(Graphics _g) {
		if(hasBackgroundImage) {
			if(isHovering && !isClicked) _g.drawImage(hoverImage, (int)uiX, (int)uiY, uiWidth, uiHeight, null);
			if(!isHovering && !isClicked) _g.drawImage(backgroundImage, (int)uiX, (int)uiY, uiWidth, uiHeight, null);
			if(isClicked && isHovering) _g.drawImage(clickImage, (int)uiX, (int)uiY, uiWidth, uiHeight, null);
			_g.setColor(Color.black);
			int uiXOffset = uiWidth / 2 - _g.getFontMetrics(uiItemFont).stringWidth(buttonText) / 2;
			int uiYOffset = uiHeight / 2 + _g.getFontMetrics(uiItemFont).getHeight() / 2;
			if(this.uiItemFont != null) _g.setFont(uiItemFont);
			_g.setColor(buttonTextColor);
			_g.drawString(buttonText, (int)uiX + uiXOffset, (int)uiY + uiYOffset);
			_g.setFont(getDefaultFont());
			_g.setColor(Color.black);
		}else {
			if(isHovering && !isClicked) _g.setColor(hoverColor);
			if(!isHovering && !isClicked)_g.setColor(backgroundColor);
			if(isClicked) _g.setColor(clickColor);
			_g.fillRoundRect((int)uiX, (int)uiY, uiWidth, uiHeight, 4, 4);
			_g.setColor(Color.black);
			int uiXOffset = uiWidth / 2 - _g.getFontMetrics(uiItemFont).stringWidth(buttonText) / 2;
			int uiYOffset = uiHeight / 2 + _g.getFontMetrics(uiItemFont).getHeight() / 2;
			if(this.uiItemFont != null) _g.setFont(uiItemFont);
			_g.setColor(buttonTextColor);
			_g.drawString(buttonText, (int)uiX + uiXOffset, (int)uiY + uiYOffset);
			_g.setFont(getDefaultFont());
			_g.setColor(Color.black);
		}
	}
	
	public void setButtonListener(IClickable _listener) {
		this.buttonListener = _listener;
	}
	
	public void setButtonText(String _text) {
		this.buttonText = _text;
	}

	public String getButtonText() {
		return this.buttonText;
	}
}
