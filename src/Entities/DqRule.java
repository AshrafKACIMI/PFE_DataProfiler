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
public class DqRule {
    private String minConstraint;
    private String maxConstraint;
    private boolean isUnique;
    private boolean isNotNull;

    public DqRule(String minConstraint, String maxConstraint, boolean isUnique, boolean isNotNull) {
        this.minConstraint = minConstraint;
        this.maxConstraint = maxConstraint;
        this.isUnique = isUnique;
        this.isNotNull = isNotNull;
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
     * @return the isUnique
     */
    public boolean isIsUnique() {
        return isUnique;
    }

    /**
     * @param isUnique the isUnique to set
     */
    public void setIsUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    /**
     * @return the isNotNull
     */
    public boolean isIsNotNull() {
        return isNotNull;
    }

    /**
     * @param isNotNull the isNotNull to set
     */
    public void setIsNotNull(boolean isNotNull) {
        this.isNotNull = isNotNull;
    }

    @Override
    public String toString() {
        return "DqRule{" + "minConstraint=" + minConstraint + ", maxConstraint=" + maxConstraint + ", isUnique=" + isUnique + ", isNotNull=" + isNotNull + '}';
    }
    
    
    
}
