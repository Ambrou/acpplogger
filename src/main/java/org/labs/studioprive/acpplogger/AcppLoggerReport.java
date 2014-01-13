package org.labs.studioprive.acpplogger;

import hudson.model.AbstractBuild;
import hudson.util.IOException2;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public final class AcppLoggerReport extends AcppLoggerObject<AcppLoggerReport> {
	private final AcppLoggerBuildAction action;
	
	private String name;
	private List<AcppLoggerContainer> mListNameFile;
	
	public List<AcppLoggerContainer> getListNameFile(){
		System.out.println("On demande le Get");
		return mListNameFile;
	}
	
	public AcppLoggerReport(AcppLoggerBuildAction action) {
        this.action = action;
//		this.file = action.statement;
//		this.Percent = action.branch;
        this.mListNameFile = action.getResultList();
        
        for(int i = 0; i < mListNameFile.size(); ++i)
        {
        	System.out.println(mListNameFile.get(i).getFile() + " " + mListNameFile.get(i).getPercent());
        }
        setName("AcppLogger");
    }
	
	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return name;
    }
    
    @Override
    public AcppLoggerReport getPreviousResult() {
    	AcppLoggerBuildAction prev = action.getPreviousResult();
        if(prev!=null)
            return prev.getResult();
        else
            return null;
    }

    @Override
    public AbstractBuild<?,?> getBuild() {
        return action.owner;
    }

}
