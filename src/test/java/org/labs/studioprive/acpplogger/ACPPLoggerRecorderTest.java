package org.labs.studioprive.acpplogger;

import java.io.FileNotFoundException;
import java.util.List;

import hudson.tasks.BuildStepMonitor;
import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner; 

import static org.junit.Assert.*;  
	
@RunWith(MockitoJUnitRunner.class) 
public class ACPPLoggerRecorderTest {

	@Test
	public void testGetRequiredMonitorService() {
		AcppLoggerPublisher acppLoggerRecorder = new AcppLoggerPublisher();
		Assert.assertEquals(BuildStepMonitor.NONE, acppLoggerRecorder.getRequiredMonitorService());
	}
/*
	@Test
	public void testACPPLoggerRecorder() {
		ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("fileToParse.coverage", "toto;tutu;titi;truc.txt");
		Assert.assertEquals("fileToParse.coverage", acppLoggerRecorder.getFileToParse());
		List<String>unselectedFilesList = acppLoggerRecorder.getFilesToExclude();
		Assert.assertEquals(4, unselectedFilesList.size());
		Assert.assertEquals("toto", unselectedFilesList.get(0));
		Assert.assertEquals("tutu", unselectedFilesList.get(1));
		Assert.assertEquals("titi", unselectedFilesList.get(2));
		Assert.assertEquals("truc.txt", unselectedFilesList.get(3));
	}
	
	
	
	@Test
	public void testPerformWithOutExclusion() {
		
		try {
			ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("ocR.coverage", " ");
			acppLoggerRecorder.splitMasterFileCoverage(System.getProperty("user.dir") + "\\src\\test\\resources\\ocR.coverage", null);
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
			acppLoggerRecorder.splitMasterFileCoverage(System.getProperty("user.dir") + "\\src\\test\\resources\\ocR.coverage", null);
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
			acppLoggerRecorder.splitMasterFileCoverage(System.getProperty("user.dir") + "\\src\\test\\resources\\ocR_Short.coverage", null);
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
	
	@Test
	public void testgetFilesToExcludeStringWithData() {
		
		ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("", "xutility;xmemory;xlocinfo;xlocale");
		Assert.assertEquals("xutility;xmemory;xlocinfo;xlocale", acppLoggerRecorder.getFilesToExcludeString());
	}

	
	@Test
	public void testgetFilesToExcludeStringWithOutData() {
		
		ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder("", "");
		Assert.assertEquals("__empty__", acppLoggerRecorder.getFilesToExcludeString());
	}*/
}
