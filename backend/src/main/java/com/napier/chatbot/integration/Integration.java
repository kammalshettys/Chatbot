package com.napier.chatbot.integration;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface Integration {
	
	public String pushVitals(String vital, String vitalValue, String tenantName) throws JsonParseException, JsonMappingException, IOException;
	public String vitalsStatus(String patientId)  throws JsonParseException, JsonMappingException, IOException;

}
