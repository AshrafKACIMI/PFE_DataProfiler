/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import Reporting.TableReport;
import fxmltest.FXMLDocumentController;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

/**
 *
 * @author Ashraf
 */
public class BasicStatisticsService extends ProfilingService{
    
    public BasicStatisticsService(TableInfo table){
        super();
        this.table = table;
        //start();
    }
    
    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BasicStatisticsProfiler profiler = new BasicStatisticsProfiler(getTable());
                FXMLDocumentController.setProfiler(profiler);
                System.out.println("Menu clicked");
                String sql = profiler.profileTableQuery();
                System.out.println(sql);
                FXMLDocumentController.setSqlAreaText(sql);
                return null;
            }
        };
    }
}
