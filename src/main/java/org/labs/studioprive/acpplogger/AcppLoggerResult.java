package org.labs.studioprive.acpplogger;

import java.io.IOException;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

import hudson.model.Item;
import hudson.model.AbstractBuild;
import hudson.model.Api;

public class AcppLoggerResult {
	
	/**
     * The Cppcheck report
     */
    private AcppLoggerReport report;

    /**
     * The Cppcheck container with all source files
     */
    //private CppcheckSourceContainer cppcheckSourceContainer;

    /**
     * The build owner
     */
    private AbstractBuild<?, ?> owner;
    
	 public AcppLoggerResult(AcppLoggerReport report, AbstractBuild<?, ?> owner) {
	        this.report = report;
	        //this.cppcheckSourceContainer = cppcheckSourceContainer;
	        this.owner = owner;   
	 }
	 
	 /**
	     * Gets the remote API for the build result.
	     *
	     * @return the remote API
	     */
	    public Api getApi() {
	        return new Api(report);
	    }
	 
	 /**
	     * Gets the dynamic result of the selection element.
	     *
	     * @param link     the link to identify the sub page to show
	     * @param request  Stapler request
	     * @param response Stapler response
	     * @return the dynamic result of the analysis (detail page).
	     * @throws java.io.IOException if an error occurs
	     */
	    public Object getDynamic(final String link, final StaplerRequest request, final StaplerResponse response) throws IOException {

//	        if (link.startsWith("source.")) {
//
//	            if (!owner.getProject().getACL().hasPermission(Item.WORKSPACE)) {
//	                response.sendRedirect2("nosourcepermission");
//	                return null;
//	            }
//
//	            Map<Integer, CppcheckWorkspaceFile> agregateMap = cppcheckSourceContainer.getInternalMap();
//	            if (agregateMap != null) {
//	                CppcheckWorkspaceFile vCppcheckWorkspaceFile = agregateMap.get(Integer.parseInt(StringUtils.substringAfter(link, "source.")));
//	                if (vCppcheckWorkspaceFile == null) {
//	                    throw new IllegalArgumentException("Error for retrieving the source file with link:" + link);
//	                }
//	                return new 	(owner, vCppcheckWorkspaceFile);
//	            }
//	        }
	    	System.out.println(" AcppLoggerResult " + link);
	    	if(link.endsWith(".html")){
	    		//Map<Integer, CppcheckWorkspaceFile> agregateMap = cppcheckSourceContainer.getInternalMap();
	    		//if (agregateMap != null) {
	    		//}
	    		return new 	AcppLoggerSource(owner, link);
	    	}
	        return null;
	    }

}
