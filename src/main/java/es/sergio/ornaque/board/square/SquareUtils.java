package es.sergio.ornaque.board.square;

import java.awt.Point;
import java.io.InputStream;
import java.util.Scanner;

import es.sergio.ornaque.Main;

public class SquareUtils {
	
	public static Square[][] loadMapFile(String map, Point topLeftCorner) {
	try {
		InputStream is = Main.class.getClassLoader().getResourceAsStream("board/maps/" + map + "/squares.cfg");
		Scanner scan = new Scanner(is);
		String[] dimensions = scan.nextLine().split(" ");
		int columns = Integer.parseInt(dimensions[0]);
		int height = Integer.parseInt(dimensions[1]);
		int line = 0;
		Square[][] squares = new Square[columns][height];
		while(scan.hasNextLine()){
			String[] lineSplit = scan.nextLine().split(", ");
			for(int column = 0; column < lineSplit.length; column++) {
				boolean enabled = Integer.parseInt(lineSplit[column]) == 1;
				Point location = new Point((Square.TILE_SIZE * column) + topLeftCorner.x, (Square.TILE_SIZE * line) + topLeftCorner.y);
				squares[column][line] = new Square(column, line, location, enabled);
			}
			line++;
		}
		scan.close();
		return squares;
	} catch (Exception e) {
		e.printStackTrace();
	}
	return null;
}
	
	
	
	
	
//	public static final int SQUARES_PNG_SIZE = 90;
//	
//	public static void draw(Graphics2D g2, SquareType st, Point location) {
//		if(st == SquareType.NONE) return;
//		g2.drawImage(st.getImage(), location.x, location.y, Square.size, Square.size, null);
//	}
//	
//	public static void drawFallingSquare(Graphics2D g2, SquareType st, float offset, boolean top, Point location) {
//		if(st == SquareType.NONE) return;
//		
//		int imageHeight = top ? (int) (SQUARES_PNG_SIZE * (1 - offset)) : (int) (SQUARES_PNG_SIZE * offset);
//		int drawHeight = top ? (int) (Square.size * (1 - offset)) : (int) (Square.size * offset);
//		
//		if(imageHeight == 0 || st.getImage() == null) {
//			return;
//		}
//	
//		// Si es la imagen de arriba solo es necesario dibujar la parte de arriba
//		if(top) {
//			BufferedImage img = st.getImage().getSubimage(0, 0, SQUARES_PNG_SIZE, imageHeight);
//			g2.drawImage(img, location.x, location.y + Square.size - drawHeight, Square.size, drawHeight, null);
//		} 
//		
//		// Si es la parte de abajo hay que dibujar la parte que cae dentro del cuadrado
//		else {
//			BufferedImage img = st.getImage().getSubimage(0, SQUARES_PNG_SIZE - imageHeight, SQUARES_PNG_SIZE, imageHeight);
//			g2.drawImage(img, location.x, location.y, Square.size, drawHeight, null);
//		}
//	}
//	
//	public static void drawGeneratingSquare(Graphics2D g2, SquareType st, float offset, Point location) {
//		if(st == SquareType.NONE) return;
//		
//		int imageHeight = (int) (SQUARES_PNG_SIZE * offset);
//		int drawHeight = (int) (Square.size * offset);
//		
//		System.out.println(st);
//		
//		BufferedImage img = st.getImage().getSubimage(0, SQUARES_PNG_SIZE - imageHeight, SQUARES_PNG_SIZE, imageHeight);
//		g2.drawImage(img, location.x, location.y, Square.size, drawHeight, null);
//	}
}
