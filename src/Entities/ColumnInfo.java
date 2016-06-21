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
public class ColumnInfo {
    private String name;
    private String type;
    private String consraint;
    private ColumnProfilingStatsRow stats;
    private String minConstraint;
    private String maxConstraint;
    private boolean isUnique;
    private boolean isNotNull;
    

    public ColumnInfo(String name, String type, String consraint) {
        this.name = name;
        this.type = type;
        this.consraint = consraint;
        this.minConstraint = null;
        this.maxConstraint = null;
        this.isUnique = false;
        this.isNotNull = false;
    }

    @Override
    public String toString() {
        return "ColumnInfo{" + "name=" + name + ", type=" + type + ", consraint=" + consraint + '}';
    }
    
    public ColumnInfo(String name, String type) {
        this.name = name;
        this.type = type;
        this.isNotNull = false;
        this.isUnique = false;
        this.stats = new ColumnProfilingStatsRow(name, 0, 0, 0, "","");
    }
    
    
    public int violDistinctFlag(){
        if (isUnique){
            if (this.stats.getNbLines() != this.stats.getNbDistinct())
                return 1;
        }
        return 0;
    }
    
    public int violMaxFlag(){
        if (maxConstraint != null && !maxConstraint.isEmpty() && !this.stats.getMax().isEmpty()){
            if (Integer.parseInt(this.stats.getMax()) >
                    Integer.parseInt(this.maxConstraint)) 
                    return 1;
        }
        return 0;
    }
    
    public int violMinFlag(){
        if (minConstraint != null && !minConstraint.isEmpty() && !this.stats.getMin().isEmpty()){
            if (Integer.parseInt(this.stats.getMin()) < 
                    Integer.parseInt(this.minConstraint))
                return 1;
        }
        return 0;
    }
    
    public int violNotNullFlag(){ return (isIsNotNull()) ? 1 : 0; }

////        if (isIsNotNull()){
////            if (this.stats.getNbNull() > 0){
////                return 1;
////            }
////        }
////        return 0;
//        
//    }
    
    public boolean isProblematic(){
        // returns true if value violates a certain DQ rule
        if (Integer.parseInt(this.maxConstraint) <= Integer.parseInt(this.stats.getMax())
                || Integer.parseInt(this.minConstraint) <= Integer.parseInt(this.stats.getMin()))
                // if value is not between min and max permitted values
            return true;
        
        else if (isIsUnique()){
            if (this.stats.getNbLines() > this.stats.getNbDistinct())
                return true;
        }
        return false;
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
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * @return the consraint
     */
    public String getConsraint() {
        return consraint;
    }

    /**
     * @param consraint the consraint to set
     */
    public void setConsraint(String consraint) {
        this.consraint = consraint;
    }

    /**
     * @return the stats
     */
    public ColumnProfilingStatsRow getStats() {
        return stats;
    }

    /**
     * @param stats the stats to set
     */
    public void setStats(ColumnProfilingStatsRow stats) {
        this.stats = stats;
    }

    /**
     * @return the minConstraint
     */
    public String getMinConstraint() {
        return minConstraint;
    }

    /**
     * @param minConstraint the minConstraint to set
     */
    public void setMinConstraint(String minConstraint) {
        this.minConstraint = minConstraint;
    }

    /**
     * @return the maxConstraint
     */
    public String getMaxConstraint() {
        return maxConstraint;
    }

    /**
     * @param maxConstraint the maxConstraint to set
     */
    public void setMaxConstraint(String maxConstraint) {
        this.maxConstraint = maxConstraint;
    }

    /**
     * @param isUnique the isUnique to set
     */
    public void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * @param isNotNull the isNotNull to set
     */
    public void setIsNotNull(boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    /**
     * @return the isUnique
     */
    public boolean isIsUnique() {
        return isUnique;
    }

    /**
     * @return the isNotNull
     */
    public boolean isIsNotNull() {
        return isNotNull;
    }
    
    public void updateStatsRules(){
        //updates the DQ rules attributes of the stats
        this.stats.setViolDistinctFlag(violDistinctFlag());
        this.stats.setViolMaxFlag(violMaxFlag());
        this.stats.setViolMinFlag(violMinFlag());
        this.stats.setViolNotNullFlag(violNotNullFlag());
    }
    
    public void updateRule(DqRule rule){
        maxConstraint = rule.getMaxConstraint();
        minConstraint = rule.getMinConstraint();
        isUnique = rule.isIsUnique();
        isNotNull = rule.isIsNotNull();
        updateStatsRules();
    }
    
    public ColumnInfo(){
        
    }
    
    
    
    
}
