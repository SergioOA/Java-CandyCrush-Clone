package es.sergio.ornaque.inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.sergio.ornaque.GameFrame;

public class KeyHandler implements KeyListener {

	private static HashMap<Integer, Boolean> pressedKeys = new HashMap<Integer, Boolean>();
	private static HashMap<Integer, List<Runnable>> onKeyPressedRunnables = new HashMap<Integer, List<Runnable>>();
	private static List<KeyResponse> onAnyKeyPressedRunnables = new ArrayList<KeyResponse>();
	
	public KeyHandler(GameFrame gameFrame) {
		gameFrame.addKeyListener(this);
	}
	
	public void keyTyped(KeyEvent e) {
		
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(!pressedKeys.containsKey(keyCode)) {
			this.uniqueKeyPress(e);
		}
		pressedKeys.put(keyCode, true);
	}

	public void keyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
	}
	
	public static void onKeyPressed(KeyResponse callback) {
		onAnyKeyPressedRunnables.add(callback);
	}
	
	public static void onKeyPressed(int key, Runnable runnable) {
		List<Runnable> current = onKeyPressedRunnables.get(key);
		if(current != null) {
			current.add(runnable);
		} else {
			List<Runnable> newList = new ArrayList<Runnable>();
			newList.add(runnable);
			onKeyPressedRunnables.put(key, newList);
		}
	}
	
	public static boolean isKeyPressed(int keyCode) {
		return pressedKeys.containsKey(keyCode);
	}
	
	private void uniqueKeyPress(KeyEvent e) {
		for(KeyResponse callback : onAnyKeyPressedRunnables) {
			callback.run(e);
		}
		
		List<Runnable> runnables = onKeyPressedRunnables.get(e.getKeyCode());
		if(runnables == null) {
			return;
		}
		
		for(Runnable runnable : runnables) {
			runnable.run();
		}
	}
	
}
