package com.napier.chatbot.controller;

import java.util.HashMap;
import java.util.Map;

public class ResponseMessage {
	private  static String  responseMsg;

	

public void setResponseMsg(String rmsg) {
		responseMsg = rmsg;
	}
    
public 	String getResponseMessage(){ 
	 return responseMsg;	
}
}