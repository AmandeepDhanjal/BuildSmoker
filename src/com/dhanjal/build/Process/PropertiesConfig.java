package com.dhanjal.build.Process;

import java.io.File;
import java.util.List;

import com.gbs.build.Report.INotify;

/**
 * 
 * @author Amandeep Dhanjal
 *
 */
public class PropertiesConfig implements IConfig{
	
	/**
	 * Read only one properties file
	 */
	
	private File propertiesFile = null;
	
	public File getPropertiesFile() {
		return propertiesFile;
	}

	public void setPropertiesFile(File propertiesFile) {
		this.propertiesFile = propertiesFile;
	}

	public PropertiesConfig(String filename) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<ExternalProcess> returnListOfAllProcess() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public INotify returnNotifyObject() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
