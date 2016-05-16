/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Computing;

import Entities.TableInfo;
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
                FXMLDocumentController.getController().setSchedulerLabel("Profiling: " + table.getName());
                FXMLDocumentController.setProfiler(profiler);
                
                FXMLDocumentController controller = FXMLDocumentController.getController();
                stateProperty().addListener((ObservableValue<? extends Worker.State> observableValue, Worker.State oldValue, Worker.State newValue) -> {
                switch (newValue) {
                    case FAILED:
                    case CANCELLED:
                    case SUCCEEDED:
                        controller.postProfilingResult(table);
                    break;
            }
        });
                return null;
            }
        };
    }
    
    @Override
    public String toString(){
        return "Basic Statistics : " + table.getName();
    }
}
