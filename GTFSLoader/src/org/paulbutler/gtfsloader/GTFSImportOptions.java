
package org.paulbutler.gtfsloader;

import java.util.EnumSet;
import java.util.Set;

/*
 * Represents the parameters of the import algorithm
 */
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
    
    @Override
    public String toString () {
        return "Options: " + edgeAlgorithm + " " + transitTypes;
    }
    
}
