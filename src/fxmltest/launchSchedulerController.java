/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fxmltest;

import Mail.EmailOptions;
import Reporting.ConcatenatedReports;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/**
 *
 * @author Ashraf
 */
public class launchSchedulerController implements Initializable {

    @FXML
    private JFXSpinner launchSpinner;
    @FXML
    private Label taskLabel;
    @FXML
    private JFXButton openReportButton;
    
    private static launchSchedulerController launchController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        launchController = this;
        //launchSpinner.setVisible(false);

    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        FXMLDocumentController.closeLaunchDialog();
    }

    @FXML
    private void launchOnAction(ActionEvent event) {
        //taskLabel.setVisible(true);
        launchSpinner.setVisible(true);
        taskLabel.setText("Executing scheduled tasks");
        FXMLDocumentController.startScheduler();
        //FXMLDocumentController.closeLoginDialog();
    }

    @FXML
    private void openReportOnAction(ActionEvent event) {
        ;//FXMLDocumentController.closeLaunchDialog();
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(EmailOptions.getFileDirectory() + ConcatenatedReports.getCurrentReportName());
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

//    public static void setLabel(String value){
//        taskLabel.setVisible(true);
//        taskLabel.setText(value);
//    }
    
    public void updateLabel(String value) {
        taskLabel.setText(value);
    }

    public void stopSpinner() {
        launchSpinner.setVisible(false);
    }

    public static launchSchedulerController getController(){
        return launchController;
    }

    public void updateButton() {
        openReportButton.setVisible(true);
        stopSpinner();
        
    }

    void startSpinner() {
        launchSpinner.setVisible(true);
    }
}
