package es.sergio.ornaque;

import java.util.ArrayList;
import java.util.List;

public class GameLoop implements Runnable {
	
	public static final int FPS = 60;
	private Thread gameThread;
	private int currentFPS = 0;
	private List<Runnable> runnables = new ArrayList<Runnable>();
	
	public GameLoop() {
		gameThread = new Thread(this);
		gameThread.start();
	}

	public void run() {
		double drawInterval = Math.pow(10, 9) / FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		while(gameThread != null) {
			currentTime = System.nanoTime();
			delta += (currentTime - lastTime) / drawInterval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				executeGameTick();
				delta--;
				drawCount++;
			}
			
			if(timer >= 1000000000) {
				currentFPS = drawCount;
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void executeGameTick() {
		for(Runnable runnable : runnables) {
			runnable.run();
		}
	}
	
	public void onTick(Runnable runnable) {
		this.runnables.add(runnable);
	}
	
	public int getCurrentFPS() {
		return this.currentFPS;
	}
	
}