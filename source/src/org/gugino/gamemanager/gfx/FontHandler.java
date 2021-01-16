package org.gugino.gamemanager.gfx;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.util.HashMap;

public class FontHandler {

	private static HashMap<String, Font> importedFonts = new HashMap<>();
	
	private boolean showDebug = false;
	
	public FontHandler() {}
	public FontHandler(boolean _debug) {this.showDebug = _debug;}
	
	public void addFont(String _id, String _fontPath, float _fontSize) {
		if(!importedFonts.containsKey(_id)) {			
			GraphicsEnvironment _ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			try {
				Font _importedFont = Font.createFont(Font.TRUETYPE_FONT, FontHandler.class.getResourceAsStream(_fontPath)).deriveFont(_fontSize);
				
				_ge.registerFont(_importedFont);
				
				importedFonts.put(_id, _importedFont);
				if(showDebug) System.out.println("Font with ID: " + _id + " imported");
				
			} catch (FontFormatException | IOException e) {
				e.printStackTrace();
			}
		}else 
			if(showDebug) System.err.println("Font with ID: " + _id + " already exists");
	}
	
	public Font getFontByID(String _id) {
		if(importedFonts.containsKey(_id)) return importedFonts.get(_id);
		
		return null;
	}
}
