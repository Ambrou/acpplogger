package org.labs.studioprive.acpplogger;

import hudson.model.AbstractBuild;

import org.kohsuke.stapler.export.Exported;
import org.kohsuke.stapler.export.ExportedBean;

@ExportedBean
public abstract class AcppLoggerObject<SELF extends AcppLoggerObject<SELF>> {
	private volatile boolean failed = false;

    public boolean isFailed() {
        return failed;
    }
    
    /**
     * Marks this coverage object as failed.
     * @see Rule
     */
    public void setFailed() {
        failed = true;
    }
    
    /**
     * Gets the build object that owns the whole coverage report tree.
     */
    public abstract AbstractBuild<?,?> getBuild();
    
    /**
     * Gets the corresponding coverage report object in the previous
     * run that has the record.
     *
     * @return
     *      null if no earlier record was found.
     */
    @Exported
    public abstract SELF getPreviousResult();
    
    public String printTest() {
//        StringBuilder buf = new StringBuilder();
//        printRatioCell(isFailed(), statement, buf);
//        printRatioCell(isFailed(), branch, buf);
//        printRatioCell(isFailed(), loop, buf);
//        printRatioCell(isFailed(), condition, buf);
        return "Ceci est un test je ne sais pas ou ça sera";
    }
}
