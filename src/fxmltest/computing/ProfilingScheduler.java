/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;

/**
 *
 * @author Ashraf
 */
public class ProfilingScheduler {
    private ArrayList<ProfilingService> tasks;
    
    public ProfilingScheduler(){
        this.tasks = new ArrayList<ProfilingService>();
    }
    
    public void start(){
        ExecutorService es = Executors.newSingleThreadExecutor();
        ArrayList<Task<Void>> taskList = new ArrayList<Task<Void>>();
        
        for (ProfilingService task: this.getTasks()){
            task.setExecutor(es);
            task.start();
            //taskList.add(task.getTask());
        }
        
        taskList.forEach(es::execute);
        es.shutdown();
    }
    
    public void addTask(ProfilingService task){
        this.getTasks().add(task);
    }
    
    public void removeTask(int i){
        this.getTasks().remove(i);
    }

    /**
     * @return the tasks
     */
    public ArrayList<ProfilingService> getTasks() {
        return tasks;
    }
        
}
