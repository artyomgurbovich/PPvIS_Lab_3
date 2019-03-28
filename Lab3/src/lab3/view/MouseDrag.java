package lab3.view;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

public class MouseDrag extends MouseAdapter {
	
	private JScrollPane scrollPane;
	private int x, y;
	private boolean rightMouseButtonPressed = false;
	private final float SMOOTH = 0.9f;
	
	public MouseDrag(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}
	
    @Override
    public void mousePressed(MouseEvent e) {
    	if(e.getButton() == MouseEvent.BUTTON3) {
    		x = e.getX();
    		y = e.getY();
    		rightMouseButtonPressed = true;
    	}
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    	if(rightMouseButtonPressed) {
    		rightMouseButtonPressed = false;
    	}
    }
    
    @Override
    public void mouseDragged(MouseEvent e){
    	if (rightMouseButtonPressed) {
            JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            
            horizontalScrollBar.setValue(horizontalScrollBar.getValue() + (int)((x - e.getX()) * SMOOTH));
            verticalScrollBar.setValue(verticalScrollBar.getValue() + (int)((y - e.getY()) * SMOOTH));

            x = e.getX();
            y = e.getY();
    	}
    }
}
