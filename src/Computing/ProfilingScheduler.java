/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Computing;

import Reporting.ConcatenatedReports;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
            //taskList.add(task.getTask());
        }
        
        taskList.forEach(es::execute);
        
//        JasperConcatenatedReportBuilder concatReports = TableReport.getConcatenatedReports(reports);
//        System.out.println("REPORT : " + concatReports.toString());
        //saveConcatenatedReports();
        tasks.clear();
        es.shutdown();
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
    }
    
//    public void saveConcatenatedReports(){
//        TableReport.saveConcatenatedReports(reports);
//    }
        
    
}
