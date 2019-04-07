package lab.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import lab.model.Point;

public class Graph {
	
	List<Point> points;
	
	public Graph() {
		points = new CopyOnWriteArrayList<Point>();
	}
	
	public List<Point> getPoints() {
		return points;
	}
	
	public void setPoints(List<Point> points) {
		this.points = points;
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public void clearPoints() {
		points.clear();
	}
	
	public boolean isEmpty() {
		return points.isEmpty();
	}
}
