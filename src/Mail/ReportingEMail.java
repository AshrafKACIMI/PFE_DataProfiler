/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Mail;


import com.sendgrid.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author Ashraf
 */
public class ReportingEMail extends SendGrid.Email{
    private static String attachementFolder = System.getProperty("user.home")
            +"\\" + "Profiling Results";
    private static String apiKey = "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";

    /**
     * @return the attachementFolder
     */
    public static String getAttachementFolder() {
        return attachementFolder;
    }

    /**
     * @param aAttachementFolder the attachementFolder to set
     */
    public static void setAttachementFolder(String aAttachementFolder) {
        attachementFolder = aAttachementFolder;
    }

    /**
     * @return the apiKey
     */
    public static String getApiKey() {
        return apiKey;
    }


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
                    new File(getAttachementFolder() +"\\"+ attachementPath));
        } catch (IOException ex) {
            Logger.getLogger(ReportingEMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public ReportingEMail(String attachementName, String name) {
        super();
        
        this.addTo("kacimi.achraf@gmail.com");
        this.setFrom("no-reply-profiler@BBI.com");
        String date = new SimpleDateFormat("yyyy-MM-dd")
                    .format(Calendar.getInstance().getTime());
        this.setSubject("Profiling results " + date);
        String text = "Hello, \n"
                + "Kindly find attached the profiling results performed the " + date + "\n";
        this.setText(text);
        
        try {
            this.addAttachment(name,
                   // new File(getAttachementFolder() + attachementName));
                    new File(attachementName));
        } catch (IOException ex) {
            Logger.getLogger(ReportingEMail.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void send(){
        
        SendGrid sendgrid = new SendGrid(getApiKey());

        try {
              SendGrid.Response response = sendgrid.send(this);
              System.out.println(response.getMessage());
            }
            catch (SendGridException e) {
              System.err.println(e);
            }
    }
    
    
}
