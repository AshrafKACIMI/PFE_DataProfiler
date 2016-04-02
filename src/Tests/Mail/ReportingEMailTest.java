/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tests.Mail;

import Mail.ReportingEMail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Ashraf
 */
public class ReportingEMailTest {
    private static final String attachementFolder = System.getProperty("user.home");
    public static final String apiKey = "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";

    
    public ReportingEMailTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
                }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of send method, of class ReportingEMail.
     */
    @Test
    public void testSend() {
        System.out.println("send");
        final String fileLocation = "\\test1.pdf";
        System.out.println(fileLocation);
        ReportingEMail instance = new ReportingEMail(fileLocation);
        instance.send();
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
