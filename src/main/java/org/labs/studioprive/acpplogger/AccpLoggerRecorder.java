/**
 * 
 */
package org.labs.studioprive.acpplogger;

import java.io.IOException;

import hudson.Launcher;
import hudson.model.Build;
import hudson.model.BuildListener;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;

/**
 * @author kdelfour
 * 
 */
public class AccpLoggerRecorder extends Recorder {

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.tasks.BuildStep#getRequiredMonitorService()
	 */
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}

	/*
	 * La collecte de données se fait en surchargeant cette méthode.
	 * (non-Javadoc)
	 * 
	 * @see hudson.tasks.BuildStepCompatibilityLayer#perform(hudson.model.Build,
	 * hudson.Launcher, hudson.model.BuildListener)
	 */
	@Override
	public boolean perform(Build<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {
		return super.perform(build, launcher, listener);
	}
}
