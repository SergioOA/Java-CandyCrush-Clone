package es.sergio.ornaque.resources;

import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class ResourcesManager {

	public static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	
	public static void load() {
		saveImage("board/tiles/blue.png");
		saveImage("board/tiles/green.png");
		saveImage("board/tiles/orange.png");
		saveImage("board/tiles/purple.png");
		saveImage("board/tiles/red.png");
		saveImage("board/tiles/yellow.png");
		saveImage("board/tiles/clicked.png");
		saveImage("board/background.png");
	}
	
	public static BufferedImage getImage(String path) {
		if(images.get(path) == null) {
			saveImage(path);
		}
		return images.get(path);
	}
	
	private static void saveImage(String path) { //"res/board/tiles/blue.png"
		try {
			images.put(path, ImageIO.read(new FileInputStream("res/" + path)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
