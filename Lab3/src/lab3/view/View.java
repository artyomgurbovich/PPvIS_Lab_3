package lab3.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import lab3.model.Point;
import lab3.view.table.PointsTableModel;

public class View {
	private final int FRAME_SIZE_X = 1250;
	private final int FRAME_SIZE_Y = 1000;
	private final int LEFT_PANEL_SIZE_X = 200;
	private final int RIGHT_PANEL_SIZE_X = 1050;
	private final int TEXT_FIELD_SIZE_X = 200;
	private final int TEXT_FIELD_SIZE_Y = 20;
	public final static String FIRST_FORMULA = " f(x) = 3 * x + 1 ";
	public final static String SECOND_FORMULA = " f(x) = inclusion sort 2-N random arrays by K times ";
	public final static int FIRST_FORMULA_INDEX = 0;
	public final static int SECOND_FORMULA_INDEX = 1;
	public final static int ERROR_RESULT = -1;
	public final static int N_MIN = 2;
	private final int K_MIN = 1;
	private final String START_TITLE = "Start";
	private final String STOP_TITLE = "Stop and clear";
	private final String ERROR_TITLE = "Error";
	
	private JRadioButton firstRadioButton;
	private JRadioButton secondRadioButton;
	private JTextField minField;
	private JTextField maxField;
	private JTextField nField;
	private JTextField kField;
	private JButton startButton;
	private JButton stopButton;
	private PointsTableModel pointsTableModel;
	private GraphicComponent graphicComponent;
	
	public View() {
		JFrame frame = new JFrame("Lab 3");
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
		
		ButtonGroup buttonGroup = new ButtonGroup();
		firstRadioButton = new JRadioButton("First function", true);
		firstRadioButton.setToolTipText(FIRST_FORMULA);
		secondRadioButton = new JRadioButton("Second function", false);
		secondRadioButton.setToolTipText(SECOND_FORMULA);
		buttonGroup.add(firstRadioButton);
		buttonGroup.add(secondRadioButton);
		
		JLabel minLabel = new JLabel("Min:");
		JLabel maxLabel = new JLabel("Max:");
		minField = new JTextField();
		maxField = new JTextField();
		minField.setMaximumSize(new Dimension(TEXT_FIELD_SIZE_X, TEXT_FIELD_SIZE_Y));
		maxField.setMaximumSize(new Dimension(TEXT_FIELD_SIZE_X, TEXT_FIELD_SIZE_Y));
		
		JLabel nLabel = new JLabel("N:");
		JLabel kLabel = new JLabel("K:");
		nField = new JTextField();
		kField = new JTextField();
		nField.setMaximumSize(new Dimension(TEXT_FIELD_SIZE_X, TEXT_FIELD_SIZE_Y));
		kField.setMaximumSize(new Dimension(TEXT_FIELD_SIZE_X, TEXT_FIELD_SIZE_Y));
		
		JPanel runPanel = new JPanel();
		runPanel.setLayout(new BoxLayout(runPanel, BoxLayout.X_AXIS));
		startButton = new JButton(START_TITLE);
		stopButton = new JButton(STOP_TITLE);
		runPanel.add(startButton);
		runPanel.add(stopButton);
		
		pointsTableModel = new PointsTableModel(new ArrayList<Point>());
		JTable table = new JTable(pointsTableModel);
		
		leftPanel.add(new JScrollPane(table));
		leftPanel.add(firstRadioButton);
		leftPanel.add(minLabel);
		leftPanel.add(minField);
		leftPanel.add(maxLabel);
		leftPanel.add(maxField);
		leftPanel.add(secondRadioButton);
		leftPanel.add(nLabel);
		leftPanel.add(nField);
		leftPanel.add(kLabel);
		leftPanel.add(kField);
		leftPanel.add(runPanel);
		
		graphicComponent = new GraphicComponent();
		rightPanel.add(graphicComponent);

		mainPanel.add(leftPanel);
		mainPanel.add(rightPanel);
		frame.add(mainPanel);
        frame.setVisible(true);
	}
	
	public void setStartListner(ActionListener startListner) {
		startButton.addActionListener(startListner);
	}
	
	public void setStopListner(ActionListener stopListner) {
		stopButton.addActionListener(stopListner);
	}
	
	public void update(List<Point> points) {
		pointsTableModel.updateData(points);
		graphicComponent.updateData(points);
	}
	
	public int getSelectedFormulaIndex() {
		if (firstRadioButton.isSelected()) {
			graphicComponent.setCurrentFunctionIndex(FIRST_FORMULA_INDEX);
			return FIRST_FORMULA_INDEX;
		} else if (secondRadioButton.isSelected()) {
			graphicComponent.setCurrentFunctionIndex(SECOND_FORMULA_INDEX);
			return SECOND_FORMULA_INDEX;
		} else {
			return ERROR_RESULT;
		}
	}
	
	public int[] getMinAndMax() {
		int[] minAndMax = new int[2];
		minAndMax[0] = ERROR_RESULT;
		minAndMax[1] = ERROR_RESULT;
		String minFieldText = minField.getText();
		String maxFieldText = maxField.getText();
		if (isInteger(minFieldText) && isInteger(maxFieldText)) {
    		int minFieldValue = Integer.parseInt(minFieldText);
    		int maxFieldValue = Integer.parseInt(maxFieldText);
    		if (minFieldValue <= maxFieldValue) {
    			minAndMax[0] = minFieldValue;
    			minAndMax[1] = maxFieldValue;
    		} else {
    			JOptionPane.showMessageDialog(new JFrame(), "Min greater max!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    		}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Incorrect Min and Max values!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		return minAndMax;
	}
	
	public int[] getNAndK() {
		int[] nAndK = new int[2];
		nAndK[0] = ERROR_RESULT;
		nAndK[1] = ERROR_RESULT;
		String nFieldText = nField.getText();
		String kFieldText = kField.getText();
		if (isInteger(nFieldText) && isInteger(kFieldText)) {
    		int nFieldValue = Integer.parseInt(nFieldText);
    		int kFieldValue = Integer.parseInt(kFieldText);
    		if (nFieldValue >= N_MIN) {
    			if (kFieldValue >= K_MIN) {
        			nAndK[0] = nFieldValue;
        			nAndK[1] = kFieldValue;
    			} else {
        			JOptionPane.showMessageDialog(new JFrame(), "K smaller " + K_MIN + "!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    			}
    		} else {
    			JOptionPane.showMessageDialog(new JFrame(), "N smaller " + N_MIN + "!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
    		}
		} else {
			JOptionPane.showMessageDialog(new JFrame(), "Incorrect N and K values!", ERROR_TITLE, JOptionPane.ERROR_MESSAGE);
		}
		return nAndK;
	}
	
	public static boolean isInteger(String text) {
	    try {
	        Integer.parseInt(text); 
	    } catch(NumberFormatException e) { 
	        return false; 
	    } catch(NullPointerException e) {
	        return false;
	    }
	    return true;
	}
	
	public static boolean checkValues(int[] values) {
		if (values[0] == ERROR_RESULT && values[1] == ERROR_RESULT) {
			return false;
		} else {
			return true;
		}
	}
}
