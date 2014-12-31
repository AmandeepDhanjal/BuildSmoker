package com.dhanjal.build;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.gbs.build.Process.ExternalProcess;
import com.gbs.build.Process.IConfig;
import com.gbs.build.Process.ThreadCounter;
import com.gbs.build.Process.XMLConfig;
import com.gbs.build.Report.INotify;
import com.gbs.build.Logs.NewLogFile;

public class RunSmoker implements IRunSmoker{
	private static Logger log = Logger.getLogger(RunSmoker.class.getName());

	public static void main(String[] args) {
		
		Logger logHandler = Logger.getLogger("");
		
		NewLogFile.changeFileNameNowForCurrentDatabase("Smoker.log", null, logHandler, "INFO", false);

		if(args.length > 0){
			String fileToProcess = args[0].toString().toLowerCase().trim();
			
			IConfig config = null;
			
			ThreadCounter tCounter = new ThreadCounter();
			List<String> listOfLogFilesToBeEmailed = new ArrayList<String>();
			
			//xml ? 
			if(fileToProcess.endsWith(".xml")){
				
				config = new XMLConfig(fileToProcess);
				List<ExternalProcess> listOfAllProcess = config.returnListOfAllProcess();
				for(ExternalProcess p : listOfAllProcess){
					log.info("Found Process: "+p.getShortName()+". Lauching it..");
					if(p.isEmailLogFile() && p.getLogFilePath() != null && p.getLogFilePath().length() > 0){
						listOfLogFilesToBeEmailed.add(p.getLogFilePath());
					}
					p.settCounter(tCounter);//set the counter object
					new Thread(p).start();
				}
				
			}//ini ?
			else if(fileToProcess.endsWith(".ini")){
				
				config = new XMLConfig(fileToProcess);
				List<ExternalProcess> listOfAllProcess = config.returnListOfAllProcess();
				for(ExternalProcess p : listOfAllProcess){
					System.out.println("Found Process: "+p.getProcessName());
				}
				
			}// properties ?
			else if(fileToProcess.endsWith(".properties")){
				
				config = new XMLConfig(fileToProcess);
				List<ExternalProcess> listOfAllProcess = config.returnListOfAllProcess();
				for(ExternalProcess p : listOfAllProcess){
					System.out.println("Found Process: "+p.getProcessName());
				}
				
			}else{
				System.out.println("Unsupported file format: "+fileToProcess);
			}
			
			// any file needs to be emailed?
			if(!listOfLogFilesToBeEmailed.isEmpty()){
				//wait till all threads are done running..
				while (tCounter.getCounter() > 0) {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
					}
				}
				INotify notify = config.returnNotifyObject();
				if (notify != null) {
					log.info("Notifying now..");
					notify.notifyNow();
				} else {
					log.warning("Not notifying because either the mail settings are wrong or not specified.");
				}
			}
			
		}// end check args length
		
	}
}
