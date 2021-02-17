package org.gugino.gamemanager.util;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.gugino.gamemanager.gfx.ImageHandler;

public class StringHelper {
	public static double[] parseCommaSeperatedStringToDouble(String _string) {
		String[] _splitString = _string.split(",");
		return new double[] {Double.parseDouble(_splitString[0]), Double.parseDouble(_splitString[1])};
	}
	
	public static int[] parseCommaSeperatedStringToInt(String _string) {
		String[] _splitString = _string.split(",");
		return new int[] {Integer.parseInt(_splitString[0]), Integer.parseInt(_splitString[1])};
	}
	
	public static BufferedImage parseStringToBufferedImage(String _string) {
		if(!_string.isEmpty()) {			
			try {
				return ImageIO.read(ImageHandler.class.getResource(_string));
			} catch (IOException e) {
				e.printStackTrace();
				return null;
			}	
		}else {
			System.err.println("Unable to parse image from string: ".concat(_string));
			return null;
		}
	}
	
	public static BufferedImage[] parseCommaSeperatedStringToBufferedImages(String _string) {
		String[] _splitString = _string.split(",");
		BufferedImage[] _images = new BufferedImage[_splitString.length];
		for (int _i = 0; _i < _splitString.length; _i++) _images[_i] = parseStringToBufferedImage(_splitString[_i]);
		return _images;
	}
	
	public static Color[] parseCommaSeperatedStringToColors(String _string) {
		String[] _splitString = _string.split(",");
		Color[] _colors = new Color[_splitString.length];
		for (int _c = 0; _c < _splitString.length; _c++) _colors[_c] = parseStringToColor(_splitString[_c]);
		return _colors;
	}
	
	public static Color parseStringToColor(String _string) {
		switch(_string) {
			case "white": return Color.white;
			case "black": return Color.black;
			case "blue": return Color.blue;
			case "green": return Color.green;
			case "pink": return Color.pink;
			case "gray": return Color.gray;
			case "light-gray": return Color.lightGray;
			case "dark-gray": return Color.darkGray;
			case "cyan": return Color.cyan;
			case "orange": return Color.orange;
			case "yellow": return Color.yellow;
			case "red": return Color.red;
			default: return Color.black;
		}
	}
}
