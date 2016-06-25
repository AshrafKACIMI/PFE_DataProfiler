/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package DQRepository;

import Entities.DqRule;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
public class MetaDataConnector {

    static String jdbc = "org.sqlite.JDBC"; // org.h2.Driver
    static String path = System.getProperty("user.home")+"\\Profiling Results\\DQMD Repository";
    static String connectionURL = "jdbc:sqlite:" + path + "\\sqlite.db";
    
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
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", "" 
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
                con = DriverManager.getConnection(connectionURL);
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
            con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", "" 
            Statement stmt = con.createStatement();
            String idQuery = getIdQuery(dbName, tableName, columnName); 
//            ResultSet rs=stmt.executeQuery(idQuery);
//            boolean bool = rs.next();
            String query;
            int id = getColumnId(stmt, dbName, tableName, columnName);
            
            if (id == -1){ // la colonne n'existe pas dans la table
                query = getColumnInsertQuery(dbName, tableName, columnName, columnType);
            } else {// la colonne existe
                query = updateColumnQuery(columnType, id);
            }
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
//            ResultSet rs=stmt.executeQuery(idQuery);
//            boolean bool = rs.next();
            String query;
            int id = getColumnId(stmt, dbName, tableName, columnName);
            
            if (id == -1){ // la colonne n'existe pas dans la table
                query = getColumnInsertQuery(dbName, tableName, columnName, columnType);
            } else {// la colonne existe
                query = updateColumnQuery(columnType, id);
            }
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
    
    public static int getCount(String dbName, String tableName, String columnName){
        int count = 0;
        String query = "SELECT a.count\n" +
        "	FROM mesures a\n" +
        "\n" +
        "	JOIN \n" +
        "	(\n" +
        "	    SELECT idColumn, MAX(timestamp) timestamp\n" +
        "	    FROM mesures\n" +
        "	    GROUP BY idColumn\n" +
        "	) b \n" +
        "	ON a.idColumn = b.idColumn AND a.timestamp = b.timestamp \n" +
        "where a.idColumn = ?";
        
        
        try {
            Class.forName(jdbc);
            Connection con;
            try {
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", ""
                Statement idStmt = con.createStatement();

                int id = getColumnId(idStmt, dbName, tableName, columnName);
                idStmt.close();
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();
                if (rs.next())
                    count = rs.getInt("count");
                stmt.close();
                con.close();
                
                
            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return count;

        
    }
    
    
    
    
    public static void insertRef(Connection con, String dbName, String parentTable, 
            String childTable, String parentColumn, String childColumn,
            String timeStamp, int nbBroken, int nbTotal) {
        try {
            Statement stmt = con.createStatement();
            int idParent = getColumnId(stmt, dbName, parentTable, parentColumn);
            int idChild = getColumnId(stmt, dbName, childTable, childColumn);
            
            String query = 
                    "insert into brokenRefs values("
                    + idParent + ", "
                    + "'" + idChild + "'" + ", "
                    + nbBroken + ", "
                    + nbTotal + ", "
                    +  "'" + timeStamp + "'" + ")";
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
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", ""
                Statement stmt = con.createStatement();
                int id = getColumnId(stmt, dbName, tableName, columnName);
                String query = "INSERT OR REPLACE INTO dqrules VALUES("
                        + id + ", " + "'" + minValue + "'" + ", "
                        + "'" + maxValue + "'" + ", "
                        + distinctInt + ", "
                        + notNullInt + ")";
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
    
    

    public static DqRule getColumnRule(Connection con, String columnName, String tableName, String dbName) {
        
        DqRule rule = null;
        try {
            Statement stmt = con.createStatement();
            int id = getColumnId(stmt, dbName, tableName, columnName);
            if (id == -1)
                return null;
            
            String query = "SELECT min, max, uniqueness, not_null "
                    + "FROM dqrules "
                    + "WHERE idColumn = ?";
            PreparedStatement ruleStmt = con.prepareStatement(query);
            ruleStmt.setInt(1, id);
            ResultSet rs=ruleStmt.executeQuery();
            if (rs.next()){
                String minConstraint = rs.getString("min");
                String maxConstraint = rs.getString("max");
                boolean isUnique = rs.getInt("uniqueness") > 0 ;
                boolean isNotNull = rs.getInt("not_null") > 0;
                rule = new DqRule(minConstraint, maxConstraint, isUnique, isNotNull);
            }
                
            
                    } catch (SQLException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rule;
    }
    
    public static PreparedStatement lastResultsQuery(Connection con, String dbName, String tableName){
        try {
            String query =
                    "SELECT * FROM \n" +
                    "(\n" +
                    "SELECT a.idColumn, a.timestamp, a.min, a.max, a.nbnull, a.count, a.nb_distinct\n" +
                    "FROM mesures a\n" +
                    "\n" +
                    "INNER JOIN (\n" +
                    "    SELECT idColumn, MAX(timestamp) timestamp\n" +
                    "    FROM mesures\n" +
                    "    GROUP BY idColumn\n" +
                    ") b ON a.idColumn = b.idColumn AND a.timestamp = b.timestamp \n" +
                    ") AS C \n" +
                    "\n" +
                    "INNER JOIN (\n" +
                    "SELECT * from Columns \n" +
                    "WHERE db_name = ? and table_name = ? \n" +
                    ") D\n" +
                    "ON D.id = C.idColumn;";
            PreparedStatement stmt =  con.prepareStatement(query);
            stmt.setString(1, dbName);
            stmt.setString(2, tableName);
            return stmt;
        } catch (SQLException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static PreparedStatement lastResultsQuery(String dbName, String tableName){
        try {
            Class.forName(jdbc);
            Connection con;
            try {
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", ""
                String query =
                    "SELECT * FROM \n" +
                    "(\n" +
                    "SELECT a.idColumn, a.timestamp, a.min, a.max, a.nbnull, a.count, a.nb_distinct\n" +
                    "FROM mesures a\n" +
                    "\n" +
                    "INNER JOIN (\n" +
                    "    SELECT idColumn, MAX(timestamp) timestamp\n" +
                    "    FROM mesures\n" +
                    "    GROUP BY idColumn\n" +
                    ") b ON a.idColumn = b.idColumn AND a.timestamp = b.timestamp \n" +
                    ") AS C \n" +
                    "\n" +
                    "INNER JOIN (\n" +
                    "SELECT * from Columns \n" +
                    "WHERE db_name = ? and table_name = ? \n" +
                    ") D\n" +
                    "ON D.id = C.idColumn;";
                    PreparedStatement stmt =  con.prepareStatement(query);
                    stmt.setString(1, dbName);
                    stmt.setString(2, tableName);
                    return stmt;
                
                
            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }


    public String getConnectionURL(){
        return connectionURL;
    }
    
    
    public static int getTotalRows(String dbName){
        int count = 1;
        String query =
           "select sum(count) total\n" +
            "from \n" +
            "(\n" +
            "SELECT distinct table_name, count FROM\n" +
            "	(\n" +
            "	SELECT a.idColumn, a.timestamp, a.count\n" +
            "	FROM mesures a\n" +
            "\n" +
            "	INNER JOIN \n" +
            "	(\n" +
            "	    SELECT idColumn, MAX(timestamp) timestamp\n" +
            "	    FROM mesures\n" +
            "	    GROUP BY idColumn\n" +
            "	) b \n" +
            "	ON a.idColumn = b.idColumn AND a.timestamp = b.timestamp \n" +
            "	) AS C\n" +
            "INNER JOIN columns D\n" +
            "ON C.idColumn = D.id"
                + " WHERE db_name = ?)" ;
        System.out.println(query);
            try {
                Class.forName(jdbc);
                Connection con;
            try {
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", "" 
                //Statement stmt = con.createStatement();
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, dbName);
                
                ResultSet rs=stmt.executeQuery();
                if (rs.next()){
                    System.out.println("lol");
                    count = rs.getInt("total");
                }

                System.out.println("count NOW: " + count);
                
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
                

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            return count;     
    }
    
    
    public static ArrayList<Integer> getCompleteness(String dbName){
        String query =
        "select Z.idColumn, Z.count, Z.nbnull, sum(Z.count) sum_count, sum(Z.nbnull) sum_null\n" +
        " from\n" +
        "(\n" +
        "(\n" +
        "	select idColumn, db_name, table_name from \n" +
        "	dqrules d inner join columns c\n" +
        "	on d.idColumn = c.id\n" +
        "	where not_null = 1\n" +
        "	and db_name = ? \n" +
        ") X JOIN\n" +
        "(\n" +
        "	\n" +
        "	SELECT a.idColumn, a.timestamp, a.count, a.nbnull\n" +
        "	FROM mesures a\n" +
        "\n" +
        "	JOIN \n" +
        "	(\n" +
        "	    SELECT idColumn, MAX(timestamp) timestamp\n" +
        "	    FROM mesures\n" +
        "	    GROUP BY idColumn\n" +
        "	) b \n" +
        "	ON a.idColumn = b.idColumn AND a.timestamp = b.timestamp \n" +
        "	\n" +
        "\n" +
        ") Y\n" +
        "\n" +
        "ON X.idColumn = Y.idColumn\n" +
        ") Z";
        
        ArrayList<Integer> results = new ArrayList<Integer>();
                
                
        System.out.println(query);
            try {
                Class.forName(jdbc);
                Connection con;
            try {
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", "" 
                //Statement stmt = con.createStatement();
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, dbName);
                
                ResultSet rs=stmt.executeQuery();
                if (rs.next()){
                    System.out.println("lol");
                    int nbnull = rs.getInt("sum_null");
                    int nb_notnull = rs.getInt("sum_count") - nbnull;
                    results.add(Integer.valueOf(nb_notnull));
                    results.add(Integer.valueOf(nbnull));
                    
                }

                System.out.println("count NOW: " + results);
                
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
                

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            return results;     
    }
    
    
    public static ArrayList<Integer> getUniqueness(String dbName){
        String query =
        "select Z.idColumn, Z.count, Z.nb_distinct, sum(Z.count) sum_count, sum(Z.nb_distinct) sum_distinct\n" +
        " from\n" +
        "(\n" +
        "(\n" +
        "	select idColumn, db_name, table_name from \n" +
        "	dqrules d inner join columns c\n" +
        "	on d.idColumn = c.id\n" +
        "	where uniqueness = 1\n" +
        "	and db_name = ? \n" +
        ") X JOIN\n" +
        "(\n" +
        "	\n" +
        "	SELECT a.idColumn, a.timestamp, a.count, a.nb_distinct\n" +
        "	FROM mesures a\n" +
        "\n" +
        "	JOIN \n" +
        "	(\n" +
        "	    SELECT idColumn, MAX(timestamp) timestamp\n" +
        "	    FROM mesures\n" +
        "	    GROUP BY idColumn\n" +
        "	) b \n" +
        "	ON a.idColumn = b.idColumn AND a.timestamp = b.timestamp \n" +
        "	\n" +
        "\n" +
        ") Y\n" +
        "\n" +
        "ON X.idColumn = Y.idColumn\n" +
        ") Z";        
                
                
        ArrayList<Integer> results = new ArrayList<Integer>();
                
                
        System.out.println(query);
            try {
                Class.forName(jdbc);
                Connection con;
            try {
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", "" 
                //Statement stmt = con.createStatement();
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, dbName);
                
                ResultSet rs=stmt.executeQuery();
                if (rs.next()){
                    System.out.println("lol");
                    int nb_distinct = rs.getInt("sum_distinct");
                    int nb_notdistinct = rs.getInt("sum_count") - nb_distinct;
                    results.add(Integer.valueOf(nb_distinct));
                    results.add(Integer.valueOf(nb_notdistinct));
                    
                }

                System.out.println("count NOW: " + results);
                
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
                

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        
            return results;     
    }
    
    public static ArrayList<Integer> getRefs(){
        String query = 
                "select sum(nbBroken) broken , sum(nbTotal) total\n" +
                "FROM\n" +
                "(\n" +
                "\n" +
                "SELECT a.idParent, a.idChild, a.nbBroken, a.nbTotal\n" +
                "	FROM brokenRefs a\n" +
                "\n" +
                "	JOIN \n" +
                "	(\n" +
                "	    SELECT idParent, idChild, MAX(timestamp) timestamp\n" +
                "	    FROM brokenRefs\n" +
                "	    GROUP BY idParent, idChild\n" +
                "	) b \n" +
                "ON a.idParent = b.idParent AND a.idChild = b.idChild AND a.timestamp = b.timestamp \n" +
                ")";
        ArrayList<Integer> results = new ArrayList<Integer>();
            try {
                Class.forName(jdbc);
                Connection con;
            try {
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", "" 
                //Statement stmt = con.createStatement();
                PreparedStatement stmt = con.prepareStatement(query);
                ResultSet rs=stmt.executeQuery();
                if (rs.next()){
                    System.out.println("lol");
                    int nb_broken = rs.getInt("broken");
                    int nb_total = rs.getInt("total");
                    results.add(Integer.valueOf(nb_broken));
                    results.add(Integer.valueOf(nb_total));
                }

                System.out.println("count NOW: " + results);
                
                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
                

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        
        return results;
    }
    
    
    public static int getDuplicates(String dbName){
        int count = 0;
        String query = 
                "select sum(nbDuplicates)\n" +
                "FROM\n" +
                "(\n" +
                "\n" +
                "SELECT a.dbName, a.tableName, a.columns, nbduplicates\n" +
                "	FROM duplicates a\n" +
                "\n" +
                "	JOIN \n" +
                "	(\n" +
                "	    SELECT dbName, tableName, columns, MAX(timestamp) timestamp\n" +
                "	    FROM duplicates\n" +
                "	    GROUP BY dbName, tableName, columns\n" +
                "	) b \n" +
                "ON a.dbName = b.dbName AND a.tableName = b.tableName AND a.columns = b.columns AND a.timestamp = b.timestamp "
                + "WHERE a.dbName = ?"
                + ")";
                
            try {
                Class.forName(jdbc);
                Connection con;
            try {
                con = DriverManager.getConnection(connectionURL); //"jdbc:h2:~/test2", "test2", "" 
                //Statement stmt = con.createStatement();
                PreparedStatement stmt = con.prepareStatement(query);
                stmt.setString(1, dbName);
                ResultSet rs=stmt.executeQuery();
                if (rs.next()){
                    count = rs.getInt(1);
                }

                stmt.close();
                con.close();

            } catch (SQLException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
                

            } catch (ClassNotFoundException ex) {
                Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        
        
        return count;
        
        
    }
    
    public static void insertDuplicate(Connection con, String dbName, String table, ArrayList<String> keys, int nbDuplicates, String timeStamp) {
        try {
            Statement stmt = con.createStatement();
            System.out.println(dbName);
            System.out.println(table);
            System.out.println(keys);
            System.out.println(nbDuplicates);
            System.out.println(timeStamp);
            
            String query = 
                    "insert into Duplicates values("
                    + "'" + dbName + "'" + ", "
                    + "'" + table + "'" + ", "
                    + "'" + String.join(",", keys) + "'" + ", "
                    + nbDuplicates + ", "
                    +  "'" + timeStamp + "'" + ")";
            stmt.executeUpdate(query);
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(MetaDataConnector.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
}
