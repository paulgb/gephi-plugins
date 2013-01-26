/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.paulbutler.gtfsloader;

/**
 *
 * @author paulbutler
 */
public enum GTFSTransitType {
    LIGHT_RAIL (0),
    SUBWAY (1),
    RAIL (2),
    BUS (3),
    FERRY (4),
    CABLE_CAR (5),
    GONDOLA (6),
    FUNICULAR (7);
    
    private int index;

    GTFSTransitType(int index) {
        this.index = index;
    }
    
    public int getIndex() {
        return index;
    }
    
}
