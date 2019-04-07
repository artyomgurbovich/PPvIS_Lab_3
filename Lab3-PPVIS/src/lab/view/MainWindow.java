package lab.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import lab.model.GraphsManager;
import lab.model.Point;
import lab.view.table.PointsTableModel;

public class MainWindow {
	
	private final int FRAME_SIZE_X = 1250;
	private final int FRAME_SIZE_Y = 1000;
	private final int LEFT_PANEL_SIZE_X = 200;
	private final int RIGHT_PANEL_SIZE_X = 1050;
	private final int TEXT_FIELD_SIZE_X = 200;
	private final int TEXT_FIELD_SIZE_Y = 20;
	private final String START_TITLE = "Start";
	private final String STOP_TITLE = "Stop and clear";
	private final String ERROR_TITLE = "Error";
	private final int N_MIN = 2;
	private final int K_MIN = 1;
	private final int TABLE_GRAPH_INDEX = 1;
	
	private JTextField maxField;
	private JTextField sortsNumberField;
	private JButton startButton;
	private JButton stopButton;
	private PointsTableModel pointsTableModel;
	private GraphicComponent graphicComponent;
	
	public MainWindow() { 
		startButton = new JButton(START_TITLE);
		stopButton = new JButton(STOP_TITLE);
	}
	
	public void start() {
		JFrame frame = new JFrame("Lab3");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JMenu commandsMenu = new JMenu("Commands");
		JMenuItem startCommand = new JMenuItem(START_TITLE);
		JMenuItem stopCommand = new JMenuItem(STOP_TITLE);
		commandsMenu.add(startCommand);
		commandsMenu.add(stopCommand);
		JMenuBar menuBar = new JMenuBar();
        menuBar.add(commandsMenu);
        frame.setJMenuBar(menuBar);
        
		JPanel mainPanel = new JPanel();
		JPanel leftPanel = new JPanel();
		JPanel rightPanel = new JPanel();
		
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.X_AXIS));
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
		
		frame.setSize(new Dimension(FRAME_SIZE_X, FRAME_SIZE_Y));
		leftPanel.setMaximumSize(new Dimension(LEFT_PANEL_SIZE_X, FRAME_SIZE_Y));
		rightPanel.setMaximumSize(new Dimension(RIGHT_PANEL_SIZE_X, FRAME_SIZE_Y));
		
		JLabel maxLabel = new JLabel("N:");
		JLabel sortsNumberLabel = new JLabel("K:");
		maxField = new JTextField();
		sortsNumberField = new JTextField();
		maxField.setMaximumSize(new Dimension(TEXT_FIELD_SIZE_X, TEXT_FIELD_SIZE_Y));
		sortsNumberField.setMaximumSize(new Dimension(TEXT_FIELD_SIZE_X, TEXT_FIELD_SIZE_Y));
		
		JPanel runPanel = new JPanel();
		runPanel.setLayout(new BoxLayout(runPanel, BoxLayout.X_AXIS));
		runPanel.add(startButton);
		runPanel.add(stopButton);
		
		pointsTableModel = new PointsTableModel(new ArrayList<Point>());
		JTable table = new JTable(pointsTableModel);
		
		leftPanel.add(new JScrollPane(table));
		leftPanel.add(maxLabel);
		leftPanel.add(maxField);
		leftPanel.add(sortsNumberLabel);
		leftPanel.add(sortsNumberField);
		leftPanel.add(runPanel);
		
		graphicComponent = new GraphicComponent();
		rightPanel.add(graphicComponent);
		
		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		frame.add(mainPanel);
        frame.setVisible(true);
	}
	
	public int getMin() {
		return N_MIN;
	}
	
	public int getMax() {
		return Integer.parseInt(maxField.getText());
	}
	
	public int getSortsNumber() {
		return Integer.parseInt(sortsNumberField.getText());
	}
	
	public void setStartListner(ActionListener startListener) {
		startButton.addActionListener(startListener);
	}
	
	public void setStopListner(ActionListener stopListener) {
		stopButton.addActionListener(stopListener);
	}
	
	public void update(GraphsManager graphsManager) {
		pointsTableModel.updateData(graphsManager.getGraphByIndex(TABLE_GRAPH_INDEX));
		graphicComponent.updateData(graphsManager);
	}
	
	public boolean valuesCorrect() {
		String maxFieldText = maxField.getText();
		String sortsNumberFieldText = sortsNumberField.getText();
		if (isInteger(maxFieldText) && isInteger(sortsNumberFieldText)) {
    		int maxFieldValue = Integer.parseInt(maxFieldText);
    		int sortsNumberFieldValue = Integer.parseInt(sortsNumberFieldText);
    		if (maxFieldValue >= N_MIN) {
    			if (sortsNumberFieldValue >= K_MIN) {
    				return true;
    			} else {
        			JOptionPane.showMessageDialog(new JFrame(), "K smaller " + K_MIN + "!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    			}
    		} else {
    			JOptionPane.showMessageDialog(new JFrame(), "N smaller " + N_MIN + "!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    		}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Incorrect N and K values!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}
	
	private boolean isInteger(String text) {
	    try {
	        Integer.parseInt(text); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
}
