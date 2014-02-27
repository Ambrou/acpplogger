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
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.tasks.BuildStepMonitor;
import hudson.tasks.Recorder;


public class ACPPLoggerRecorder extends Recorder{

	private final String	fileNameToParse;
	private final String[]	unselectedFilesList;
	private List<ACPPLoggerCoverageDataFile> coverageDataFiles;
	
	@Extension
	public static final ACPPLoggerDescriptor DESCRIPTOR = new ACPPLoggerDescriptor();
	
	@DataBoundConstructor
	public ACPPLoggerRecorder(String fileNameToParse, String unselectedFilesList){
		this.fileNameToParse = fileNameToParse;
		this.unselectedFilesList = unselectedFilesList.split(";");
		this.setCoverageDataFiles(new ArrayList<ACPPLoggerCoverageDataFile>());
	}
	
	public BuildStepMonitor getRequiredMonitorService() {
		return BuildStepMonitor.BUILD;
	}
	
	@Override
	public boolean perform(AbstractBuild<?,?> build, Launcher launcher, BuildListener listener) throws InterruptedException, IOException {
		perform(build.getWorkspace() + "\\"  + getFileToParse());
		return true;
	}
	
	public void perform(String fileToParse) throws FileNotFoundException{
		
		
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
								coverageDataFiles.add(new ACPPLoggerCoverageDataFile(
													name, 
													percent, 
													Lines));
							}
						}
						firstFile = false;
						name = getName(line);
						percent = getPercent(line);
						Lines = new ArrayList<String>();
					}
					else
					{
						Lines.add(line);
					}
				}
				if(isFileIncluded(name) == true) {
					coverageDataFiles.add(new ACPPLoggerCoverageDataFile(
										name, 
										percent, 
										Lines));
					//System.out.println(name);
				}
			} finally {
				buff.close();
			}
		} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
		}
	}

	private Integer getPercent(String line) {
		String[] element = line.split(" ");
		return Integer.parseInt(element[2].replaceAll("%",  ""));
	}

	private String getName(String line) {
		String[] element = line.split(" ");
		return element[1];
	}

	private boolean isFileIncluded(String strName) {
		int iLoop = 0;
		boolean fileIncluded = true;
		for(iLoop = 0; (iLoop < getUnselectedFilesList().length) && (fileIncluded == true); ++iLoop){
			if(getUnselectedFilesList()[iLoop].compareTo(strName) == 0){
				fileIncluded = false;
			}
		}
		return fileIncluded;
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
	public String[] getUnselectedFilesList() {
		return unselectedFilesList;
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
