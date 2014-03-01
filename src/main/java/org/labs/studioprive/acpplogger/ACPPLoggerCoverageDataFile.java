package org.labs.studioprive.acpplogger;

import java.util.List;

public class ACPPLoggerCoverageDataFile {
	private final String fileName;
	private final Integer coveragePercent;
	private final List<String> linesFile;
	
	public ACPPLoggerCoverageDataFile(String fileName, Integer coveragePercent, List<String>linesFile){
		this.fileName = fileName;
		this.coveragePercent = coveragePercent;
		this.linesFile = linesFile;
	}

	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @return the coveragePercent
	 */
	public Integer getCoveragePercent() {
		return coveragePercent;
	}
	
	/**
	 * @return the percent
	 */
	public String getPercent() {
		return getCoveragePercent().toString() + "%";
	}

	/**
	 * @return the linesFile
	 */
	public List<String> getLinesFile() {
		return linesFile;
	}

}
