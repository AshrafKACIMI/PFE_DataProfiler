/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mail;

import fxmltest.FXMLDocumentController;
import java.util.ArrayList;
import java.lang.String;

/**
 *
 * @author Ashraf
 */
public class EmailOptions {
    static private boolean on;
    static private ArrayList<String> mailingList = 
            FXMLDocumentController.getMailListView().getMailList();

    /**
     * @return the on
     */
    public static boolean isOn() {
        return on;
    }

    /**
     * @param aOn the on to set
     */
    public static void setOn(boolean aOn) {
        on = aOn;
    }
    
    public static String[] getMails(){
        return mailingList.toArray(new String[0]);
    }
    
    
}
