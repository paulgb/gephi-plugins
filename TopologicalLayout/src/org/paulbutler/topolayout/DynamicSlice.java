package org.paulbutler.topolayout;

import java.util.ArrayList;
import java.util.HashSet;


public class DynamicSlice {
    public DynamicSlice lastSlice;
    public HashSet<DynamicPoint> points;
    public DynamicSlice nextSlice;
    private final GridDimension dim;
    private boolean removed = false;
    public int index;
    
    public DynamicSlice(DynamicSlice lastSlice, GridDimension dim) {
        this.lastSlice = lastSlice;
        this.dim = dim;
        points = new HashSet<DynamicPoint>();
        if (lastSlice != null) {
            lastSlice.nextSlice = this;
        }
    }
    
    public DynamicSlice(GridDimension dim) {
        this(null, dim);
    }

    void addPoint(DynamicPoint p) {
        assert !this.removed;
        points.add(p);
    }

    boolean canRemove() {
        assert !this.removed;
        assert lastSlice != null;
        HashSet<DynamicSlice> al = new HashSet<DynamicSlice>();
        if (dim == GridDimension.COL) {
            for (DynamicPoint p : points) {
                al.add(p.row);
            }
            for (DynamicPoint p : lastSlice.points) {
                if (al.contains(p.row)) {
                    return false;
                }
            }
        } else {
            for (DynamicPoint p : points) {
                al.add(p.col);
            }
            for (DynamicPoint p : lastSlice.points) {
                if (al.contains(p.col)) {
                    return false;
                }
            }
        }
        return true;
    }

    void remove() {
        assert !this.removed;
        assert lastSlice != null;
        for (DynamicPoint point : points) {
            if (dim == GridDimension.ROW) {
                point.setPosition(point.col, this.lastSlice);
            } else {
                point.setPosition(this.lastSlice, point.row);
            }
        }
        this.lastSlice.nextSlice = nextSlice;
        if (this.nextSlice != null) {
            this.nextSlice.lastSlice = lastSlice;
        }
        this.removed = true;
    }

    public int reIndex(int i) {
        this.index = i;
        
        if (this.nextSlice != null) {
            return this.nextSlice.reIndex(i + 1);
        } else {
            return i;
        }
    }

}
