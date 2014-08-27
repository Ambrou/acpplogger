package org.labs.studioprive.acpplogger;


import junit.framework.Assert;

import org.junit.Test;

public class ACPPLoggerActionTest {

	@Test
	public void testGetIconFileName() {
		ACPPLoggerAction acppLoggerAction = new ACPPLoggerAction(null, null);
		Assert.assertEquals("graph.gif", acppLoggerAction.getIconFileName());
	}

	@Test
	public void testGetDisplayName() {
		ACPPLoggerAction acppLoggerAction = new ACPPLoggerAction(null, null);
		Assert.assertEquals("ACPPLogger Result", acppLoggerAction.getDisplayName());
	}

	@Test
	public void testGetUrlName() {
		ACPPLoggerAction acppLoggerAction = new ACPPLoggerAction(null, null);
		Assert.assertEquals("ACPPLoggerResult", acppLoggerAction.getUrlName());
	}

	@Test
	public void testGetBuildHealth() {
		ACPPLoggerAction acppLoggerAction = new ACPPLoggerAction(null, null);
		Assert.assertNotNull(acppLoggerAction.getBuildHealth());
	}

}
