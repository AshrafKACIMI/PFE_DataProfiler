/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ashraf
 */
public class ReferentialIntegrityEngine {
    private IConnector connector;
    private String parentTable;
    private String parentColumn;
    private String childTable;
    private String childColumn;
    private ArrayList<String> sample;

    public ReferentialIntegrityEngine(IConnector connector, String parentTable, String parentColumn, 
            String childTable, String childColumn) {
        this.connector = connector;
        this.parentTable = parentTable;
        this.parentColumn = parentColumn;
        this.childTable = childTable;
        this.childColumn = childColumn;
    }
    
    public String referentialIntegritySampleQuery(int nbRows){
        return "SELECT * FROM ("
                + "SELECT DISTINCT (" + parentColumn + ") FROM " + parentTable
                + " MINUS "
                + "SELECT DISTINCT (" + childColumn + ") FROM " + childTable
                + ") A "
                + "LIMIT " + nbRows;
    }
    
    public String referentialIntegritySampleQuery(){
        return "SELECT * FROM ("
                + "SELECT DISTINCT (" + parentColumn + ") FROM " + parentTable
                + " MINUS "
                + "SELECT DISTINCT (" + childColumn + ") FROM " + childTable
                + ") A ";
    }
    
    public String referentialIntegrityViolCountQuery(){
        return "SELECT count(*) FROM ("
                + "SELECT DISTINCT (" + parentColumn + ") FROM " + parentTable
                + " MINUS "
                + "SELECT DISTINCT (" + childColumn + ") FROM " + childTable
                + ") A ";
    }
    
    
    public int checkReferentialIntegrity(){
        int nbViol = -1;
        try {
            String connectionURL = connector.getConnectionURL();
            String userName = connector.getUserName();
            String password = connector.getPassword();
            String driverClass = connector.getDriverClass();
            
            //step1 load the driver class
            Class.forName(driverClass);
            
            //step2 create  the connection object
            Connection con=DriverManager.getConnection(
                    connectionURL,
                    userName,
                    password);
            
            //step3 create the statement object
            Statement stmt=con.createStatement();
            
            try {
                //step4 execute query
                ResultSet rs=stmt.executeQuery(referentialIntegrityViolCountQuery());
                rs.next();
                nbViol = rs.getInt(1);
            } catch (SQLException ex) {
                Logger.getLogger(ReferentialIntegrityEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ReferentialIntegrityEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(ReferentialIntegrityEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return nbViol;
    }
    
    
    
    
}
