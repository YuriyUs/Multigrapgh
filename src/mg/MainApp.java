/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import mg.model.Setup;
import mg.model.SetupListWrapper;
import mg.view.AddGraphDialogController;
import mg.view.GraphController;
import mg.view.GraphLegendController;
import mg.view.RootLayoutController;


/**
 *
 * @author xx
 */
public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    /**
    * The data as an observable list of Settings.
    * setupData - array of sets of properties for every line on the chart.
    *             Also is used to build GraphLegend Menu.
    * series - array of line.series on the chart. Appearance of Lines on the 
    *             chart controlled by setupData triggers.
    * setMainApp - is the reference back to class MainApp across all classes
    */
    
    private final ObservableList<Setup> setupData = FXCollections.observableArrayList();
    
    //public List<LineChart.Series<Long, Double>> seriesData = new ArrayList<>(); //former series, now seriesData.
    
    public GraphController ctrlGraph;   // Main class to control Graph.
    
    /**
     * Constructor
     */
    public MainApp() {
        // Add some sample data
        //setupData.add(new Setup());
    }

    
    
    public ScrollPane scrollPane; 
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MultiGraph");

        initRootLayout();

        showGraphLegend();  
        showGraph();
        
        //setupChkBoxListener();
        
    }

    /**
     * Initializes the root layout.
     */
    public void initRootLayout() {
        try {
            // Load the main / root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            RootLayoutController ctrl = (RootLayoutController)loader.getController();
            ctrl.setMainApp(this);
            
            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
         // Try to load last opened person file.
        File file = getProjectFilePath();
        if (file != null) {
            loadSetupDataFromFile(file);
        }
    }

    /**
     * Shows the Graph Legend main Pane inside the root layout.
     */
    public void showGraphLegend() {
        try {
            // Load GraphLegend.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GraphLegend.fxml"));
            Pane graphLegend = (Pane) loader.load();
           
            GraphLegendController ctrl = (GraphLegendController)loader.getController();
            ctrl.setMainApp(this);
            System.out.println("The Controller is= " + ctrl );
            
           // Set legend on the left side of the screen                  
            rootLayout.setLeft(graphLegend); 
            
                      
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Shows the lineChart Graph inside the root layout.
     */
    public void showGraph() {
        try {
            // Load person overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Graph.fxml"));
            AnchorPane lineGraph = (AnchorPane) loader.load();
            
            ctrlGraph = (GraphController)loader.getController();
            ctrlGraph.setMainApp(this);

            // Set graph overview into the center of root layout.
            rootLayout.setCenter(lineGraph);
            ctrlGraph.displayGraphs();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    /**
    * Opens a dialog to edit details for the specified graph. If the user
    * clicks OK, the changes are saved into the SetupData.
    * 
    * @param setup the person object to be edited
    * @return true if the user clicked OK, false otherwise.
    */
    public boolean showSetupEditDialog(Setup setup) {
        try {
            System.out.println("********* Enter  showSetupEditDialog" );
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/AddGraphDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            if (setup.getGraphDataFile().isEmpty()) {
                dialogStage.setTitle("Add Graph");
            } else {
                dialogStage.setTitle("Edit Graph");
            }
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the mainApp the controller.
            AddGraphDialogController controller = loader.getController();
            controller.setMainApp(this);
            controller.setDialogStage(dialogStage);
            controller.setGraph(setup);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**    //????????????????????to find what this code is for????????????????????????????????????????????????????????????????????????
     *  Sync setup and graph to reflect changes
     * @param ctrlGraph
     * @param setup
     */
    public void syncGraph(GraphController ctrlGraph, Setup setup){
        ctrlGraph.addGraphToChart(setup);
    }
    
    
    /**
     * Returns the main stage.
     * @return
     */
    public File getProjectFilePath() {
    Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
    String filePath = prefs.get("filePath", null);
    if (filePath != null) {
        return new File(filePath);
    } else {
        return null;
    }
}   
    /**
     * Sets the file path of the currently loaded file. The path is persisted in
     * the OS specific registry.
     * 
     * @param file the file or null to remove the path
     */
    public void setProjectFilePath(File file) {
        Preferences prefs = Preferences.userNodeForPackage(MainApp.class);
        if (file != null) {
            prefs.put("filePath", file.getPath());

            // Update the stage title.
            primaryStage.setTitle("MultiGraph - " + file.getName());
        } else {
            prefs.remove("filePath");

            // Update the stage title.
            primaryStage.setTitle("MultiGraph - New Project");
        }
    }
    
    /**
     * Loads setups from the specified file. 
     * 
     * @param file
     */
    public void loadSetupDataFromFile(File file) {
        try {
            System.out.println("Enter loadSetupDataFromFile. File name = "+file);
            JAXBContext context = JAXBContext
                    .newInstance(SetupListWrapper.class);
            Unmarshaller um = context.createUnmarshaller();
            
            
            // Reading XML from the file and unmarshalling.
            SetupListWrapper wrapper = (SetupListWrapper) um.unmarshal(file);
                        
            setupData.clear();
            setupData.addAll(wrapper.getSetups());
            
            // add listener to just read from xml properties
            for (Setup stp : wrapper.getSetups()){
                System.out.println("Legendnum=" + stp.getLegendNum());
                //ctrlGraph.addGraphToChart(stp);  // reading line data from file
                
                // setup listener for changes in the line checkbox
                stp.graphChkProperty().addListener(new ChangeListener<Boolean>(){
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        // Only if completed
                        //if (newValue) {
                            System.out.println("changed " + oldValue + "->" + newValue);
                            ctrlGraph.toggleLine();
                        //}
                    }
                });
            }
            
            // Save the file path to the registry.
            System.out.println("Save the file path to the registry.");
            setProjectFilePath(file);

        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not load data");
            alert.setContentText("Could not load data from file:\n" + file.getPath());

            alert.showAndWait();
        }
    }

    /**
     * Saves the current graph setup data to the specified file.
     * 
     * @param file
     */
    public void saveSetupDataToFile(File file) {
        try {
            JAXBContext context = JAXBContext
                    .newInstance(SetupListWrapper.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            // Wrapping our curve data.
            SetupListWrapper wrapper = new SetupListWrapper();
            wrapper.setSetups(setupData);

            // Marshalling and saving XML to the file.
            m.marshal(wrapper, file);

            // Save the file path to the registry.
            setProjectFilePath(file);
        } catch (Exception e) { // catches ANY exception
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Could not save data");
            alert.setContentText("Could not save data to file:\n" + file.getPath());

            alert.showAndWait();
        }
    }
    /**
     * Returns the data as an observable list of setupData. 
     * @return
     */
    public ObservableList<Setup> getSetupData() {
        return setupData;
    }
    
    /**
     * Returns the main stage.
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
