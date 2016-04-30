package fxmltest.computing;

import DQRepository.MetaDataConnector;
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
    
    
    public TablesFactory(IConnector connector) {
        String lastTable = "";
        this.tables = new ArrayList<TableInfo>();
        TableInfo ti = null;
        
        Connection sqliteCon = MetaDataConnector.getSqliteConnection();
        
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
                    ti = new TableInfo(connector.getDbName(), tableName);
//                    System.out.println("tabInfo: "+ti);

                    this.tables.add(ti);
                }
                ti.getColumns().add(new ColumnInfo(colName, colType));
                MetaDataConnector.updateColumn(sqliteCon, colName, colType, tableName, connector.getDbName());
                lastTable = tableName;  
                //System.out.println("ti :"+ti);

            }
            //step5 close the connection object  
            sqliteCon.close();
            con.close();  

        }catch(Exception e){ System.out.println(e);}  
//        
//        for (TableInfo t: this.tables){
//            System.out.println(t);
//        }
          
    }
    
    public void updateMDRepository(){
        
    }
    

   
    

    @Override
    public String toString() {
        return "TablesFactory{" + "tables=" + getTables() + '}';
    }
    
    
    
    
    
}
