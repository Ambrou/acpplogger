package org.labs.studioprive.acpplogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import hudson.FilePath;
import hudson.model.AbstractBuild;

public class AcppLoggerSource {
	
	private final AbstractBuild<?, ?> owner;
	private final String  link;
	private String sourceCode = StringUtils.EMPTY;
	
	
	public AcppLoggerSource(final AbstractBuild<?, ?> owner, String link) {
        this.owner = owner;
        this.link = link;
        System.out.println(link);
        buildFileContent();
        
        
    }
	
	 private void buildFileContent() {
		 InputStream is = null;
	        try {
	        	FilePath acppLoggerFolder = new FilePath(new File(owner.getRootDir(), "acpplogger"));
	            File tempFile = new File(acppLoggerFolder + "\\" + link);
	            System.out.println(acppLoggerFolder + "\\" + link);
	            if (tempFile.exists()) {
	                is = new FileInputStream(tempFile);
	            } else {

	                if (link == null) {
	                    throw new IOException("The file doesn't exist.");
	                }
	            }

	            splitSourceFile(acppLoggerFolder + "\\" + link);
	        } catch (IOException exception) {
	            sourceCode = "Can't read file: " + exception.getLocalizedMessage();
	        } catch (RuntimeException re) {
	            sourceCode = "Problem for display the source code content: " + re.getLocalizedMessage();
	        } finally {
	            IOUtils.closeQuietly(is);
	        }
		
	}

	private void splitSourceFile(String link) {
		BufferedReader buff = null;
		try {
			
			StringBuilder output = new StringBuilder();
			
			buff = new BufferedReader(new FileReader(link));
			System.out.println("Ouverture " + link);
			
			
			String line;
			System.out.println("LEcture ligne");
			while ((line = buff.readLine()) != null) {
				System.out.println(line);
				output.append(line);
				output.append("\n");
			}
			sourceCode = output.toString();
			System.out.println("Fin de lecture");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		 finally {
			 try {
				if(buff != null) {
					buff.close();
				}
				
			}
			catch (IOException e) {
				e.printStackTrace();
			}
	    }
		
	}

	public String getSourceCode() {
	        return sourceCode;
	    }
	public String getFileName() {
        return this.link;
    }

}
