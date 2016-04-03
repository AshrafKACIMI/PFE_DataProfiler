/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import fxmltest.FXMLDocumentController;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 *
 * @author Ashraf
 */
public abstract class ProfilingService extends Service<Void> {
    protected TableInfo table;

    protected ProfilingService() {
        super();
    }
    
    
    
}
