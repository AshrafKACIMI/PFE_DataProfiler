/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

/**
 *
 * @author Ashraf
 */
public interface IConnector {
    
    String connectionQuery();

    String profileTableQuery(TableInfo table);

    String referentialIntegritySampleQuery(String parentTable, String parentColumn, String childTable, String childColumn, int nbRows);

    String referentialIntegrityViolCountQuery(String parentTable, String parentColumn, String childTable, String childColumn);
    
    String getAlternativeNull();
    
    String getDriverClass();
    
    String getConnectionURL();
    
    String getUserName();
    
    String getPassword();
    
    String getDbName();
}
