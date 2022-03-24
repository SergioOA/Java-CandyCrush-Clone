package es.sergio.ornaque.board;

import java.awt.Graphics2D;
import java.awt.Point;

import es.sergio.ornaque.board.square.SquaresManager;

public class Board {
	
	private SquaresManager squaresManager;
	
	public Board(String map, Point location) {
		this.squaresManager = new SquaresManager(map, location);
	}
	
	public void update() {
		squaresManager.update();
	}
	
	public void draw(Graphics2D g2) {
		squaresManager.draw(g2);
	}

	public SquaresManager getSquaresManager() {
		return squaresManager;
	}

	public void setSquaresManager(SquaresManager squaresManager) {
		this.squaresManager = squaresManager;
	}
	
}