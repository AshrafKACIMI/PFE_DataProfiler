/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;

import DQRepository.IConnector;
import DQRepository.OracleConnector;
import Entities.TableInfo;
import Entities.TablesFactory;
import GraphicWidgets.RegexFieldValidator;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ashraf
 */
public class LoginController implements Initializable {
    @FXML
    private Parent root;
    private ArrayList<TableInfo> tables;
    
    @FXML
    private JFXTextField serverInput;
    @FXML
    private JFXTextField userInput;
    @FXML
    private JFXTextField portInput;
    @FXML
    private JFXTextField SIDInput;
    @FXML
    private JFXButton loginButton;
    @FXML
    private JFXButton cancelButton;
    @FXML
    private JFXPasswordField passwordInput;
    @FXML
    JFXRadioButton oracleRadio;
    @FXML
    JFXRadioButton greenplumRadio;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        RegexFieldValidator validator = new RegexFieldValidator(RegexFieldValidator.IPADDRESS_PATTERN);
        validator.setMessage("Password Can't be empty");
        validator.setIcon(new Icon(AwesomeIcon.WARNING,"1em",";","error"));
        serverInput.getValidators().add(validator);
        serverInput.focusedProperty().addListener((o,oldVal,newVal)->{
                if(!newVal) serverInput.validate();
        });
    }    

    @FXML
    private void loginOnAction(ActionEvent event) {
        // check here if oracle or the other one is selected
        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                IConnector connector = null;
                if (oracleRadio.isSelected()){
                    String url = "jdbc:oracle:thin:@" + serverInput.getText()
                        + ":" + portInput.getText()
                        + ":" + SIDInput.getText();
                    connector = new OracleConnector(userInput.getText(), passwordInput.getText(), url);
                } else if (greenplumRadio.isSelected()){
                    // TODO
                }

                try {
                    Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                    FXMLDocumentController controller = FXMLDocumentController.getController();
                    System.out.println(controller);
                    //updating TablesFactory fields to ensure connection
                        FXMLDocumentController.setConnector(connector);
                        controller.initializeTableTreeView();
                        controller.initializeRefTab();
                        controller.initializeDateCheck();
                        controller.initializeDuplicateCheck();
                        controller.initializeDashboard();
                        controller.setTitleLabel(userInput.getText());
                        controller.refreshDashboard();
                        
                        FXMLDocumentController.closeLoginDialog();
                        
                        return null;
                        //FXMLDocumentController.closeLoginPopup();

            }
        };


        new Thread(task).run();
        

    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
      FXMLDocumentController.closeLoginDialog();
        
    }
    
}
