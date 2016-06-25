/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Reporting;

import Entities.TableInfo;
import Mail.EmailOptions;
import Mail.ReportingEMail;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.dynamicreports.jasper.builder.JasperConcatenatedReportBuilder;
import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.exception.DRException;

/**
 *
 * @author Ashraf
 */
public class ConcatenatedReports extends JasperConcatenatedReportBuilder{

    /**
     * @return the currentReportName
     */
    public static String getCurrentReportName() {
        return currentReportName;
    }
    private ArrayList<JasperReportBuilder> reports;
    private static String currentReportName;
    
    public ConcatenatedReports(){
        super();
        this.reports = new ArrayList<JasperReportBuilder>();
    }
    
    public ConcatenatedReports(ArrayList<TableInfo> tables){
        super();
        this.reports = new ArrayList<JasperReportBuilder>();
        for (TableInfo table: tables)
            this.reports.add(new TableReport(table, false).getReport());
    }
    
    
    
    public void addReport(JasperReportBuilder report){
        this.reports.add(report);
    }
    
    public void makePdf(){
        for (JasperReportBuilder report: reports){
            this.concatenate(report);
            System.out.println("Report : " + report);
        }
        
        String fileTimeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(Calendar.getInstance().getTime());
        String fileName = "Concat results" + " " 
                    + fileTimeStamp + ".pdf";
        currentReportName = fileName;
        String saveFolder = System.getProperty("user.home")+"\\Profiling Results\\";
        String saveTo = saveFolder + fileName;
        
        FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(saveTo);
                try {
                    toPdf(fos);
                    String attached = EmailOptions.getFileDirectory() + ConcatenatedReports.getCurrentReportName();
                    if (EmailOptions.isOn()){
                        ReportingEMail mail = new ReportingEMail(attached);
                        mail.send();
                    }
                        
                    try {
                        fos.close();
                    } catch (IOException ex) {
                        Logger.getLogger(ConcatenatedReports.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } catch (DRException ex) {
                    Logger.getLogger(ConcatenatedReports.class.getName()).log(Level.SEVERE, null, ex);
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TableReport.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    
    
   
    
}
