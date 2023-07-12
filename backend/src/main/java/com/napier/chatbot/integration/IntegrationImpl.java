package com.napier.chatbot.integration;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.type.CollectionType;


@Service
public class IntegrationImpl implements Integration {
	
	@Autowired
	private Environment env;
	
	//@Value("${push.vitals.url}")
	private String pushVitalsUrl= "https://rpmsit.napierhealthcare.com:9443/NapierHomeCare/app/napier/receive/vitals";
	private String vitalStatusUrl = "http://10.5.6.30:9080/NapierHomeCare/app/dashboard/patientDashBoard/getPatientVitalStatus/7";

	public String pushVitals(String vital, String vitalValue, String tenantValue) throws JsonParseException, JsonMappingException, IOException {
		// TODO Auto-generated method stub
	
		//utc time 
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'.000Z'"); 
		sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
		String utcTime = 	sdf.format(date);
	//	System.out.println(utcTime);
		
		
		//pushVitalsUrl = pushVitalsUrl + tenantName + "/receive/vitals" ;
		String rfid = null;
		String result = null;
		ObjectMapper mapper1 = new ObjectMapper();
		ObjectNode rootnode1 = mapper1.createObjectNode();
		rootnode1.put("appId", "hpXAz7MUvh");
		rootnode1.put("tokenId", "CAsL2NP2tJ");
		
		ObjectMapper mapper = new ObjectMapper();
		
		ArrayNode arrayNode = mapper.createArrayNode();
		//arrayNode.add("Data");
		ObjectMapper mapper2 = new ObjectMapper();
		ObjectNode rootnode = mapper2.createObjectNode();
		rootnode.put("patientId","7");
		rootnode.put("deviceId", "z0a7830108b8");
		rootnode.put("userId",  "5");
		rootnode.put("entityId", "");
		rootnode.put("recordId", "56613734fb417028180317577271112");
		rootnode.put("rfid", rfid);
	
		rootnode.put("recordDate", utcTime);
		
		if(vital.equalsIgnoreCase("spo2")) { 
	//	rootnode.put("last_reading", true);
		rootnode.put("spo2", vitalValue);
		} 
	
		//temperature
		else if(vital.equalsIgnoreCase("temperature")) { 
			//	rootnode.put("last_reading", true);
			rootnode.put("temperature", vitalValue);
			rootnode.put("temperatureUnit","C");
			} 
	
	
		//glucose push
		else if(vital.equalsIgnoreCase("glucose")) { 
			//	rootnode.put("last_reading", true);
			rootnode.put("concentration", vitalValue);
			rootnode.put("concentrationUnit","mg/dL");
			rootnode.put("sampleLocation","Finger");
			rootnode.put("sampleType","Capillary Whole Blood");
			rootnode.put("mealSettings","5004");
			rootnode.put("notes","aaaaaaaaaa");
		} 
		
		//weight push
		
		else if(vital.equalsIgnoreCase("weight")) { 
			//	rootnode.put("last_reading", true);
			rootnode.put("height", "");
			rootnode.put("heightUnit","cm");
			rootnode.put("weight",vitalValue);
			rootnode.put("weightUnit","kg");
			rootnode.put("bmi","");
		} 
			
		//bp push
		
		else if(vital.equalsIgnoreCase("bp")) { 
			
			String[] k = new String[2];
				int i=0;
	         Pattern p = Pattern.compile("-?\\d+");
	         Matcher m = p.matcher(vitalValue);
	         while (m.find()) {
	        	 k[i]=m.group();
	        	 i++;
	         	}
			
			//	rootnode.put("last_reading", true);
			rootnode.put("systolic", k[0] );
			rootnode.put("diastolic", k[1]);
			rootnode.put("heartRate","");
			rootnode.put("sampleType","Capillary Whole Blood");
			
			} 
		else if(vital.equalsIgnoreCase("pulse")) { 
		
		
		//	rootnode.put("last_reading", true);
		rootnode.put("systolic", "" );
		rootnode.put("diastolic", "");
		rootnode.put("heartRate",vitalValue);
		rootnode.put("sampleType","Capillary Whole Blood");
		
		} 
		
		
		arrayNode.add(rootnode);
		
		rootnode1.put("Data",arrayNode);
		
		//rootnode1.put("Data", rootnode1);
		String postData = rootnode1.toString();
		
		System.out.println("Post Data :: " + postData);
		
		RestTemplate template = new RestTemplate();

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("X-TenantName","care");
		

		HttpEntity<String> entity = new HttpEntity<String>(postData,
				headers);
		

		ResponseEntity<String> response = template.exchange(pushVitalsUrl, HttpMethod.POST, entity, String.class);
		System.out.println("Response Code : " + response.getStatusCode());
		
		if (response.getStatusCode() == HttpStatus.OK) {
			
			String responseBody = response.getBody();
			//System.out.println("Push Vitals ResponseBody :" + response.getBody());
			result = vitalsStatus(vital); 
		}
//		System.out.println("vitals Status :" + result);
		return result;
		
	}

	public String vitalsStatus(String vital) throws JsonParseException, JsonMappingException, IOException {
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>(); 
		RestTemplate template = new RestTemplate();
		String vitalStatus = null;

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add("Content-Type", "application/json");
		headers.add("X-TenantName","care");
		headers.add("X-Auth-Token", "eyJ1c2VybmFtZSI6Im5hcGllcmJuZyIsImV4cGlyZXMiOjE1MDU4MzA0OTM4MjIsImlzRmlyc3RMb2dpbiI6Ik4iLCJ1c2VySWQiOiI0IiwidXNlckZhY2lsaXRpZXMiOlt7ImZhY2lsaXR5Ijp7ImZhY2lsaXR5SWQiOjIsImZhY2lsaXR5TmFtZSI6IkJhbmdhbG9yZSJ9LCJyb2xlcyI6W3sicm9sZU5hbWUiOiJBZG1pbmlzdHJhdG9yIiwicm9sZUlkIjoxLCJhdXRob3JpdHkiOiJST0xFX0FETUlOIiwicGVyc29uSWQiOjQsImJ1c2luZXNzVHlwZSI6NTEzMywiYnVzaW5lc3NOYW1lIjoiSG9tZSBCYXNlZCJ9XX1dfQ==.+jRSZtPQ1aCWQd1oa/HURvijBFSwUhqy8gwzL4me10Y=");
		

		HttpEntity<String> entity = new HttpEntity<String>(headers);
		

		ResponseEntity<String> response = template.exchange(vitalStatusUrl, HttpMethod.GET, entity, String.class);
		
         if (response.getStatusCode() == HttpStatus.OK) {
			
			String responseBody = response.getBody();
	//		System.out.println("vitalsStatus ResponseBody :" + response.getBody());
			ObjectMapper mapper = new ObjectMapper();
			
			CollectionType mapCollectionType = mapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) List.class, Map.class);
	        result = mapper.readValue(responseBody, mapCollectionType);
	        for(Map<String, Object>  obj:result ){
	        	if(obj.get("vitalSign").toString().equalsIgnoreCase(vital)) {
	        	vitalStatus = (String) obj.get("vitalStatus");
	        	//System.out.println("VitalStatus :: " + vitalStatus);
	        	}
	        	}
	        }
		
         return vitalStatus;
	 
         
		
	}
	
	 
}
