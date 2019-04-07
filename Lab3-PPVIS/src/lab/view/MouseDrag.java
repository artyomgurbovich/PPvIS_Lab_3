package lab.view;

import java.awt.event.*;
import javax.swing.*;

import lab.model.Point;

public class MouseDrag extends MouseAdapter {
	
	private JScrollPane scrollPane;
	private Point point;
	private boolean rightMouseButtonPressed = false;
	private final float SMOOTH = 0.9f;
	
	public MouseDrag(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
    @Override
    public void mousePressed(MouseEvent e) {
    	if (e.getButton() == MouseEvent.BUTTON3) {
    		point.setX(e.getX());
    		point.setY(e.getY());
    		rightMouseButtonPressed = true;
    	}
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    	if (rightMouseButtonPressed) {
    		rightMouseButtonPressed = false;
    	}
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
    	if (rightMouseButtonPressed) {
            JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            
            horizontalScrollBar.setValue(horizontalScrollBar.getValue() + (int)((point.getX() - e.getX()) * SMOOTH));
            verticalScrollBar.setValue(verticalScrollBar.getValue() + (int)((point.getY() - e.getY()) * SMOOTH));

    		point.setX(e.getX());
    		point.setY(e.getY());
    	}
    }
}
