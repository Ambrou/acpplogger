/**
 * 
 */
package org.labs.studioprive.acpplogger;

import hudson.Plugin;
import java.util.logging.Logger;

/**
 * @author kdelfour
 * 
 */
public class PluginImpl extends Plugin {
	private final static Logger LOG = Logger.getLogger(PluginImpl.class
			.getName());

	 public void start() throws Exception {
         LOG.info("Ambroise CPP Logger give you more funny output for your insure ++ stdout");
 }
}
