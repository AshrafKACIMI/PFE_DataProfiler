/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;
import Mail.ReportingEMail;
import Reporting.ConcatenatedReports;
import Reporting.TableReport;

/**
 *
 * @author Ashraf
 */
public class MailTest {
    
    public static String apiKey = "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";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        ReportingEMail mail = new ReportingEMail("Concat results 20160530_100133.pdf");
        mail.send();
        
        
            
    }
    
}
