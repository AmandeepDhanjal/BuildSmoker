package com.dhanjal.build.Process;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.gbs.build.Report.EmailReport;
import com.gbs.build.Report.INotify;


public class XMLConfig extends DefaultHandler implements IConfig{

	private static String DEFAULT_FILE_NAME = "BuildSmoker.xml";
	private File fileToBeParsed;
	private List<ExternalProcess> allProcessList = new ArrayList<ExternalProcess>();
	private ExternalProcess processObj;
	private String tempVal;
	private EmailReport emailNotify;

	private static Logger log = Logger.getLogger(XMLConfig.class.getName());

	public XMLConfig(){
		this(DEFAULT_FILE_NAME);
	}
	
	public XMLConfig(String fileName){
		this(new File(fileName));
	}
	
	public XMLConfig(File file){
		fileToBeParsed = file;
	}
	
	private void parseXML(){
		if(fileToBeParsed == null){
			log.severe("Either no XML file passed to program or file passed does not exists. Exiting.");
			return;
		}
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		
		try {
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the file and also register this class for call backs
			sp.parse(fileToBeParsed, this);	
		}catch(SAXException se) {
			log.log(Level.SEVERE,"SAXException: ",se);
		}catch(ParserConfigurationException pce) {
			log.log(Level.SEVERE,"ParserConfigurationException: ",pce);
		}catch (IOException ie) {
			log.log(Level.SEVERE,"IOException: ",ie);
		}
	}
	

	//Event Handlers
	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		if(qName.equalsIgnoreCase(PROCESS)) {
			//create a new instance of process
			processObj = new ExternalProcess();
		}else if(qName.equalsIgnoreCase(MAIL)){
			emailNotify = new EmailReport();
		}
	}
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {

		//Process
		if(qName.equalsIgnoreCase(PROCESS)) {
			//add it to the list
			allProcessList.add(processObj);
			
		}else if (qName.equalsIgnoreCase(PROCESS_NAME)) {
			processObj.setProcessName(tempVal);
		}else if (qName.equalsIgnoreCase(COMMAND_LINE)) {
			processObj.setCommandLineArgs(tempVal);
		}else if (qName.equalsIgnoreCase(EMAIL_LOG)) {
			processObj.setEmailLogFile(tempVal);
		}else if (qName.equalsIgnoreCase(LOG_FILE_PATH)) {
			processObj.setLogFilePath(tempVal);
		}else if (qName.equalsIgnoreCase(PROCESS_SHORT_NAME)) {
			processObj.setShortName(tempVal);
		}
		//MAIL
		else if (qName.equalsIgnoreCase(MAIL_FROM)) {
			emailNotify.setFrom(tempVal);
		}else if (qName.equalsIgnoreCase(MAIL_HOST)) {
			emailNotify.setMailHost(tempVal);
		}else if (qName.equalsIgnoreCase(MAIL_RECIPIENTS)) {
			emailNotify.setListOfRecipients(tempVal);
		}else if (qName.equalsIgnoreCase(MAIL_TRANSPORT_PROTOCOL)) {
			emailNotify.setProtocol(tempVal);
		}
	}
	
	public List<ExternalProcess> returnListOfAllProcess(){
		parseXML();
		return allProcessList;
	}
	
	public INotify returnNotifyObject(){
		return emailNotify;
	}
	
	
}
