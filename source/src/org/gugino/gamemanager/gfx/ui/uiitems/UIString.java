package org.gugino.gamemanager.gfx.ui.uiitems;

import java.awt.Color;
import java.awt.Graphics;

public class UIString extends UIItem {

	private String stringText = "Example String";
	
	private Color stringColor = Color.black;
	
	public UIString(String _id, String _text, double _x, double _y) {
		super(_id, _x, _y, 0, 0);
		this.stringText = _text;
	}
	
	public UIString(String _id, String _text, double _x, double _y, Color _textColor) {
		super(_id, _x, _y, 0, 0);
		this.stringText = _text;
		this.stringColor = _textColor;
	}

	@Override
	public void update(double _delta) {}

	@Override
	public void render(Graphics _g) {
		_g.setColor(stringColor);
		if(this.uiItemFont != null) _g.setFont(uiItemFont);
		_g.drawString(stringText, (int)uiX, (int)uiY);
		_g.setFont(getDefaultFont());
	}

}
