package lab3;

import java.util.Random;
import lab3.model.Point;

public class FunctionsManager {
	
	public static final float DIVIDER = 100.0f;
	
	public static Point calculateFirstFunction(int x) {
		return new Point(x, 3 * x + 1);
	}
	
	public static Point calculateSecondFunction(int size, int k) {
		float all_time = 0.0f;
		for (int index = 0; index < k; index++) {
			all_time += inclusionSort(generateRandomArray(size));
		}
		return new Point(size, all_time / k);
	}
	
	public static float inclusionSort(int[] array) {
		long start = System.nanoTime();
		for (int index = 1; index < array.length; index++) {
			int currentValue = array[index];
			int currentIndex = index;
			while ((currentIndex > 0) && (array[currentIndex - 1] > currentValue)) {
				array[currentIndex] = array[currentIndex - 1];
				currentIndex--;
			}
			array[currentIndex] = currentValue;
		}
		long end = System.nanoTime();
		return (end - start) / DIVIDER;
	}
	
	public static int[] generateRandomArray(int size) {
		Random random = new Random();
		int[] result = new int[size];
		for (int index = 0; index < result.length; index++) {
			result[index] = random.nextInt();
		}
		return result;
	}
}
