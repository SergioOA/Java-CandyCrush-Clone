package es.sergio.ornaque;

import javax.swing.JFrame;

public class GameFrame extends JFrame {
	
	private static final long serialVersionUID = -4962272081001765514L;
	
	public GameFrame() {
		super.setTitle("Game Dev");
		super.setSize(1200, 800);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		super.setResizable(false);
	}
	
}
