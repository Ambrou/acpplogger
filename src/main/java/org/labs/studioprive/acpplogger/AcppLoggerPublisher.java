/**
 * 
 */
package org.labs.studioprive.acpplogger;

import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;

import java.io.IOException;
import java.io.Serializable;

import org.jenkinsci.lib.dryrun.DryRun;

/**
 * @author kdelfour
 * 
 */
public class AcppLoggerPublisher extends Recorder implements DryRun,
		Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see hudson.tasks.BuildStep#getRequiredMonitorService()
	 */
	@Override
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.NONE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.jenkinsci.lib.dryrun.DryRun#performDryRun(hudson.model.AbstractBuild,
	 * hudson.Launcher, hudson.model.BuildListener)
	 */
	@Override
	public boolean performDryRun(AbstractBuild<?, ?> build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {
		try {
			AcppLoggerProcessor.performParseLog();
		} catch (Throwable t) {
			listener.getLogger()
					.println(
							"[ERROR] - There is an error: "
									+ t.getCause().getMessage());
		}
		// Always exit on success (returned code and status)
		build.setResult(Result.SUCCESS);
		return true;
	}
}
