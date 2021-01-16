package org.gugino.gamemanager.camera;

import org.gugino.gamemanager.entities.Entity;

public class Camera {

	private float xOffset, yOffset;
	private int windowWidth, windowHeight;
	
	private boolean limitCamera = false;
	
	private int minMoveX = 0, minMoveY = 0, maxMoveX = 0, maxMoveY = 0;
	
	public Camera(float _xOffset, float _yOffset, int _windowWidth, int _windowHeight) {
		this.xOffset = _xOffset;
		this.yOffset = _yOffset;
		this.windowWidth = _windowWidth;
		this.windowHeight = _windowHeight;
	}
	
	public void limitCamera(int _minMoveX, int _minMoveY, int _maxMoveX, int _maxMoveY) {
		limitCamera = true;
		this.minMoveX = _minMoveX;
		this.minMoveY = _minMoveY;
		
		this.maxMoveX = _maxMoveX;
		this.maxMoveY = _maxMoveY;
	}
	
	private void checkBlankSpace() {
		if(xOffset < minMoveX) {
			xOffset = minMoveX;
		}else if(xOffset > maxMoveX) {
			xOffset = maxMoveX;
		}
		
		if(yOffset < minMoveY) {
			yOffset = minMoveY;
		}else if(yOffset > maxMoveY) {
			yOffset = maxMoveY;
		}
	}
	
	public void moveToEntity(Entity _target) {
		xOffset = (float) (_target.getEntityData().entityX - windowWidth / 2 + _target.getEntityData().entityWidth / 2);
		yOffset = (float) (_target.getEntityData().entityY - windowHeight / 2 + _target.getEntityData().entityHeight / 2);
		if(limitCamera)checkBlankSpace();
	}
	
	public void moveCameraOffset(float _xOffset, float _yOffset) {
		this.xOffset += _xOffset;
		this.yOffset += _yOffset;
		if(limitCamera)checkBlankSpace();
	}
	
	public void setWindowDims(int _width, int _height) {
		this.windowWidth = _width;
		this.windowHeight = _height;
	}
	
	public int[] getCameraPosition(int[] _positions) {
		return new int[] {(int)(_positions[0] - xOffset), (int)(_positions[1] - yOffset)};
	}
	
	public float getXOffset() {
		return xOffset;
	}
	
	public float getYOffset() {
		return yOffset;
	}
}
