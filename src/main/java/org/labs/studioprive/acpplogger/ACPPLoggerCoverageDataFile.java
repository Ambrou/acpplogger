package org.labs.studioprive.acpplogger;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ACPPLoggerCoverageDataFile  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String fileName;
	private final Integer coveragePercent;
	private final List<String> linesFile;
	
	public ACPPLoggerCoverageDataFile(String fileName, Integer coveragePercent){
		this.fileName = fileName;
		this.coveragePercent = coveragePercent;
		this.linesFile = new ArrayList<String>();
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
	
	public void addLine(String line) {
		linesFile.add(line);
	}

}
