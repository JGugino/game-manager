package org.gugino.gamemanager.data.entities;

public class EntityData {
	
	public double entityX, entityY;
	public float entityVelocityX = 0, entityVelocityY = 0;
	public int entityWidth, entityHeight;
	public float entitySpeed;
	public boolean entityActive = true;
	
	public double entityBoundsXOffset = 0, entityBoundsYOffset = 0;
	public int entityBoundsWidth, entityBoundsHeight;
	
	
	private static final double DEFAULT_X = 0;
	private static final double DEFAULT_Y = 0;
	private static final int DEFAULT_WIDTH = 32;
	private static final int DEFAULT_HEIGHT = 32;
	private static final float DEFAULT_SPEED = 2f;
	
	public EntityData() {
		this.entityX = DEFAULT_X;
		this.entityY = DEFAULT_Y;
		this.entityWidth = DEFAULT_WIDTH;
		this.entityHeight = DEFAULT_HEIGHT;
		this.entitySpeed = DEFAULT_SPEED;
		this.entityBoundsWidth = DEFAULT_WIDTH;
		this.entityBoundsHeight = DEFAULT_HEIGHT;
	}
	
	public EntityData(double _x, double _y, int _width, int _height, float _speed) {
		this.entityX = _x;
		this.entityY = _y;
		this.entityWidth = _width;
		this.entityHeight = _height;
		this.entitySpeed = _speed;
		this.entityBoundsWidth = _width;
		this.entityBoundsHeight = _height;
	}
	
	public EntityData(double _x, double _y, int _width, int _height, int _boundsWidth, int _boundsHeight, float _speed) {
		this.entityX = _x;
		this.entityY = _y;
		this.entityWidth = _width;
		this.entityHeight = _height;
		this.entitySpeed = _speed;
		this.entityBoundsWidth = _boundsWidth;
		this.entityBoundsHeight = _boundsHeight;
	}
}
