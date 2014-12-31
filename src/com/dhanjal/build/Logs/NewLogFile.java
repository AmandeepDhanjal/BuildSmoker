/**
 * 
 */
package com.dhanjal.build.Logs;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.SimpleFormatter;

/**
 * 
 * @author Amandeep Dhanjal
 *
 */

public class NewLogFile {

	private static FileHandler fileHandler = null;
	
	public static void setUserChoiceLogLevel(java.util.logging.Logger log, final String UserLogLevel) {
		if (UserLogLevel == null) {
			System.out.println("Log Level passed on command line: " + UserLogLevel + "(null). Setting log level to write Everything.");
			log.setLevel(Level.ALL);
			return;
		}

		if (UserLogLevel.equalsIgnoreCase("ALL")) {
			log.setLevel(Level.ALL);
		} else if (UserLogLevel.equalsIgnoreCase("FINEST")) {
			log.setLevel(Level.FINEST);
		} else if (UserLogLevel.equalsIgnoreCase("FINER")) {
			log.setLevel(Level.FINER);
		} else if (UserLogLevel.equalsIgnoreCase("FINE")) {
			log.setLevel(Level.FINE);
		} else if (UserLogLevel.equalsIgnoreCase("CONFIG")) {
			log.setLevel(Level.CONFIG);
		} else if (UserLogLevel.equalsIgnoreCase("INFO")) {
			log.setLevel(Level.INFO);
		} else if (UserLogLevel.equalsIgnoreCase("WARNING")) {
			log.setLevel(Level.WARNING);
		} else if (UserLogLevel.equalsIgnoreCase("SEVERE")) {
			log.setLevel(Level.SEVERE);
		} else if (UserLogLevel.equalsIgnoreCase("OFF")) {
			log.setLevel(Level.OFF);
		} else {
			System.out.println("WRONG Log Level passed on command line: " + UserLogLevel + ". Setting log level to write Everything.");
			log.setLevel(Level.ALL);
		}
	}

	public static void changeFileNameNowForCurrentDatabase(String template, HashMap<String, String> tempMap, java.util.logging.Logger log, String UserLogLevel, boolean append) {

		String tempFileName;
		tempFileName = expandTokens(template, tempMap);

		if (tempFileName.length() > 0) {

			// try to close the previous file handler if it exists..
			if (getFileHandler() != null) {
				getFileHandler().close();
				log.removeHandler(getFileHandler());
			}

			try {
				setFileHandler(new FileHandler(tempFileName, append));
				getFileHandler().setFormatter(new SimpleFormatter(){
					public String format(LogRecord record){
						boolean throwException = (record.getThrown() != null);
						String makeMessageForException = null;
						StringBuffer sb = new StringBuffer();
						String extraFormatting = "\n" + "\t\t";
						
						if(throwException){
							Throwable t = record.getThrown();
							makeMessageForException = extraFormatting +" EXCEPTION -:- "+ t;
							StackTraceElement[] ste = t.getStackTrace();
							if(ste != null){
								for(int i = 0; i < ste.length ; i++){
									sb.append(extraFormatting + ste[i]);
								}
							}
						}
						
						String formattedLog = record.getLevel() + "  :  "
								+ new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()) + " -:- "
					            + record.getSourceClassName() + " -:- "
					            + record.getSourceMethodName() + " -:- "
					            + record.getMessage()
					            + (throwException ? makeMessageForException + sb.toString() : " ")
					            + "\n";
						
						return formattedLog;
					}
				});
				log.addHandler(getFileHandler());
				setUserChoiceLogLevel(log, UserLogLevel);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("New log file set:" + tempFileName);
		}
	}

	@SuppressWarnings("rawtypes")
	private static String expandTokens(String template, Map tokens) {
		// TODO: It would be great to use modern replaceAll method on String ...
		StringBuffer templateSB = new StringBuffer(template);
		int tokenIdx;
		if (tokens != null && !tokens.isEmpty()) {// checking just in case
			Iterator iter = tokens.keySet().iterator();
			while (iter.hasNext()) {
				String thisToken = (String) iter.next();
				Object thisTokenObj = tokens.get(thisToken);
				if (thisTokenObj == null)
					continue;// this could perhaps be text of the form %foo% in
								// the body of the template -- let it pass
				// through
				String thisTokenValue = thisTokenObj.toString();

				tokenIdx = templateSB.toString().indexOf(thisToken, 0);

				while (tokenIdx != -1) {
					templateSB.replace(tokenIdx, tokenIdx + thisToken.length(), thisTokenValue);
					int startIdx = tokenIdx + thisTokenValue.length();
					if (startIdx >= templateSB.length())
						break;
					tokenIdx = templateSB.toString().indexOf(thisToken, startIdx);
				}
			}
		}
		return templateSB.toString();
	}
	
	public static void closeFileHandler(){
		// try to close the previous file handler if it exists..
		if (getFileHandler() != null) {
			getFileHandler().close();
		}
	}

	public static FileHandler getFileHandler() {
		return fileHandler;
	}

	public static void setFileHandler(FileHandler fileHandler) {
		NewLogFile.fileHandler = fileHandler;
	}
}
