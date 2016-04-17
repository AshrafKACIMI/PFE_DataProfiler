/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest.computing;

import Reporting.TableReport;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;

/**
 *
 * @author Ashraf
 */
public class ProfilingScheduler {
    private ArrayList<ProfilingService> tasks;
    private ArrayList<JasperReportBuilder> reports;
    
    
    public ProfilingScheduler(){
        this.tasks = new ArrayList<ProfilingService>();
        this.reports = new ArrayList<JasperReportBuilder>();
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
//        JasperConcatenatedReportBuilder concatReports = TableReport.getConcatenatedReports(reports);
//        System.out.println("REPORT : " + concatReports.toString());
        saveConcatenatedReports();
        tasks.clear();
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

    /**
     * @return the reports
     */
    public ArrayList<JasperReportBuilder> getReports() {
        return reports;
    }
    
    public void addReport(JasperReportBuilder report){
        this.reports.add(report);
    }
    
    public void saveConcatenatedReports(){
        TableReport.saveConcatenatedReports(reports);
    }
        
}
