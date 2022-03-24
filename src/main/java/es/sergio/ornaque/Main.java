package es.sergio.ornaque;

import es.sergio.ornaque.inputs.KeyHandler;
import es.sergio.ornaque.inputs.MouseHandler;
import es.sergio.ornaque.resources.ResourcesManager;

public class Main {
	
	private static GameFrame gameFrame;
	private static GamePanel gamePanel;

	public static void main(String[] args) {
		
		// Resources
		ResourcesManager.load();
		
		// Drawing
		Main.gameFrame = new GameFrame();
		Main.gamePanel = new GamePanel();
		gameFrame.add(gamePanel);
		gameFrame.pack();
		
		// Inputs
		new KeyHandler(gameFrame);
		new MouseHandler(gameFrame);
		
		// Game Loop
		GameLoop gameLoop = new GameLoop();
		gameLoop.onTick(() -> {
			Main.gamePanel.tick();
		});
		
	}
	
	public static GameFrame getGameFrame() {
		return Main.gameFrame;
	}
	
	public static GamePanel getGamePanel() {
		return Main.gamePanel;
	}
	
}
