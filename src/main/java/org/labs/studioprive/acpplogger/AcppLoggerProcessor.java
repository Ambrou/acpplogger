/**
 * 
 */
package org.labs.studioprive.acpplogger;

import hudson.model.BuildListener;
import hudson.model.Result;
import hudson.model.AbstractBuild;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.labs.studioprive.acpplogger.service.AcppLoggerLog;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;

/**
 * @author kdelfour
 * 
 */
public class AcppLoggerProcessor {

	private final String strFileToParse;
	
	private List<AcppLoggerContainer> mListNameFile;
	
	public AcppLoggerProcessor(String strFileToParse)
	{
		this.strFileToParse = strFileToParse;
		mListNameFile = new ArrayList<AcppLoggerContainer>();	
	}
	
	
	public boolean performParseLog(AbstractBuild<?, ?> build, BuildListener listener) {
		
		final AcppLoggerLog acppLoggerLog = getAcppLoggerLogObject(listener);
		FileWriter writer = null;
		BufferedReader buff = null;
		String strCoverted = "";
		
		try{
			int iLine = 0;
			acppLoggerLog.infoConsoleLogger("start parsing file");
			//writer = new FileWriter(build.getWorkspace() + "\\ocR.html", true);
			buff = new BufferedReader(new FileReader("C:\\Ambroise\\developpement\\Eclipse\\ocR.coverage"));
			//buff = new BufferedReader(new FileReader(strFileToParse));
			String line;
			//accpLoggerLog.infoConsoleLogger("Read file");
			//acppLoggerLog.infoConsoleLogger("Write file" + build.getWorkspace() + "\\ocR.html");
			while ((line = buff.readLine()) != null) {
				if(line.startsWith("FILE ") == true){
					if(writer != null){
						writer.write("</table>");
						writer.close();
						iLine = 0;
						strCoverted = "";
					}
					String [] strTemp = line.split(" ");
					AcppLoggerContainer acppLoggerContainer = new AcppLoggerContainer(strTemp[1], strTemp[2]);
					mListNameFile.add(acppLoggerContainer);
					writer = new FileWriter(build.getWorkspace() + "\\" + strTemp[1] + ".html", true);
					acppLoggerLog.infoConsoleLogger("Write file" + build.getWorkspace() + "\\" + strTemp[1] + ".html");
					line = buff.readLine();
					line = buff.readLine();
					writer.write("<table>");
				}
				if(writer != null){
					writer.write("<tr>\n");
					writer.write("<td>" + Integer.toString(iLine) + "</td>");
					// Coverted
					if(line.contains(". ->") == true){
						strCoverted = "Coverted";
					}
					// Not Coverted
					else if(line.contains("! ->") == true){
						strCoverted = "Not coverted";
					}
					writer.write("<td>"  + strCoverted + "</td>\n");
					writer.write("<td>"  + line + "</td>\n");
					writer.write("</tr>\n");
					iLine++;
				}
			}
		}
		catch (IOException ioe) {
			System.out.println("Erreur --" + ioe.toString());
			acppLoggerLog.errorConsoleLogger(ioe.toString());
		}
		finally {
			try {
				if(buff != null) {
					buff.close();
				}
				if(writer != null){
					writer.close();
				} 
			}
			catch (IOException ioe) {
				acppLoggerLog.errorConsoleLogger(ioe.toString());
			}
		}
		acppLoggerLog.infoConsoleLogger("stop parsing file");
		return true;
	}

	private AcppLoggerLog getAcppLoggerLogObject(final BuildListener listener) {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(BuildListener.class).toInstance(listener);
            }
        }).getInstance(AcppLoggerLog.class);
    }


	public List<AcppLoggerContainer> getResult() {
		return mListNameFile;
	}

}
