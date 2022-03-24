package es.sergio.ornaque.inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import es.sergio.ornaque.GameFrame;

public class MouseHandler implements MouseListener {

	private static List<MouseResponse> clickListener = new ArrayList<MouseResponse>();
 
	public MouseHandler(GameFrame gameFrame) {
		gameFrame.addMouseListener(this);
	}
	
    public void mouseReleased(MouseEvent arg0) {
    	
    }
   
    public void mousePressed(MouseEvent e) {

    }
   
    public void mouseExited(MouseEvent arg0) {
    	
    }

    public void mouseEntered(MouseEvent arg0) {
    	
    }
   
    public void mouseClicked(MouseEvent e) {
    	for(MouseResponse mr : clickListener) {
    		mr.run(e);
    	}
    }
    
    public static void addListener(MouseResponse mouseResponse) {
    	clickListener.add(mouseResponse);
    }

}