/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testhierarchie.Graphics;

import testhierarchie.Graphics.TableTreeView;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author Ashraf
 */
public class Login extends VBox{
    
    private static String connectionURL = ""; //jdbc:oracle:thin:@localhost:1522:orcl
    private static String server = "";
    private static String userName = "";
    private static String password = "";  //
    private static String port = "";
    private static String SID = "";


    
    private HBox serverBox;
    private HBox userBox;
    private HBox passwordBox;
    private HBox portBox;
    private HBox SIDBox;
    private Button login;

    public Login() {
        super(30);
        
        Label serverLabel = new Label("Server");
        Label userLabel = new Label("Username");
        Label passwordLabel = new Label("Password");
        Label portLabel = new Label("Port");
        Label SIDLabel = new Label("SID");
        
        TextField serverInput = new TextField();
        TextField userInput = new TextField();
        TextField passwordInput = new TextField();
        TextField portInput = new TextField();
        TextField SIDInput = new TextField();
        
        this.serverBox = new HBox(20);
        this.userBox = new HBox(20);
        this.passwordBox = new HBox(20);
        this.portBox = new HBox(20);
        this.SIDBox = new HBox(20);
        
        this.serverBox.getChildren().addAll(serverLabel, serverInput);
        this.userBox.getChildren().addAll(userLabel, userInput);
        this.passwordBox.getChildren().addAll(passwordLabel, passwordInput);
        this.portBox.getChildren().addAll(portLabel, portInput);
        this.SIDBox.getChildren().addAll(SIDLabel, SIDInput);
        
        this.login = new Button("Login");
        this.login.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                setServer(serverInput.getText());
                setUserName(userInput.getText());
                setPassword(passwordInput.getText());
                setPort(portInput.getText());
                setSID(SIDInput.getText());
                
                System.out.println(getServer() + " - " + getUserName() + " - " +
                        getPassword() + " - " + getPort() + " - " + getSID());
                
                Stage hier = new Stage();
                hier.setScene(new Scene(new TableTreeView()));
                hier.show();
                
                
            }
        });
        
        this.getChildren().addAll(serverBox, userBox, passwordBox, portBox, SIDBox, login);
        
    }
    
    /**
     * @return the serverBox
     */
    public HBox getServerBox() {
        return serverBox;
    }

    /**
     * @param serverBox the serverBox to set
     */
    public void setServerBox(HBox serverBox) {
        this.serverBox = serverBox;
    }

    /**
     * @return the userBox
     */
    public HBox getUserBox() {
        return userBox;
    }

    /**
     * @param userBox the userBox to set
     */
    public void setUserBox(HBox userBox) {
        this.userBox = userBox;
    }

    /**
     * @return the passwordBox
     */
    public HBox getPasswordBox() {
        return passwordBox;
    }

    /**
     * @param passwordBox the passwordBox to set
     */
    public void setPasswordBox(HBox passwordBox) {
        this.passwordBox = passwordBox;
    }

    /**
     * @return the portBox
     */
    public HBox getPortBox() {
        return portBox;
    }

    /**
     * @param portBox the portBox to set
     */
    public void setPortBox(HBox portBox) {
        this.portBox = portBox;
    }

    /**
     * @return the SIDBox
     */
    public HBox getSIDBox() {
        return SIDBox;
    }

    /**
     * @param SIDBox the SIDBox to set
     */
    public void setSIDBox(HBox SIDBox) {
        this.SIDBox = SIDBox;
    }

    /**
     * @return the login
     */
    public Button getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(Button login) {
        this.login = login;
    }

    
    /**
     * @return the connectionURL
     */
    public static String getConnectionURL() {
        return "jdbc:oracle:thin:@" + server  + ":" + port + ":" + SID;//"jdbc:oracle:thin:@localhost:1522:orcl"
    }

    /**
     * @param aConnectionURL the connectionURL to set
     */

    /**
     * @return the server
     */
    public static String getServer() {
        return server;
    }

    /**
     * @param aServer the server to set
     */
    public static void setServer(String aServer) {
        server = aServer;
    }

    /**
     * @return the userName
     */
    public static String getUserName() {
        return userName;
    }

    /**
     * @param aUserName the userName to set
     */
    public static void setUserName(String aUserName) {
        userName = aUserName;
    }

    /**
     * @return the password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * @param aPassword the password to set
     */
    public static void setPassword(String aPassword) {
        password = aPassword;
    }

    /**
     * @return the port
     */
    public static String getPort() {
        return port;
    }

    /**
     * @param aPort the port to set
     */
    public static void setPort(String aPort) {
        port = aPort;
    }

    /**
     * @return the SID
     */
    public static String getSID() {
        return SID;
    }

    /**
     * @param aSID the SID to set
     */
    public static void setSID(String aSID) {
        SID = aSID;
    }    
    
}
