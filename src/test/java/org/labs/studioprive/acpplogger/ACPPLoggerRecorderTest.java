package org.labs.studioprive.acpplogger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import hudson.FilePath;
import hudson.Launcher;
import hudson.model.AbstractBuild;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepMonitor;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;  
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner; 

import static org.junit.Assert.*;  
import static org.mockito.Mockito.*;
	
@RunWith(MockitoJUnitRunner.class) 
public class ACPPLoggerRecorderTest {

	
	@Test
	public void testACPPLoggerRecorder() {
		ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("fileToParse.coverage", "toto;tutu;titi;truc.txt");
		Assert.assertEquals("fileToParse.coverage", acppLoggerRecorder.getFileToParse());
		String[] unselectedFilesList = acppLoggerRecorder.getUnselectedFilesList();
		Assert.assertEquals(4, unselectedFilesList.length);
		Assert.assertEquals("toto", unselectedFilesList[0]);
		Assert.assertEquals("tutu", unselectedFilesList[1]);
		Assert.assertEquals("titi", unselectedFilesList[2]);
		Assert.assertEquals("truc.txt", unselectedFilesList[3]);
	}
	
	@Test
	public void testGetRequiredMonitorService() {
		ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("", "");
		Assert.assertEquals(BuildStepMonitor.BUILD, acppLoggerRecorder.getRequiredMonitorService());
	}
	
	@Test
	public void testPerformWithOutExclusion() {
		
		try {
			ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("", "");
			acppLoggerRecorder.perform(System.getProperty("user.dir") + "\\src\\test\\resources\\ocR.coverage");
			Assert.assertEquals(201, acppLoggerRecorder.getCoverageDataFiles().size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPerformWithExclusion() {
		
		try {
			ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("", "xutility;xmemory;xlocinfo;xlocale");
			acppLoggerRecorder.perform(System.getProperty("user.dir") + "\\src\\test\\resources\\ocR.coverage");
			Assert.assertEquals(197, acppLoggerRecorder.getCoverageDataFiles().size());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
	
	@Test
	public void testPerformGetFileLines() {
		
		try {
			ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("", "xutility;xmemory;xlocinfo;xlocale");
			acppLoggerRecorder.perform(System.getProperty("user.dir") + "\\src\\test\\resources\\ocR_Short.coverage");
			Assert.assertEquals(21, acppLoggerRecorder.getCoverageDataFiles().get(0).getLinesFile().size());
			Assert.assertEquals("   . ->         deleteArgument();", acppLoggerRecorder.getCoverageDataFiles().get(0).getLinesFile().get(8));
			Assert.assertEquals(55, acppLoggerRecorder.getCoverageDataFiles().get(1).getLinesFile().size());
			Assert.assertEquals("        //-------------------------------------------------------------------------------------------", acppLoggerRecorder.getCoverageDataFiles().get(1).getLinesFile().get(8));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			fail(e.getMessage());
			e.printStackTrace();
		}
	}
}
