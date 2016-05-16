/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Entities;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlValue;

/**
 *
 * @author Ashraf
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class ColumnProfilingStatsRow extends ColumnProfilingStats{
    private int violMaxFlag;
    private int violMinFlag;
    private int violDistinctFlag;
    private int violNotNullFlag;
    /**
     *
     */
    
    
    
    
    public ColumnProfilingStatsRow(String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max, ColumnInfo ci){
        super(columnName, nbNull, nbDistinct, nbLines, min, max);
        this.violMinFlag = ci.violMinFlag();
        this.violMaxFlag = ci.violMaxFlag();
        this.violDistinctFlag = ci.violDistinctFlag();
        this.violNotNullFlag = ci.violNotNullFlag();
    }
    
    public ColumnProfilingStatsRow(String columnName, int nbNull, int nbDistinct, int nbLines, String min, String max){
        super(columnName, nbNull, nbDistinct, nbLines, min, max);
        this.violMinFlag = 0;
        this.violMaxFlag = 0;
        this.violDistinctFlag = 0;
        this.violNotNullFlag = 0;
    }
    
    

    @Override
    public String toString() {
        return "ColumnProfilingStatsRow{  ColName: " + columnName +  ", violMaxFlag=" + getViolMaxFlag() + ", violMinFlag=" + getViolMinFlag() + ", violDistinctFlag=" + getViolDistinctFlag() + ", violNotNullFlag=" + getViolNotNullFlag() + '}';
    }

    /**
     * @return the violMaxFlag
     */
    public int getViolMaxFlag() {
        return violMaxFlag;
    }

    /**
     * @param violMaxFlag the violMaxFlag to set
     */
    public void setViolMaxFlag(int violMaxFlag) {
        this.violMaxFlag = violMaxFlag;
    }

    /**
     * @return the violMinFlag
     */
    public int getViolMinFlag() {
        return violMinFlag;
    }

    /**
     * @param violMinFlag the violMinFlag to set
     */
    public void setViolMinFlag(int violMinFlag) {
        this.violMinFlag = violMinFlag;
    }

    /**
     * @return the violDistinctFlag
     */
    public int getViolDistinctFlag() {
        return violDistinctFlag;
    }

    /**
     * @param violDistinctFlag the violDistinctFlag to set
     */
    public void setViolDistinctFlag(int violDistinctFlag) {
        this.violDistinctFlag = violDistinctFlag;
    }

    /**
     * @return the violNotNullFlag
     */
    public int getViolNotNullFlag() {
        return violNotNullFlag;
    }

    /**
     * @param violNotNullFlag the violNotNullFlag to set
     */
    public void setViolNotNullFlag(int violNotNullFlag) {
        this.violNotNullFlag = violNotNullFlag;
    }
    
    public ColumnProfilingStatsRow(){
        
    }
    
}
