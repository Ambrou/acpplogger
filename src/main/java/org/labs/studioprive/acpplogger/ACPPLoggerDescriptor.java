package org.labs.studioprive.acpplogger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.kohsuke.stapler.StaplerRequest;

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
	
	
	@Override
    public Publisher newInstance(StaplerRequest req, JSONObject json) throws FormException {
		
		JSONObject tmp = json.getJSONObject("dynamic");
		String fileName = json.getString("fileNameToParse");
		String unSelectedFiles  = tmp.getString("unSelectedFiles");

		ACPPLoggerRecorder acppLoggerRecorder = new ACPPLoggerRecorder(fileName, unSelectedFiles);

        // end ugly hack
        return acppLoggerRecorder;
    }

}
