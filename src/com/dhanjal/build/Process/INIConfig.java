package com.dhanjal.build.Process;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.gbs.build.Report.INotify;

public class INIConfig implements IConfig {

	private static String DEFAULT_FILE_NAME = "BuildSmoker.ini";
	private File fileToBeParsed;
	private List<ExternalProcess> allProcessList = new ArrayList<ExternalProcess>();
	private ExternalProcess processObj;
	
	private static Logger log = Logger.getLogger(XMLConfig.class.getName());

	public INIConfig(){
		this(DEFAULT_FILE_NAME);
	}
	
	public INIConfig(String fileName){
		this(new File(fileName));
	}
	
	public INIConfig(File file){
		fileToBeParsed = file;
	}
	
	private void readINISections(){
		if(fileToBeParsed == null || fileToBeParsed.length() <= 0){
			log.severe("Either no INI file passed to program or file passed does not exists. Exiting.");
			return;
		}
		
		//Read INI Sections and Make Process Objects and then add them to the list
		
	}
	
	@Override
	public List<ExternalProcess> returnListOfAllProcess() {
		readINISections();
		return allProcessList;
	}

	@Override
	public INotify returnNotifyObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
