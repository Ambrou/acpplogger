/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jenkinsci.plugins.acppLogger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.jvnet.hudson.test.JenkinsRule;

/**
 *
 * @author Mar_B_000
 */
public class acppLoggerPublisherJUnitTest {
    
    @Rule 
    public JenkinsRule j = new JenkinsRule();
    
    public acppLoggerPublisherJUnitTest() {
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

    @Test
    public void hello() {
        acppLoggerPublisher ambrexRecorder = new acppLoggerPublisher("toto");
        assertEquals("Ambrex !!!", ambrexRecorder.getDescriptor().getDisplayName());
    }
}
