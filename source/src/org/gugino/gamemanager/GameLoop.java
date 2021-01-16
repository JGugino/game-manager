package org.gugino.gamemanager;

import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class GameLoop implements Runnable{

	private GameManager gameManager;
	
	private Graphics graphics;
	
	private boolean isRunning;
	
	public GameLoop(GameManager _gameManager) {
		this.gameManager = _gameManager;
	}
	
	@Override
	public void run() {
		isRunning = true;
		long _lastTime = System.nanoTime();
		double _amountOfTicks = 60.0;
		double _ns = 1000000000 / _amountOfTicks;
		double _deltaTime = 0;
		long _timer = System.currentTimeMillis();
		int _frames = 0;

		boolean _shouldRender = false;

		while (isRunning) {

			// Sets shouldRender back to false
			_shouldRender = false;

			long _now = System.nanoTime();
			_deltaTime += (_now - _lastTime) / _ns;
			_lastTime = _now;

			while (_deltaTime >= 1) {
				// Sets thats the frame should render equal to true
				
				update(_deltaTime);
				
				_shouldRender = true;
				_deltaTime--;
			}

			// Checks if the frame should render
			if (_shouldRender) {
				// Runs the render loop
				render();
			}
			_frames++;

			if (System.currentTimeMillis() - _timer > 1000) {
				_timer += 1000;
				int fps = _frames;
				if(gameManager.getShowDebug()) {
					System.out.println("FPS: " + fps);	
				}
				_frames = 0;
			}
		}
		
		gameManager.stopGameThread();
	}
	
	private void update(double _deltaTime) {
		gameManager.getGame().update(_deltaTime);
	}
	
	private void render() {
		BufferStrategy _bs = gameManager.getGameCanvas().getBufferStrategy();
		
		if(_bs == null) {
			gameManager.getGameCanvas().createBufferStrategy(3);
			return;
		}
		
		graphics = gameManager.getGameCanvas().getBufferStrategy().getDrawGraphics();
		
		graphics.clearRect(0, 0, GameManager.getWindowData().windowCurrentWidth,
				GameManager.getWindowData().windowCurrentHeight);
		
		if(graphics != null) {
			gameManager.getGame().render(graphics);
		}
		
		graphics.dispose();
		_bs.show();
	}
	
	public Graphics getGraphics() {
		return graphics;
	}

}
