/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mg.MainApp;


/**
 *
 * @author xx
 */
public class DataFile {

    private int ind=0; // current index in collection;

    private int bufSize = 1000;// size of reading buffer for datareader.
    private String line = null;
   
    List<BufferedReader> dataReaders = new ArrayList<>();
    private MainApp mainApp;

    
    /**
     * Default constructor.
     */
    public DataFile(){}
    public void addReader(int index, String fileName){
        try{
            System.out.println("Reader is created");
            //dataReaders.add(ind, new BufferedReader(new FileReader(fileName)));
            //dataReaders.get(ind++).readLine(); // skip Header line in CSV file
            dataReaders.add(index, new BufferedReader(new FileReader(fileName)));
            dataReaders.get(index).readLine(); // skip Header line in CSV file
        }
        catch(FileNotFoundException e) {
            System.out.println(
                "Unable to open file '" + 
                fileName + "'");
            //ind--;  find if ind icreased in case of exception. if YES, the ind-- is needed
        }
        catch(IOException e) {
            e.printStackTrace();
            //ind--;  find if ind icreased in case of exception. if YES, the ind-- is needed
        }
    }
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
        
    }
    
    // Methods to receive lower and upper values on axises
    
    
    public void delReader(String fileName)throws IOException{
        int index=0;
        for ( BufferedReader dr : dataReaders){
            System.out.println("Removing DataReader for index="+index+ " for "+dr.getClass().getName());
            //delReader(index++);
            dr.close();
        }
    }
    public void delReader(int index) throws IOException{
        
        
        dataReaders.get(index).close();
        dataReaders.remove(index);
    }
    
    public void delAllReaders() throws IOException{
        System.out.println("delAllReaders started");
        for (BufferedReader dr : dataReaders){
             dr.close();
             System.out.println("Reader Closed");
        }
        dataReaders.clear();
        System.out.println("All Readers removed");
    }
    
    public List<String> getGraphPart(int index) {
        List<String> records = new ArrayList<>();
        int count=0;
        try {
            while ((line = dataReaders.get(index).readLine()) != null) {
                records.add(line);
                if (bufSize <= count++) return records;
            }
        } catch (IOException ex) {
            Logger.getLogger(DataFile.class.getName()).log(Level.SEVERE, null, ex);
        }
        return records;
    }

    //List<File> dataFiles = new ArrayList<>();
    private List<String> readFile(String filename)
            throws Exception {
        String line = null;
        List<String> records = new ArrayList<>();

        // wrap a BufferedReader around FileReader
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

        // use the readLine method of the BufferedReader to read one line at a time.
        // the readLine method returns null when there is nothing else to read.
        while ((line = bufferedReader.readLine()) != null) {
            records.add(line);
        }

        // close the BufferedReader when we're done
        bufferedReader.close();
        return records;
    }
    
}
