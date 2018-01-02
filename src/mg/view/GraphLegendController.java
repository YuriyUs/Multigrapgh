/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.view;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import mg.MainApp;
import mg.model.Setup;


/**
 * FXML Controller class
 *
 * @author xx
 */
public class GraphLegendController{

     @FXML
    private TableView<Setup> tabLegend;   // these are my setups

    @FXML
    private TableColumn<Setup, Number> numColumn;

    @FXML
    private TableColumn<Setup, String> graphNameColumn;
    
    @FXML
    private TableColumn<Setup, Boolean> graphChkColumn;

    // Reference to the main application.
    private MainApp mainApp; 
    
        
     /**
    * Called when the user clicks the add graph. Opens a dialog to edit
    * details for a new graph.
    */
    @FXML
    private void handleAddGraph() {
        System.out.println("******* Clicked AddGraph button" );
        Setup tempGraph = new Setup(mainApp);
        //tempGraph.setMainApp(mainApp);  // set mainApp in the setup. In some cases I read this from file, so this have to be changed.
        //System.out.println("******** tempGraph created " + tempGraph.getGraphName() + tempGraph.getGraphColor() );
        boolean okClicked = mainApp.showSetupEditDialog(tempGraph);
        System.out.println("******** Dialog opened "  );
        if (okClicked) {
            mainApp.ctrlGraph.isMinMaxSet = false; // this will reset boundaries values and cause recalculating.
            tempGraph.setLegendNum(mainApp.getSetupData().size()+1); // Set proper index on new line on legend.
            //System.out.println("Next Element to add = " + mainApp.getSetupData().size() );
            mainApp.getSetupData().add(tempGraph);
            //mainApp.syncGraph(mainApp.ctrlGraph,mainApp.getSetupData().get(tabLegend.getSelectionModel().getSelectedIndex()));
            mainApp.syncGraph(mainApp.ctrlGraph,tempGraph);
        }
    } 
       
    @FXML
    private void handleEditGraph() {
        System.out.println("******* Clicked EditGraph button" );
        Setup selectedSetup = tabLegend.getSelectionModel().getSelectedItem();
        if (selectedSetup != null) {
            if (mainApp.showSetupEditDialog(selectedSetup)) {
                // Clicked OK
                mainApp.ctrlGraph.isMinMaxSet = false; // this will reset boundaries values and cause recalculating.
            };
                       
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Graph Selected");
            alert.setContentText("Please select a graph in the table.");

            alert.showAndWait();
        }
    }  
     
    
    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeleteGraph() {
        System.out.println("Clicked DeleteGraph button" );
        int selectedIndex = tabLegend.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            tabLegend.getItems().remove(selectedIndex);
            reNumInd();
        } else {
            // Nothing selected.
            Alert alert = new Alert(AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Graph Selected");
            alert.setContentText("Please select a graph in the table.");

            alert.showAndWait();
        }
    } 
    
    /**
     * Is called by the main application to give a reference back to itself.
     * 
     * @param mainApp
     */
    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        tabLegend.setItems(mainApp.getSetupData());
    }
    
    private void reNumInd(){
        int ind =1;
        for (Setup stp : tabLegend.getItems()){
            stp.setLegendNum(ind++);
        }
    }
    /**
     * Initializes the controller class.
     */
    //@Override
    //public void initialize(URL url, ResourceBundle rb) {
    //    // TODO
    //}    
    @FXML
    private void initialize() {
        System.out.println("GrafLegendController Initialize started.");
        String IncompleteLegend = "tableview_incomplete_legend"; // class from CSS
        // Initialize the legend table with the three columns.
        numColumn.setCellValueFactory(
                cellData -> cellData.getValue().legendNumProperty());
        
        numColumn.setCellFactory((TableColumn<Setup, Number> c) -> {
            return new TableCell<Setup, Number>() {
                @Override
                protected void updateItem(Number item, boolean empty) {    
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Setup> currentRow = getTableRow();

                    if (!isEmpty()) {
                            
                            setTextFill(Color.web( mainApp.getSetupData().get(currentRow.getIndex()).getGraphColor())); 
                            //currentRow.setStyle("-fx-background-color:white");

                    }
                }
            };
        });
       
        numColumn.setSortable(false);

        // ===================================
        graphNameColumn.setCellValueFactory(
                cellData -> cellData.getValue().graphNameProperty());
        
        graphNameColumn.setCellFactory((TableColumn<Setup, String> column) -> {
            return new TableCell<Setup, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    setText(empty ? "" : getItem().toString());
                    setGraphic(null);

                    TableRow<Setup> currentRow = getTableRow();

                    if (!isEmpty()) {
                            
                            setTextFill(Color.web( mainApp.getSetupData().get(currentRow.getIndex()).getGraphColor())); 
                            //currentRow.setStyle("-fx-background-color:white");

                    }
                }
            };
        });
        
        // ****************** experimental third column ****************************
        // from here:https://stackoverflow.com/questions/20879242/get-checkbox-value-in-a-table-in-javafx
        //
        graphChkColumn.setCellValueFactory(
        new Callback<CellDataFeatures<Setup,Boolean>,ObservableValue<Boolean>>()
        {
            //This callback tell the cell how to bind the data model 'Registered' property to
            //the cell, itself.
            @Override
            public ObservableValue<Boolean> call(CellDataFeatures<Setup, Boolean> param)
            {   
                return param.getValue().graphChkProperty();
            }   
        });

        graphChkColumn.setCellFactory(CheckBoxTableCell.forTableColumn(graphChkColumn));
        
        tabLegend.setRowFactory(new Callback<TableView<Setup>, TableRow<Setup>>() {
            @Override
            public TableRow<Setup> call(TableView<Setup> paramP) {
                return new TableRow<Setup>() {
                    @Override
                    protected void updateItem(Setup paramT, boolean paramBoolean) {
                        super.updateItem(paramT, paramBoolean);
                        //setStyle(""); //reset styles
                        if (!isEmpty()) {
                            //int line = this.getIndex() + 1;
                            //System.out.println("Applying row factory for line");
                            //setStyle("-fx-focus-color:"+paramT.getGraphColor());
                            setStyle("-fx-background-color:white");
                            //this.setTextFill(Color.RED);
                            if (!paramT.getGraphDataFile().isEmpty()) {     //if file with data exist
                                if (getStyleClass().contains(IncompleteLegend)) {   // and or this line set up strikeout
                                    getStyleClass().remove(IncompleteLegend);       // remove that style
                                    
                                    //System.out.println("Remove "+IncompleteLegend);
                                // when data file is missing and strike out style is not set    
                                } 
                            }   else {
                                //System.out.println("GraphDataFile missing ");
                                if (!getStyleClass().contains(IncompleteLegend)) {
                                    getStyleClass().add(IncompleteLegend);
                                    
                                    //System.out.println("Add "+IncompleteLegend);
                                } //else System.out.println(" found property "+ IncompleteLegend);
                            }    
                            
                        }
                    }
                };
            };
            
        });
        
    }    

//    /**
//     * Initializes the controller class.
//     */
//    //@Override
//    //public void initialize(URL url, ResourceBundle rb) {
//    //    // TODO
//    //}    
//    @FXML
//    private void initialize() {
//        System.out.println("GrafLegendController Initialize started.");

     
    
    /**
    * Fills all text fields to show details about the person.
    * If the specified person is null, all text fields are cleared.
    * 
    * @param person the person or null
    */
  /* private void showGraphDetails(Setup setup) {
       if (setup != null) {
           //to force to redraw table tabLegend to refresh color and other
                tabLegend.getColumns().get(0).setVisible(false);
                tabLegend.getColumns().get(0).setVisible(true);
                
           
           

           
       } else {

       }
    }
    */
}
