package es.sergio.ornaque.board.square;

import java.awt.Graphics2D;
import java.awt.Point;

import es.sergio.ornaque.resources.ResourcesManager;

public class Square {
	
	public static final double FALLING_TICK_SPEED = 8f;
	public static final int TILE_SIZE = 100;

	private int column;
	private int row;
	private Point location;
	private boolean enabled;
	private SquareType type = null;
	private boolean clicked = false;
	
	/* Cooldown a la hora de generar nuevos valores */
	private float cooldown = 0;
	
	/* Distancia que tiene que caer el dibujo */
	private int offset = 0;

	public Square(int column, int row, Point location, boolean enabled) {
		this.column = column;
		this.row = row;
		this.location = location;
		this.enabled = enabled;
	}
	
	/**
	 * Agrega un dibujo aleatorio al slot y la altura a 
	 * la que se encuentra el dibujo respecto al slot.
	 */
	public void addRandomContent(int offset) {
		this.type = SquareType.getRandom();
		this.offset = offset;
	}
	
	/**
	 * Hacer caer el dibujo
	 */
	public boolean decreaseOffset() {
		this.offset -= FALLING_TICK_SPEED;
		if(this.offset < 0) {
			this.offset = 0;
		}
		return this.offset == 0;
	}
	
	/*
	 * Regresa si el slot esta vacío
	 */
	public boolean isEmpty() {
		return this.type == null;
	}
	
	/**
	 * Generar un valor nuevo
	 */
	public void generate() {
		if(this.cooldown == 0) {
			this.type = SquareType.getRandom();
			this.offset = (this.row + 1) * TILE_SIZE;
			this.cooldown = TILE_SIZE;
			return;
		}
		this.cooldown -= FALLING_TICK_SPEED;
		if(this.cooldown < 0) {
			this.cooldown = 0;
		}
	}
	
	/*
	 * Dibujar
	 */
	public void draw(Graphics2D g2) {
		this.drawContent(g2);
	}
	
	/*
	 * Dibujar el contenido
	 */
	public void drawContent(Graphics2D g2) {
		if(!isEmpty()) {
			g2.drawImage(type.getImage(), location.x, location.y - this.offset, TILE_SIZE, TILE_SIZE, null);
		}
		if(this.clicked) {
			g2.drawImage(ResourcesManager.getImage("board/tiles/clicked.png"), location.x, location.y - this.offset, TILE_SIZE, TILE_SIZE, null);
		}
	}
	
	/**
	 * Resetea el cuadrado
	 */
	public void destroy() {
		this.type = null;
		this.offset = 0;
		this.cooldown = 0;
	}
	
	/*
	 * Setear el offset en función del tamaño de los
	 * cuadrados
	 */
	public void setOffsetTiles(int tiles) {
		this.offset = tiles * TILE_SIZE;
	}
	
	/*
	 * Aumenta el offset en función del tamaño de los
	 * cuadrados
	 */
	public void increaseOffsetTiles(int tiles) {
		this.offset += tiles * TILE_SIZE;
	}
	
	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public SquareType getType() {
		return type;
	}

	public void setType(SquareType type) {
		this.type = type;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public boolean isClicked() {
		return clicked;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}
	
}
