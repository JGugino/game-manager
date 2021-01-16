package org.gugino.gamemanager.entities;

import java.awt.Graphics;

import org.gugino.gamemanager.data.entities.EntityData;

public abstract class Entity {
	
	protected int entityID;
	
	protected EntityData entityData;
	
	protected int parentStateID = -1;
	
	public Entity(int _entityID) {
		this.entityID = _entityID;
		this.entityData = new EntityData();
	}
	
	public Entity(int _entityID, EntityData _data) {
		this.entityID = _entityID;
		this.entityData = _data;
	}
	
	public Entity(int _entityID, EntityData _data, int _parentStateID) {
		this.entityID = _entityID;
		this.entityData = _data;
		this.parentStateID = _parentStateID;
	}
	
	public abstract void update(double _deltaTime);
	
	public abstract void render(Graphics _g);
	

	public void setParentStateID(int _parentStateID) {
		this.parentStateID = _parentStateID;
	}
	
	public int getID() {
		return entityID;
	}
	
	public EntityData getEntityData() {
		return entityData;
	}

	public int getEntityID() {
		return entityID;
	}
	
	public int getParentStateID() {
		return parentStateID;
	}
}
