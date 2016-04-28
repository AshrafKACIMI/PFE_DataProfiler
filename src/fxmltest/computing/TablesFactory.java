package fxmltest.computing;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
/**
 *
 * @author Ashraf
 */
public class TablesFactory{ 
    
    private static ArrayList<TableInfo> tables;


//    //private HashMap<String, TableInfo> tables;
//    private static String driverClass = "oracle.jdbc.driver.OracleDriver";
//    private static String connectionURL;//jdbc:oracle:thin:@localhost:1522:orcl
//    private static String userName;//SYSTEM
//    private static String password;//?? OracleAdmin1
    private static String query = "SELECT table_name, column_name, data_type " 
                                 + "FROM user_tab_columns " 
                                 + "ORDER BY table_name";

    /**
     * @return the tables
     */
    public static ArrayList<TableInfo> getTables() {
        return tables;
    }

    /**
     * @param aTables the tables to set
     */
    public static void setTables(ArrayList<TableInfo> aTables) {
        tables = aTables;
    }
    
//    
//    public TablesFactory() {
//        String lastTable = "";
//        
//        System.out.println("values and stuff");
//        System.out.println(connectionURL);
//        System.out.println(userName);
//        System.out.println(password);
//        
//        this.tables = new ArrayList<TableInfo>();
//        TableInfo ti = null;
//        try{  
//            Class.forName(driverClass);  
//            Connection con=DriverManager.getConnection(  
//            this.connectionURL,
//            this.userName,
//            this.password);  
//            
//            //step3 create the statement object  
//            Statement stmt=con.createStatement();  
//
//            //step4 execute query  
//            ResultSet rs=stmt.executeQuery(query);  
//            while(rs.next()){
//                
////                System.out.println("rs sadi9i: "+rs.getString(1)+rs.getString(2)+rs.getString(3));
//                String tableName = rs.getString(1);
//                String colName = rs.getString(2);
//                String colType = rs.getString(3);
//
//                if (!tableName.equals(lastTable)){ //cas d'une nouvelle table
//                    // on doit créer une nouvelle TableInfo
////                    System.out.println("tab name: "+tableName);
//                    ti = new TableInfo(tableName);
////                    System.out.println("tabInfo: "+ti);
//
//                    this.tables.add(ti);
//                }
//                ti.getColumns().add(new ColumnInfo(colName, colType));
//                lastTable = tableName;  
//                //System.out.println("ti :"+ti);
//
//            }
//            //step5 close the connection object  
//            con.close();  
//
//        }catch(Exception e){ System.out.println(e);}  
//        
////        for (TableInfo t: this.tables){
////            System.out.println(t);
////        }
//          
//    }
//    
//    public TablesFactory(String user, String pass, String connURL) {
//        String lastTable = "";
//        this.tables = new ArrayList<TableInfo>();
//        TableInfo ti = null;
//        this.connectionURL = connURL;
//        this.userName = user;
//        this.password = pass;
////        System.out.println("pre-try!");
//        try{  
//            //step1 load the driver class  
////            System.out.println("pre-class!");
//            Class.forName(driverClass);  
////            System.out.println("pre-con!");
//            //step2 create  the connection object  
//            Connection con=DriverManager.getConnection(  
//            connectionURL,
//            userName,
//            password);  
////            System.out.println(query);
////            System.out.println("well shit!");
////            System.out.println("con "+con);
////            System.out.println("well shit!");
////            
//            
//            //step3 create the statement object  
//            Statement stmt=con.createStatement();  
//
//            //step4 execute query  
//            ResultSet rs=stmt.executeQuery(query);  
//            while(rs.next()){
//                
////                System.out.println("rs sadi9i: "+rs.getString(1)+rs.getString(2)+rs.getString(3));
//                String tableName = rs.getString(1);
//                String colName = rs.getString(2);
//                String colType = rs.getString(3);
//
//                if (!tableName.equals(lastTable)){ //cas d'une nouvelle table
//                    // on doit créer une nouvelle TableInfo
////                    System.out.println("tab name: "+tableName);
//                    ti = new TableInfo(tableName);
////                    System.out.println("tabInfo: "+ti);
//
//                    this.tables.add(ti);
//                }
//                ti.getColumns().add(new ColumnInfo(colName, colType));
//                lastTable = tableName;  
//                //System.out.println("ti :"+ti);
//
//            }
//            //step5 close the connection object  
//            con.close();  
//
//        }catch(Exception e){ System.out.println(e);}  
////        
////        for (TableInfo t: this.tables){
////            System.out.println(t);
////        }
//          
//    }
    
    public TablesFactory(IConnector connector) {
        String lastTable = "";
        this.tables = new ArrayList<TableInfo>();
        TableInfo ti = null;
        
        try{  

            Class.forName(connector.getDriverClass());  
            Connection con=DriverManager.getConnection(  
            connector.getConnectionURL(),
            connector.getUserName(),
            connector.getPassword());  

            //step3 create the statement object  
            Statement stmt=con.createStatement();  

            //step4 execute query  
            ResultSet rs=stmt.executeQuery(query);  
            while(rs.next()){
                String tableName = rs.getString(1);
                String colName = rs.getString(2);
                String colType = rs.getString(3);

                if (!tableName.equals(lastTable)){ //cas d'une nouvelle table
                    // on doit créer une nouvelle TableInfo
//                    System.out.println("tab name: "+tableName);
                    ti = new TableInfo(tableName);
//                    System.out.println("tabInfo: "+ti);

                    this.tables.add(ti);
                }
                ti.getColumns().add(new ColumnInfo(colName, colType));
                lastTable = tableName;  
                //System.out.println("ti :"+ti);

            }
            //step5 close the connection object  
            con.close();  

        }catch(Exception e){ System.out.println(e);}  
//        
//        for (TableInfo t: this.tables){
//            System.out.println(t);
//        }
          
    }
    
    public static void setStats(String tableName, ArrayList<ColumnProfilingStats> stats){
        // sets the profiling stats to the columns of the table

//        for (TableInfo tab:tables){
        
//            tab.setStats();
//        }
    }
//
//        public TablesFactory() {
//
//        System.out.println("pre-try!");
//        try{  
//            //step1 load the driver class  
//            System.out.println("pre-class!");
//            Class.forName(driverClass);  
//            System.out.println("pre-con!");
//            //step2 create  the connection object  
//            Connection con=DriverManager.getConnection(  
//            connectionURL,
//            userName,
//            password);  
//            System.out.println(query);
//            System.out.println("well shit!");
//            System.out.println("con "+con);
//            System.out.println("well shit!");
//                        
//            //step3 create the statement object  
//            Statement stmt=con.createStatement();  
//
//            //step4 execute query  
//            ResultSet rs=stmt.executeQuery(query);  
//            
//            while(rs.next()){
//                System.out.println("rs sadi9i: "+rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3));
//                String tableName = rs.getString(1);
//                String colName = rs.getString(2);
//                String colType = rs.getString(3);
//
//            }
//            //step5 close the connection object  
//            con.close();  
//
//        }catch(Exception e){ System.out.println(e);}  
//          
//    }

   
    

    @Override
    public String toString() {
        return "TablesFactory{" + "tables=" + getTables() + '}';
    }
    
//    /**
//     * @return the driverClass
//     */
//    public static String getDriverClass() {
//        return driverClass;
//    }
//
//    /**
//     * @param aDriverClass the driverClass to set
//     */
//    public static void setDriverClass(String aDriverClass) {
//        driverClass = aDriverClass;
//    }
//
//    /**
//     * @return the connectionURL
//     */
//    public static String getConnectionURL() {
//        return connectionURL;
//    }
//
//    /**
//     * @param aConnectionURL the connectionURL to set
//     */
//    public static void setConnectionURL(String aConnectionURL) {
//        connectionURL = aConnectionURL;
//    }
//
//    /**
//     * @return the userName
//     */
//    public static String getUserName() {
//        return userName;
//    }
//
//    /**
//     * @param aUserName the userName to set
//     */
//    public static void setUserName(String aUserName) {
//        userName = aUserName;
//    }
//
//    /**
//     * @return the password
//     */
//    public static String getPassword() {
//        return password;
//    }
//
//    /**
//     * @param aPassword the password to set
//     */
//    public static void setPassword(String aPassword) {
//        password = aPassword;
//    }
//
//    /**
//     * @return the query
//     */
//    public static String getQuery() {
//        return query;
//    }
//
//    /**
//     * @param aQuery the query to set
//     */
//    public static void setQuery(String aQuery) {
//        query = aQuery;
//    }
    
    
    
    
    
}
