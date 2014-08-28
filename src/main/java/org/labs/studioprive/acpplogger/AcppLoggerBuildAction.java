package org.labs.studioprive.acpplogger;

import java.lang.ref.WeakReference;

import hudson.model.HealthReport;
import hudson.model.HealthReportingAction;
import hudson.model.Result;
import hudson.model.AbstractBuild;

import java.util.List;
import java.util.logging.Logger;

import org.kohsuke.stapler.StaplerProxy;




public final class AcppLoggerBuildAction extends AcppLoggerObject<AcppLoggerBuildAction> implements HealthReportingAction, StaplerProxy{

	public final AbstractBuild<?,?> owner;
	
	private transient WeakReference<AcppLoggerReport> report;
	private List<AcppLoggerContainer> mListNameFile;
	
	public AcppLoggerBuildAction(AbstractBuild<?,?> owner, List<AcppLoggerContainer>listNameFile){
		this.owner = owner;
		this.mListNameFile = listNameFile;
	}
	@Override
	public String getDisplayName() {
		return "AcppLogger Report";
	}

	@Override
	public String getIconFileName() {
		return "graph.gif";
	}

	@Override
	public String getUrlName() {
		return "AcppLogger";
	}

	@Override
	public HealthReport getBuildHealth() {
		return new HealthReport(100, "Test");
	}
	
	public Object getTarget() {
        return getResult();
    }
	
	public List<AcppLoggerContainer> getResultList(){
		return mListNameFile;
	}

    @Override
	 public AbstractBuild<?,?> getBuild() {
		 return owner;
	 }
	 
	 /**
     * Obtains the detailed {@link CoverageReport} instance.
     */
    public synchronized AcppLoggerReport getResult() {

        if(report!=null) {
            final AcppLoggerReport r = report.get();
            if(r!=null)     return r;
        }

		// Generate the report
        AcppLoggerReport r = new AcppLoggerReport(this);

		report = new WeakReference<AcppLoggerReport>(r);
		return r;
    }
	@Override
	public AcppLoggerBuildAction getPreviousResult() {
		return getPreviousResult(owner);
	}
	
	/**
     * Gets the previous {@link AcppLoggerBuildAction} of the given build.
     */
    /*package*/ static AcppLoggerBuildAction getPreviousResult(AbstractBuild<?,?> start) {
        AbstractBuild<?,?> b = start;
        while(true) {
            b = b.getPreviousBuild();
            if(b==null)
                return null;
            if(b.getResult()== Result.FAILURE)
                continue;
            AcppLoggerBuildAction r = b.getAction(AcppLoggerBuildAction.class);
            if(r!=null)
                return r;
        }
    }
    
    private static final Logger logger = Logger.getLogger(AcppLoggerBuildAction.class.getName());
}
