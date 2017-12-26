package mg.view;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import mg.MainApp;
import mg.model.Setup;

public class AddGraphDialogController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField dataFileField;

    @FXML
    private TextField graphNameField;

//    @FXML
//    private CheckBox hideGraphChekBox;

    @FXML
    private TextField yShiftField;

    @FXML
    private TextField yScaleField;
    
    @FXML
    private TextField xScaleField;

    @FXML
    private TextField xShiftField;

    @FXML
    private ColorPicker graphColorPicker;
    
    private Stage dialogStage;
    private Setup setup;
    private boolean okClicked = false;
    private MainApp mainApp; 

    @FXML
    void initialize() {
        assert dataFileField != null : "fx:id=\"dataFileField\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";
        assert graphNameField != null : "fx:id=\"graphNameField\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";
        //assert hideGraphChekBox != null : "fx:id=\"hideGraphChekBox\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";
        assert yShiftField != null : "fx:id=\"yShiftField\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";
        assert yScaleField != null : "fx:id=\"yScaleField\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";
        assert xShiftField != null : "fx:id=\"xShiftField\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";
        assert xScaleField != null : "fx:id=\"xScaleField\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";        
        assert graphColorPicker != null : "fx:id=\"graphColorPicker\" was not injected: check your FXML file 'AddGraphDialog.fxml'.";

    }
    
    /**
     * Sets the stage of this dialog.
     * 
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }
    
    /**
     * Sets the graph setup to be edited in the dialog.
     * 
     * @param setup
     */
    public void setGraph(Setup setup) {
        this.setup = setup;
        dataFileField.setText(setup.getGraphDataFile());
        graphNameField.setText(setup.getGraphName());
        //hideGraphChekBox.setDisable(setup.getGraphChk());
        yShiftField.setText(Double.toString(setup.getGraphYShift()));
        yScaleField.setText(Double.toString(setup.getGraphYScale()));
        xShiftField.setText(Double.toString(setup.getGraphXShift()));
        xScaleField.setText(Double.toString(setup.getGraphXScale()));
        graphColorPicker.getStyleClass().add("split-button");
        graphColorPicker.setValue(Color.valueOf(setup.getGraphColor()));
    }
    /**
     * Returns true if the user clicked OK, false otherwise.
     * 
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }
    
    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            setup.setGraphDataFile(dataFileField.getText());
            setup.setGraphName(graphNameField.getText());
           // setup.setGraphChk(hideGraphChekBox.isDisable());
            setup.setGraphXShift(Double.parseDouble(xShiftField.getText()));
            setup.setGraphXScale(Double.parseDouble(xScaleField.getText()));
            setup.setGraphYShift(Double.parseDouble(yShiftField.getText()));
            setup.setGraphYScale(Double.parseDouble(yScaleField.getText()));
            setup.setGraphColor("#" + Integer.toHexString(graphColorPicker.getValue().hashCode()));
            
            
            okClicked = true;
            //Redraw the line
            try {
                mainApp.ctrlGraph.graphData.delAllReaders();
            } catch (IOException ex) {
                Logger.getLogger(AddGraphDialogController.class.getName()).log(Level.SEVERE, null, ex);
            }
           
            mainApp.ctrlGraph.displayGraphs();//.getLineChart().get(ind).getNode().setVisible(true);
            dialogStage.close();
        }
    }
    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    
    
    
    
    @FXML
    void handleGraphFileChooser() {
        FileChooser grfileChooser = new FileChooser();
        grfileChooser.setTitle("Select Graph File");
        grfileChooser.getExtensionFilters().addAll(
         new ExtensionFilter("CSV Files", "*.csv"),
         new ExtensionFilter("All Files", "*.*"));
        File file = grfileChooser.showOpenDialog(this.dialogStage); // you could pass a stage reference here if you wanted.
        if (file != null){
            dataFileField.setText(file.getAbsolutePath());
            graphNameField.setText(file.getName());
        } 
    }


    /**
     * Validates the user input in the text fields.
     * 
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
        /*
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n"; 
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n"; 
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n"; 
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n"; 
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n"; 
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n"; 
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
        */
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
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
}
