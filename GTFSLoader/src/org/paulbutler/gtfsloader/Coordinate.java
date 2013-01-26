
package org.paulbutler.gtfsloader;

class Coordinate {
    private final float lat;
    private final float lon;
    
    Coordinate(Float lat, Float lon) {
        this.lat = lat;
        this.lon = lon;
    }

    float getLon() {
        return lon;
    }
    
    float getLat() {
        return lat;
    }
    
    public String toString() {
        return "[" + lat + ", " + lon + "]";
    }
    
    @Override
    public boolean equals (Object b) {
        Coordinate coord = (Coordinate) b;
        return lon == coord.lon && lat == coord.lat;
    }
    
    @Override
    public int hashCode () {
        return (new Float(lat).hashCode()) + 31 * (new Float(lon).hashCode());
    }
}
