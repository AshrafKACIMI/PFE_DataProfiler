/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Computing;

import DQRepository.IConnector;
import Entities.ColumnProfilingStatsRow;
import Entities.ColumnInfo;
import Entities.TableInfo;
import DQRepository.MetaDataConnector;
import fxmltest.FXMLDocumentController;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * @author Ashraf
 */
public class BasicStatisticsProfiler {

    public static String alternativeNull = "'-99'";
    private TableInfo table;
    


    public BasicStatisticsProfiler(TableInfo tableInfo) {
        this.table = tableInfo;
    }
    
       
     

    public ArrayList<ColumnProfilingStatsRow> profilingResult(){
        IConnector connector = FXMLDocumentController.getConnector();
        ArrayList<ColumnProfilingStatsRow> results = new ArrayList<ColumnProfilingStatsRow>();
        String query = connector.profileTableQuery(table);
        String connectionURL = connector.getConnectionURL();
        String userName = connector.getUserName();
        String password = connector.getPassword();
        String driverClass = connector.getDriverClass();
        
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss")
                .format(Calendar.getInstance().getTime());

        try{  
            //step1 load the driver class  
            Class.forName(driverClass);  

            //step2 create  the connection object  
            Connection con=DriverManager.getConnection(  
            connectionURL,
            userName,
            password);
            // make connection for Sqlite writing
            Connection Sqliteconn = MetaDataConnector.getSqliteConnection();
            
            //step3 create the statement object  
            Statement stmt=con.createStatement();  

            //step4 execute query  
            ResultSet rs=stmt.executeQuery(query);  
            while(rs.next()){
                System.out.println("rs strikes again !");
                String columnName = rs.getString(1);
                int nbLines = rs.getInt(2);
                int nbNull = rs.getInt(3);
                int nbDistinct = rs.getInt(6);
                String min = rs.getString(4);
                String max = rs.getString(5);
                MetaDataConnector.insertMesure(Sqliteconn, connector.getDbName(),
                        table.getName(), columnName, timeStamp, min, max,
                        nbNull, nbLines, nbDistinct);
                                
                ColumnProfilingStatsRow stat = new ColumnProfilingStatsRow(columnName, nbNull, nbDistinct, nbLines, min, max);
                ColumnInfo col = table.getColumnByName(columnName);
                col.setStats(stat);
                /*
                Add logic to save the profiling results !
                Add results to ColumnResults
                                
                */
                results.add(stat);
                System.out.println("results : " + results);

            }
            //step5 close the connection object  
            Sqliteconn.close();
            con.close();  

        }catch(Exception e){ System.out.println(e);}  
        
        System.out.println(results);
        return results;
        
    }

    /* 'SELECT CAST('||quote_literal(n)||' AS INTEGER) ordinal_position,
    sum(case when '||col||' is null or CAST('||col||' AS char)='||quote_literal(alternative_null)|| ' then 1 else 0 end) count_null,
    sum(case when '||col||' is null or CAST('||col||' AS char)='||quote_literal(alternative_null)|| ' then 0 else 1 end) count_notnull,
    (case when sum(case when '||col||' is null or CAST('||col||' AS char)='||quote_literal(alternative_null)|| ' then 1 else 0 end) = 0 then -99 else  sum(case when '||col||' is null or CAST('||col||' AS char)='||quote_literal(alternative_null)|| ' then 0 else 1 end)*100.0/sum(case when '||col||' is null or CAST('||col||' AS char)='||quote_literal(alternative_null)|| ' then 1 else 0 end) end) count_p,
    cast(max(length('||col||')) as text),
    cast(min(length('||col||')) as text),
    count(DISTINCT('||col||')) nb_distinct
    FROM  ' || schem || '.' ||tab;
     */    
    @Override
    public String toString() {
        return "BasicStatisticsProfiler{" + "table=" + table + '}';
    }
    
    
}
