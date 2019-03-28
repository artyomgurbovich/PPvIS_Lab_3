package lab3.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Model {
	
	List<Point> points;
	
	public Model() {
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
		points = new CopyOnWriteArrayList<Point>();
	}
	
	public boolean isEmpty() {
		return points.isEmpty();
	}
}
