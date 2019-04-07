package lab.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GraphsManager {
	
	private final int DEFAULT_MIN = 0;
	private final int DEFAULT_MAX = 0;
	
	private List<Graph> graphs;
	private int min = DEFAULT_MIN;
	private int max = DEFAULT_MAX;
	private int currentX = min;
	
	public GraphsManager(int graphsNumber) {
		graphs = new CopyOnWriteArrayList<Graph>();
		for (int index = 0; index < graphsNumber; index++) {
			graphs.add(new Graph());
		}
	}
	
	public void addPoint(Point point, int graphIndex) {
		Graph graph = graphs.get(graphIndex);
		graph.addPoint(point);
		graphs.set(graphIndex, graph);
	}
	
	public void setMinAndMax(int min, int max) {
		this.min = min;
		this.max = max;
		currentX = min;
	}
	
	public void clear() {
		for (Graph graph: graphs) {
			graph.clearPoints();
		}
		currentX = DEFAULT_MIN;
	}
	
	public List<Graph> getGraphs() {
		return graphs;
	}
	
	public Graph getGraphByIndex(int index) {
		return graphs.get(index);
	}
	
	public boolean isComplete() {
		return currentX > max;
	}
	
	public int getX() {
		return currentX;
	}
	
	public void increaseX() {
		currentX++;
	}
	
	public boolean isEmpty() {
		for (Graph graph: graphs) {
			if (graph.isEmpty()) {
				return true;
			}
		}
		return false;
	}
}
