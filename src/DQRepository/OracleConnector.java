/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DQRepository;

import Entities.ColumnInfo;
import Entities.TableInfo;

/**
 *
 * @author Ashraf
 */
public class OracleConnector implements IConnector {

    private String alternativeNull = "'-99'";
    private String driverClass = "oracle.jdbc.driver.OracleDriver";
    private String connectionURL;//jdbc:oracle:thin:@localhost:1522:orcl
    private String userName;//SYSTEM
    private String password;//?? OracleAdmin1

    /**
     * @return the alternativeNull
     */
    public String getAlternativeNull() {
        return alternativeNull;
    }

    /**
     * @return the driverClass
     */
    public String getDriverClass() {
        return driverClass;
    }

    /**
     * @return the connectionURL
     */
    public String getConnectionURL() {
        return connectionURL;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    public OracleConnector(String user, String pass, String connURL) {
        driverClass = "oracle.jdbc.driver.OracleDriver";
        connectionURL = connURL;
        userName = user;
        password = pass;
    }
    
    
    @Override
    public String referentialIntegrityViolCountQuery(String parentTable, String parentColumn, String childTable, String childColumn) {
        return "SELECT count(*) FROM ("
                + "SELECT DISTINCT (" + parentColumn + ") FROM " + parentTable
                + " MINUS "
                + "SELECT DISTINCT (" + childColumn + ") FROM " + childTable
                + ") A ";
    }

    @Override
    public String referentialIntegritySampleQuery(String parentTable, String parentColumn, String childTable, String childColumn, int nbRows) {
        return "SELECT * FROM ("
                + "SELECT DISTINCT (" + parentColumn + ") FROM " + parentTable
                + " MINUS "
                + "SELECT DISTINCT (" + childColumn + ") FROM " + childTable
                + ") A "
                + "LIMIT " + nbRows;
    }

    public String profileColumnQuery(TableInfo table, ColumnInfo column) {
        String query = "";
        String tableName = table.getName();
        String col = column.getName();
        query += "SELECT " + "'" + col + "'" + ",";
        query += "count(*),";
        query += "SUM(CASE WHEN " + col + " is null or TO_CHAR(" + col + ")=" + getAlternativeNull() + " then 1 else 0 end) count_null, ";
        if (!column.getType().equals("VARCHAR2")){
            query += "TO_CHAR(max( " + col + " ) ),";
            query += "TO_CHAR(min( " + col + " ) ),";
        }
            
        else{
            query += "TO_CHAR(max(length( " + col + " )) ),";
            query += "TO_CHAR(min(length( " + col + " )) ),";
        }
        query += "count(DISTINCT(" + col + ")) nb_distinct ";
        query += "FROM " + userName + "." + tableName;
        return query;
    }

    @Override
    public String profileTableQuery(TableInfo table) {
        String query = "";
        String col;
        for (ColumnInfo c : table.getColumns()) {
            col = c.getName();
            if (query.length() > 0) //Si c'est pas la première colonne
            {
                query += " UNION ALL ";
            }
            query += profileColumnQuery(table, c);
        }
        return query;
    }

    @Override
    public String connectionQuery() {
        return "SELECT table_name, column_name, data_type "
                + "FROM user_tab_columns "
                + "ORDER BY table_name";

    }

    @Override
    public String getDbName() {
        return userName;
    }
    
    
    
    
    
    
    

}
