/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Tests.fxmltest.computing;

import fxmltest.computing.BasicStatisticsProfiler;
import fxmltest.computing.ColumnInfo;
import fxmltest.computing.TableInfo;
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
public class BasicStatisticsProfilerTest {
    
    public BasicStatisticsProfilerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        BasicStatisticsProfiler.alternativeNull = "-99";
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of profileColumn method, of class BasicStatisticsProfiler.
     */
//    @Test
//    public void testProfileColumn() {
//        System.out.println("profileColumn");
//        String columnName = "";
//        BasicStatisticsProfiler instance = null;
//        int expResult = 0;
//        int result = instance.profileColumn(columnName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of profileColumnQuery method, of class BasicStatisticsProfiler.
//     */
//    @Test
//    public void testProfileColumnQuery() {
//        System.out.println("profileColumnQuery");
//        TableInfo table = null;
//        ColumnInfo column = null;
//        BasicStatisticsProfiler instance = null;
//        String expResult = "";
//        String result = instance.profileColumnQuery(table, column);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of profileColumnQuery2 method, of class BasicStatisticsProfiler.
     */
    @Test
    public void testProfileColumnQuery2() {
        System.out.println("profileColumnQuery2");
        String tab = "tab";
        String col = "col";
        String expResult = "";
        String result = BasicStatisticsProfiler.profileColumnQuery2(tab, col);
        //assertEquals(expResult, result);
        System.out.println(result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
