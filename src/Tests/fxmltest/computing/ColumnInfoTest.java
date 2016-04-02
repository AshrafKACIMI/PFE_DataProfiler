///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//
//package Tests.fxmltest.computing;
//
//import fxmltest.computing.ColumnInfo;
//import fxmltest.computing.ColumnProfilingStats;
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
//public class ColumnInfoTest {
//    
//    private static ColumnInfo columnInfo;
//    
//    public ColumnInfoTest() {
//    }
//    
//    @BeforeClass
//    public static void setUpClass() {
//        /*
//        this.name = name;
//        this.type = type;
//        this.consraint = consraint;
//        this.minConstraint = null;
//        this.maxConstraint = null;
//        this.isUnique = false;
//        this.isNotNull = false;
//        */
//        
//        columnInfo = new ColumnInfo("MANAGER_ID", "varchar2");
//        columnInfo.setStats(
//                new ColumnProfilingStats(
//                        "Manager ID",
//                        5,
//                        10,
//                        12,
//                        "4",
//                        "7"
//                ));
//        columnInfo.setIsNotNull(true);
//        columnInfo.setIsUnique(true);
//        columnInfo.setMinConstraint("5");
//        columnInfo.setMaxConstraint("7");
//                
//                
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
//     * Test of toString method, of class ColumnInfo.
//     */
////    @Test
////    public void testToString() {
////        System.out.println("toString");
////        ColumnInfo instance = null;
////        String expResult = "";
////        String result = instance.toString();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//
//    /**
//     * Test of violDistinctFlag method, of class ColumnInfo.
//     */
//    @Test
//    public void testViolDistinctFlag() {
//        System.out.println("violDistinctFlag");
//        int expResult = 1;
//        int result = columnInfo.violDistinctFlag();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//    }
//
//    /**
//     * Test of violMaxFlag method, of class ColumnInfo.
//     */
//    @Test
//    public void testViolMaxFlag() {
//        System.out.println("violMaxFlag");
//        ColumnInfo instance = null;
//        int expResult = 0;
//        int result = columnInfo.violMaxFlag();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        
//    }
//
//    /**
//     * Test of violMinFlag method, of class ColumnInfo.
//     */
//    @Test
//    public void testViolMinFlag() {
//        System.out.println("violMinFlag");
//        ColumnInfo instance = null;
//        int expResult = 1;
//        int result = columnInfo.violMinFlag();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        
//    }
//
//    /**
//     * Test of violNotNullFlag method, of class ColumnInfo.
//     */
//    @Test
//    public void testViolNotNullFlag() {
//        System.out.println("violNotNullFlag");
//        ColumnInfo instance = null;
//        int expResult = 1;
//        int result = columnInfo.violNotNullFlag();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        
//    }
//
//    /**
//     * Test of isProblematic method, of class ColumnInfo.
//     */
////    @Test
////    public void testIsProblematic() {
////        System.out.println("isProblematic");
////        ColumnInfo instance = null;
////        boolean expResult = false;
////        boolean result = instance.isProblematic();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
//
//    /**
//     * Test of getName method, of class ColumnInfo.
//     */
////    @Test
////    public void testGetName() {
////        System.out.println("getName");
////        ColumnInfo instance = null;
////        String expResult = "";
////        String result = instance.getName();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setName method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetName() {
////        System.out.println("setName");
////        String name = "";
////        ColumnInfo instance = null;
////        instance.setName(name);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of getType method, of class ColumnInfo.
////     */
////    @Test
////    public void testGetType() {
////        System.out.println("getType");
////        ColumnInfo instance = null;
////        String expResult = "";
////        String result = instance.getType();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setType method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetType() {
////        System.out.println("setType");
////        String type = "";
////        ColumnInfo instance = null;
////        instance.setType(type);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of getConsraint method, of class ColumnInfo.
////     */
////    @Test
////    public void testGetConsraint() {
////        System.out.println("getConsraint");
////        ColumnInfo instance = null;
////        String expResult = "";
////        String result = instance.getConsraint();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setConsraint method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetConsraint() {
////        System.out.println("setConsraint");
////        String consraint = "";
////        ColumnInfo instance = null;
////        instance.setConsraint(consraint);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of getStats method, of class ColumnInfo.
////     */
////    @Test
////    public void testGetStats() {
////        System.out.println("getStats");
////        ColumnInfo instance = null;
////        ColumnProfilingStats expResult = null;
////        ColumnProfilingStats result = instance.getStats();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setStats method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetStats() {
////        System.out.println("setStats");
////        ColumnProfilingStats stats = null;
////        ColumnInfo instance = null;
////        instance.setStats(stats);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of getMinConstraint method, of class ColumnInfo.
////     */
////    @Test
////    public void testGetMinConstraint() {
////        System.out.println("getMinConstraint");
////        ColumnInfo instance = null;
////        String expResult = "";
////        String result = instance.getMinConstraint();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setMinConstraint method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetMinConstraint() {
////        System.out.println("setMinConstraint");
////        String minConstraint = "";
////        ColumnInfo instance = null;
////        instance.setMinConstraint(minConstraint);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of getMaxConstraint method, of class ColumnInfo.
////     */
////    @Test
////    public void testGetMaxConstraint() {
////        System.out.println("getMaxConstraint");
////        ColumnInfo instance = null;
////        String expResult = "";
////        String result = instance.getMaxConstraint();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setMaxConstraint method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetMaxConstraint() {
////        System.out.println("setMaxConstraint");
////        String maxConstraint = "";
////        ColumnInfo instance = null;
////        instance.setMaxConstraint(maxConstraint);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setIsUnique method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetIsUnique() {
////        System.out.println("setIsUnique");
////        boolean isUnique = false;
////        ColumnInfo instance = null;
////        instance.setIsUnique(isUnique);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of setIsNotNull method, of class ColumnInfo.
////     */
////    @Test
////    public void testSetIsNotNull() {
////        System.out.println("setIsNotNull");
////        boolean isNotNull = false;
////        ColumnInfo instance = null;
////        instance.setIsNotNull(isNotNull);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of isIsUnique method, of class ColumnInfo.
////     */
////    @Test
////    public void testIsIsUnique() {
////        System.out.println("isIsUnique");
////        ColumnInfo instance = null;
////        boolean expResult = false;
////        boolean result = instance.isIsUnique();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////
////    /**
////     * Test of isIsNotNull method, of class ColumnInfo.
////     */
////    @Test
////    public void testIsIsNotNull() {
////        System.out.println("isIsNotNull");
////        ColumnInfo instance = null;
////        boolean expResult = false;
////        boolean result = instance.isIsNotNull();
////        assertEquals(expResult, result);
////        // TODO review the generated test code and remove the default call to fail.
////        fail("The test case is a prototype.");
////    }
////    
//}
