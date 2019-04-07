package lab.view;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.List;
import javax.swing.JPanel;
import lab.model.Graph;
import lab.model.GraphsManager;
import lab.model.Point;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel {
	
	private final String FONT_NAME = "Arial";
	
	private final int DEFAULT_SIZE = 747;
	private int panelSize = DEFAULT_SIZE;
	
	private final int DEFAULT_MULT = 30;
	private final float MIN_SCALE = 1.0f / DEFAULT_MULT;
	private float scale = 1.0f;
	
	private GraphsManager graphsManager;
	
	public DrawPanel() {
		graphsManager = new GraphsManager(1);
		setBackground(Color.BLACK);
	}
	
	public boolean isEmpty() {
		return graphsManager.isEmpty();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(panelSize, panelSize);
	}
	
	@Override
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		Graphics2D graphics2 = (Graphics2D) graphics;
		graphics2.setFont(new Font(FONT_NAME, Font.PLAIN, getUnit() / 2));
		drawAxis(graphics2);
		drawPoints(graphics2);
	}
	
	private void drawPoints(Graphics2D g2) {
		g2.setPaint(Color.WHITE);
		if (!graphsManager.isEmpty()
				&& graphsManager.getGraphByIndex(0).getPoints().size() > 1) {
			List<Graph> graphs = graphsManager.getGraphs();
			List<Point> points = null;
			for (Graph graph: graphs) {
				points = graph.getPoints();
				for (int index = 0; index < points.size() - 1; index++) {
					g2.draw(new Line2D.Double(panelSize/2 + points.get(index).getX() * getUnit(),
							                  panelSize/2 - points.get(index).getY() * getUnit(),
							                  panelSize/2 + points.get(index + 1).getX() * getUnit(),
							                  panelSize/2 - points.get(index + 1).getY() * getUnit()));
				}
			}
		}
	}
	
	private void drawAxis(Graphics2D graphics2) {
		if (!graphsManager.isEmpty()) {
			graphics2.setPaint(Color.GRAY);
			int unit = getUnit();
			int longestPoint = getLongestPoint();
			int newPanelSize = (longestPoint * 2 + 4) * unit;
			if (newPanelSize > DEFAULT_SIZE) {
				panelSize = newPanelSize;
			}
			graphics2.draw(new Line2D.Double(2*unit, panelSize/2, panelSize-unit, panelSize/2));
			graphics2.draw(new Line2D.Double(panelSize/2, unit, panelSize/2, panelSize-2*unit));
			int index = 0;
			int end = (panelSize / unit - 4) / 2;
			while (index < end) {
				index++;
				graphics2.draw(new Line2D.Double(panelSize/2-unit/4, panelSize/2+index*unit, panelSize/2+unit/4, panelSize/2+index*unit));
				graphics2.draw(new Line2D.Double(panelSize/2+index*unit, panelSize/2-unit/4, panelSize/2+index*unit, panelSize/2+unit/4));
				graphics2.draw(new Line2D.Double(panelSize/2-unit/4, panelSize/2-index*unit, panelSize/2+unit/4, panelSize/2-index*unit));
				graphics2.draw(new Line2D.Double(panelSize/2-index*unit, panelSize/2-unit/4, panelSize/2-index*unit, panelSize/2+unit/4));
				graphics2.drawString(String.valueOf(index), panelSize/2+unit/4, panelSize/2-index*unit);
				graphics2.drawString(String.valueOf(-index), panelSize/2+unit/4, panelSize/2+index*unit);
				graphics2.drawString(String.valueOf(-index), panelSize/2-index*unit, panelSize/2-unit/4);
				graphics2.drawString(String.valueOf(index), panelSize/2+index*unit, panelSize/2-unit/4);
			}
			graphics2.draw(new Line2D.Double(panelSize-unit, panelSize/2, panelSize-2*unit, panelSize/2+unit/4));
			graphics2.draw(new Line2D.Double(panelSize-unit, panelSize/2, panelSize-2*unit, panelSize/2-unit/4));
			graphics2.draw(new Line2D.Double(panelSize/2, unit, panelSize/2+unit/4, 2*unit));
			graphics2.draw(new Line2D.Double(panelSize/2, unit, panelSize/2-unit/4, 2*unit));
			graphics2.drawString(String.valueOf("X"), panelSize-unit, panelSize/2);
			graphics2.drawString(String.valueOf("Y"), panelSize/2, unit);
			graphics2.drawString(String.valueOf("0"), panelSize/2+unit/4, panelSize/2-unit/4);
		}
	}
	
	public void setGraphs(GraphsManager graphsManager) {
		this.graphsManager = graphsManager;
		repaint();
	}
	
	public int getLongestPoint() {
		int result = 0;
		List<Graph> graphs = graphsManager.getGraphs();
		List<Point> points = null;
		for (Graph graph: graphs) {
			points = graph.getPoints();
			for (Point point: points) {
				result = (int) Math.ceil(Math.max(Math.max(Math.abs(point.getX()), Math.abs(point.getY())), result));
			}
		}
		return result;
	}

	public int getUnit() {
		return (int) (DEFAULT_MULT * scale);
	}
	
	public float zoomIn() {
		scale *= 1.1f;
		updateUI();
		return scale;
	}
	
	public float zoomOut() {
		if (scale / 1.1f > MIN_SCALE) {
			scale /= 1.1f;
		}
		updateUI();
		return scale;
	}
}