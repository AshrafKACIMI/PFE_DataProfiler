/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Computing;

import DQRepository.IConnector;
import DQRepository.MetaDataConnector;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ashraf
 */
public class DuplicatesEngine {
    private IConnector connector;
    private String table;
    private ArrayList<String> keys;

    public DuplicatesEngine(IConnector connector, String table, ArrayList<String> keys) {
        this.connector = connector;
        this.table = table;
        this.keys = keys;
    }
    
    public int checkDuplicates(){
        int count = 0;
        String keysString = String.join(", ", this.keys);
        String query = 
                "SELECT\n" +
                "   ?, COUNT(*) c\n" +
                "FROM " + table +
                " \n " +
                "GROUP BY\n" +
                "    ? \n" +
                "HAVING \n" +
                "    COUNT(*) > 1";
        
        
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
            PreparedStatement stmt=con.prepareStatement(query);
            stmt.setString(1, keysString);
            System.out.println("keys STATEMENT: " + keysString);
            
            
            stmt.setString(2, keysString);
            System.out.println("keys STATEMENT: " + keysString);
            
            try {
                //step4 execute query
                ResultSet rs=stmt.executeQuery();
                if (rs.next())
                    count = rs.getInt("c");
            } catch (SQLException ex) {
                Logger.getLogger(DuplicatesEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DuplicatesEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DuplicatesEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        //updateMeta(count);
        updateMeta(count);
        return count;
    }
    
    
    private void updateMeta(int nbDuplicates){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
            .format(Calendar.getInstance().getTime());
        Connection Sqliteconn = MetaDataConnector.getSqliteConnection();
        MetaDataConnector.insertDuplicate(Sqliteconn, connector.getDbName(), table, keys, nbDuplicates, timeStamp);
        
    
    }
    
    
    
    
}
