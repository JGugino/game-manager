package org.gugino.gamemanager.gfx.ui.uiitems;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIPanel extends UIItem{

	private BufferedImage panelImage;
	private Color panelColor = Color.gray;
	
	private boolean hasBackgroundImage = false;
	
	public UIPanel(String _id, Color _color, double _x, double _y, int _width, int _height) {
		super(_id, _x, _y, _width, _height);
		this.panelImage = null;
		this.panelColor = _color;
		this.hasBackgroundImage = false;
	}
	
	public UIPanel(String _id, BufferedImage _image, double _x, double _y, int _width, int _height) {
		super(_id, _x, _y, _width, _height);
		this.panelImage = _image;
		this.hasBackgroundImage = true;
	}

	@Override
	public void update(double _delta) {}

	@Override
	public void render(Graphics _g) {
		if(hasBackgroundImage) _g.drawImage(panelImage, (int)uiX, (int)uiY, uiWidth, uiHeight, null);
		else {
			_g.setColor(panelColor);
			_g.fillRect((int)uiX, (int)uiY, uiWidth, uiHeight);
		}
	}

}
