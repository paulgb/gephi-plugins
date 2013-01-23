
package org.paulbutler.topolayout;

import java.util.Comparator;

public class DynamicPoint {
    public DynamicSlice row = null;
    public DynamicSlice col = null;
    public int rowIndex;
    public int colIndex;

    public String toString() {
        return "Point originally at " + colIndex + ", " + rowIndex;
    }
    
    public DynamicPoint(int rowIndex, int colIndex) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    void setPosition(DynamicSlice col, DynamicSlice row) {
        this.col = col;
        this.row = row;
        this.col.addPoint(this);
        this.row.addPoint(this);
    }

    public static class RowComparator implements Comparator<DynamicPoint> {
        @Override
        public int compare(DynamicPoint a, DynamicPoint b) {
            int diff = a.rowIndex - b.rowIndex;
            return diff;
        }
    }

    public static class ColComparator implements Comparator<DynamicPoint> {
        @Override
        public int compare(DynamicPoint a, DynamicPoint b) {
            int diff = a.colIndex - b.colIndex;
            return diff;
        }
    }
}