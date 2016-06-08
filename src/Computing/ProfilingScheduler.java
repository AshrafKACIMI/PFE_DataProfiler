/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Computing;

import Reporting.ConcatenatedReports;
import fxmltest.launchSchedulerController;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javafx.concurrent.Task;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

/**
 *
 * @author Ashraf
 */
public class ProfilingScheduler {

    /**
     * @return the count
     */
    public int getCount() {
        return count;
    }
    private ArrayList<ProfilingService> tasks;
    //private ArrayList<JasperReportBuilder> reports;
    private ConcatenatedReports report;
    private int count;
    
    public ProfilingScheduler(){
        this.tasks = new ArrayList<ProfilingService>();
        report = new ConcatenatedReports();
    }
    
    public void start(){
        ArrayList<Task<Void>> taskList = new ArrayList<Task<Void>>();
                    ExecutorService es = Executors.newSingleThreadExecutor();
                    
                    
        for (ProfilingService task: this.getTasks()){
            task.setExecutor(es);
            task.start();
//            taskList.add(task.getTask());
        }
        
//        taskList.forEach(es::execute);
        

        tasks.clear();
        es.shutdown();
        System.out.print("TASKS : " + tasks.size());
    }

    
        public void delay(LocalDateTime exeDate){
        ArrayList<Task<Void>> taskList = new ArrayList<Task<Void>>();
        ScheduledExecutorService  es = Executors.newSingleThreadScheduledExecutor();
        
        long currentMilliseconds = System.currentTimeMillis()/1000;
        LocalDateTime now = LocalDateTime.now();
        System.out.println("NOW : " + now);
        System.out.println("EXE : " + exeDate);
        
        Duration diff = Duration.between(exeDate, now);
        Duration diff2 = Duration.between(now, exeDate);
        
        System.out.println("DIFF: " + diff.toMillis());
        System.out.println("DIFF2: " + diff2.toMillis());
        
        for (ProfilingService task : getTasks()) 
            System.out.println("taskkkkk" + task);
                        

        es.schedule(
                new Callable() {
                    public Object call() throws Exception {
                        for (ProfilingService task : getTasks()) {
                            System.out.println("taskkkkk" + task);
                            task.setExecutor(es);
                            task.start();
//            taskList.add(task.getTask());
                        }
                        
                        if (es.isTerminated()){
                            System.out.println("oisdqksdjlaze");
                            es.shutdown();
                        }
                        tasks.clear();
                        
                        System.out.print("TASKS : " + tasks.size());
                        return "Called!";
                    }
                },
                //10000,
                diff2.toMillis(),
                // date.getTime() - System.currentTimeMillis()s
                TimeUnit.MILLISECONDS);

        
                    

        
//        taskList.forEach(es::execute);
        

 
    }

    
    
    public void addTask(ProfilingService task){
        this.getTasks().add(task);
        this.incCount();
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

    /**
     * @return the reports
     */
    public ConcatenatedReports getReport() {
        return report;
    }
    
    public void addReport(JasperReportBuilder report){
        this.report.addReport(report);
    }
    
    public void incCount(){
        count++;
    }
    
    public void decCount(){
        count--;
        if (count == 0){
            // last job just finished
           if (launchSchedulerController.getController() != null) 
                launchSchedulerController.getController().updateButton();// TODO
        }
    }
    
//    public void saveConcatenatedReports(){
//        TableReport.saveConcatenatedReports(reports);
//    }
        
    
}
