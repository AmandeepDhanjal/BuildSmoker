package com.dhanjal.build.Process;

import java.util.List;

import com.gbs.build.Report.INotify;

public interface IConfig {

	public String PROCESS = "Process";
	public String PROCESS_SHORT_NAME = "ProcessShortName";
	public String PROCESS_NAME = "ProcessName";
	public String COMMAND_LINE = "CommandLineArgs";
	public String EMAIL_LOG = "EmailLog";
	public String LOG_FILE_PATH = "LogFile";
	
	public String MAIL = "Mail";
	public String MAIL_HOST= "MailHost";
	public String MAIL_RECIPIENTS = "Recipient";
	public String MAIL_FROM = "From";
	public String MAIL_TRANSPORT_PROTOCOL = "Protocol";
	
	public List<ExternalProcess> returnListOfAllProcess();
	public INotify returnNotifyObject();
	
}
