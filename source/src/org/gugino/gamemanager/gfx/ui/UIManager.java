package org.gugino.gamemanager.gfx.ui;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.HashMap;

import org.gugino.gamemanager.gfx.ui.uiitems.UIItem;


public class UIManager {
	private HashMap<String, UIItem> uiItems = new HashMap<>();

	private boolean showDebug = false;
	
	public UIManager() {}
	public UIManager(boolean _showDebug) {this.showDebug = _showDebug;}	
	
	public void update(double _delta) {
		for(UIItem _s : uiItems.values()) {
			if(_s.isEnabled()) _s.update(_delta);
		}
	}
	
	public void render(Graphics _g) {
		ArrayList<UIItem> _foregroundItems = new ArrayList<>();
		ArrayList<UIItem> _midgroundItems = new ArrayList<>();
		ArrayList<UIItem> _backgroundItems = new ArrayList<>();
		
		for(UIItem _s : uiItems.values()) {
			if(_s.isEnabled()) {
				if(_s.getRenderLayer() == UIRenderLayer.BACKGROUND) _backgroundItems.add(_s);
				if(_s.getRenderLayer() == UIRenderLayer.MID_GROUND) _midgroundItems.add(_s);
				if(_s.getRenderLayer() == UIRenderLayer.FOREGROUND) _foregroundItems.add(_s);
			}
		}

		for (UIItem _background : _backgroundItems) if(_background.isEnabled()) _background.render(_g);
		for (UIItem _midground : _midgroundItems) if(_midground.isEnabled()) _midground.render(_g);
		for (UIItem _foreground : _foregroundItems) if(_foreground.isEnabled()) _foreground.render(_g);
	}
	
	public void addUIItem(UIItem _item) {
		if(!uiItems.containsKey(_item.getUiID())) {uiItems.put(_item.getUiID(), _item); if(showDebug) System.out.println("UIItem with ID: " + _item.getUiID() + " has been added");}
		else if(showDebug) System.err.println("UIItem with ID: " + _item.getUiID() + " already exists");
	}
	
	public void removeUIItem(String _uiID) {
		if(uiItems.containsKey(_uiID)) uiItems.remove(_uiID);
		else if(showDebug) System.err.println("UIItem with ID: " + _uiID + " doesn't exist");
	}
	
	
	public UIItem getUIItemByID(String _uiID) {
		if(uiItems.containsKey(_uiID)) return uiItems.get(_uiID);
		if(showDebug) System.err.println("UIItem with ID: " + _uiID + " doesn't exist");
		return null;
	}
	
	
	public HashMap<String, UIItem> getUiItems() {
		return uiItems;
	}
}
