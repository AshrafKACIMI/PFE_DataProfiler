///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package Tests.fxmltest.computing;
//
//import fxmltest.computing.ReferentialIntegrityEngine;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
///**
// *
// * @author Ashraf
// */
//public class ReferentialIntegrityEngineTest {
//    
//    public ReferentialIntegrityEngineTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//    }
//    
//    @AfterClass
//    public static void tearDownClass() {
//    }
//    
//    @Before
//    public void setUp() {
//    }
//    
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of referentialIntegrityQuery method, of class ReferentialIntegrityEngine.
//     */
//    @Test
//    public void testReferentialIntegrityQuery() {
//        System.out.println("referentialIntegrityQuery");
//        ReferentialIntegrityEngine instance = 
//                new ReferentialIntegrityEngine("A", "ca", "B", "cb");
//        
//        String expResult = "SELECT count(*) FROM (SELECT DISTINCT ca FROM A MINUS SELECT DISTINCT cb FROM B) A";
//        String result = instance.referentialIntegritySampleQuery();
//        System.out.println(result);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        //fail("The test case is a prototype.");
//    }
//    
//}
