package lab.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import lab.model.GraphsManager;
import lab.view.MainWindow;
import lab.controller.FunctionsManager;

public class Controller {
	
	private final int CALC_SEM_DEFAULT_STATE = 1;
	private final int DRAW_SEM_DEFAULT_STATE = 0;
	
	private final int SLEEP_TIME = 10;
	
	private MainWindow mainWindow;
	private GraphsManager graphsManager;
	private FunctionsManager functionsManager;
	private boolean stop;
	private int sortsNumber;
	
	public Controller(MainWindow mainWindow, GraphsManager graphsManager) {
		this.mainWindow = mainWindow;
		this.graphsManager = graphsManager;
		functionsManager = new FunctionsManager();
		mainWindow.setStartListner(startListener);
		mainWindow.setStopListner(stopListener);
	}
	
	public ActionListener startListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	if (mainWindow.valuesCorrect()) {
        		graphsManager.clear();
        		graphsManager.setMinAndMax(mainWindow.getMin(), mainWindow.getMax());
        		sortsNumber = mainWindow.getSortsNumber();
        		Semaphore calcSem = new Semaphore(CALC_SEM_DEFAULT_STATE);
        		Semaphore drawSem = new Semaphore(DRAW_SEM_DEFAULT_STATE);
        		stop = false;
        		new CalcThread(calcSem, drawSem);
        		new DrawThread(calcSem, drawSem);
        	}
        }
    };
    
    public ActionListener stopListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	stop = true;
        }
    };
    
    private boolean isRunning() {
    	return !graphsManager.isComplete() && stop == false;
    }
    
    public void clear() {
    	if (stop) {
    		graphsManager.clear();
    		mainWindow.update(graphsManager);
    	}
    }
    
    private class CalcThread extends Thread {
		Semaphore calcSem;
		Semaphore drawSem;
		
		public CalcThread(Semaphore calcSem, Semaphore drawSem) {
			this.calcSem = calcSem;
			this.drawSem = drawSem;
			start();
		}
		
		@Override
		public void run() {
			while(isRunning()) {
				try {
					calcSem.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				int x = graphsManager.getX();
				System.out.println(functionsManager.calculateFirstFunction(x).getX());
				graphsManager.addPoint(functionsManager.calculateFirstFunction(x), 0);
				graphsManager.addPoint(functionsManager.calculateSecondFunction(x, sortsNumber), 1);
				graphsManager.increaseX();
				drawSem.release();
			}
			clear();
		}
	}
    
    private class DrawThread extends Thread {
		Semaphore calcSem;
		Semaphore drawSem;
		
		public DrawThread(Semaphore calcSem, Semaphore drawSem) {
			this.calcSem = calcSem;
			this.drawSem = drawSem;
			start();
		}
		
		@Override
		public void run() {
			while(isRunning()) {
				try {
					drawSem.acquire();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				mainWindow.update(graphsManager);
				try {
					Thread.sleep(SLEEP_TIME);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				calcSem.release();
			}
			clear();
		}
	}
}