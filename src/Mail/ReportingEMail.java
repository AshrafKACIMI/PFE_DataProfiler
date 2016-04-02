/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mail;


import com.sendgrid.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Ashraf
 */
public class ReportingEMail extends SendGrid.Email{
    private static String attachementFolder = System.getProperty("user.home");
    public static String apiKey = "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";


    public ReportingEMail(String from, String to, String subject, String content) {
        super();
        this.setFrom(from);
        this.addTo(to);
        this.setSubject(subject);
        this.setText(content);
    }

    public ReportingEMail(String from, String to, String subject, String content, String attachementPath, String attachementName) {
        super();
        this.setFrom(from);
        this.addTo(to);
        this.setSubject(subject);
        this.setText(content);
        
        try {
            this.addAttachment(attachementName,
                    new File(attachementFolder +"\\"+ attachementPath));
        } catch (IOException ex) {
            Logger.getLogger(ReportingEMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ReportingEMail(String attachementName) {
        super();

        this.addTo("kacimi.achraf@gmail.com");
        this.setFrom("no-reply-profiler@BBI.com");
        this.setSubject("Profiling results");
        this.setText("Kindly find attached the results :D");
        
        try {
            this.addAttachment(attachementName,
                    new File(attachementFolder +"\\"+ attachementName));
        } catch (IOException ex) {
            Logger.getLogger(ReportingEMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void send(){
        
        SendGrid sendgrid = new SendGrid(apiKey);

        try {
              SendGrid.Response response = sendgrid.send(this);
              System.out.println(response.getMessage());
            }
            catch (SendGridException e) {
              System.err.println(e);
            }
    }
}
