package closestpair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author tkandaz
 */
public class ClosestPairTestDivideandConquer {
    
    ArrayList<String> inputFileDirectories = new ArrayList<String>();
    ArrayList<String> outputFileDirectories = new ArrayList<String>();
    ArrayList<String> outputFile = new ArrayList<String>();
    ArrayList<String> outputFileCheck = new ArrayList<String>();
    String outputFileToCheck;
    
    public ClosestPairTestDivideandConquer() {
        this.inputFileDirectories.add("./InputTestFiles/sample_input_2_8.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_3_1000.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_4_4.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_10_100.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_37_204.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_47_171.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_67_122.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_85_93.tsv");
        this.inputFileDirectories.add("./InputTestFiles/sample_input_91_732.tsv");

        this.outputFileDirectories.add("./OutputTestFiles/sample_output_2_8.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_3_1000.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_4_4.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_10_100.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_37_204.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_47_171.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_67_122.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_85_93.txt");
        this.outputFileDirectories.add("./OutputTestFiles/sample_output_91_732.txt");
        
        this.outputFileToCheck = "./OutputTestFiles/UnitTestOutput.txt";
    }

    /**
     * Test of run method, of class ClosestPair.
     */
    @Test
    public void testRun() throws Exception {
        Boolean result = true;
        ClosestPair instance;
        
        for(int i=0; i<inputFileDirectories.size(); i++){
            instance = new ClosestPair("2", inputFileDirectories.get(i), outputFileToCheck);
            instance.run();
            
            if(validate(outputFileDirectories.get(i), outputFileToCheck)){
                System.out.println(inputFileDirectories.get(i) + " has passed the test.");
            }
            else{
                result = false;
                System.out.println(inputFileDirectories.get(i) + " has failed the test.");
            }
        }
        
        if(!result){
            System.out.println("Test Failed");
            fail("Test failed");
        }
        else{
            System.out.println("Test Successfull");
        }
    }
    
    public boolean validate(String outputFileDirectory, String outputFileDirectoryToCheck) throws Exception{
        boolean check = true;
        
        outputFile = readInputFile(outputFileDirectory);
        outputFileCheck = readInputFile(outputFileDirectoryToCheck);
        
        if(outputFile.size() == outputFileCheck.size()){
            for(int i=0; i<outputFile.size();i++){
                if(!(outputFile.get(i).equals(outputFileCheck.get(i)))){
                    check = false;
                    break;
                }
            }
        }
        else{
            check = false;
        }
        
        return check;
    }
    
    ArrayList<String> readInputFile(String fileDirectory) throws Exception{
        String line;
        ArrayList<String> tempCoordinates = new ArrayList<>(); 
        
        try{
            BufferedReader buffReader = new BufferedReader(new FileReader(new File(fileDirectory)));
            
            while((line = buffReader.readLine())!= null){
                String[] lineCoordinates = line.split("\t"); //strip the tab character
                
                for(int i = 0; i<lineCoordinates.length; i++){
                    tempCoordinates.add(lineCoordinates[i]);
                }
            }
            
            buffReader.close();
            return tempCoordinates;
        }
        catch(Exception ex){
            throw ex;
        }
    }
}
