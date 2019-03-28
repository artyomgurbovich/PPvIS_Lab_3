package lab3.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Semaphore;
import lab3.FunctionsManager;
import lab3.model.Model;
import lab3.view.View;

public class Controller {
	
	private int SLEEP_TIME = 10;
	
	private Model model;
	private View view;
	private int min;
	private int max;
	private int x;
	private boolean stop;
	
	public Controller(Model model, View view) {
		this.model = model;
		this.view = view;
		view.setStartListner(startListener);
		view.setStopListner(stopListener);
	}
	
    public ActionListener startListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	int formulaIndex = view.getSelectedFormulaIndex();
        	if (formulaIndex == View.FIRST_FORMULA_INDEX) {
            	int[] minAndMax = view.getMinAndMax();
            	if (View.checkValues(minAndMax)) {
            		min = minAndMax[0];
            		max = minAndMax[1];
            		x = min;
            		model.clearPoints();
            		stop = false;
            		Semaphore calcSem = new Semaphore(1);
            		Semaphore drawSem = new Semaphore(0);
            		new CalcFirstFunctionThread(calcSem, drawSem);
            		new DrawThread(calcSem, drawSem);
            	}
        	} else if (formulaIndex == View.SECOND_FORMULA_INDEX) {
        		int[] nAndK = view.getNAndK();
            	if (View.checkValues(nAndK)) {
            		max = nAndK[0];
            		x = View.N_MIN;
            		model.clearPoints();
            		stop = false;
            		Semaphore calcSem = new Semaphore(1);
            		Semaphore drawSem = new Semaphore(0);
            		new CalcSecondFunctionThread(calcSem, drawSem, nAndK[1]);
            		new DrawThread(calcSem, drawSem);
            	}
        	}
        }
    };
    
    public ActionListener stopListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
        	stop = true;
        }
    };
    
    public void clear() {
    	if (stop) {
    		model.clearPoints();
    		view.update(model.getPoints());
    	}
    }
    
    public boolean isRunning() {
    	return x <= max && stop == false;
    }
    
    public class CalcFirstFunctionThread extends Thread {
		Semaphore calcSem;
		Semaphore drawSem;
		
		public CalcFirstFunctionThread(Semaphore calcSem, Semaphore drawSem) {
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
				model.addPoint(FunctionsManager.calculateFirstFunction(x));
				x++;
				drawSem.release();
			}
			clear();
		}
	}
    
    public class CalcSecondFunctionThread extends Thread {
		Semaphore calcSem;
		Semaphore drawSem;
		int k;
		
		public CalcSecondFunctionThread(Semaphore calcSem, Semaphore drawSem, int k) {
			this.calcSem = calcSem;
			this.drawSem = drawSem;
			this.k = k;
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
				model.addPoint(FunctionsManager.calculateSecondFunction(x, k));
				x++;
				drawSem.release();
			}
			clear();
		}
	}
    
	public class DrawThread extends Thread {
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
				if (!model.isEmpty()) {
					view.update(model.getPoints());
				}
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
