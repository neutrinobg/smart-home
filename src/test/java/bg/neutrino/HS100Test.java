/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bg.neutrino;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author atonkin
 */
public class HS100Test {
    static HS100 instance;
    
    public HS100Test() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        instance = new HS110("84.40.125.153");
        try {
			instance.nightModeOff();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			instance.switchOn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
    }
    
    @After
    public void tearDown() {
        try {
            instance.nightModeOff();
        } catch (IOException ex) {
            Logger.getLogger(HS100Test.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
			instance.switchOn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    /**
     * Test of getIp method, of class HS100.
     */
    @Test
    @Ignore
    public void testGetIp() {
        System.out.println("getIp");
        HS100 instance = null;
        String expResult = "";
        String result = instance.getIp();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setIp method, of class HS100.
     */
    @Test
    @Ignore
    public void testSetIp() {
        System.out.println("setIp");
        String ip = "";
        HS100 instance = null;
        instance.setIp(ip);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPort method, of class HS100.
     */
    @Test
    @Ignore
    public void testGetPort() {
        System.out.println("getPort");
        HS100 instance = null;
        int expResult = 0;
        int result = instance.getPort();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPort method, of class HS100.
     */
    @Test
    @Ignore
    public void testSetPort() {
        System.out.println("setPort");
        int port = 0;
        HS100 instance = null;
        instance.setPort(port);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPresent method, of class HS100.
     */
    @Test
    public void testIsPresent_0args() {
        System.out.println("isPresent");
        HS100 instance = new HS110("84.40.125.153");
        boolean expResult = true;
        boolean result = instance.isPresent();
        assertEquals(expResult, result);
    }

    /**
     * Test of isPresent method, of class HS100.
     */
    @Test
    @Ignore
    public void testIsPresent_int() {
        System.out.println("isPresent");
        int timeout = 0;
        HS100 instance = null;
        boolean expResult = false;
        boolean result = instance.isPresent(timeout);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of switchOn method, of class HS100.
     */
    @Test
    public void testSwitchOn() throws Exception {
        System.out.println("switchOn");
        HS100 instance = new HS110("84.40.125.153");
        boolean expResult = true;
        boolean result = instance.switchOn();
        assertEquals(expResult, result);
    }

    /**
     * Test of switchOff method, of class HS100.
     */
    @Test
    public void testSwitchOff() throws Exception {
        System.out.println("switchOff");
        HS100 instance = new HS110("84.40.125.153");
        boolean expResult = true;
        boolean result = instance.switchOff();
        assertEquals(expResult, result);
    }

    /**
     * Test of nightModeOn method, of class HS100.
     */
    @Test
    public void testNightModeOn() throws Exception {
        System.out.println("nightModeOn");
        HS100 instance = new HS110("84.40.125.153");
        boolean expResult = true;
        boolean result = instance.nightModeOn();
        assertEquals(expResult, result);
    }

    /**
     * Test of nightModeOff method, of class HS100.
     */
    @Test
    public void testNightModeOff() throws Exception {
        System.out.println("nightModeOff");
        HS100 instance = new HS110("84.40.125.153");
        boolean expResult = true;
        boolean result = instance.nightModeOff();
        assertEquals(expResult, result);
    }

    /**
     * Test of readState method, of class HS100.
     */
    @Test
    @Ignore
    public void testReadState() throws Exception {
        System.out.println("readState");
        HS100 instance = null;
        int expResult = 0;
        int result = instance.readState();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getInfo method, of class HS100.
     */
    @Test
    @Ignore
    public void testGetInfo() throws Exception {
        System.out.println("getInfo");
        HS100 instance = null;
        Map<String, String> expResult = null;
        Map<String, String> result = instance.getInfo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of sendCommand method, of class HS100.
     */
    @Test
    @Ignore
    public void testSendCommand() throws Exception {
        System.out.println("sendCommand");
        String command = "";
        HS100 instance = null;
        String expResult = "";
        String result = instance.sendCommand(command);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
