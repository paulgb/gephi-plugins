package org.paulbutler.topolayout;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class DynamicGrid {
    ArrayList<DynamicSlice> rows = new ArrayList<DynamicSlice>();
    ArrayList<DynamicSlice> cols = new ArrayList<DynamicSlice>();
    private final List<DynamicPoint> points;
    private int numRows;
    private int numCols;


    public void reIndex() {
        numRows = rows.get(0).reIndex(0);
        numCols = cols.get(0).reIndex(0);
    }
    
    public void print() {
        reIndex();
        System.out.println("Rows: " + numRows + " cols " + numCols);
        DynamicSlice row = rows.get(0);
        do {
            HashSet<Integer> s = new HashSet<Integer>();
            for (DynamicPoint p : row.points) {
                s.add(p.col.index);
            }
            
            for (int i = 0; i <= numCols; i++) {
                if (s.contains(i)) {
                    System.out.print('X');
                } else {
                    System.out.print('.');
                }
            }
            
            row = row.nextSlice;
            System.out.println();
        } while(row != null);
    }
    
    public DynamicGrid(Collection<DynamicPoint> points) {
        int maxRow = (Collections.max(points, new DynamicPoint.RowComparator())).rowIndex;
        int maxCol = (Collections.max(points, new DynamicPoint.ColComparator())).colIndex;
        
        this.points = new ArrayList<DynamicPoint>(points);
        DynamicSlice lastSlice = new DynamicSlice(GridDimension.ROW);
        rows.add(lastSlice);
        for (int i = 1; i <= maxRow; i++) {
            DynamicSlice slice = new DynamicSlice(lastSlice, GridDimension.ROW);
            rows.add(slice);
            lastSlice = slice;
        }
        
        lastSlice = new DynamicSlice(GridDimension.COL);
        cols.add(lastSlice);
        for (int i = 1; i <= maxCol; i++) {
            DynamicSlice slice = new DynamicSlice(lastSlice, GridDimension.COL);
            cols.add(slice);
            lastSlice = slice;
        }
        
        for (DynamicPoint p : points) {
            p.setPosition(cols.get(p.colIndex), rows.get(p.rowIndex));
        }
    }
    
    public void RemoveRow(int rowIndex) {
        DynamicSlice row = rows.get(rowIndex);
        if (row.canRemove()) {
            row.remove();
        }
    }

    public void RemoveCol(int colIndex) {
        DynamicSlice col = rows.get(colIndex);
        if (col.canRemove()) {
            col.remove();
        }
    }

}