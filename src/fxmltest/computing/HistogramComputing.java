/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import java.util.ArrayList;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;

/**
 *
 * @author Ashraf
 */
public class HistogramComputing {

    private int min;
    private int max;
    private int pas;
    private static int nb_histo = 5;

    public HistogramComputing(int min, int max) {
        this.min = min;
        this.max = max;
        this.pas = (max - min) / nb_histo;
    }
    
//    
//    public ArrayList<Integer> generateHistograms(ArrayList<Integer> values){
//        ArrayList<Integer> histos = new ArrayList<Integer>();
//        histos.add(new Integer(0));histos.add(new Integer(0));histos.add(new Integer(0));histos.add(new Integer(0));histos.add(new Integer(0));
//        for (Integer v:values){
//            Integer i = histos.get(v / this.pas);
//            i = 
//            histos.get(v / this.pas) = new Integer(intValue();
//            
//        }
//        
//    }
    
    /**
     * @return the nb_histo
     */
    public static int getNb_histo() {
        return nb_histo;
    }

    /**
     * @param aNb_histo the nb_histo to set
     */

    public static void setNb_histo(int aNb_histo) {
        nb_histo = aNb_histo;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @return the pas
     */
    public int getPas() {
        return pas;
    }

    /**
     * @param pas the pas to set
     */
    public void setPas(int pas) {
        this.pas = pas;
    }
    
    
    
    
    
}
