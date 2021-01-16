package org.gugino.gamemanager.gfx.spritesheets;

import java.awt.image.BufferedImage;

public class SpriteSheet {
	private BufferedImage[][] sheetImages;
	
	public SpriteSheet(BufferedImage[][] _sheetImages) {
		this.sheetImages = _sheetImages;
	}
	
	public BufferedImage[][] getSheetImages() {
		return sheetImages;
	}
}
