package org.labs.studioprive.acpplogger;

import java.text.DecimalFormat;

public class AcppLoggerContainer {
	
	public AcppLoggerContainer(String strFile, String strPercent){
		this.strFile = strFile;
		this.strPercent = strPercent;
	}
	
	public String getFile(){
		System.out.println("On demande le Get de " + strFile);
		return strFile;
	}
	
	public String getPercent(){
		System.out.println("On demande le Get de " + strPercent);
		return strPercent;
	}
	
	private String strFile = null;
	private String strPercent = null;
}
