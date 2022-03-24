package es.sergio.ornaque;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import es.sergio.ornaque.board.Board;
import es.sergio.ornaque.resources.ResourcesManager;


public class GamePanel extends JPanel {

	private static final long serialVersionUID = -7467691334632270363L;
	private BufferedImage background;
	private Board board;
	
	public GamePanel() {
		Point location = new Point(150, 50);
		this.board = new Board("map_00", location);
		super.setPreferredSize(new Dimension(1200, 800));
		super.setBackground(Color.BLACK);
		super.setDoubleBuffered(true);
		super.setFocusable(true);
		this.background = ResourcesManager.getImage("board/background.png");
	}
	
	public void tick() {
		this.update();
		super.repaint();
	}
	
	public void update() {
		board.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.paintBackground(g2);
		board.draw(g2);
		g2.dispose();
	}
	
//	private void scaleGraphics(Graphics2D g2) {
//		Dimension size = super.getBounds().getSize();
//		double heightScale = size.getHeight() / 720;
//		double widthScale = size.getWidth() / 1280;
//		g2.scale(widthScale, heightScale);
//	}
	
	private void paintBackground(Graphics2D g2) {
		g2.drawImage(background, 0, 0, 1200, 800, null);
	}
	
}
