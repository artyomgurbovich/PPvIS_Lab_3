package lab3.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.*;
import java.util.List;
import javax.swing.*;

import lab3.model.Point;

@SuppressWarnings("serial")
public class GraphicComponent extends JPanel {
	
	private final int SIZE_X = 750;
	private final int SIZE_Y = 750;
	private final int INFO_SIZE_X = 750;
	private final int INFO_SIZE_Y = 120;
	private final int UNIT_SIZE_X = 200;
	private final int UNIT_SIZE_Y = 100;
	
	private DrawPanel drawPanel;
	private UnitPanel unitPanel;
	private JLabel coloredLabel;
	private JLabel scaleLabel;
	private final String SCALE_TEXT_START = "    Scale = ";
	private final String SCALE_TEXT_END = "    ";
	private float scale = 1.0f;
	
	public GraphicComponent() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		drawPanel = new DrawPanel();
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
		infoPanel.setPreferredSize(new Dimension(INFO_SIZE_X, INFO_SIZE_Y));
		
		JScrollPane scrollPane = new JScrollPane(drawPanel);
		scrollPane.setPreferredSize(new Dimension(SIZE_X, SIZE_Y));
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.addMouseWheelListener(new MouseWheelListener() {
		      public void mouseWheelMoved(MouseWheelEvent e) {
		    	  if (e.isControlDown() && !drawPanel.isEmpty()) {
		            	if (e.getWheelRotation() < 0) {
		            		scale = drawPanel.zoomIn();
		            	} else if (e.getWheelRotation() > 0) {
		            		scale = drawPanel.zoomOut();
		            	}
		            	scaleLabel.setText(SCALE_TEXT_START + String.valueOf(scale) + SCALE_TEXT_END);
		            	unitPanel.update(drawPanel.getUnit());
		            }
		      }
		});
		
		MouseDrag mouseListeners = new MouseDrag(scrollPane);
		drawPanel.addMouseListener(mouseListeners);
		drawPanel.addMouseMotionListener(mouseListeners);
		
		JButton zoomInButton = new JButton("Zoom In");
		zoomInButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scale = drawPanel.zoomIn();
            	scaleLabel.setText(SCALE_TEXT_START + String.valueOf(scale) + SCALE_TEXT_END);
            	unitPanel.update(drawPanel.getUnit());
            }
        });
		
		JButton zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	scale = drawPanel.zoomOut();
            	scaleLabel.setText(SCALE_TEXT_START + String.valueOf(scale) + SCALE_TEXT_END);
            	unitPanel.update(drawPanel.getUnit());
            }
        });
		
		coloredLabel = new JLabel();
		coloredLabel.setOpaque(true);
		scaleLabel = new JLabel(SCALE_TEXT_START + String.valueOf(scale) + SCALE_TEXT_END);
		
		unitPanel = new UnitPanel();
		JScrollPane unitScrollPane = new JScrollPane(unitPanel);
		unitScrollPane.setMaximumSize(new Dimension(UNIT_SIZE_X, UNIT_SIZE_Y));
		unitScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		unitScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		
		infoPanel.add(zoomInButton);
		infoPanel.add(zoomOutButton);
		infoPanel.add(coloredLabel);
		infoPanel.add(scaleLabel);
		infoPanel.add(unitScrollPane);
		
		add(scrollPane);
		add(infoPanel);
		setMinimumSize(new Dimension(SIZE_X, SIZE_Y));
	}
	
	public void setColoredLabelText(String text) {
		coloredLabel.setText(text);
	}
	
	public void setCurrentFunctionIndex(int functionIndex) {
		if (functionIndex == View.FIRST_FORMULA_INDEX) {
			coloredLabel.setText(View.FIRST_FORMULA);
			coloredLabel.setBackground(Color.CYAN);
		} else if (functionIndex == View.SECOND_FORMULA_INDEX) {
			coloredLabel.setText(View.SECOND_FORMULA);
			coloredLabel.setBackground(Color.MAGENTA);
		}
		unitPanel.update(drawPanel.getUnit());
	}

	public void updateData(List<Point> points) {
		drawPanel.setPoints(points);
		drawPanel.updateUI();
	}
}
