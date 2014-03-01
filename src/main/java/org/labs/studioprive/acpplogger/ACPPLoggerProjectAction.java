package org.labs.studioprive.acpplogger;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.ProminentProjectAction;
import hudson.model.Result;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;

public class ACPPLoggerProjectAction implements ProminentProjectAction{

	private final AbstractProject<?, ?> project;
	
	public ACPPLoggerProjectAction(AbstractProject<?, ?> project) {
		this.project = project;
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
	
	public void doIndex(StaplerRequest req, StaplerResponse rsp) throws IOException {
        Integer buildNumber = getLastResultBuild();
        if (buildNumber == null) {
            rsp.sendRedirect2("nodata");
        } else {
            rsp.sendRedirect2("../" + buildNumber + "/" + getUrlName());
        }
    }
	
	public Integer getLastResultBuild() {
        for (AbstractBuild<?, ?> b = (AbstractBuild<?, ?>) project.getLastBuild(); b != null; b = b.getPreviousNotFailedBuild()) {
            if (b.getResult() == Result.FAILURE)
                continue;
            ACPPLoggerAction r = b.getAction(ACPPLoggerAction.class);
            if (r != null)
                return b.getNumber();
        }
        return null;
    }

}
