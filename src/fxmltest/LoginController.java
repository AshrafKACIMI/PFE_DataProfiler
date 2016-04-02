/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;

import fxmltest.computing.TableInfo;
import fxmltest.computing.TablesFactory;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    private TextField serverInput;
    @FXML
    private TextField userInput;
    @FXML
    private TextField portInput;
    @FXML
    private TextField SIDInput;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;
    @FXML
    private PasswordField passwordInput;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      //TODO  
    }    

    @FXML
    private void loginOnAction(ActionEvent event) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
            FXMLDocumentController controller = FXMLDocumentController.getController();
            System.out.println(controller);
            Platform.runLater(() -> {
            //updating TablesFactory fields to ensure connection
                TablesFactory.setConnectionURL("jdbc:oracle:thin:@" + serverInput.getText()
                + ":" + portInput.getText()
                + ":" + SIDInput.getText());//jdbc:oracle:thin:@localhost:1522:orcl
                TablesFactory.setUserName(userInput.getText());
                TablesFactory.setPassword(passwordInput.getText());
                controller.initializeTableTreeView();
        });
              
    }

    @FXML
    private void cancelOnAction(ActionEvent event) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }
    
}
