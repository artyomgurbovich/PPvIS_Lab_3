package lab;

import lab.controller.Controller;
import lab.model.GraphsManager;
import lab.view.MainWindow;

public class Main {
	
	private static final int GRAPHS_NUMBER = 2;
	
	public static void main(String[] args) {
		MainWindow mainWindow = new MainWindow();
		GraphsManager graphsManager = new GraphsManager(GRAPHS_NUMBER);
		new Controller(mainWindow, graphsManager);
		mainWindow.start();
	}
}
