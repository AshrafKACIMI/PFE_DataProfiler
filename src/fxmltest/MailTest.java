/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package fxmltest;
import DQRepository.MetaDataConnector;
import Mail.ReportingEMail;
import com.sendgrid.SendGrid;
import com.sendgrid.SendGridException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        // TODO code application logic here
        
//                Mail.ReportingEMail mail = new ReportingEMail(args[0]);
//                mail.send();
        
                MetaDataConnector.updateDb();

        
//        
//        SendGrid sendgrid = new SendGrid(apiKey);
//
//            SendGrid.Email email = new SendGrid.Email();
//            email.addTo("kacimi.achraf@gmail.com");
//            email.setFrom("no-reply-profiler@BBI.com");
//            email.setSubject("Profiling results");
//            email.setText("My first email with SendGrid Java!");
//        try {
//            email.addAttachment("test.pdf", new File(System.getProperty("user.home")+"\\"+"intro.pdf"));
//            
//        } catch (IOException ex) {
//            Logger.getLogger(MailTest.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
            
    }
    
}
