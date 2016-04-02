/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Ashraf
 */
public class ColumnProfilingProperty {
    private SimpleStringProperty columnName;
    private SimpleStringProperty nbNull;
    private SimpleStringProperty nbDistinct;
//    private int nbLines;
    private SimpleStringProperty min;
    private SimpleStringProperty max;

    public ColumnProfilingProperty(ColumnProfilingStats stat) {
        this.columnName = new SimpleStringProperty(stat.getColumnName());
        this.nbNull = new SimpleStringProperty(Integer.toString(stat.getNbNull()));
        this.nbDistinct = new SimpleStringProperty(Integer.toString(stat.getNbDistinct()));
        this.min = new SimpleStringProperty(stat.getMin());
        this.max = new SimpleStringProperty(stat.getMax());
        
    }

    /**
     * @return the columnName
     */
    public SimpleStringProperty getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(SimpleStringProperty columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the nbNull
     */
    public SimpleStringProperty getNbNull() {
        return nbNull;
    }

    /**
     * @param nbNull the nbNull to set
     */
    public void setNbNull(SimpleStringProperty nbNull) {
        this.nbNull = nbNull;
    }

    /**
     * @return the nbDistinct
     */
    public SimpleStringProperty getNbDistinct() {
        return nbDistinct;
    }

    /**
     * @param nbDistinct the nbDistinct to set
     */
    public void setNbDistinct(SimpleStringProperty nbDistinct) {
        this.nbDistinct = nbDistinct;
    }

    /**
     * @return the min
     */
    public SimpleStringProperty getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(SimpleStringProperty min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public SimpleStringProperty getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(SimpleStringProperty max) {
        this.max = max;
    }
    
    
    
    
    
}
