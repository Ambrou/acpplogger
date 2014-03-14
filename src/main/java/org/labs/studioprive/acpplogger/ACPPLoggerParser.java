package org.labs.studioprive.acpplogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;

import hudson.FilePath;
import hudson.Util;
import hudson.remoting.VirtualChannel;

public class ACPPLoggerParser implements FilePath.FileCallable<ACPPLoggerReport> {

	//private final ACPPLoggerLog acppLoggerLog;
	private final String fileToParse;
	private final List<String> filesToExclude;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ACPPLoggerParser(String fileToParse, List<String> filesToExclude) {
		//this.acppLoggerLog = acppLoggerLog;
		this.fileToParse = fileToParse;
		this.filesToExclude = filesToExclude;
		
	}

	public ACPPLoggerReport invoke(File file, VirtualChannel channel)
			throws IOException {
	
		ACPPLoggerReport acppLoggerReport = new ACPPLoggerReport();
		File f = new File(file, fileToParse);
		//File f2 = new File(file, "test.txt");
		if(f.getAbsoluteFile().exists()){
	//		FileOutputStream fou = new FileOutputStream(f2);
			//try {
	//			fou.write(41);
	//			fou.close();
			//} catch (IOException e) {

			//	e.printStackTrace();
		//	}
		//	splitMasterFileCoverage(f.getAbsoluteFile().toString());
			splitMasterFileCoverage(file, acppLoggerReport);
			
			//return "TRouve" + f.getAbsoluteFile();
		}
		return acppLoggerReport;
	}
	
	public void splitMasterFileCoverage(File file, ACPPLoggerReport acppLoggerReport) throws FileNotFoundException{
		
		
		
		File f1 = new File(file, fileToParse);
		//File f2 = new File(file, "test.txt");
		//FileOutputStream fou = new FileOutputStream(f2);
	
		
		try {
		
			
			InputStream fis = new FileInputStream(f1);
			InputStreamReader ipsr=new InputStreamReader(fis);
			BufferedReader br = new BufferedReader(ipsr);
			String line;
			//ACPPLoggerCoverageDataFile acppLoggerCoverageFile = null;
			
			while((line=br.readLine())!=null){
				if(line.startsWith("FILE ") == true){
				//	if(acppLoggerCoverageFile != null) {
					//	fou.write(" ajouté".getBytes());
						//fou.write('\n');
						//coverageDataFiles.add(acppLoggerCoverageFile);
						
					//}
					
					String name = extractName(line);
					Integer percent = extractPercent(line);
					if(isFileIncluded(name) == true) {
						acppLoggerReport.createFile(name, percent);
					}
					else{
						acppLoggerReport.notAdd();
					}
					//acppLoggerCoverageFile = new ACPPLoggerCoverageDataFile(name, percent);
					//fou.write(name.getBytes());
					
				} else {
					//if(acppLoggerCoverageFile != null) {
					//	acppLoggerCoverageFile.addLine(line);
						//acppLoggerReport.addLine(line);
					//}
					acppLoggerReport.addLine(line);
				}
			}
			//if(acppLoggerCoverageFile != null) {
				//fou.write(" ajouté".getBytes());
				//fou.write('\n');
				//coverageDataFiles.add(acppLoggerCoverageFile);
			//}
			acppLoggerReport.add();
			//fou.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
		
		/*try{
			//if(acppLoggerLog != null) acppLoggerLog.infoConsoleLogger("open file " + fileToParse);
			BufferedReader buff = new BufferedReader(new FileReader(fileToParse));
			System.out.println("Fichier ouvert");
			try {
				String line;
				boolean firstFile = true;
				String name = null;
				Integer percent = null;
				List<String> Lines = new ArrayList<String>();
				while ((line = buff.readLine()) != null) {
					System.out.println("Lecture " + line);
					if(line.startsWith("FILE ") == true){
						
						if(firstFile == false){
					//		if(isFileIncluded(name) == true) {
							//	if(acppLoggerLog != null) acppLoggerLog.infoConsoleLogger("add file " + name);
							coverageDataFiles.add(new ACPPLoggerCoverageDataFile(
													name, 
													percent, 
													Lines));
							System.out.println(name + " " + percent);
						//	}
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
				//if(isFileIncluded(name) == true) {
					coverageDataFiles.add(new ACPPLoggerCoverageDataFile(
										name, 
										percent, 
										Lines));
				//}
			} finally {
			//	if(acppLoggerLog != null) acppLoggerLog.infoConsoleLogger("close file " + fileToParse);
				buff.close();
				System.out.println("Fichier fermé");
			}
		} catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
			//if(acppLoggerLog != null) acppLoggerLog.errorConsoleLogger("Erreur --" + ioe.toString());
		}
	}

	/*
	private String findACCPLoggerReport(File arg0) {
		FileSet fs = Util.createFileSet(arg0, fileToParse);
		DirectoryScanner ds = fs.getDirectoryScanner();
		String[] str = ds.getIncludedFiles();
		if(str.length == 0){
			acppLoggerLog.errorConsoleLogger("No Files");
		}
		return str[0];
	}
*/
	private boolean isFileIncluded(String strName) {
		
		for(int iLoop = 0; iLoop < getFilesToExclude().size(); ++iLoop){
			if(getFilesToExclude().get(iLoop).trim().compareTo(strName) == 0){
				return false;
			}
		}
		return true;
	}
	
	
	private List<String> getFilesToExclude() {
		return filesToExclude;
	}

	private Integer extractPercent(String line) {
		String[] element = line.split(" ");
		return Integer.parseInt(element[2].replaceAll("%",  ""));
	}

	private String extractName(String line) {
		String[] element = line.split(" ");
		return element[1];
	}

	

}
