package org.gugino.gamemanager.statemanagement;

import java.awt.Graphics;
import java.util.HashMap;

import org.gugino.gamemanager.entities.Entity;
import org.gugino.gamemanager.entities.EntityHandler;
import org.gugino.gamemanager.gfx.ui.UIManager;
import org.gugino.gamemanager.gfx.ui.uiitems.UIItem;

public class StateManager {

	private HashMap<Integer, State> states = new HashMap<>();
	
	private int currentStateID = -1;
	
	private boolean showDebug = false;
	
	public static boolean switchingStates = false, updatingEntities = false, updatingUIItems = false;
	
	public StateManager() {}
	public StateManager(boolean _showDebug) {this.showDebug = _showDebug;}	
	
	public void update(double _delta) {
		for(State _s : states.values()) {
			if(_s.isEnabled()) _s.update(_delta);
		}
	}
	
	public void render(Graphics _g) {
		for(State _s : states.values()) {
			if(_s.isEnabled()) _s.render(_g);
		}
	}
	
	public void addState(State _state) {
		if(!states.containsKey(_state.getStateID())) {states.put(_state.getStateID(), _state); if(showDebug) System.out.println("State with ID: " + _state.getStateID() + " has been added");}
		else if(showDebug) System.err.println("State with ID: " + _state.getStateID() + " already exists");
	}
	
	public void removeState(int _stateID) {
		if(states.containsKey(_stateID)) states.remove(_stateID);
		else if(showDebug) System.err.println("State with ID: " + _stateID + " doesn't exist");
	}
	
	public void changeState(int _stateID) {
		switchingStates = true;
		if(currentStateID == _stateID) {if(showDebug) System.err.println("State with ID: " + _stateID + " is already active"); switchingStates = false; return;}
		if(!states.containsKey(_stateID)){if(showDebug) System.err.println("State with ID: " + _stateID + " doesn't exist"); switchingStates = false; return;}
		
		if(currentStateID == -1) {
			if(showDebug) System.out.println("State with ID: " + _stateID + " has been set to active");
			states.get(_stateID).setEnabled(true);
			currentStateID = _stateID;
		}else if(currentStateID != -1){
			if(showDebug) System.out.println("State with ID: " + _stateID + " has been set to active, previous state ID: " + currentStateID);
			states.get(currentStateID).setEnabled(false);
			states.get(_stateID).setEnabled(true);
			currentStateID = _stateID;
		}
		switchingStates = false;
	}	
	
	public void updateEntityStates(EntityHandler _entityHandler) {
		updatingEntities = true;
		if(_entityHandler != null) {
			for(Entity _e : _entityHandler.getActiveEntities().values()) {
				if(!_e.getEntityData().entityActive) {
					_e.getEntityData().entityActive = true;
				}else if(_e.getParentStateID() != currentStateID){
					if(_e.getEntityData().entityActive)_e.getEntityData().entityActive = false;
				}
			}
		}	
		updatingEntities = false;
	}
	
	public void updateUIItemStates(UIManager _uiManager) {
		updatingUIItems = true;
		if(_uiManager != null) {
			for(UIItem _i : _uiManager.getUiItems().values()) {
				if(_i.getParentID() == currentStateID) {
					if(!_i.isEnabled())_i.setEnabled(true);
				}else if(_i.getParentID() != currentStateID){
					if(_i.isEnabled())_i.setEnabled(false);
				}
			}	
		}
		updatingUIItems = false;
	}
	
	public State getStateByID(int _stateID) {
		if(states.containsKey(_stateID)) return states.get(_stateID);
		if(showDebug) System.err.println("State with ID: " + _stateID + " doesn't exist");
		return null;
	}
	
	public HashMap<Integer, State> getStates(){
		return states;
	}
	
	public int getCurrentStateID() {
		return currentStateID;
	}
}
