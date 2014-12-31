package com.dhanjal.build.Process;

import java.util.List;

import com.gbs.build.Report.INotify;

public abstract class AConfig implements IConfig {

	public abstract List<ExternalProcess> returnListOfAllProcess();

	public abstract INotify returnNotifyObject();
	
	public IConfig getInstance(String fileName){
		return null;
	}

	

}
