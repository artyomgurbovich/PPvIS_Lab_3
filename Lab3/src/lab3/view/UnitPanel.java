package lab3.view;

import java.awt.*;
import java.awt.geom.Line2D;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class UnitPanel extends JPanel {
	
	private int unitSize = 1;
	private final int SIZE_Y = 80;
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.draw(new Line2D.Double(unitSize/2, SIZE_Y/2, unitSize+unitSize/2, SIZE_Y/2));
		g2.draw(new Line2D.Double(unitSize/2, SIZE_Y/4, unitSize/2, SIZE_Y-SIZE_Y/4));
		g2.draw(new Line2D.Double(unitSize+unitSize/2, SIZE_Y/4, unitSize+unitSize/2, SIZE_Y-SIZE_Y/4));
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(unitSize * 2, SIZE_Y);
	}
	
	public void update(int unitSize) {
		this.unitSize = unitSize;
		updateUI();
	}
}
