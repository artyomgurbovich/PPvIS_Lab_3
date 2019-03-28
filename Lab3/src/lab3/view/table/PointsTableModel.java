package lab3.view.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import lab3.model.Point;

@SuppressWarnings("serial")
public class PointsTableModel extends AbstractTableModel {
	
	private final int FIRST_COLUMN = 0;
	private final int SECOND_COLUMN = 1;
	private final int COLUMN_COUNT = 2;
	
	private List<Point> points;
		
	public PointsTableModel(List<Point> points) {
	    super();
	    this.points = points;
	}
	
	public void updateData(List<Point> points) {
		this.points = points;
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
    public String getColumnName(int c) {  // change like in getValueAt (without break - only return)
        String result = "";
        switch (c) {
            case FIRST_COLUMN:
                result = "x";
                break;
            case SECOND_COLUMN:
                result = "y";
                break;
        }
        return result;
    }
    
    @Override
    public Object getValueAt(int r, int c) {
        switch (c) {
            case FIRST_COLUMN:
                return points.get(r).getX();
            case SECOND_COLUMN:
            	return points.get(r).getY();
            default:
                return "";
        }
    }
}
