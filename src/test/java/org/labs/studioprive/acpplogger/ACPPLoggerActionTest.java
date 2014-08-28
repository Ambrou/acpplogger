package org.labs.studioprive.acpplogger;


import junit.framework.Assert;

import org.junit.Test;

public class ACPPLoggerActionTest {

	@Test
	public void testGetIconFileName() {
		AcppLoggerBuildAction acppLoggerAction = new AcppLoggerBuildAction(null, null);
		Assert.assertEquals("graph.gif", acppLoggerAction.getIconFileName());
	}

	@Test
	public void testGetDisplayName() {
		AcppLoggerBuildAction acppLoggerAction = new AcppLoggerBuildAction(null, null);
		Assert.assertEquals("AcppLogger Report", acppLoggerAction.getDisplayName());
	}

	@Test
	public void testGetUrlName() {
		AcppLoggerBuildAction acppLoggerAction = new AcppLoggerBuildAction(null, null);
		Assert.assertEquals("AcppLogger", acppLoggerAction.getUrlName());
	}

	@Test
	public void testGetBuildHealth() {
		AcppLoggerBuildAction acppLoggerAction = new AcppLoggerBuildAction(null, null);
		Assert.assertNotNull(acppLoggerAction.getBuildHealth());
	}

}
