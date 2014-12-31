package com.dhanjal.build.Process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author Amandeep Dhanjal
 *
 */

public class ExternalProcess implements Runnable {

	private String processName;
	private String commandLineArgs;
	private String logFilePath;
	private boolean emailLogFile;
	private String shortName;
	private ThreadCounter tCounter;
	
	private static Logger log = Logger.getLogger(ExternalProcess.class.getName());

	
	public ExternalProcess(){}
	
	/**
	 * Only pass the process name
	 * @param processName
	 */
	public ExternalProcess(String processName){
		this(processName, null, null);
	}
	
	/**
	 * Pass the processName and command line arguments
	 * @param processName
	 * @param commandLineArgs
	 */
	public ExternalProcess(String processName, String commandLineArgs){
		this(processName,commandLineArgs, null);
	}
	
	/**
	 * Pass process name, command line arguments and log filePath
	 * @param processName
	 * @param commandLineArgs
	 * @param logFilePath
	 */
	public ExternalProcess(String processName, String commandLineArgs, String logFilePath){
		this(processName,commandLineArgs, null, "true");
	}
	
	/**
	 * Pass process name, command line arguments and log filePath and whether to email log or no
	 * @param processName
	 * @param commandLineArgs
	 * @param logFilePath
	 * @param emailLog
	 */
	public ExternalProcess(String processName, String commandLineArgs, String logFilePath, String emailLog){
		this.processName = processName;
		this.commandLineArgs = commandLineArgs;
		this.logFilePath = logFilePath;
		this.emailLogFile = convertStrToBoolean(emailLog);
	}
	
	private boolean convertStrToBoolean(String emailLog) {
		if(emailLog == null || emailLog.length() < 1)
			return false;
		
		String trimmedEmailLog = emailLog.trim();
		
		if(trimmedEmailLog.equalsIgnoreCase("Yes") || trimmedEmailLog.equalsIgnoreCase("true") || trimmedEmailLog.equalsIgnoreCase("1"))
			return true;
		
		return false;
	}

	@Override
	public void run() {
		// check the process name if its valid or not?
		if(this.processName == null || this.processName.length() <= 0){
			log.severe("Process name is not valid.");
			return;
		}
		
		//increment thread count
		this.tCounter.incrementCounter();		

		// check the path to process (check if file exists)?
	//		File f = new File(this.processName);
	//		if(!f.exists()){
	//			log.severe("Process file does not exist: "+this.processName);
	//			//return;
	//		}
		
		String tempProcessName = (this.shortName == null) ? this.processName : this.shortName;
		Process process = null;
		Thread.currentThread().setName(tempProcessName);
		try {
			
			log.info("Started running process: "+tempProcessName);

			ProcessBuilder procBuild = new ProcessBuilder(this.processName + " " + this.commandLineArgs);
			procBuild.redirectErrorStream(true);

			
			// run the process and wait for it to come back..
			process = procBuild.start();
			InputStream is = process.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			while ((br.readLine()) != null) {
			//		System.out.println(line);
			}

			boolean done = false;

			while (!done) {
				try {
					process.exitValue();
					done = true;
				} catch (IllegalThreadStateException e) {
					// still running
				}
				try {
					Thread.sleep(1000);
				} catch (InterruptedException ie) {
					done = true;
				}
			}
		} catch (IOException e1) {
			log.log(Level.SEVERE,"IOException while launching process: "+this.processName,e1);
		} catch(Exception e){
			log.log(Level.SEVERE,"Exception while launching process: "+this.processName,e);
		}finally{
			if(process != null){
				process.destroy();
			}
			this.tCounter.decrementCounter();
		}
		
		//decrement count
		log.info("Done running process: "+tempProcessName);
	}
	
	public String getProcessName() {
		return processName;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public String getCommandLineArgs() {
		return commandLineArgs;
	}

	public void setCommandLineArgs(String commandLineArgs) {
		this.commandLineArgs = commandLineArgs;
	}

	public String getLogFilePath() {
		return logFilePath;
	}

	public void setLogFilePath(String logFilePath) {
		this.logFilePath = logFilePath;
	}

	public boolean isEmailLogFile() {
		return emailLogFile;
	}

	public void setEmailLogFile(String emailLogFile) {
		this.emailLogFile = convertStrToBoolean(emailLogFile);
	}
	
	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void settCounter(ThreadCounter tCounter) {
		this.tCounter = tCounter;
	}

}
