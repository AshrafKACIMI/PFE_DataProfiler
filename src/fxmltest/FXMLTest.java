/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;

import Mail.ReportingEMail;
import com.jfoenix.controls.JFXDecorator;
import fxmltest.computing.BasicStatisticsProfiler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Ashraf
 */
public class FXMLTest extends Application {
    private static Parent root;
    private static Stage mainStage;

    /**
     * @return the mainStage
     */
    public static Stage getMainStage() {
        return mainStage;
    }
    
    @Override
    public void start(Stage stage) throws Exception {

        
        root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
        //Scene scene = new Scene(root);
        mainStage = stage;
        Scene scene;
        scene = new Scene(new JFXDecorator(stage, getRoot(), true, true, true));
	stage.setWidth(1200);
        stage.setHeight(720);
        
        scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-fonts.css").toExternalForm());
	scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-design.css").toExternalForm());
	scene.getStylesheets().add(getClass().getResource("/resources/css/jfoenix-main-demo.css").toExternalForm());
        
        stage.setScene(scene);
        stage.setTitle("Cool Profiler");

        stage.show();
        
        FXMLDocumentController controller;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * @return the root
     */
    public static Parent getRoot() {
        return root;
    }
    
    
    
}
