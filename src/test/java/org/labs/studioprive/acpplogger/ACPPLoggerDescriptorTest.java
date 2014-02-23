package org.labs.studioprive.acpplogger;

import junit.framework.Assert;

import org.junit.Test;

public class ACPPLoggerDescriptorTest {

	@Test
	public void testIsApplicableClassOfQextendsAbstractProject() {
		ACPPLoggerDescriptor accLoggerDescriptor = new ACPPLoggerDescriptor();
		Assert.assertEquals(true, accLoggerDescriptor.isApplicable(null));
	}

	@Test
	public void testGetDisplayName() {
		ACPPLoggerDescriptor accLoggerDescriptor = new ACPPLoggerDescriptor();
		Assert.assertEquals("A Cpp Logger for insure++", accLoggerDescriptor.getDisplayName());
	}

}
