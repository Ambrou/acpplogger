package org.labs.studioprive.acpplogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

import hudson.Extension;
import hudson.Launcher;
import hudson.model.Action;
import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;


public class ACPPLoggerRecorder extends Recorder{

	private final String	fileNameToParse;
	private final List<String>	filesToExclude;
	private List<ACPPLoggerCoverageDataFile> coverageDataFiles;
	//private ACPPLoggerLog acppLoggerLog;
	
	@Extension
	public static final ACPPLoggerDescriptor DESCRIPTOR = new ACPPLoggerDescriptor();
	
	@DataBoundConstructor
	public ACPPLoggerRecorder(String fileNameToParse, String filesToExclude){
		this.fileNameToParse = fileNameToParse;
		this.filesToExclude = new ArrayList<String>();
		if(filesToExclude != null) {
			String [] strTemp = filesToExclude.split(";");
			for(int iLoop = 0; iLoop < strTemp.length; ++iLoop) {
				this.filesToExclude.add(strTemp[iLoop].trim());
			}
		}
		
		this.setCoverageDataFiles(new ArrayList<ACPPLoggerCoverageDataFile>());
	}

	
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	@Override
	public boolean perform(AbstractBuild<?,?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
		
		
		
		//try
		{
		
		final ACPPLoggerLog acppLoggerLog = getAcppLoggerLogObject(listener);
		
		ACPPLoggerParser acppLoggerParser = new ACPPLoggerParser(getFileToParse(), filesToExclude); 
		
		
		acppLoggerLog.infoConsoleLogger("start processing with exlude files" + filesToExclude);
		acppLoggerLog.infoConsoleLogger("start parsing file " + build.getWorkspace() + "\\"  + getFileToParse());
		//splitMasterFileCoverage(build.getWorkspace() + "\\"  + getFileToParse(), acppLoggerLog);
		ACPPLoggerReport acppLoggerReport =	build.getWorkspace().act(acppLoggerParser);
		//setCoverageDataFiles(build.getWorkspace().act(acppLoggerParser));
		acppLoggerLog.infoConsoleLogger("parsing file" + getFileToParse() + " done" );
		
		ACPPLoggerResult acppLoggerResult = new ACPPLoggerResult(build, acppLoggerReport.getCoverage());
		
		System.out.println(acppLoggerResult.getListNameFile().size());
		//System.out.println(coverage.size());
		
		final ACPPLoggerAction action = new ACPPLoggerAction(build, acppLoggerResult);
		build.addAction(action);
		/*final ACPPLoggerResult result =	action.getResult();
		
		if (result == null) {
            //logger.println("AcppLogger: Could not parse coverage results. Setting Build to failure.");
            build.setResult(Result.FAILURE);
        } else if (result.isFailed()) {
            //logger.println("AcppLogger: code coverage enforcement failed. Setting Build to unstable.");
            build.setResult(Result.UNSTABLE);
        }*/
		// Always exit on success (returned code and status)
		build.setResult(Result.SUCCESS);
		}
		//catch(IOException e)
		{
			
		}
		return true;
	}
	
	/*public void splitMasterFileCoverage(String fileToParse, ACPPLoggerLog acppLoggerLog) throws FileNotFoundException{
		
		try{
			if(acppLoggerLog != null) acppLoggerLog.infoConsoleLogger("open file " + fileToParse);
			BufferedReader buff = new BufferedReader(new FileReader(fileToParse));

			try {
				String line;
				boolean firstFile = true;
				String name = null;
				Integer percent = null;
				List<String> Lines = new ArrayList<String>();
				while ((line = buff.readLine()) != null) {

					if(line.startsWith("FILE ") == true){
						
						if(firstFile == false){
							if(isFileIncluded(name) == true) {
								if(acppLoggerLog != null) acppLoggerLog.infoConsoleLogger("add file " + name);
								getCoverageDataFiles().add(new ACPPLoggerCoverageDataFile(
													name, 
													percent, 
													Lines));
							}
						}
						firstFile = false;
						name = extractName(line);
						percent = extractPercent(line);
						Lines = new ArrayList<String>();
					}
					else
					{
						Lines.add(line);
					}
				}
				if(isFileIncluded(name) == true) {
					getCoverageDataFiles().add(new ACPPLoggerCoverageDataFile(
										name, 
										percent, 
										Lines));
				}
			} finally {
				if(acppLoggerLog != null) acppLoggerLog.infoConsoleLogger("close file " + fileToParse);
				buff.close();
			}
		} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
			if(acppLoggerLog != null) acppLoggerLog.errorConsoleLogger("Erreur --" + ioe.toString());
		}
	}*/

	
	

	/**
	 * @return the fileToParse
	 */
	public String getFileToParse() {
		return fileNameToParse;
	}


	/**
	 * @return the unselectedFilesList
	 */
	public String getFilesToExcludeString() {
		String str = new String("");
		for(int iLoop = 0; iLoop < filesToExclude.size(); ++iLoop){
			if(iLoop != 0){
				str += ";";
			}
			str += filesToExclude.get(iLoop);
			
		}
		if(str.length() == 0)
		{
			str = "__empty__";
		}
		return str;
	}

	
	/**
	 * @return the coverageDataFiles
	 */
	public List<ACPPLoggerCoverageDataFile> getCoverageDataFiles() {
		return coverageDataFiles;
	}

	/**
	 * @param coverageDataFiles the coverageDataFiles to set
	 */
	public void setCoverageDataFiles(List<ACPPLoggerCoverageDataFile> coverageDataFiles) {
		this.coverageDataFiles = coverageDataFiles;
	}
	
	@Override
    public Action getProjectAction(AbstractProject<?, ?> project) {
        return new ACPPLoggerProjectAction(project);
    }
	
	private ACPPLoggerLog getAcppLoggerLogObject(final BuildListener listener) {
        return Guice.createInjector(new AbstractModule() {
            @Override	
            protected void configure() {
                bind(BuildListener.class).toInstance(listener);
            }
        }).getInstance(ACPPLoggerLog.class);
    }

}
