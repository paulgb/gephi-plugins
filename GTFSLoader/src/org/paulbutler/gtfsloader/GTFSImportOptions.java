
package org.paulbutler.gtfsloader;

import java.util.EnumSet;
import java.util.Set;


public class GTFSImportOptions {
    private EdgeAlgorithm edgeAlgorithm;
    private Set<GTFSTransitType> transitTypes;
    
    public GTFSImportOptions () {
        edgeAlgorithm = EdgeAlgorithm.DISTANCE;
        transitTypes = EnumSet.allOf(GTFSTransitType.class);
    }
    
    public void setTransitTypes (Set<GTFSTransitType> transitTypes) {
        this.transitTypes = transitTypes;
    }
    
    public Set<GTFSTransitType> getTransitTypes () {
        return transitTypes;
    }
    
    public void setEdgeAlgorithm (EdgeAlgorithm edgeAlgorithm) {
        this.edgeAlgorithm = edgeAlgorithm;
    }
    
    public EdgeAlgorithm getEdgeAlgorithm () {
        return edgeAlgorithm;
    }
    
    public String toString () {
        return "Options: " + edgeAlgorithm + " " + transitTypes;
    }
    
}
