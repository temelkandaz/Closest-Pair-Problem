/*
 * Classname: ClosestPair.java 
 *
 * Version: v1.0 
 *
 * Date 08/11/2017
 *
 */
package closestpair;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

/**
 * ClosestPair class is where all the action goes down. Points are read using readInputFile method, Closest Pair could be find using either 
 * findClosestBruteForce or findClosest methods, finally closest pair is written to output file using writeOutputFile method.
 * @author tkandaz
 */
public class ClosestPair {
    String inputFileDirectory;
    String outputFileDirectory;
    String algorithmChoice;
    ArrayList<Point> elementList = new ArrayList<>(); //points are read into this list
    ArrayList<Point> elementListSortedByCoordinates = new ArrayList<>(); //points are stored in this list after being sorted
    double[][] distances; //lookup table in which all calculated distances will be stored in order to avoid calculating same distances again and again
    double minimumDistance = Double.MAX_VALUE; 
    double delta = 0; //delta value will be used to compare minimum distances calculated in two different halves in divide and conquer stage
    Point firstClosest; //one of the points in closest pair, not necessarily the first one wrt their line numbers
    Point secondClosest; //one of the points in closest pair, not necessarily the second one wrt their line numbers
    Point medianPoint;
    
    /**
     * This empty constructor is used if command line arguments are not provided.
     * Algorithm choice, Input and Output File Directories should be hard coded if empty constructor will be used.
     */
    ClosestPair(){ 
        this.algorithmChoice = "2";
        this.inputFileDirectory = "../InputTestFiles/sample_input_91_732.tsv";
        this.outputFileDirectory = "../OutputTestFiles/sample_output_91_732.txt";
    }
    
    /**
     * This constructor is used if algorithm choice, input and output file directories are provided via command line arguments.
     * @param algorithmChoice: 1 is for brute-force algorithm, 2 and any other input is for divide-and-conquer algorithm.
     * @param inputFileDirectory : Input File Directory
     * @param outputFileDirectory : Output File Directory
     */
    ClosestPair(String algorithmChoice, String inputFileDirectory, String outputFileDirectory){
        this.algorithmChoice = algorithmChoice;
        this.inputFileDirectory = inputFileDirectory;
        this.outputFileDirectory = outputFileDirectory;
    }
    
    /**
     * This is used to calculate the minimum distance and to find the closest pair.
     * Firstly, points are read using readInputFile method. 
     * In order to find closest pairs findClosestBruteForce or findClosest methods are used according to the input taken from command line.
     * Finally writeOutputFile method is used to give output.
     * @throws Exception : Any exception related with BufferedReader and BufferedWriter
     */
    void run() throws Exception{
        readInputFile(inputFileDirectory);
        
	if(algorithmChoice.equals("1")){
	    findClosestBruteForce(elementList);
        }
        else{
            for(int d=0; d<elementList.get(0).coordinates.size(); d++){//iterates for every dimension
            	Collections.sort(elementListSortedByCoordinates, new CoordinateBasedComparator(d)); //Sorts points for a different dimension for every iteration
            	findClosest(elementListSortedByCoordinates, d, 0, elementList.size()-1); //finds closest pair using divide and conquer algorithm
            }
	}
        
        writeOutputFile(outputFileDirectory);
    }
    
    /**
     * This reads the points from a file located at the provided fileDirectory address.
     * @param fileDirectory : Input File Directory
     * @throws Exception : Any exception related with BufferedReader
     */
    void readInputFile(String fileDirectory) throws Exception{
        String line;
        int index = 0;
        ArrayList<Double> tempCoordinates; 
        
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader(new File(fileDirectory)));
            
            while((line = buffReader.readLine())!= null){
                tempCoordinates = new ArrayList<>(); //to store coordinate values of a point
                String[] lineCoordinates = line.split("\t"); //strip the tab character
                
                for(int i = 0; i<lineCoordinates.length; i++){
                    tempCoordinates.add(Double.parseDouble(lineCoordinates[i]));
                }
                elementList.add(new Point(++index,tempCoordinates));
                elementListSortedByCoordinates.add(new Point(index,tempCoordinates));
            }
            buffReader.close();
            this.distances = new double[elementList.size()+1][elementList.size()+1]; //initialize an empty lookup table for all distances created
        }
        catch(Exception ex){
            throw ex;
        }
    }
    
    /**
     * This finds the closest pair using brute-force algorithm.
     * If there is just one point in input list then this sets both of the closest points to that one point and doesn't calculate distances.
     * If there are more than one points in the input list then this calculates distances between each point.
     * Stores one of the points in firstClosest and the other point in secondClosest.
     * @param points : All points read from input file
     */
    void findClosestBruteForce(ArrayList<Point> points){
        if(points.size() == 1){
            firstClosest = points.get(0);
            secondClosest = points.get(0);
        }
        else{
            for(int i=0; i<points.size();i++){ //iterates all points
                for(int j=i+1; j<points.size(); j++){
                    delta = findDistance(points.get(i), points.get(j));
                    
                    if(delta < minimumDistance){ //checks for minimumDistance
                        minimumDistance = delta;
                        firstClosest = points.get(i);
                        secondClosest = points.get(j);
                    }
                }
            }
        }
    }
    
    /**
     * This finds the closest pair and returns the minimum distance by using a divide and conquer algorithm and a lookup table.
     * Aim of lookup table and divide and conquer algorithm approach is to reduce the number of comparisons between points.
     * If size of input point list is less than or equal to 2, method enters the base case and calculates the minimum distance between the points.
     * If size of input point list is greater than 2 then method divides that list into two and conquers it.
     * Whenever a distance should be calculated this at first checks the lookup table if that calculation is already made. If it is gets the distance from
     * lookup table, if not this calculates the distance and updates the lookup table with this distance value.
     * @param pointsSortedByCoordinates : Input point list sorted by some "dimension"
     * @param dimension : Coordinate index number that input points list was sorted by
     * @param low : First index of the input list
     * @param high : Last index of the input list
     * @return returns the minimumDistance between two points in pointsSortedByCoordinates among the points with indexes starting from low to high
     */
    double findClosest(ArrayList<Point> pointsSortedByCoordinates, int dimension, int low, int high){
       
        if(high - low <= 2){//base case
            double tempMinimum = Double.MAX_VALUE;
            for(int i=low; i<=high;i++){
                for(int j=i+1; j<=high; j++){
                    if(distances[pointsSortedByCoordinates.get(i).lineNumber][pointsSortedByCoordinates.get(j).lineNumber] != 0){//checks if exists in lookup table
                        delta = distances[pointsSortedByCoordinates.get(i).lineNumber][pointsSortedByCoordinates.get(j).lineNumber];
                    } 
                    else{//if it doesn't exist in lookup table firstly calculates the distance and then updates the lookup table
                        delta = findDistance(pointsSortedByCoordinates.get(i), pointsSortedByCoordinates.get(j));
                        distances[pointsSortedByCoordinates.get(i).lineNumber][pointsSortedByCoordinates.get(j).lineNumber] = delta;
                        distances[pointsSortedByCoordinates.get(j).lineNumber][pointsSortedByCoordinates.get(i).lineNumber] = delta;
                    }
                    
                    if(delta < tempMinimum){ //if delta is less than tempMinimum
                        tempMinimum = delta;
                        if(delta < minimumDistance){ 
                            firstClosest = pointsSortedByCoordinates.get(i);
                            secondClosest = pointsSortedByCoordinates.get(j);
                        }
                    }
                }
            }
            return tempMinimum;
        }
        
        int median = (high+low)/2;//finds middle point in a sorted list
        medianPoint = pointsSortedByCoordinates.get(median);
        
        double deltal = findClosest(pointsSortedByCoordinates, dimension, low, median); //left side of the sorted list 
        double deltar = findClosest(pointsSortedByCoordinates, dimension, median+1, high); //right side of the sorted list
        double deltax = Math.min(deltal, deltar); //compares the two sides
        
        ArrayList<Point> temp = new ArrayList<>();
        
        //finds the points closer to middle point than deltax in the specified coordinate in order to compare them among each other
        for(int i=low; i<=high; i++){
            if(Math.abs(pointsSortedByCoordinates.get(i).coordinates.get(dimension) - medianPoint.coordinates.get(dimension)) < deltax){
                temp.add(pointsSortedByCoordinates.get(i));
            }
        }
        
        //compares the points closer to middle point than deltax in the specified coordinate among each other
        double tempDelta = 0;
        for(int i=0; i<temp.size(); i++){
            //checks if the selected two points are closer to each other than deltax in the specified coordinate
            for(int j=i+1; j<temp.size() && Math.abs(temp.get(i).coordinates.get(dimension) - temp.get(j).coordinates.get(dimension)) < deltax; j++){ 
                if(distances[temp.get(i).lineNumber][temp.get(j).lineNumber] != 0){ //checks if exists in lookup table
                    tempDelta = distances[temp.get(i).lineNumber][temp.get(j).lineNumber];
                }
                else{//if it doesn't exist in lookup table firstly calculates the distance and then updates the lookup table
                    tempDelta = findDistance(temp.get(i), temp.get(j));
                    distances[temp.get(i).lineNumber][temp.get(j).lineNumber] = tempDelta;
                    distances[temp.get(j).lineNumber][temp.get(i).lineNumber] = tempDelta;
                }
                
                if(tempDelta < deltax){ //if delta is less than tempMinimum
                    deltax = tempDelta;
                    if(deltax < minimumDistance){ //checks if found a deltax less than minimumDistance
                        minimumDistance = deltax;
                        firstClosest = temp.get(i);
                        secondClosest = temp.get(j);
                    }
                } 
            }
        }
        return Math.min(minimumDistance, deltax);
    }
    
    /**
     * This takes two points and returns the distance between them regardless of their dimension using euclidean distance formula
     * @param p1: First point
     * @param p2: Second point
     * @return Returns the distance between the two input points
     */
    double findDistance(Point p1, Point p2){
        double result = 0;
        
        for(int i=0; i<p1.coordinates.size(); i++){
            result += Math.pow(p1.coordinates.get(i) - p2.coordinates.get(i),2);
        }
        
        return Math.sqrt(result);
    }
    
    /**
     * This writes the two closest point in the given input list to an output file.
     * @param outputDirectory : Output File Directory
     * @throws Exception : Any exception related with BufferedWriter
     */
    void writeOutputFile(String outputDirectory) throws Exception{
        String line = "";
        String line2 = "";
        
        try{
            File outputFile = new File(outputDirectory);
            outputFile.createNewFile();
            BufferedWriter buffWriter = new BufferedWriter(new FileWriter(outputFile));
            
            //gets the line number and coordinates of the first input point
            line = firstClosest.lineNumber + ":";
            for(int i=0; i<firstClosest.coordinates.size(); i++){
                line += Math.round(firstClosest.coordinates.get(i)) + "\t";
            }
            
            //gets the line number and coordinates of the second input point
            line2 = secondClosest.lineNumber + ":";
            for(int i=0; i<secondClosest.coordinates.size(); i++){
                line2 += Math.round(secondClosest.coordinates.get(i)) + "\t";
            }
            
            //finds the point with the smallest line number and writes the information of that point first
            if(firstClosest.lineNumber < secondClosest.lineNumber){
                buffWriter.write(line);
                buffWriter.newLine();
                buffWriter.write(line2);
            }
            else{
                buffWriter.write(line2);
                buffWriter.newLine();
                buffWriter.write(line);
            }
            
            buffWriter.close();
        }
        catch(Exception ex){
            throw ex;
        }
    }
}