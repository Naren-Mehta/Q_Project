package com.wm.brta.utility;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wm.brta.domain.User;
import com.wm.brta.util.BaleUtils;


public class MailServiceUtility {
	private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceUtility.class);
	static String  host = "mail.wm.com";
	static String port = "25";
	
	static String from = "do-notreply@wm.com"; 
    
    public static void sendMail(User persistenceObject) {
    	Properties _properties = new Properties();
    	_properties.setProperty("mail.smtp.host", host);
    	_properties.setProperty("mail.smtp.port", port);
    	Session session = Session.getDefaultInstance(_properties);
    	try {
    		MimeMessage _message = new MimeMessage(session);
    		_message.setFrom(new InternetAddress(from));
    		_message.addRecipient(Message.RecipientType.TO, new InternetAddress(
    				persistenceObject.getEmailId().trim()));
//    		_message.addRecipient(Message.RecipientType.CC, new InternetAddress(
//    	    		"nmehta@wm.com"));
    		_message.setSubject("Welcome to Bale Route tracking Application");
    		
    		String userName=persistenceObject.getFirstName();
    		
    		if(userName!= null){
    			userName=userName.substring(0, 1).toUpperCase()+userName.substring(1);
    		}else{
    			userName="";
    		}
    		
    		String html = "Hello "+ userName +",You have access to the Bale Route Mobile App now. Your registered phone number is:"+persistenceObject.getMobilePhone()+
    				"\n" + "\n <br/><br/>For Android APK <a href="+ BaleUtils.androidApkUrl+">Please download the Android App from the Play store.</a><br/>";
    		_message.setText(html, "UTF-8", "html");
    		
    		// Send message
    		Transport.send(_message);
    		

    		} catch (MessagingException mex) {
    			LOGGER.error("====Exception while sending Email======"+mex);
    		//mex.printStackTrace();
    		}

    		}



}
