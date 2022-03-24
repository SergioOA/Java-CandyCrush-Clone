package es.sergio.ornaque.board.square;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.SwingUtilities;

import es.sergio.ornaque.Main;
import es.sergio.ornaque.inputs.KeyHandler;
import es.sergio.ornaque.inputs.MouseHandler;

public class SquaresManager {
	
	private Square[][] squares;
	private Point location;
	private Point clicked;
	private boolean disabled = true;
	
	public SquaresManager(String map, Point location) {
		this.location = location;
		this.squares = SquareUtils.loadMapFile(map, location);
		
		KeyHandler.onKeyPressed(KeyEvent.VK_R, () -> {
			this.reset();
		});
		
		MouseHandler.addListener((MouseEvent e) -> {
			if(this.disabled) return;
			
			double i =  (e.getX() - location.getX()) / Square.TILE_SIZE;
			double j =  (e.getY() - 30 - location.getY()) / Square.TILE_SIZE;
			
			if(i < 0 || j < 0 || this.squares.length <= (int) i || this.squares[(int) i].length <= (int) j) {
				return;
			}
			
			this.clickSquare((int) i,(int) j);
		});
	}

	public void update() {
		boolean isFalling = updateFallingSquares();
		boolean updatedOffsets = updateOffsets();
		if(!updatedOffsets) breakSquares();
		boolean generated = generateEmptySquares();
		this.disabled = isFalling || updatedOffsets || generated;
	}
	
	public void draw(Graphics2D g2) {
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[i].length; j++) {
				squares[i][j].draw(g2);
			}
		}
	}
	
	private void clickSquare(int i, int j) {
		if(this.clicked != null && this.clicked.x == i && this.clicked.y == j) {
			this.squares[i][j].setClicked(false);
			this.clicked = null;
		} else if(this.clicked != null) {
			System.out.println("move");
			Square clickedSquare = this.squares[this.clicked.x][this.clicked.y];
			Square otherSquare = this.squares[i][j];
			SquareType clickedSquareType = clickedSquare.getType();
			clickedSquare.setType(otherSquare.getType());
			otherSquare.setType(clickedSquareType);
			clickedSquare.setClicked(false);
			this.clicked = null;
		} else {
			if(this.clicked != null) {
				this.squares[this.clicked.x][this.clicked.y].setClicked(false);
			}
			this.clicked = new Point(i, j);
			this.squares[i][j].setClicked(true);
		}
	}
	 
	private void breakSquares() {
		ArrayList<int[]> result = new ArrayList<int[]>();
		// Columnas
		for(int i = 0; i < squares.length; i++) {
			SquareType lastType = null;
			result.add(new int[] {i, 0, i, 0});
			for(int j = 0; j < squares[i].length; j++) {
				Square current = squares[i][j];
				if(current.isEnabled() && current.getType() == lastType) {
					result.get(result.size() - 1)[3] = j;
				} else {
					result.add(new int[] {i, j, i, j});
					lastType = current.getType();
				}
			}
		}
		// Filas
		for(int j = 0; j < squares[0].length; j++) {
			SquareType lastType = null;
			result.add(new int[] {0, j, 0, j});
			for(int i = 0; i < squares.length; i++) {
				Square current = squares[i][j];
				if(current.isEnabled() && current.getType() == lastType) {
					result.get(result.size() - 1)[2] = i;
				} else {
					result.add(new int[] {i, j, i, j});
					lastType = current.getType();
				}
			}
		}
		for(int[] val : result) {
			if(val[3] - val[1] >= 2 || val[2] - val[0] >= 2) {
				destroyArea(val[0], val[1], val[2], val[3]);
			}
		}
		
	}
	
	private void destroyArea(int x1, int y1, int x2, int y2) {
		int xStart = x1 < x2 ? x1 : x2;
		int xEnd = x1 > x2 ? x1 : x2;
		int yStart = y1 < y2 ? y1 : y2;
		int yEnd = y1 > y2 ? y1 : y2;
		for(int x = xStart; x <= xEnd; x++) {
			for(int y = yStart; y <= yEnd; y++) {
				this.squares[x][y].destroy();
			}
		}
	}
	
	private boolean updateFallingSquares() {
		boolean updated = false;
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[i].length; j++) {
				Square current = squares[i][j];
				Square below = getSquareBelow(i, j);
				if(current.getType() != null && below != null && below.isEmpty()) {
					fallToNextSquare(current, below);
					updated = true;
				}
			}
		}
		return updated;
	}
	
	private boolean updateOffsets() {
		boolean updated = false;
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[i].length; j++) {
				if(!squares[i][j].decreaseOffset()) {
					updated = true;
				}
			}
		}
		return updated;
	}
	
	/*
	 * Genera los cuadrados vacíos, regresa True si se ha
	 * generado almenos un cuadrado.
	 */
	private boolean generateEmptySquares() {
		boolean generated = false;
		for(int i = 0; i < squares.length; i++) {
			Square current = this.getFirstSquare(i);
			if(current.isEmpty()) {
				current.generate();
				generated = true;
			}
		}
		return generated;
	}
	
	private Square getSquareBelow(int column, int row) {
		for(int i = row + 1; i < squares[column].length; i++) {
			Square current = squares[column][i];
			if(current.isEnabled()) {
				return current;
			}
		}
		return null;
	}
	
	private Square getFirstSquare(int column) {
		for(int i = 0; i < squares[column].length; i++) {
			Square current = squares[column][i];
			if(current.isEnabled()) {
				return current;
			}
		}
		return null;
	}
	
	private void fallToNextSquare(Square current, Square below) {
		if(current.getType() != null && below != null && below.isEmpty()) {
			below.setType(current.getType());
			below.setOffset(current.getOffset());
			below.increaseOffsetTiles(below.getRow() - current.getRow());
			current.setOffset(0);
			current.setType(null);
		}
	}
	
	public void destroyColumn(int column, int start, int end) {
		for(int i = start; i <= end; i++) {
			this.squares[column][i].destroy();
		}
	}
	
	public Square getSquare(int column, int row) {
		return this.squares[column][row];
	}
	
	public void reset() {
		for(int i = 0; i < squares.length; i++) {
			for(int j = 0; j < squares[i].length; j++) {
				squares[i][j].destroy();

			}
		}
	}
}
