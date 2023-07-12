package com.napier.chatbot.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;


public class WatsonMain {
/*	public static String WORKSPACE_ID= "5d3c2ceb-5dcd-48e5-b046-f3963554f966";
	public static String usermessage;
	
	
	
	public Map<String,String> conversationVitals() throws InterruptedException {
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
			service.setUsernameAndPassword("11467c9f-f8e7-4faf-83b5-daf0251c4f76", "BEfwN7Csun1Z");

			Map<String,String> res=new HashMap<String,String>();
			String messageFromUser = usermessage;
			Map<String, Object> context = null;
			boolean endConversation = false;
			do {
				MessageRequest request = new MessageRequest.Builder().inputText(messageFromUser).context(context).build();
				MessageResponse response = service.message(WORKSPACE_ID, request).execute();
				context = response.getContext();
				Map<String, Object> output = response.getOutput();
				List<String> responseText = (List<String>) output.get("text");
				if (responseText.size() > 0) {
					System.out.println("Bot: " + responseText.get(0));
				}
				
				
				if (output.get("action") != null) {
					String actionString = (String) output.get("action");
					if ("vitals".equalsIgnoreCase(actionString)) {
						vitals(context);
						context = null;
						endConversation = true;
					} else if ("end_conversation".equalsIgnoreCase(actionString)) {
						endConversation = true;
					}
				}
				if (!endConversation) {
					messageFromUser = getUserInput();
				}
			} while (!endConversation);
			String exitMessage = "Exiting program. Have a nice day!";
			System.out.println(exitMessage);
			return res;
		}
public String getUserInput(String userMsg)
{
	usermessage=userMsg;
	return usermessage;
}	
	
}
	
	
	
	
	
	
	
	
	*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static final String BP_KEY = "BP";
	public static final String SPO2_KEY = "spo2";
	public static final String PULSE_KEY = "pulse";
	public static final String BLOODGROUP_KEY = "bloodgroup";
	public static final String END = "endConversation";
	public static final String BLOOD_PRES = "bloodPressure";
	public static final String SPO2 = "bodyTemperature";
	public static final String BLOOD_GROUP = "bloodGroup";
	public static final String PULSE_RATE = "pulseRate";
	public static final String VITALS = "vitals";
	public static String bp;
	public static String spo2Value;
	public static String pulseRate;
	public static String bloodGroup;
	
	private static String  usermsg;
	private static String  responsemsg;
	public static String WORKSPACE_ID = "5d3c2ceb-5dcd-48e5-b046-f3963554f966";

	public  Map<String,String> conversationVitals() throws InterruptedException {
		ConversationService service = new ConversationService(
				ConversationService.VERSION_DATE_2016_07_11);
		service.setUsernameAndPassword("11467c9f-f8e7-4faf-83b5-daf0251c4f76","BEfwN7Csun1Z");
		ResponseMessage responsemsg= new ResponseMessage();
		String messageFromUser = "";
		messageFromUser = usermsg ;
		System.out.println(messageFromUser);
		Map<String, Object> context = null;
		boolean endConversation = false;
		//while (!endConversation) { while loop starts here
			MessageRequest request = new MessageRequest.Builder().inputText(messageFromUser).context(context).build();
			MessageResponse response = service.message(WORKSPACE_ID, request).execute();
			System.out.println(response);
			context = response.getContext();
			Map<String, Object> output = response.getOutput();
			Map<String,String> rdx = new HashMap<String,String>();
			List<String> responseText = (List<String>) output.get("text");
			//System.out.println(responseText);
			if (responseText.size() > 0) {
				//System.out.println("msg came upto here");
				rdx.put("responseMsg", responseText.get(0).toString());
				System.out.println("BOT RESPONSE: " + responseText.get(0).toString());
	
			
			}
	//	System.out.println(output);
			
			
		/*	ArrayList al = (ArrayList) output.get("nodes_visited");
			String op = al.get(0).toString();
			*/
			
		/*	 //System.out.println(al.get(0));
			if (op.equalsIgnoreCase(END)) {
				// System.out.println("in");
				break;
			}
			
			else if ( op.equalsIgnoreCase(BLOOD_PRES)) {
				System.out.println(context);
				vitalBloodPressure(context);
			}

			else if ( op.equalsIgnoreCase(SPO2)) {
				//System.out.println(context);
				vitalBodySpo2(context);
			}
			
			else if ( op.equalsIgnoreCase(BLOOD_GROUP)) {
				//System.out.println(context);
				vitalBloodGroup(context);
			}
			
			else if ( op.equalsIgnoreCase(PULSE_RATE)) {
				//System.out.println(context);
				vitalPulseRate(context);
			}
			
			
			*/
			
			//if (!endConversation) {
				
				//messageFromUser = usermsg ;
			//} check out the last if statement

	//	} while loop ends here
			return rdx;
	}

	public static String getUserInput(String inputMessage) {
		usermsg= inputMessage;
		return usermsg;	

	}
	
/*
	private static void vitalPulseRate(Map<String, Object> context)
			throws InterruptedException {
		pulseRate = (String) context.get(PULSE_KEY);
	}

	private static void vitalBodySpo2(Map<String, Object> context)
			throws InterruptedException {
		spo2Value = (String) context.get(SPO2_KEY);
	}


	private static void vitalBloodPressure(Map<String, Object> context)
			throws InterruptedException {
		bp = (String) context.get(BP_KEY);
	}

	private static void vitalBloodGroup(Map<String, Object> context)
			throws InterruptedException {
		bloodGroup = (String) context.get(BLOODGROUP_KEY);
	}
	
	
	public String toString() {
		return "\nHello,\n your blood pressure value is " + bp
				+ "\n your spo2 is  " +spo2Value
				+ "\n your pulse rate is  " + pulseRate
				+ "\n your blood group is " + bloodGroup;
	}
	*/
}
