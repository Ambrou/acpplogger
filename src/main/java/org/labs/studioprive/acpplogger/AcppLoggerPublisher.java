/**
 * 
 */
package org.labs.studioprive.acpplogger;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Publisher;
import hudson.tasks.Recorder;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

import net.sf.json.JSONObject;

import org.jenkinsci.lib.dryrun.DryRun;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.StaplerRequest;
import org.labs.studioprive.acpplogger.service.AcppLoggerLog;

/**
 * @author kdelfour
 * 
 */
public class AcppLoggerPublisher extends Recorder implements DryRun,
		Serializable {

	public static String DISPLAY_NAME = "A cpp Logger for insure++";

	private String fileToParse;

	// Fields in config.jelly must match the parameter names in the
	// "DataBoundConstructor"
	@DataBoundConstructor
	public AcppLoggerPublisher() {

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
			listener.getLogger().println(
					"PUBLISH: we will parse this file " + fileToParse);
			//AcppLoggerProcessor.performParseLog(getFileToParse());
			AcppLoggerProcessor acppLoggerProcessor = new AcppLoggerProcessor(getFileToParse());
			acppLoggerProcessor.performParseLog(build, listener);
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
	public boolean perform(AbstractBuild build, Launcher launcher,
			BuildListener listener) throws InterruptedException, IOException {
		
		final PrintStream logger = listener.getLogger();
		FilePath[] reports;
		AcppLoggerProcessor acppLoggerProcessor = new AcppLoggerProcessor(getFileToParse());
		acppLoggerProcessor.performParseLog(build, listener);
		logger.println(build.getWorkspace());
		reports = locateCoverageReports(build.getWorkspace(), "");
		if (reports.length == 0) {
			logger.println("AcppLogger: no coverage files found in workspace. Was any report generated?");
            build.setResult(Result.FAILURE);
            return true;
		} else {
			String found = "";
        	for (FilePath f: reports) 
        		found += "\n          " + f.getRemote();
            logger.println("AcppLogger: found " + reports.length  + " report files: " + found );
		}
		FilePath acppLoggerFolder = new FilePath(getAcppLoggerReport(build));
		saveAcppLoggerReports(acppLoggerFolder, reports);
		logger.println("AcppLogger: stored " + reports.length + " report files in the build folder: "+ acppLoggerFolder);
		
		final AcppLoggerBuildAction action = new AcppLoggerBuildAction(build, acppLoggerProcessor.getResult());
		logger.println("AcppLogger: " + action.getBuildHealth().getDescription());
		
		build.getActions().add(action);
		
		final AcppLoggerReport result = action.getResult();
		
		if (result == null) {
            logger.println("AcppLogger: Could not parse coverage results. Setting Build to failure.");
            build.setResult(Result.FAILURE);
        } else if (result.isFailed()) {
            logger.println("AcppLogger: code coverage enforcement failed. Setting Build to unstable.");
            build.setResult(Result.UNSTABLE);
        }
		// Always exit on success (returned code and status)
		build.setResult(Result.SUCCESS);
		return true;
	}
	
	protected static FilePath[] locateCoverageReports(FilePath workspace, String includes) throws IOException, InterruptedException {
		try {
        	FilePath[] ret = workspace.list(includes);
            if (ret.length > 0) { 
            	return ret;
            }
        } catch (Exception e) {
        }
		
		// If it fails, do a legacy search
        ArrayList<FilePath> files = new ArrayList<FilePath>();
		String parts[] = includes.split("\\s*[;:,]+\\s*");
		for (String path : parts) {
			System.out.println("Search in " + workspace.child(path));
			FilePath src = workspace.child(path);
			if (src.exists()) {
				if (src.isDirectory()) {
					files.addAll(Arrays.asList(src.list("**/*.html")));
				} else {
					files.add(src);
				}
			}
		}
		return files.toArray(new FilePath[files.size()]);
	}
	
	/**
     * Gets the directory to store report files
     */
    static File getAcppLoggerReport(AbstractBuild<?,?> build) {
        return new File(build.getRootDir(), "acpplogger");
    }
    
    protected static void saveAcppLoggerReports(FilePath folder, FilePath[] files) throws IOException, InterruptedException {
		folder.mkdirs();
		for (int i = 0; i < files.length; i++) {
			//String name = "coverage" + (i > 0 ? i : "") + ".html";
			FilePath src = files[i];
			FilePath dst = folder.child(files[i].getName());
			src.copyTo(dst);
		}
	}
    
    protected static void saveCoverageReports(FilePath folder, String[] files, BuildListener listener) throws IOException, InterruptedException {
		folder.mkdirs();
		for (int i = 0; i < files.length; i++) {
			String name = folder + files[i] + ".txt";
			File temp = new File(name);
			listener.getLogger().println(
					"Creation du fichier " + temp.getAbsolutePath() + temp.getName());
			temp.createNewFile();
			
		}
	}
    
    @Extension
    public static final BuildStepDescriptor<Publisher> DESCRIPTOR = new DescriptorImpl();

	@Extension
	public static class DescriptorImpl extends
			BuildStepDescriptor<Publisher> {
		
		public DescriptorImpl() {
            super(AcppLoggerPublisher.class);
        }
		
		@Override
		public String getDisplayName() {
			return "Publish " + AcppLoggerPublisher.DISPLAY_NAME;
		}

		@Override
		public boolean isApplicable(Class<? extends AbstractProject> aClass) {
			return true;
		}
		
		@Override
        public Publisher newInstance(StaplerRequest req, JSONObject json) throws FormException {
			AcppLoggerPublisher pub = new AcppLoggerPublisher();
			pub.fileToParse = new String("fileToParse.coverage");
 //           req.bindParameters(pub, "codecover.");
  //          req.bindParameters(pub.fileToParse, "codecoverHealthReports.");
//			//set max defaults
//            if ("".equals(req.getParameter("codecoverHealthReports.maxStatement"))) {
//                pub.healthReports.setMaxStatement(90);
//            }
//            if ("".equals(req.getParameter("codecoverHealthReports.maxBranch"))) {
//                pub.healthReports.setMaxBranch(80);
//            }
//            if ("".equals(req.getParameter("codecoverHealthReports.maxLoop"))) {
//                pub.healthReports.setMaxLoop(50);
//            }
//            if ("".equals(req.getParameter("codecoverHealthReports.maxCondition"))) {
//                pub.healthReports.setMaxCondition(50);
//            }
            // end ugly hack
            return pub;
        }
	}

}
