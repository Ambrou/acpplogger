package org.labs.studioprive.acpplogger;

import hudson.model.AbstractProject;
import hudson.tasks.BuildStepDescriptor;
import hudson.tasks.Publisher;


public class ACPPLoggerDescriptor  extends BuildStepDescriptor<Publisher> {

	public ACPPLoggerDescriptor(){
        super(ACPPLoggerRecorder.class);
    }
	
	@Override
	@SuppressWarnings("rawtypes")
	public boolean isApplicable( Class<? extends AbstractProject> jobType) {
		// plug in always applicable for all project type
		return true;
	}

	@Override
	public String getDisplayName() {
		// name's plug in
		return "A Cpp Logger for insure++";
	}

}
