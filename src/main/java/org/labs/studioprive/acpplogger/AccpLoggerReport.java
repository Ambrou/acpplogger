	package org.labs.studioprive.acpplogger;

import hudson.model.AbstractBuild;

public final class AccpLoggerReport extends AcppLoggerObject<AccpLoggerReport> {
	private final AcppLoggerBuildAction action;
	
	private String name;
	
	public AccpLoggerReport(AcppLoggerBuildAction action) {
        this.action = action;
//		this.statement = action.statement;
//		this.branch = action.branch;
//		this.loop = action.loop;
//		this.condition = action.condition;
		
        setName("AccpLogger");
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
    public AccpLoggerReport getPreviousResult() {
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
