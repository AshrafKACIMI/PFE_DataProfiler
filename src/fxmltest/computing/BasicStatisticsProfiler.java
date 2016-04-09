/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.Clock;
import java.util.ArrayList;
import testhierarchie.Graphics.Login;

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
    
       
    public static String profileColumnQuery(TableInfo table, ColumnInfo column){
        String query = "";
        String tableName = table.getName();
        String col = column.getName();
        query += "SELECT " + "'" + col + "'" +",";
        query += "count(*),";
        query += "SUM(CASE WHEN "+ col + " is null or TO_CHAR("+col+")="+ alternativeNull+" then 1 else 0 end) count_null, ";
        query += "TO_CHAR(max(length( " + col + " )) ),";
        query += "TO_CHAR(min(length( " + col + " )) ),";
        query += "count(DISTINCT(" + col + ")) nb_distinct ";
        query += "FROM " + TablesFactory.getUserName() + "." + tableName;
        return query;
    }
    
    public  String profileTableQuery(){
        String query = "";
        String col;
        for (ColumnInfo c: this.table.getColumns()){
            col = c.getName();
            if (query.length() > 0) //Si c'est pas la première colonne
                query+= " UNION ALL ";
            query+= profileColumnQuery(table, c);
            }
        return query;        
    }

    public ArrayList<ColumnProfilingStatsRow> profilingResult(){
        
        ArrayList<ColumnProfilingStatsRow> results = new ArrayList<ColumnProfilingStatsRow>();
        String query = profileTableQuery();
        String connectionURL = TablesFactory.getConnectionURL();
        String userName = TablesFactory.getUserName();
        String password = TablesFactory.getPassword();
        String driverClass = TablesFactory.getDriverClass();

        try{  
            //step1 load the driver class  
            Class.forName(driverClass);  

            //step2 create  the connection object  
            Connection con=DriverManager.getConnection(  
            connectionURL,
            userName,
            password);
            
            //step3 create the statement object  
            Statement stmt=con.createStatement();  

            //step4 execute query  
            ResultSet rs=stmt.executeQuery(query);  
            while(rs.next()){
                System.out.println("rs strikes again !");
                String columnName = rs.getString(1);
                int nbLines = rs.getInt(2);
                int nbNull = rs.getInt(3);
                int nbDistinct = rs.getInt(4);
                String min = rs.getString(5);
                String max = rs.getString(6);
                System.out.println("rs get DONE!");
                System.out.println(columnName);
                System.out.println(nbLines);
                System.out.println(nbDistinct);
                System.out.println(min);
                System.out.println(max);
                
                ColumnProfilingStatsRow stat = new ColumnProfilingStatsRow(columnName, nbNull, nbDistinct, nbLines, min, max);
                ColumnInfo col = table.getColumnByName(columnName);
                System.out.println("col = " + col);
                col.setStats(stat);
                System.out.println("col.stat: "+ stat);
                /*
                Add logic to save the profiling results !
                Add results to ColumnResults
                
                
                */
                
                
                results.add(stat);
                System.out.println("results : " + results);

            }
            //step5 close the connection object  
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
