package org.gugino.gamemanager.gfx.ui.uiitems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import org.gugino.gamemanager.GameManager;

public class UIInputField extends UIItem {

	private BufferedImage backgroundImage = null;
	private boolean hasBackgroundImage = false;
	private Color backgroundColor = Color.lightGray;
	private Color textColor = Color.black;
	
  	private String inputText = "";
  	
  	private int characterCap = 0;
  	
  	private boolean isSelected = false;
  	private boolean isEditable = true;
	private boolean isHovering = false;
	private boolean isClicked = false;
	
	private boolean backspaceClicked = false;
	
	public UIInputField(String _id, double _x, double _y, int _width, int _height) {
		super(_id, _x, _y, _width, _height);
		this.hasBackgroundImage = false;;
	}
	public UIInputField(String _id, double _x, double _y, int _width, int _height, Color _backgroundColor, Color _textColor) {
		super(_id, _x, _y, _width, _height);
		this.backgroundColor = _backgroundColor;
		this.textColor = _textColor;
		this.hasBackgroundImage = false;;
	}
	public UIInputField(String _id, double _x, double _y, int _width, int _height, BufferedImage _backgroundImage, Color _textColor) {
		super(_id, _x, _y, _width, _height);
		this.backgroundImage = _backgroundImage;
		this.textColor = _textColor;
		this.hasBackgroundImage = true;
	}

	@Override
	public void update(double _delta) {
		if(isEnabled) {
			if(isEditable) {
				
				if(isSelected) {
					if(GameManager.keyHandler.isKeyPressed(KeyEvent.VK_BACK_SPACE)) {
						if(!backspaceClicked) {
							backspaceClicked = true;
							if(inputText.length() > 0) {
								StringBuffer _stringBuffer = new StringBuffer(inputText);
								_stringBuffer.deleteCharAt(_stringBuffer.length()-1);
								inputText = _stringBuffer.toString();
								GameManager.keyHandler.typedString = inputText;	
							}else {inputText = "";}	
						}
					}else if(!backspaceClicked){
						String _input = GameManager.keyHandler.typedString;
						
						if(_input.length() <= characterCap) {
							inputText = _input;
						}
					}
				}
				
				if(!GameManager.keyHandler.isKeyPressed(KeyEvent.VK_BACK_SPACE)) if(backspaceClicked) backspaceClicked = false;
				
				if(!mouseInsideButton()) {
					if(GameManager.mouseHandler.mouseButtonPressed(MouseEvent.BUTTON1)) {
						if(!isClicked) {
							isClicked = true;
							isSelected = false;
							return;
						}	
					}
					if(isHovering) isHovering = false;}
				else if(mouseInsideButton()) {
					isHovering = true;
					if(isHovering && GameManager.mouseHandler.mouseButtonPressed(MouseEvent.BUTTON1)) 
					
					if(!isClicked) {
						isClicked = true;
						isSelected = true;
						GameManager.keyHandler.typedString = "";
						return;
					}			
				}
				
				if(!GameManager.mouseHandler.mouseButtonPressed(MouseEvent.BUTTON1)) if(isClicked) isClicked = false;	
			}
		}
	}

	public void setCharacterCap(int _cap) {
		this.characterCap = _cap;
	}
	
	@Override
	public void render(Graphics _g) {
		if(hasBackgroundImage) _g.drawImage(backgroundImage, (int)uiX, (int)uiY, uiWidth, uiHeight, null);
		else {
			_g.setColor(backgroundColor);
			_g.fillRoundRect((int)uiX, (int)uiY, uiWidth, uiHeight, 4, 4);
			if(this.uiItemFont != null) _g.setFont(uiItemFont);
			_g.setColor(textColor);
			_g.drawString(this.inputText, (int)uiX + uiWidth / 2 - _g.getFontMetrics().stringWidth(inputText) / 2, ((int)uiY + uiHeight / 2) + _g.getFontMetrics().getHeight() / 2);	
			if(isSelected)_g.fillRect((int)uiX + uiWidth / 2 + _g.getFontMetrics().stringWidth(inputText) / 2 + 2, ((int)uiY + uiHeight / 2 - 20 / 2 + 4), 2, 20);
			_g.setFont(getDefaultFont());
		}
	}
	
	public void setInputText(String _text) {
		this.inputText = _text;
	}
	
	public String getInputText() {
		return inputText;
	}
	public boolean isEditable() {
		return isEditable;
	}
	public void setEditable(boolean isEditable) {
		this.isEditable = isEditable;
	}
	public int getCharacterCap() {
		return characterCap;
	}
	public boolean isSelected() {
		return isSelected;
	}
}
