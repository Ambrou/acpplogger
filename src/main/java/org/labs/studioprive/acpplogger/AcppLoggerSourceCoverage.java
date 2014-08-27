package org.labs.studioprive.acpplogger;

public class AcppLoggerSourceCoverage {

	final ACPPLoggerCoverageDataFile acppLoggetCoverageDataFile;
	
	public AcppLoggerSourceCoverage(ACPPLoggerCoverageDataFile acppLoggetCoverageDataFile) {
		this.acppLoggetCoverageDataFile = acppLoggetCoverageDataFile;
	}
	
	public String getFileName(){
		return getACPPLoggetCoverageDataFile().getFileName();
	}
	
	public String getSourceCode(){
		StringBuilder output = new StringBuilder();
		
		output.append("<table>\n");
		boolean codeLine = false;
		boolean coverted = false;
		int nbOpenedAccolade = 0;
		
		for(int iLoop = 0; iLoop < getACPPLoggetCoverageDataFile().getLinesFile().size(); ++iLoop){
			String line = getACPPLoggetCoverageDataFile().getLinesFile().get(iLoop);
			
			if(line.contains(". ->") == true) {
				codeLine = true;
				coverted = true;
			}
			if(line.contains("! ->") == true){
				codeLine = true;
				coverted = false;
			}
			if(codeLine == false) {
				output.append("<tr bgcolor=\"skyblue\" >\n");
			}
			else {
				if(coverted == true){
					output.append("<tr bgcolor=\"palegreen\" >\n");
				}
				if(coverted == false){
					output.append("<tr bgcolor=\"peru\" >\n");
				}
			}
			
			if(line.contains("{") == true) {
				nbOpenedAccolade++;
			}
			
			if(line.contains("}") == true){
				nbOpenedAccolade--;
			}
			if(nbOpenedAccolade == 0){
				codeLine = false;
			}
			//output.append("<tr bgcolor=\"skyblue\" >\n");
			//if(line.contains(". ->") == true) {
			//	output.append("<tr bgcolor=\"palegreen\" >\n");
			//}
			//else if(line.contains("! ->") == true){
			//	output.append("<tr bgcolor=\"indianred\" >\n");
			//}
			
			output.append("<td>" + iLoop + "</td>\n");
			output.append("<td><pre>");
			if(line.length() > 8){
				output.append(line.substring(8));
			}
			else{
				output.append(line);
			}
			output.append("</pre></td>\n");
			output.append("</tr>\n");
		}
		output.append("</table>");
		return output.toString();
	}
	
	ACPPLoggerCoverageDataFile getACPPLoggetCoverageDataFile(){
		return acppLoggetCoverageDataFile;
	}

}
