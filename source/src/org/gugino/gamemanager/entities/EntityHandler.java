package org.gugino.gamemanager.entities;

import java.awt.Graphics;
import java.util.HashMap;

public class EntityHandler {
	
	private HashMap<Integer, Entity> activeEntities = new HashMap<>();
	
	private boolean showDebug = false;
	
	public EntityHandler() {}
	
	public EntityHandler(boolean _showDebug) {this.showDebug = _showDebug;}
	
	public void update(double _delta) {
		for (Entity _entity : activeEntities.values()) {
			if(_entity.entityData.entityActive) _entity.update(_delta);
		}
	}
	
	public void render(Graphics _g) {
		for (Entity _entity : activeEntities.values()) {
			if(_entity.entityData.entityActive) _entity.render(_g);
		}
	}
	
	public void addEntity(Entity _entity) {
		if(!activeEntities.containsKey(_entity.entityID)) {
			activeEntities.put(_entity.entityID, _entity);
			if(showDebug) System.out.println("Added entity with ID: " + _entity.entityID);
		}else {
			if (showDebug) System.err.println("Entity with ID: " + _entity.entityID + " already exists");
			return;
		}
	}
	
	public void removeEntity(Entity _entity) {
		if(activeEntities.containsKey(_entity.entityID)) {
			activeEntities.remove(_entity.entityID);
			if(showDebug) System.out.println("Removed entity with ID: " + _entity.entityID);
		}else {
			if (showDebug) System.err.println("Entity with ID: " + _entity.entityID + " doesn't exists");
			return;
		}
	}
	
	public Entity getEntityByID(int _id) {
		if(activeEntities.containsKey(_id)) return activeEntities.get(_id);
		else {
			if (showDebug) System.err.println("Entity with ID: " + _id + " doesn't exists");
			return null;
		}
	}
	
	public HashMap<Integer, Entity> getActiveEntities(){
		return activeEntities;
	}
}
