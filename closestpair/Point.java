/*
 * Classname: Point.java
 *
 * Version: v1.0 
 *
 * Date 08/11/2017
 *
 */
package closestpair;

import java.util.ArrayList;

/**
 * Point class stores information of a point read from the input text file.
 * @author tkandaz
 */
public class Point {
    
    int lineNumber; //line number of a point in the input file
    ArrayList<Double> coordinates = new ArrayList<>(); //stores coordinate values of a point 
    
    public Point(int lineNumber, ArrayList<Double> coordinates){
        this.lineNumber = lineNumber;
        this.coordinates = coordinates;
    }
}
