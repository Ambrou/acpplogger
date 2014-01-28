package org.labs.studioprive.acpplogger;

import hudson.model.AbstractBuild;
import hudson.util.IOException2;

import org.apache.commons.digester.Digester;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
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
	private Integer PercentTotal;
	private List<AcppLoggerContainer> mListNameFile;
	
	public List<AcppLoggerContainer> getListNameFile(){
		System.out.println("On demande le Get");
		return mListNameFile;
	}
	
	public AcppLoggerReport(AcppLoggerBuildAction action) {
        this.action = action;
        PercentTotal = new Integer(0);
        this.mListNameFile = action.getResultList();
        
        for(int i = 0; i < mListNameFile.size(); ++i)
        {
        	//System.out.println(mListNameFile.get(i).getFile() + " " + mListNameFile.get(i).getPercent());
        	String strTemp = mListNameFile.get(i).getPercent();
        	String [] split = strTemp.split("%");
        	System.out.println("On demande le Get Percent " + split[0]);
        	PercentTotal += Integer.parseInt(split[0]);
        }
        PercentTotal /= mListNameFile.size();
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
    
    public String getPercentTotal(){
    	return Integer.toString(PercentTotal) + "%";
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
    
    
    public Object getDynamic(final String link, final StaplerRequest request, final StaplerResponse response) throws IOException {

//        if (link.startsWith("source.")) {
//
//            if (!owner.getProject().getACL().hasPermission(Item.WORKSPACE)) {
//                response.sendRedirect2("nosourcepermission");
//                return null;
//            }
//
//            Map<Integer, CppcheckWorkspaceFile> agregateMap = cppcheckSourceContainer.getInternalMap();
//            if (agregateMap != null) {
//                CppcheckWorkspaceFile vCppcheckWorkspaceFile = agregateMap.get(Integer.parseInt(StringUtils.substringAfter(link, "source.")));
//                if (vCppcheckWorkspaceFile == null) {
//                    throw new IllegalArgumentException("Error for retrieving the source file with link:" + link);
//                }
//                return new 	(owner, vCppcheckWorkspaceFile);
//            }
//        }
    	System.out.println(" AcppLoggerReport " + link);
    	if(link.endsWith(".html")){
    		//Map<Integer, CppcheckWorkspaceFile> agregateMap = cppcheckSourceContainer.getInternalMap();
    		//if (agregateMap != null) {
    		//}
    		return new 	AcppLoggerSource(getBuild(), link);
    	}
        return null;
    }

}
