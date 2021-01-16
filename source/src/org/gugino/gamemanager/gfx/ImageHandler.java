package org.gugino.gamemanager.gfx;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ImageHandler {
	
	private HashMap<String, BufferedImage> loadedImages = new HashMap<>();
	
	private boolean showDebug = false;
	
	public ImageHandler() {}
	public ImageHandler(boolean _showDebug) {showDebug = _showDebug;}
	
	public BufferedImage loadImage(String _id, String _path) {
		if(!loadedImages.containsKey(_id)) {
			try {
				loadedImages.put(_id, ImageIO.read(ImageHandler.class.getResource(_path)));
				if(showDebug) System.out.println("Image with ID: " + _id + " has been loaded");
				return loadedImages.get(_id);
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}	
		}else {
			if(showDebug) System.err.println("Image with ID: " + _id + " already loaded");
			return loadedImages.get(_id);
		}
	}
	
	public BufferedImage getImageByID(String _id) {
		if(loadedImages.containsKey(_id)) return loadedImages.get(_id);
		else {if(showDebug) System.err.println("Image with ID: " + _id + " doesn't exists"); return null;}
	}
	
}
