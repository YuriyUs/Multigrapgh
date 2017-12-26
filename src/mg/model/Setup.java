/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.model;

import com.sun.javafx.property.adapter.PropertyDescriptor.Listener;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import mg.MainApp;

/**
 * Model class for Setup.
 * 
 * @author xx
 * Note: // interesting links about chkbox 
 * https://stackoverflow.com/questions/7217625/how-to-add-checkboxs-to-a-tableview-in-javafx
 * https://stackoverflow.com/questions/40310612/how-to-set-javafx-checkbox-value-in-a-tableview-column
 */
public class Setup {
    
    private final IntegerProperty legendNum; // number on the Legend list
    private final StringProperty dataFile;   // SCV file name with data
    private final StringProperty graphName;  // Tag to show curve on Legend. Default is dataFile
    private final BooleanProperty graphChk;  // Checkbox Hide/Show curve on the graph. Checked to hide
    private final DoubleProperty graphXShift; // Shift curve along X axis.
    private final DoubleProperty graphXScale; // Scale along X axis.
    private final DoubleProperty graphYShift; // Shift curve along Y axis.
    private final DoubleProperty graphYScale; // Scale along Y axis.
    private final StringProperty setupFile;  // Project file to keep all settings.
    private final StringProperty graphColor; // Custom Color of line
    
    
    
    /**
     * Default constructor.
     */
    
    //public Setup( ) {
        //this(null);
    //};
    
    public Setup() {
        // this(null);
        this.dataFile = new SimpleStringProperty("dataFile");
        this.legendNum = new SimpleIntegerProperty(1);
        this.graphName = new SimpleStringProperty("GraphName");
        this.graphChk = new SimpleBooleanProperty(false); // not checked = not to hide the curve
        this.graphXShift = new SimpleDoubleProperty(1.25);
        this.graphXScale = new SimpleDoubleProperty(2.5);
        this.graphYShift = new SimpleDoubleProperty(1.25);
        this.graphYScale = new SimpleDoubleProperty(2.5);
        this.setupFile = new SimpleStringProperty("SetupName");
        this.graphColor = new SimpleStringProperty("Red");
    };
    
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * mainApp is used to setup refresh of graph in listener in case check-box is changed
     * @param fileSetUp
     * @param mainApp
     */
    
    public Setup( String fileSetUp, MainApp mainApp) {
        // some initial dummy data
        this.dataFile = new SimpleStringProperty("");
        this.legendNum = new SimpleIntegerProperty(-1);
        this.graphName = new SimpleStringProperty("");
       
        this.graphChk = new SimpleBooleanProperty(false); // not checked = not to hide the curve
        // add listener to newly created properties.
        this.graphChk.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                //System.out.println("changed " + oldValue + "->" + newValue);
                mainApp.ctrlGraph.toggleLine();
            }
        });
        
        
        this.graphXShift = new SimpleDoubleProperty(1.0);
        this.graphXScale = new SimpleDoubleProperty(1.0);
        this.graphYShift = new SimpleDoubleProperty(1.0);
        this.graphYScale = new SimpleDoubleProperty(1.0);
        this.setupFile = new SimpleStringProperty(fileSetUp);
        this.graphColor = new SimpleStringProperty("0x000000ff");
        // TO DO
    }
public Setup( MainApp mainApp) {
        // some initial dummy data
        this.dataFile = new SimpleStringProperty("");
        this.legendNum = new SimpleIntegerProperty(1);
        this.graphName = new SimpleStringProperty("");
        this.graphChk = new SimpleBooleanProperty(false); // not checked = not to hide the curve
        // add listener to newly created, not read from file, properties.
        this.graphChk.addListener(new ChangeListener<Boolean>(){
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue == false) {
                    //System.out.println("changed " + oldValue + "->" + newValue);
                    mainApp.ctrlGraph.toggleLine();
                }
            }
        });
        
        
        this.graphXShift = new SimpleDoubleProperty(1.0);
        this.graphXScale = new SimpleDoubleProperty(1.0);
        this.graphYShift = new SimpleDoubleProperty(1.0);
        this.graphYScale = new SimpleDoubleProperty(1.0);
        this.setupFile = new SimpleStringProperty("SetupFile");
        this.graphColor = new SimpleStringProperty("#0000ff");
        // TO DO
    }
    
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    /*
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;
    }
    */
    /*-----LegendNum------*/
    public int getLegendNum() {
        return legendNum.get();
    }
    
    public void setLegendNum(int legendNum) {
        this.legendNum.set(legendNum);
    }
    
    public IntegerProperty legendNumProperty() {
        return legendNum;
    }
    
    /*-----graphDataFile------*/
    public String getGraphDataFile() {
        return dataFile.get();
    }
    
    public void setGraphDataFile( String graphDataFile) {
        this.dataFile.set(graphDataFile);
    }
    
    public StringProperty graphDataFileProperty() {
        return dataFile;
    }
    
    
    /*-----graphName------*/
    public String getGraphName() {
        return graphName.get();
    }
    
    public void setGraphName( String graphName) {
        this.graphName.set(graphName);
    }
    
    public StringProperty graphNameProperty() {
        return graphName;
    }
    
    /*------graphChk------*/
    public Boolean getGraphChk() {
        return graphChk.get();
    }
    
    public void setGraphChk( Boolean graphChk) {
        this.graphChk.set(graphChk);
    }
    
    public BooleanProperty graphChkProperty() {
        return graphChk;
    }
    
    /*--------graphShift------*/
    public double getGraphXShift() {
        return graphXShift.get();
    }
    
    public void setGraphXShift( double graphShift) {
        this.graphXShift.set(graphShift);
    }
    
    public DoubleProperty graphXShiftProperty() {
        return graphXShift;
    }
    
    public double getGraphYShift() {
        return graphYShift.get();
    }
    
    public void setGraphYShift( double graphShift) {
        this.graphYShift.set(graphShift);
    }
    
    public DoubleProperty graphYShiftProperty() {
        return graphYShift;
    }
    
    /*------graphScale------*/
    public double getGraphXScale() {
        return graphXScale.get();
    }
    
    public void setGraphXScale( double graphScale) {
        this.graphXScale.set(graphScale);
    }
    
    public DoubleProperty graphXScaleProperty() {
        return graphXScale;
    }
    
    public double getGraphYScale() {
        return graphYScale.get();
    }
    
    public void setGraphYScale( double graphScale) {
        this.graphYScale.set(graphScale);
    }
    
    public DoubleProperty graphYScaleProperty() {
        return graphYScale;
    }
    /*------graphColorPicker------*/
    public String getGraphColor() {
        return graphColor.get();
    }
    
    public void setGraphColor( String graphColor) {
        this.graphColor.set(graphColor);
    }
    
    public StringProperty graphColorProperty() {
        return graphColor;
    }
}
