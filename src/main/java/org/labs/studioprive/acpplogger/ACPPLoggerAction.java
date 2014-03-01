package org.labs.studioprive.acpplogger;

import java.util.List;

import org.kohsuke.stapler.StaplerProxy;

import hudson.model.HealthReport;
import hudson.model.HealthReportingAction;
import hudson.model.AbstractBuild;

public class ACPPLoggerAction implements HealthReportingAction, StaplerProxy {
	
	private final AbstractBuild<?,?> build;
	private ACPPLoggerResult acppLoggerResult;
	
	
	public ACPPLoggerAction(AbstractBuild<?, ?> build, ACPPLoggerResult acppLoggerResult){
		this.build = build;
		//this.coverageDataFiles = coverageDataFiles;
		this.acppLoggerResult = acppLoggerResult; 
	}



	public String getIconFileName() {
		return "graph.gif";
	}

	public String getDisplayName() {
		return "ACPPLogger Result";
	}

	public String getUrlName() {
		return "ACPPLoggerResult";
	}
	
	public ACPPLoggerResult getResult() {
		return acppLoggerResult;
	}
	
	AbstractBuild<?, ?> getBuild() {
        return build;
    }

	public Object getTarget() {
		return getResult();
	}
	
	public HealthReport getBuildHealth() {
		return new HealthReport(100, "green_anime.gif");
	}

	

	

}
