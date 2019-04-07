package lab.controller;

import java.util.Random;
import java.security.SecureRandom;
import lab.model.Point;

public class FunctionsManager {
	
	private final float SCALE = 100.0f;
	private Random random;
	
	public FunctionsManager() {
		random = new Random();
	}
	
	public Point calculateFirstFunction(int x) {
		return new Point(x, 3 * x + 1);
	}
	
	public Point calculateSecondFunction(int x, int sortsNumber) {
		long start;
		long end;
		float allTime = 0.0f;
		for (int index = 0; index < sortsNumber; index++) {
			start = System.nanoTime();
			inclusionSort(generateRandomArray(x));
			end = System.nanoTime();
			allTime += ((end - start) / SCALE);
		}
		return new Point(x, allTime / sortsNumber);
	}
	
	public void inclusionSort(int[] array) {
		for (int index = 1; index < array.length; index++) {
			int currentValue = array[index];
			int currentIndex = index;
			while ((currentIndex > 0) && (array[currentIndex - 1] > currentValue)) {
				array[currentIndex] = array[currentIndex - 1];
				currentIndex--;
			}
			array[currentIndex] = currentValue;
		}
	}
	
	public int[] generateRandomArray(int size) {
		int[] result = new int[size];
		for (int index = 0; index < result.length; index++) {
			result[index] = random.nextInt(1000);
		}
		return result;
	}
}
