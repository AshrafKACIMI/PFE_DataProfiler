package fxmltest.computing;

import java.util.ArrayList;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Ashraf
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Table")
public class TableInfo {
    private String schema;
    private String name;
    private ArrayList<ColumnInfo> columns;

    public TableInfo(String schema, String name, ArrayList<ColumnInfo> columns) {
        this.schema = schema;
        this.name = name;
        this.columns = columns;
    }
    
    public TableInfo(String name, ArrayList<ColumnInfo> columns) {
        this.name = name;
        this.columns = columns;
    }
    
    public TableInfo(String name) {
        this.name = name;
        this.columns = new ArrayList<ColumnInfo>();
    }

    @Override
    public String toString() {
        return "TableInfo{" + "name=" + name + ", columns=" + columns + '}';
    }

    /**
     * @return the schema
     */
    public String getSchema() {
        return schema;
    }

    /**
     * @param schema the schema to set
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the columns
     */
    
    public ArrayList<ColumnInfo> getColumns() {
        return columns;
    }

    /**
     * @param columns the columns to set
     */
    public void setColumns(ArrayList<ColumnInfo> columns) {
        this.columns = columns;
    }
    
    public ColumnInfo getColumnByName(String colName){
        for (int i = 0; i < this.columns.size(); i++){
            ColumnInfo col = this.columns.get(i);
            if (col.getName().equals(colName)){
                return col;
            }
        }
        return null; // ewwww !
    }
    
    public int getOverallCompleteness(){
        double sumNbNull = 0;
        double sumNbLines = 0;
        
        
        for (ColumnInfo col: this.columns){
            sumNbNull += col.getStats().getNbNull();
            sumNbLines += col.getStats().getNbLines();
            System.out.println(sumNbLines + " | "+ sumNbNull);
        }
        
        return (int) (100 - 100 * (sumNbNull / sumNbLines));
    }
    
    public int getOverallUniqueness(){
        double sumNbDistinct = 0;
        double sumNbLines = 0;
        
        
        for (ColumnInfo col: this.columns){
            sumNbDistinct += col.getStats().getNbDistinct();
            sumNbLines += col.getStats().getNbLines();
            System.out.println(sumNbLines + " | "+ sumNbDistinct);
        }
        
        return (int) (100 - 100 * (sumNbDistinct / sumNbLines));
    }
    
    public TableInfo(){
        
    }
    
}
