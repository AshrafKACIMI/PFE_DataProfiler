/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import java.util.ArrayList;

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
        for (ProfilingService task: this.tasks){
            task.start();
        }
    }
    
    public void addTask(ProfilingService task){
        this.tasks.add(task);
    }
        
}
