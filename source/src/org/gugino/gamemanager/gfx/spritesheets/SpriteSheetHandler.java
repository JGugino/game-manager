package org.gugino.gamemanager.gfx.spritesheets;

import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SpriteSheetHandler {
	
	private HashMap<String, SpriteSheet> loadedSpriteSheets = new HashMap<>();
	
	private boolean showDebug = false;
	
	public SpriteSheetHandler() {}
	public SpriteSheetHandler(boolean _showDebug) {showDebug = _showDebug;}
	
	public SpriteSheet loadSpriteSheet(String _id, BufferedImage _spriteSheet, int _sheetWidth, int _sheetHeight, int _spriteWidth, int _spriteHeight) {
		if(!loadedSpriteSheets.containsKey(_id)) {

				BufferedImage[][] _images = new BufferedImage[_sheetWidth][_sheetHeight];
				for(int y = 0; y < _sheetHeight; y+= _spriteHeight) {
					for(int x = 0; x < _sheetWidth; x+= _spriteWidth) {
						_images[x][y] = _spriteSheet.getSubimage(x, y, _spriteWidth, _spriteHeight);
					}
				}
				
				SpriteSheet _createdSheet = new SpriteSheet(_images);

				loadedSpriteSheets.put(_id, _createdSheet);
				
				if(showDebug) System.out.println("SpriteSheet with ID: " + _id + " has been loaded");
				return loadedSpriteSheets.get(_id);
		}else {
			if(showDebug) System.err.println("SpriteSheet with ID: " + _id + " already loaded");
			return loadedSpriteSheets.get(_id);
		}
	}
	
	public SpriteSheet getSpriteSheetByID(String _id) {
		if(loadedSpriteSheets.containsKey(_id)) return loadedSpriteSheets.get(_id);
		else {if(showDebug) System.err.println("SpriteSheet with ID: " + _id + " doesn't exists"); return null;}
	}
	
}
