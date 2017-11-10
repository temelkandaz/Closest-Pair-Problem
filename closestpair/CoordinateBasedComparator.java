/*
 * Classname: CoordinateBasedComparator.java
 *
 * Version: v1.0 
 *
 * Date 08/11/2017
 *
 */
package closestpair;

import java.util.Comparator;

/**
 * This comparator is created to be able to sort a collection of Points according to any given coordinate (i.e x, y, z, t ...)
 * @author tkandaz
 */
public class CoordinateBasedComparator implements Comparator<Point>{
    final int coordinateIndex;
    
    public CoordinateBasedComparator(int coordinateIndex){
        this.coordinateIndex = coordinateIndex;
    }
    
    @Override
    public int compare(Point one, Point other) {
        return one.coordinates.get(this.coordinateIndex).compareTo(other.coordinates.get(this.coordinateIndex));
    }
}