package es.sergio.ornaque.board.square;

import java.awt.image.BufferedImage;
import java.util.Random;

import es.sergio.ornaque.resources.ResourcesManager;

public enum SquareType {
	BLUE,
	GREEN,
	ORANGE,
	PURPLE,
	RED,
	YELLOW;
	
	public BufferedImage getImage() {
		return ResourcesManager.getImage("board/tiles/" + this.toString().toLowerCase() + ".png");
	}
	
	public static SquareType getRandom() {
		return values()[new Random().nextInt(SquareType.values().length)];
	}
}