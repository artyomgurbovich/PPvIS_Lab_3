package lab.view.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;

import lab.model.Graph;
import lab.model.Point;

@SuppressWarnings("serial")
public class PointsTableModel extends AbstractTableModel {
	
	private final int X_COLUMN_INDEX = 0;
	private final int Y_COLUMN_INDEX = 1;
	private final int COLUMN_COUNT = 2;
	
	private List<Point> points;
		
	public PointsTableModel(List<Point> points) {
	    super();
	    this.points = points;
	}
	
	public void updateData(Graph graph) {
		this.points = graph.getPoints();
		fireTableDataChanged();
	}
	
    @Override
    public int getRowCount() {
        return points.size();
    }
    
    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }
    
    @Override
    public String getColumnName(int c) {
        String result = "";
        switch (c) {
            case X_COLUMN_INDEX:
                result = "x";
                break;
            case Y_COLUMN_INDEX:
                result = "y";
                break;
        }
        return result;
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case X_COLUMN_INDEX:
                return points.get(r).getX();
            case Y_COLUMN_INDEX:
            	return points.get(r).getY();
            default:
                return "";
        }
    }
}