package org.gugino.gamemanager.statemanagement;

import java.awt.Graphics;

public abstract class State {
	
	private int stateID;
	private boolean isEnabled;
	
	public State(int _stateID) {
		this.stateID = _stateID;
		this.isEnabled = false;
	}
	
	public abstract void update(double _delta);
	
	public abstract void render(Graphics _g);

	public void setEnabled(boolean _enabled) {
		this.isEnabled = _enabled;
	}
	
	public int getStateID() {
		return stateID;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
}
