package com.dhanjal.build.Report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmailReport implements INotify{

	/*
	 * public String MAIL = "Mail";
	public String MAIL_HOST= "MailHost";
	public String MAIL_RECIPIENTS = "Recipient";
	public String MAIL_FROM = "From";
	public String MAIL_TRANSPORT_PROTOCOL = "Protocol";
	 */
	
	private List<String> listOfRecipients;
	private String mailHost;
	
	private String from;
	private String protocol;
	private static final String DEFAULT_MAIL_PROTOCOL= "smtp";
	private static final String DEFAULT_MAIL_HOST= "localhost";
	
	public EmailReport(String listOfRecp, String from, String protocol,String mailHost){
		setListOfRecipients(listOfRecp);
		this.mailHost = (mailHost == null) ? DEFAULT_MAIL_HOST : mailHost;
		this.from = from;
		this.protocol = (protocol == null) ? DEFAULT_MAIL_PROTOCOL : protocol;;
	}
	
	public EmailReport(String listOfRecp, String from, String protocol){
		this(listOfRecp,from,protocol, null);
	}
	
	public EmailReport() {
		// TODO Auto-generated constructor stub
	}

	public List<String> getListOfRecipients() {
		return listOfRecipients;
	}

	public void setListOfRecipients(String listOfRecipients) {
		if(listOfRecipients != null && listOfRecipients.length() > 0){
			if(listOfRecipients.contains(",")){
				String[] splittedList = listOfRecipients.split(",");
				this.listOfRecipients = Arrays.asList(splittedList);
			}else if(listOfRecipients.contains(";")){
				String[] splittedList = listOfRecipients.split(";");
				this.listOfRecipients = Arrays.asList(splittedList);
			} else{
				this.listOfRecipients = new ArrayList<String>();
				this.listOfRecipients.add(listOfRecipients);
			}
		}	
	}
	
	public void setListOfRecipients(List<String> listOfRecipients) {
		this.listOfRecipients = listOfRecipients;
	}
	
	public String getMailHost() {
		return mailHost;
	}
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	@Override
	public boolean notifyNow() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
