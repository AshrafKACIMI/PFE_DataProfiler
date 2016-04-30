/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DQRepository;

import Mail.EmailOptions;
import fxmltest.computing.ColumnInfo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ashraf
 */
public class MetaDataConnector {

    static String jdbc = "org.sqlite.JDBC"; // org.h2.Driver
    static String path = System.getProperty("user.home")+"\\Profiling Results\\DQMD Repository";
    static String connection = "jdbc:sqlite:" + path + "\\sqlite.db";
    
    static String columnQuery = 
            "CREATE TABLE IF NOT EXISTS `Columns` (\n" +
            "	`id`	INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "	`db`	TEXT NOT NULL,\n" +
            "	`table`	TEXT NOT NULL,\n" +
            "	`name`	TEXT NOT NULL,\n" +
            "	`type`	TEXT NOT NULL\n" +
            ")";
    
    static String recommandationQuery = 
            "CREATE TABLE IF NOT EXISTS `recommandation` (\n" +
            "	`id`	INTEGER NOT NULL,\n" +
            "	`text`	TEXT NOT NULL,\n" +
            "	PRIMARY KEY(id)\n" +
            ")";
    
    static String mesuresQuery = 
            "CREATE TABLE IF NOT EXISTS `mesures` (\n" +
            "	`idColumn`	INTEGER NOT NULL,\n" +
            "	`timestamp`	TEXT NOT NULL,\n" +
            "	`min`	TEXT,\n" +
            "	`max`	TEXT,\n" +
            "	`nbnull`	INTEGER,\n" +
            "	`count`	INTEGER\n" +
            ")";
    
    static String dqRulesQuery =
            "CREATE TABLE IF NOT EXISTS `dqrules` (\n" +
            "	`idColumn`	INTEGER NOT NULL,\n" +
            "	`timestamp`	INTEGER NOT NULL,\n" +
            "	`min`	TEXT,\n" +
            "	`max`	TEXT,\n" +
            "	`uniqueness`	INTEGER,\n" +
            "	`notnull`	INTEGER,\n" +
            "	PRIMARY KEY(idColumn,timestamp)\n" +
            ")";
        
    public static void updateDb(){
        try {
            Class.forName(jdbc);
            Connection con;
            try {
                con = DriverManager.getConnection(connection); //"jdbc:h2:~/test2", "test2", "" 
                Statement stmt = con.createStatement();
                stmt.executeUpdate(columnQuery);
                stmt.executeUpdate(recommandationQuery);
                stmt.executeUpdate(mesuresQuery);
                stmt.executeUpdate(dqRulesQuery);
                
                stmt.close();
                con.close();
            
            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    public static Connection getSqliteConnection(){
        Connection con = null;

        try {
            Class.forName(jdbc);
            try {
                con = DriverManager.getConnection(connection);
            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return con;
    }
    
    
    public static void updateColumn(String columnName, String columnType, String tableName, String dbName) throws SQLException{
        try {
            Class.forName(jdbc);
            Connection con;
            con = DriverManager.getConnection(connection); //"jdbc:h2:~/test2", "test2", "" 
            Statement stmt = con.createStatement();
            String idQuery = getIdQuery(dbName, tableName, columnName); 
            System.out.println(idQuery);
//            ResultSet rs=stmt.executeQuery(idQuery);
//            boolean bool = rs.next();
            String query;
            int id = getColumnId(stmt, dbName, tableName, columnName);
            
            if (id == -1){ // la colonne n'existe pas dans la table
                query = getColumnInsertQuery(dbName, tableName, columnName, columnType);
            } else {// la colonne existe
                query = updateColumnQuery(columnType, id);
            }
            System.out.println(query);
            stmt.executeUpdate(query);
            stmt.close();
            con.close();

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateColumn(Connection con, String columnName, String columnType, String tableName, String dbName) throws SQLException{
            
            Statement stmt = con.createStatement();
            String idQuery = getIdQuery(dbName, tableName, columnName); 
            System.out.println(idQuery);
//            ResultSet rs=stmt.executeQuery(idQuery);
//            boolean bool = rs.next();
            String query;
            int id = getColumnId(stmt, dbName, tableName, columnName);
            
            if (id == -1){ // la colonne n'existe pas dans la table
                query = getColumnInsertQuery(dbName, tableName, columnName, columnType);
            } else {// la colonne existe
                query = updateColumnQuery(columnType, id);
            }
            System.out.println(query);
            stmt.executeUpdate(query);
            stmt.close();
    }

    private static String getColumnInsertQuery(String dbName, String tableName, String columnName, String columnType) {
        String query;
        query = "insert into columns(db_name, table_name, column_name, column_type) values ("
                + "'" + dbName + "'" + ", "
                + "'" + tableName + "'" + "," +
                "'" + columnName + "'" + ","+
                "'" + columnType + "')";
        return query;
    }

    private static String updateColumnQuery(String columnType, int id) {
        String updateColQuery =
                "UPDATE Columns "
                + "SET column_type = " + "'" + columnType + "'" +
                " WHERE id = " + id;
        return updateColQuery;
    }
    
    private static String getIdQuery(String dbName, String tableName, String columnName){
        return "SELECT id " + 
                    "FROM Columns "
                    + "WHERE db_name = " + "'" + dbName + "'" +
                    " AND table_name = " + "'" +tableName + "'" + 
                    " AND column_name = " + "'" +columnName+ "'" ;
    }
    
    private static int getColumnId(Statement stmt, String dbName, String tableName, String columnName){
        int id = -1;
        String idQuery = getIdQuery(dbName, tableName, columnName);
        System.out.println(idQuery);
        try {
            ResultSet rs=stmt.executeQuery(idQuery);
            if (rs.next())
                id = rs.getInt("id");
            
        } catch (SQLException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return id;
    }

    public static void insertMesure(Connection con, String dbName, String tableName, String columnName, String timeStamp, String min, String max, int nbNull, int nbLines, int nbDistinct) {
        try {
            Statement stmt = con.createStatement();
            int id = getColumnId(stmt, dbName, tableName, columnName);
            String query = 
                    "INSERT INTO mesures VALUES("
                    + id + ", "
                    + "'" + timeStamp + "'" + ", "
                    + "'" + min + "'" + ", "
                    + "'" + max + "'" + ", "
                    +  nbNull+ ", "
                    + nbLines + ", "
                    + nbDistinct + ")";
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
   

    public static void insertRule(String dbName, String tableName, String columnName, String minValue, String maxValue, int distinctInt, int notNullInt) {
        try {
            Class.forName(jdbc);
            Connection con;
            try {
                con = DriverManager.getConnection(connection); //"jdbc:h2:~/test2", "test2", ""
                Statement stmt = con.createStatement();
                int id = getColumnId(stmt, dbName, tableName, columnName);
                String query = "INSERT OR REPLACE INTO dqrules VALUES("
                        + id + ", " + "'" + minValue + "'" + ", "
                        + "'" + maxValue + "'" + ", "
                        + distinctInt + ", "
                        + notNullInt + ")";
                System.out.println(query);
                stmt.executeUpdate(query);
                stmt.close();
                con.close();
                
                
            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
