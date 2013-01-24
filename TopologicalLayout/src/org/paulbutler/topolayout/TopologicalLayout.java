/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paulbutler.topolayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import org.gephi.graph.api.Graph;
import org.gephi.graph.api.GraphModel;
import org.gephi.graph.api.Node;
import org.gephi.graph.api.NodeData;
import org.gephi.graph.api.NodeIterable;
import org.gephi.layout.spi.Layout;
import org.gephi.layout.spi.LayoutBuilder;
import org.gephi.layout.spi.LayoutProperty;

/**
 *
 * @author paulbutler
 */
public class TopologicalLayout implements Layout {
    private GraphModel graphModel;
    private Graph graph;
    private final LayoutBuilder layoutBuilder;
    private boolean converged;
    
    public TopologicalLayout (LayoutBuilder layoutBuilder) {
        this.layoutBuilder = layoutBuilder;
    }
    
    @Override
    public void setGraphModel(GraphModel graphModel) {
        this.graphModel = graphModel;
    }
    
    @Override
    public boolean canAlgo() {
        return !converged;
    }

    @Override
    public void initAlgo() {
        graph = graphModel.getGraphVisible();
        converged = false;
    }

    @Override
    public void goAlgo() {
        
        
        ArrayList<Node> xSorted = new ArrayList<Node>();
        ArrayList<Node> ySorted = new ArrayList<Node>();
        
        for (Node node : graph.getNodes()) {
            xSorted.add(node);
            ySorted.add(node);
        }
        
        Collections.sort(xSorted, new XComparitor());
        Collections.sort(ySorted, new YComparitor());
        
        ArrayList<Removal> removals = new ArrayList<Removal>();
        HashMap<Node, DynamicPoint> nodeToPoint = new HashMap<Node, DynamicPoint>();
        HashMap<DynamicPoint, Node> pointToNode = new HashMap<DynamicPoint, Node>();
        
        for (Node node : graph.getNodes()) {
            DynamicPoint point = new DynamicPoint();
            nodeToPoint.put(node, point);
            pointToNode.put(point, node);
        }
        
        for (int i = 0; i < xSorted.size(); i++) {
            nodeToPoint.get(xSorted.get(i)).colIndex = i;
        }
        
        for (int i = 0; i < ySorted.size(); i++) {
            nodeToPoint.get(ySorted.get(i)).rowIndex = i;
        }
        
        DynamicGrid grid = new DynamicGrid(nodeToPoint.values());
        
        //grid.print();
        
        float lastX = xSorted.get(0).getNodeData().x();
        float lastY = ySorted.get(0).getNodeData().y();
        
        for (int i = 1; i < xSorted.size(); i++) {
            float x = xSorted.get(i).getNodeData().x();
            float dist = x - lastX;
            System.out.println("x " + x + " lastX " + lastX + " dist " + dist);
            
            removals.add(new Removal(GridDimension.COL, dist, i));
            lastX = x;
        }
        
        for (int i = 1; i < ySorted.size(); i++) {
            float y = ySorted.get(i).getNodeData().y();
            float dist = lastY - y;
            System.out.println("y " + y + " lastY " + lastY + " dist " + dist);
            
            removals.add(new Removal(GridDimension.ROW, dist, i));
            lastY = y;
        }
        
        Collections.sort(removals);
                
        for (Removal removal : removals) {
            
            if (removal.dim == GridDimension.ROW) {
                grid.removeRow(removal.index);
            } else {
                grid.removeCol(removal.index);
            }
            
            //grid.print();
        }
        
        grid.reIndex();;
        for (DynamicPoint p : grid.points) {
            Node n = pointToNode.get(p);
            NodeData nd = n.getNodeData();
            nd.setX(p.col.index);
            nd.setY(-p.row.index);
            System.out.println("i:" + p.col.index);
        }
        
        converged = true;
    }
    
    private class Removal implements Comparable<Removal> {
        public GridDimension dim;
        public float distance;
        public int index;
        
        public String toString() {
            return dim.toString() + ' ' + distance + ' ' + index;
        }
        
        public Removal (GridDimension dim, float distance, int index) {
            this.dim = dim;
            this.distance = distance;
            this.index = index;
        }
        
        @Override
        public int compareTo(Removal t) {
            float diff = distance - t.distance;
            if (diff > 0) {
                return 1;
            } else if (diff == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }
    
    private class XComparitor implements Comparator<Node> {        
        @Override
        public int compare(Node a, Node b) {
            float diff = a.getNodeData().x() - b.getNodeData().x();
            if (diff > 0) {
                return 1;
            } else if (diff == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }

    private class YComparitor implements Comparator<Node> {        
        @Override
        public int compare(Node a, Node b) {
            float diff = a.getNodeData().y() - b.getNodeData().y();
            if (diff < 0) {
                return 1;
            } else if (diff == 0) {
                return 0;
            } else {
                return -1;
            }
        }
    }
    
    @Override
    public void endAlgo() {
    }

    @Override
    public LayoutProperty[] getProperties() {
        return new LayoutProperty[0];
    }

    @Override
    public void resetPropertiesValues() {
    }

    @Override
    public LayoutBuilder getBuilder() {
        return this.layoutBuilder;
    }
    
    
}
