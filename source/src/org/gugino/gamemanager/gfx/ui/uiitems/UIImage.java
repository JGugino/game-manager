package org.gugino.gamemanager.gfx.ui.uiitems;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class UIImage extends UIItem{

	private BufferedImage uiImage;
	
	public UIImage(String _id, BufferedImage _image, double _x, double _y, int _width, int _height) {
		super(_id, _x, _y, _width, _height);
		this.uiImage = _image;
	}

	@Override
	public void update(double _delta) {
		
	}

	@Override
	public void render(Graphics _g) {
		_g.drawImage(uiImage, (int)uiX, (int)uiY, uiWidth, uiHeight, null);
	}

}
