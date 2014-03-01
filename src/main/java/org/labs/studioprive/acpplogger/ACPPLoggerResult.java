package org.labs.studioprive.acpplogger;

import hudson.model.AbstractBuild;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

public class ACPPLoggerResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * The build owner
     */
    private AbstractBuild<?, ?> build;
    private final List<ACPPLoggerCoverageDataFile> coverageDataFiles;
    
    public ACPPLoggerResult(AbstractBuild<?, ?> build, List<ACPPLoggerCoverageDataFile> coverageDataFiles) {///*CppcheckReport report, CppcheckSourceContainer cppcheckSourceContainer, */AbstractBuild<?, ?> owner) 
    	this.coverageDataFiles = coverageDataFiles;
        //this.report = report;
        //this.cppcheckSourceContainer = cppcheckSourceContainer;
        this.build = build;
    }

	/**
	 * @return the owner
	 */
	public AbstractBuild<?, ?> getBuild() {
		return build;
	}
	
	public String getPercentTotal(){
		Integer PercentTotal = new Integer(0);
		for(int iLoop = 0; iLoop < getListNameFile().size(); ++iLoop){
			PercentTotal += getListNameFile().get(iLoop).getCoveragePercent();
		}
    	return Integer.toString(PercentTotal/getListNameFile().size()) + "%";
    }
	
	public List<ACPPLoggerCoverageDataFile> getListNameFile(){
		return coverageDataFiles;
	}
	
	
	public Object getDynamic(final String link, final StaplerRequest request, final StaplerResponse response) throws IOException {

		System.out.println(link);
		System.out.println(getListNameFile().indexOf(link));
		for(int iLoop = 0; iLoop < getListNameFile().size();++iLoop){
			if(link.compareTo(getListNameFile().get(iLoop).getFileName()) == 0){
				return new AcppLoggerSourceCoverage(getListNameFile().get(iLoop));
			}
		}
		/*if(getListNameFile().contains(link) == true) {
			ACPPLoggerCoverageDataFile acppLoggetCoverageDataFile = getListNameFile().get(getListNameFile().indexOf(link));
			return new AcppLoggerSourceCoverage(acppLoggetCoverageDataFile);
		}
	  	if(link.endsWith(".html")){
	  		//Map<Integer, CppcheckWorkspaceFile> agregateMap = cppcheckSourceContainer.getInternalMap();
	  		//if (agregateMap != null) {
	  		//}
	  		//return new 	AcppLoggerSource(getBuild(), link);
	  	}*/
	      return null;
  }

}
