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
	
	private final String fileToParse;
	
	// Fields in config.jelly must match the parameter names in the "DataBoundConstructor"
    @DataBoundConstructor
    public AcppLoggerPublisher(String fileToParse) {
        this.fileToParse = fileToParse;
    }
    
    /**
     * We'll use this from the <tt>config.jelly</tt>.
     */
    public String getFileToParse() {
    	return fileToParse;
    }

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
			listener.getLogger().println("PUBLISH: we will parse this file " + fileToParse);
			AcppLoggerProcessor.performParseLog(getFileToParse());
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
	
	@Override
    public boolean perform(AbstractBuild build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
		try {
			listener.getLogger().println("PUBLISH: we will parse this file " + fileToParse);
			AcppLoggerProcessor.performParseLog(getFileToParse());
		} catch (Throwable t) {
			listener.getLogger()
					.println(
							"[ERROR] - During parsing this file " + getFileToParse() + ".There is an error: "
									+ t.getCause().getMessage());
		}
		// Always exit on success (returned code and status)
		build.setResult(Result.SUCCESS);
		return true;
	}
	
	
}
