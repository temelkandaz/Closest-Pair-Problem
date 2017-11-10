/*
 * Classname: Main.java
 *
 * Version: v1.0 
 *
 * Date 08/11/2017
 *
 */
package closestpair;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;

/**
 * Main class of the program, calls ClosestPair to compute closest distance between two pairs.
 * @author tkandaz
 */
public class Main {
    
    /** 
     * If command line arguments are provided main method calls the appropriate constructor of ClosestPair, if not calls the empty constructor.
     * @param args[0] : Input file location
     * @param args[1] : Output file location
     * @throws java.lang.Exception
     */
    public static void main(String[] args) throws Exception {
        
        ClosestPair cp;
        
       if(args.length == 3){
            cp = new ClosestPair(args[0], args[1], args[2]);
        }
        else{
            cp = new ClosestPair();
        }
        
        cp.run();
    }
    
    /**
     * This method is used to generate input test files. This creates all input values randomly. 
     * Dimension is generated randomly and is in [1,100]
     * Size(number of points) is generated randomly and is in [2, 1001]
     * Coordinate values are generated randomly and is in [-100000, 100000]
     * @throws Exception: Any exception related with BufferedWriter
     */
    static void generateInputFile() throws Exception{
        Random randi = new Random();
        int dimension = randi.nextInt(100) + 1;
        int size = Math.abs(randi.nextInt(1000) + 2);
        String line = "";
        
        BufferedWriter buffWriter = new BufferedWriter(new FileWriter(new File("./sample_input_" + dimension + "_" + size + ".tsv")));
        
        for(int i=0; i<size; i++){
            line = "";
            for(int j=0; j<dimension; j++){
                line += "" + (-100000 + (100000 - (-100000)) * randi.nextDouble()) + "\t";
            }
            buffWriter.write(line);
            buffWriter.newLine();
        }
        
        buffWriter.close();
    }
}
