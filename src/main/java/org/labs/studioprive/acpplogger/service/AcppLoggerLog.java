package org.labs.studioprive.acpplogger.service;

import java.io.Serializable;

import com.google.inject.Inject;

import hudson.model.BuildListener;


public class AcppLoggerLog implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private BuildListener buildListener;

    @Inject
    void set(BuildListener buildListener) {
        this.buildListener = buildListener;
    }

    /**
     * Log an info output to the console logger
     *
     * @param message The message to be outputted
     */
    public void infoConsoleLogger(String message) {
        buildListener.getLogger().println("[AccpLogger] [INFO] - " + message);
    }

    /**
     * Log an error output to the console logger
     *
     * @param message The message to be outputted
     */
    public void errorConsoleLogger(String message) {
        buildListener.getLogger().println("[AccpLogger] [ERROR] - " + message);
    }

    /**
     * Log a warning output to the console logger
     *
     * @param message The message to be outputted
     */
    public void warningConsoleLogger(String message) {
        buildListener.getLogger().println("[AccpLogger] [WARNING] - " + message);
    }
}
