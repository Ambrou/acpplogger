package org.labs.studioprive.acpplogger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

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
	
	@Extension
	public static final ACPPLoggerDescriptor DESCRIPTOR = new ACPPLoggerDescriptor();
	
	@DataBoundConstructor
	public ACPPLoggerRecorder(String fileNameToParse, String filesToExclude){
		this.fileNameToParse = fileNameToParse;
		this.filesToExclude = new ArrayList<String>();
		String [] strTemp = filesToExclude.split(";");
		for(int iLoop = 0; iLoop < strTemp.length; ++iLoop) {
			this.filesToExclude.add(strTemp[iLoop]);
		}
		this.setCoverageDataFiles(new ArrayList<ACPPLoggerCoverageDataFile>());
	}

	
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	@Override
	public boolean perform(AbstractBuild<?,?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
		splitMasterFileCoverage(build.getWorkspace() + "\\"  + getFileToParse());
		
		ACPPLoggerResult acppLoggerResult = new ACPPLoggerResult(build, getCoverageDataFiles());
		
		final ACPPLoggerAction action = new ACPPLoggerAction(build, acppLoggerResult);
		build.getActions().add(action);
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
		return true;
	}
	
	public void splitMasterFileCoverage(String fileToParse) throws FileNotFoundException{
		
		try{
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
				buff.close();
			}
		} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
		}
	}

	private Integer extractPercent(String line) {
		String[] element = line.split(" ");
		return Integer.parseInt(element[2].replaceAll("%",  ""));
	}

	private String extractName(String line) {
		String[] element = line.split(" ");
		return element[1];
	}

	private boolean isFileIncluded(String strName) {
		
		for(int iLoop = 0; iLoop < getFilesToExclude().size(); ++iLoop){
			System.out.println(getFilesToExclude().get(iLoop));
			if(getFilesToExclude().get(iLoop).compareTo(strName) == 0){
				return false;
			}
		}
		return true;
	}

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
		for(int iLoop = 0; iLoop < getFilesToExclude().size(); ++iLoop){
			if(iLoop != 0){
				str += ";";
			}
			str += getFilesToExclude().get(iLoop);
			
		}
		return str;
	}

	/**
	 * @return the getUnSelectedFilesList
	 */
	public List<String> getFilesToExclude() {
		return filesToExclude;
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

}
