
package org.paulbutler.gtfsloader;

/*
 * Represents a coordinate on a two-dimensional Cartesian plain, or simply
 * a pair of floats.
 */
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
    
    @Override
    public String toString() {
        return "[" + lat + ", " + lon + "]";
    }
    
    @Override
    public boolean equals (Object b) {
        if (!(b instanceof Coordinate)) {
            return false;
        }
        Coordinate coord = (Coordinate) b;
        return lon == coord.lon && lat == coord.lat;
    }
    
    @Override
    public int hashCode () {
        return (new Float(lat).hashCode()) + 31 * (new Float(lon).hashCode());
    }
}
