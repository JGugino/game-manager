package org.gugino.gamemanager;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import org.gugino.gamemanager.data.window.WindowData;
import org.gugino.gamemanager.input.KeyHandler;
import org.gugino.gamemanager.input.MouseHandler;

public class GameManager{

	private static WindowData windowData;
	
	private JFrame gameWindow;
	
	private Canvas gameCanvas;
	
	private Thread gameThread;
	
	private GameLoop gameLoop;
	
	private Game game;
	
	public static KeyHandler keyHandler;
	public static MouseHandler mouseHandler;
	
	private boolean showDebug = false;
	
	public GameManager(Game _game){
		windowData = new WindowData();
		this.game = _game;
		setupGameWindow();
	}
	
	public GameManager(Game _game, WindowData _windowData) {
		windowData = _windowData;
		this.game = _game;
		setupGameWindow();
	}
	
	//Sets up game window depending on provided specs
	private void setupGameWindow() {
		gameWindow = new JFrame(windowData.windowTitle);
		
		gameWindow.setMinimumSize(new Dimension(windowData.windowWidth, windowData.windowHeight));
		gameWindow.setPreferredSize(new Dimension(windowData.windowWidth, windowData.windowHeight));
		gameWindow.setResizable(windowData.resizable);
		gameWindow.setDefaultCloseOperation(windowData.closeOperation);
		gameWindow.setLocationRelativeTo(gameWindow.getRootPane());
		
		gameCanvas = new Canvas();
		gameCanvas.setPreferredSize(gameWindow.getPreferredSize());
		
		gameWindow.add(gameCanvas);
		
		gameWindow.pack();
		gameWindow.setVisible(true);
		gameWindow.setFocusable(false);
		gameCanvas.requestFocus();
		
		gameLoop = new GameLoop(this);
		gameThread = new Thread(gameLoop);
		
		initListeners();
		
		gameThread.start();
	}
	
	//Initializes misc. listeners
	private void initListeners() {		
		//Adds Key Handler
		keyHandler = new KeyHandler();
		gameCanvas.addKeyListener(keyHandler);
		
		//Adds Mouse Handler
		mouseHandler = new MouseHandler();
		gameCanvas.addMouseListener(mouseHandler);
		gameCanvas.addMouseMotionListener(mouseHandler);
		gameCanvas.addMouseWheelListener(mouseHandler);
	
		gameCanvas.addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				super.componentResized(e);
				windowData.windowCurrentWidth = gameCanvas.getWidth();
				windowData.windowCurrentHeight = gameCanvas.getHeight();
				game.windowResize();
			}
		});
	}
	
	protected void stopGameThread() {
		try {
			gameThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	//GETTERS
	
	public Game getGame() {
		return game;
	}
	
	public boolean getShowDebug() {
		return showDebug;
	}
	
	public JFrame getGameWindow() {
		return gameWindow;
	}
	
	public Canvas getGameCanvas() {
		return gameCanvas;
	}

	public Thread getGameThread() {
		return gameThread;
	}
	
	public GameLoop getGameLoop() {
		return gameLoop;
	}
	
	public static WindowData getWindowData() {
		return windowData;
	}

	//SETTERS
	
	public void setShowDebug(boolean _show) {
		showDebug = _show;
	}
}
