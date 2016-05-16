/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

/**
 *
 * @author Ashraf
 */
public class ColumnProfilingStats {
    String columnName;
    private int nbNull;
    private int nbDistinct;
    private int nbLines;
    private String min;
    private String max;

    public ColumnProfilingStats(String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max) {
        this.columnName = columnName;
        this.nbNull = nbNull;
        this.nbDistinct = nbDistinct;
        this.nbLines = nbLines;
        this.min = min;
        this.max = max;
    }
    

    /**
     * @return the columnName
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * @param columnName the columnName to set
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * @return the nbNull
     */
    public int getNbNull() {
        return nbNull;
    }

    /**
     * @param nbNull the nbNull to set
     */
    public void setNbNull(int nbNull) {
        this.nbNull = nbNull;
    }

    /**
     * @return the nbDistinct
     */
    public int getNbDistinct() {
        return nbDistinct;
    }

    /**
     * @param nbDistinct the nbDistinct to set
     */
    public void setNbDistinct(int nbDistinct) {
        this.nbDistinct = nbDistinct;
    }

    /**
     * @return the nbLines
     */
    public int getNbLines() {
        if (nbLines > 0)
            return nbLines;
        else return 9999; //à corriger !!!!!!!!!!
    }

    /**
     * @param nbLines the nbLines to set
     */
    public void setNbLines(int nbLines) {
        this.nbLines = nbLines;
    }

    /**
     * @return the min
     */
    public String getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(String min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public String getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(String max) {
        this.max = max;
    }
    
    public ColumnProfilingStats(){
        
    }
    
    
}
