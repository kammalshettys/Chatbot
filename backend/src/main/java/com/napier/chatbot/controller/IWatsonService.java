package com.napier.chatbot.controller;

import java.io.IOException;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;

public interface IWatsonService {

	
	public Map<String,String> conversationVitals(UserMessage map)throws JsonParseException, JsonMappingException, InterruptedException, IOException ;
	
	//public MessageResponse getUserInput(UserMessage message);
	
	
	
}
