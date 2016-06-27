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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ashraf
 */
public class DatesEngine {
    private IConnector connector;
    private String table;
    private String validFrom;
    private String validTo;
    private ArrayList<String> keys;

    public DatesEngine(IConnector connector, String table, String validFrom, String validTo, ArrayList<String> keys) {
        this.connector = connector;
        this.table = table;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.keys = keys;
    }

    
    
    
    public ArrayList<Integer> checkDates(){
        int count = 0;
        ArrayList<Integer> results = new ArrayList<Integer>();
        int nbHoles = 0;
        int nbOverlaps = 0;
        
        String keysString = String.join(", ", this.keys);
        String overlapQuery = overlapQuery();
        String holesQuery = holesQuery();
        
        
        
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
            Statement overlapStmt=con.createStatement();
            Statement holesStmt=con.createStatement();
            
                
            
            
            try {
                //step4 execute query
                ResultSet rs=overlapStmt.executeQuery(overlapQuery);
                if (rs.next())
                    nbOverlaps = rs.getInt("overlaps");
                ResultSet rs2=holesStmt.executeQuery(holesQuery);
                if (rs2.next())
                    nbHoles = rs2.getInt("holes");
                
            } catch (SQLException ex) {
                Logger.getLogger(DatesEngine.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatesEngine.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatesEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
        //updateMeta(count);
        updateMeta(nbHoles, nbOverlaps);
        results.add(nbHoles);
        results.add(nbOverlaps);
        results.add(MetaDataConnector.getCount(connector.getDbName(), table, keys.get(0)));
        
        
        return results;
    }
    
    
    private void updateMeta(int nbHoles, int nbOverlaps){
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
            .format(Calendar.getInstance().getTime());
        Connection Sqliteconn = MetaDataConnector.getSqliteConnection();
        System.out.println("KEY 1 " + keys.get(0));
        int nbTotal = MetaDataConnector.getCount(connector.getDbName(), table, keys.get(0));
        MetaDataConnector.insertDate(Sqliteconn, connector.getDbName(), table, nbHoles, nbOverlaps, nbTotal, timeStamp);
    }
    
    public String overlapQuery(){
        String query = "";
        query += "SELECT count(*) overlaps FROM("
                + "SELECT ";
        for (String key:keys)
            query += "a."+ key + ", ";
        query += validFrom + ", ";
        query += validTo +"\n";
        query += ", lag(" +validTo + ",     1) over \n";
        query += "(PARTITION BY ";
        for (String key:keys)
            query += "a."+ key;
        query += "\n order by ";
        for (String key:keys)
            query += "a."+ key + ", ";
        query += "a."+validFrom + " ) old_valid_to";
        query += " FROM "+ table + " a ";
        query += ", \n(select count(*), ";
        query += String.join(", ", keys) + "\n";
        query += " FROM " + table;
        query += " GROUP BY " + String.join(", ", keys);
        query += "\n having count(*)>1) b \n";
        query += "WHERE ";
        for (int i = 0; i < keys.size(); i++){
            if (i>0)
                query += " AND ";
            String key =keys.get(i);
            query += "a."+key + " = b." + key;
        }
        query += " \n order by ";
        for (String key:keys)
            query += "a."+ key + ", ";
        query += "a."+validFrom + " \n";
        query += ") x \n";
        query += "WHERE " + validFrom + " < old_valid_to \n";
        query += "ORDER BY ";
        for (String key:keys)
            query += key + ", ";
        query += validFrom;
        
        return query;
    }
    
    
    public String holesQuery(){
        String query = "";
        query += "SELECT count(*) holes FROM("
                + "SELECT ";
        for (String key:keys)
            query += "a."+ key + ", ";
        query += validFrom + ", ";
        query += validTo +"\n";
        query += ", lag(" +validTo + ", 1) over \n";
        query += "(PARTITION BY ";
        for (String key:keys)
            query += "a."+ key;
        query += "\n order by ";
        for (String key:keys)
            query += "a."+ key + ", ";
        query += "a."+validFrom + " ) old_valid_to";
        query += " FROM " + table + " a ";
        query += ", \n(select count(*), ";
        query += String.join(", ", keys) + "\n";
        query += " FROM " + table;
        query += " GROUP BY " + String.join(", ", keys);
        query += "\n having count(*)>1) b \n";
        query += "WHERE ";
        for (int i = 0; i < keys.size(); i++){
            if (i>0)
                query += " AND ";
            String key =keys.get(i);
            query += "a."+key + " = b." + key;
        }
        query += " \n order by ";
        for (String key:keys)
            query += "a."+ key + ", ";
        query += "a."+validFrom + " \n";
        query += ") x \n";
        //  old_valid_to<>valid_from- interval '1 sec'

        query += "WHERE " + validFrom + " - old_valid_to > 1 \n";
        query += "ORDER BY ";
        for (String key:keys)
            query += key + ", ";
        query += validFrom;
        
        return query;
    }
}
