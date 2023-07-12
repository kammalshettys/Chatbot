/**
 * 
 */
package com.napier.chatbot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageRequest;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;
import com.napier.chatbot.integration.IntegrationImpl;

/**
 * @author nap1230
 *
 */
@Service
public class IWatsonServiceImpl implements IWatsonService {
	
	IntegrationImpl prePush=new IntegrationImpl();
	
	public static String usermessage;
	
	public static String WORKSPACE_ID= "5d3c2ceb-5dcd-48e5-b046-f3963554f966";
	ResponseMessage rsm;
	ArrayList<String> checkUserId;
	//public  Map<String, Object> context = null;
	public  Map<String, Object> tempContext = null;
	public final static String spo2Node ="node_1_1505273455881";
	public final static String tempNode ="node_9_1505719498332";
	public final static String pulseNode ="node_8_1505214349217";
	public final static String weightNode ="node_4_1505719061708";
	public final static String glucoseNode ="node_3_1505719056932";
	public final static String bpNode ="node_2_1505273873040";
	public final static String superSpo2Node ="bodyTemp";
	/* conversationVitals
	 * @see com.napier.chatbot.controller.IWatsonService#conversationVitals()
	 
*/

		
	public Map<String, String> conversationVitals(UserMessage usMsg) throws JsonParseException, JsonMappingException, InterruptedException, IOException {
		CacheManager cm =  CacheManager.getInstance();
			System.out.println(cm);
			cm.addCacheIfAbsent("cache1");
			System.out.println("after cache1" +usMsg.getUserMsg());
	//MessageRequest request =null;
			Map<String, String> res;
			int i=0;
			boolean IsCheckId = false;
			String userId=null;
	Map<String,Object> context=null;
	Cache cache = cm.getCache("cache1");
	System.out.println("I m here");
	
	if(checkUserId.get(Integer.parseInt(usMsg.getUserId()))!=null){
		Element element = cache.get(usMsg.getUserId());
		Object value = element.getObjectValue();
		context=(Map<String, Object>) value;
		res=MsgExecution(usMsg,cache);
	
	}
	else{
		
		Element element = new Element(usMsg.getUserId(),context);
		cache.put(element);
		checkUserId.add(usMsg.getUserId());
		res=MsgExecution(usMsg,cache);
	}
		return res;
		
		
	}
	private  Map<String, String> MsgExecution( UserMessage usMsg,Cache cache) throws JsonParseException, JsonMappingException, IOException, InterruptedException{
		ConversationService service = getConvertions();
		Element element = cache.get(usMsg.getUserId());
		Object value = element.getObjectValue();
		 Map<String, Object>  context = ( Map<String, Object> )value;
		MessageRequest request = new MessageRequest.Builder().inputText(usMsg.getUserMsg()).context(context).build();
		MessageResponse response = service.message(WORKSPACE_ID, request).execute();

		context = response.getContext();
		cache.put(new Element(usMsg.getUserId(), context));
		System.out.println("the context is::::::::::::"+context)	;
		
		Map<String, Object> output = response.getOutput();

		
	
	
	
	String vitalStatus;
	Map<String,String> res=new HashMap<String,String>();
	String messageFromUser ="";
	messageFromUser = usMsg.getUserMsg();;
	boolean endConversation = false;
	/*MessageRequest request = new MessageRequest.Builder().inputText(messageFromUser).context(context).build();
	MessageResponse response = service.message(WORKSPACE_ID, request).execute();
	context = response.getContext();
	System.out.println("the context is::::::::::::"+context)	;
	
	Map<String, Object> output = response.getOutput();
	*///System.out.println(output);
	List<String> responseText = (List<String>) output.get("text");
	
	//System.out.println("RESPONSE :: " +response);
	
	ArrayList nodearray = (ArrayList) output.get("nodes_visited");
	String nodeVisted = nodearray.get(0).toString();
	
	
/*	if(nodeVisted.equalsIgnoreCase(superSpo2Node))
	
	{
		tempContext =context;
	}
*/
	// visiting spo2value node
	if(nodeVisted.equalsIgnoreCase(spo2Node))
	{
		String spo2Value = (String) context.get("spo2");
		int num = Integer.parseInt(spo2Value);
		
/*		if(num>100){
			messageFromUser="enter vitals";
			context=tempContext;
			System.out.println("msg came here");
			request = new MessageRequest.Builder().inputText(messageFromUser).context(context).build();
			response = service.message(WORKSPACE_ID, request).execute();
			res.put("response","Please check the reading again. It doesnot seem to be valid." );
			return res;
		}
*/		 vitalStatus= vitalBodySpo2(spo2Value);
		 System.out.println(vitalStatus);
		 if(vitalStatus.equalsIgnoreCase("Abnormal_High") || vitalStatus.equalsIgnoreCase("Abnormal_Low"))
		 {	
			 res.put("response"," Your SpO2 is entered and it is in abnormal range. Please make sure you are following your doctor's advice.Take care and nurse/doctor will soon follow up with you on this reading.");
		 	return res;
		 }
		
		 
		 if(vitalStatus.equalsIgnoreCase("Critical_High") || vitalStatus.equalsIgnoreCase("Critical_Low"))
		 {	
			 res.put("response","Your SPO2 value is entered and it is critical. You need to consult your doctor as early as possible");
		 	return res;
		 }

		 
		 if(vitalStatus.equalsIgnoreCase("Normal")){
			 res.put("response","Your SpO2 is entered and it is in normal range. That is great, keep up the good job!");
			 return res;
		 }
		 
		 else{
				res.put("response",responseText.get(0).toString() +", Your status is " +vitalStatus );
				return res;
			}
		 
		//vitalBodySpo2(spo2Value);
	}
	
   else if(nodeVisted.equalsIgnoreCase(tempNode)){
	   String  tempValue = (String) context.get("Temperature");
	   vitalStatus =vitalBodyTemp(tempValue);
	    System.out.println(vitalStatus);
	    if(vitalStatus.equalsIgnoreCase("Abnormal_High") || vitalStatus.equalsIgnoreCase("Abnormal_Low"))
		 {	
			 res.put("response","Your body temperature is entered and it is in abnormal range. Please make sure you are following your doctor's advice. Take care and a nurse/doctor will soon follow up with you on this reading.");
		 	return res;
		 }
		
		 
		 if(vitalStatus.equalsIgnoreCase("Critical_High") || vitalStatus.equalsIgnoreCase("Critical_Low"))
		 {	
			 res.put("response","Your body temperature is entered and it is critical. You need to consult your doctor as early as possible");
		 	return res;
		 }

		 
		 if(vitalStatus.equalsIgnoreCase("Normal")){
			 res.put("response","Your body temperature is entered and it is in normal range. That is great, keep up the good job!");
			 return res;
		 }
   }
	else if(nodeVisted.equalsIgnoreCase(pulseNode)){
		String  pulseValue = (String) context.get("pulse");
		vitalStatus =vitalBodyPulse(pulseValue);
		 if(vitalStatus.equalsIgnoreCase("Abnormal_High") || vitalStatus.equalsIgnoreCase("Abnormal_Low"))
		 {	
			 res.put("response","Your pulse rate is entered and it is in abnormal range. Please make sure you are following your doctor's advice.Take care and nurse/doctor will soon follow up with you on this reading.");
		 	return res;
		 }
		
		 
		 if(vitalStatus.equalsIgnoreCase("Critical_High") || vitalStatus.equalsIgnoreCase("Critical_Low"))
		 {	
			 res.put("response","Your pulse rate is entered and it is critical. You need to consult your doctor as early as possible");
		 	return res;
		 }

		 
		 if(vitalStatus.equalsIgnoreCase("Normal")){
			 res.put("response","Your pulse rate is entered and it is in normal range. That is great, keep up the good job!");
			 return res;
		 }
	}
	else if(nodeVisted.equalsIgnoreCase(weightNode)){
		String  weightValue = (String) context.get("weight");
		vitalStatus =vitalBodyWeight(weightValue);
		 if(vitalStatus.equalsIgnoreCase("Abnormal_High") || vitalStatus.equalsIgnoreCase("Abnormal_Low"))
		 {	
			 res.put("response", "Your weight is entered and it is above ideal body weight as per your height. Please make sure you are following your doctor's advice. Take care and nurse/doctor will soon follow up with you on this reading.");
		 	return res;
		 }
		
		 
		 if(vitalStatus.equalsIgnoreCase("Critical_High") || vitalStatus.equalsIgnoreCase("Critical_Low"))
		 {	
			 res.put("response", "Your weight  is entered and it is above ideal body weight as per your height. Please make sure you are following your doctor's advice. Take care and nurse/doctor will soon follow up with you on this reading. ");
		 	return res;
		 }

		 
		 if(vitalStatus.equalsIgnoreCase("Normal")){
			 res.put("response","Your body weight is entered and it is within the range for ideal body weight as per your height. That is great, keep up the good job!");
			 return res;
		 }
	}
	
	else if(nodeVisted.equalsIgnoreCase(glucoseNode)){
		String  glucoseValue = (String) context.get("glucose");
		vitalStatus =vitalBodyGlucose(glucoseValue);
		 if(vitalStatus.equalsIgnoreCase("Abnormal_High") || vitalStatus.equalsIgnoreCase("Abnormal_Low"))
		 {	
			 res.put("response","Your blood glucose is entered and it is in abnormal range. Please make sure you are following your doctor's advice.Take care and nurse/doctor will soon follow up with you on this reading.");
		 	return res;
		 }
		
		 
		 if(vitalStatus.equalsIgnoreCase("Critical_High") || vitalStatus.equalsIgnoreCase("Critical_Low"))
		 {	
			 res.put("response","Your blood glucose value is entered and it is critical. You need to consult your doctor as early as possible");
		 	return res;
		 }

		 
		 if(vitalStatus.equalsIgnoreCase("Normal")){
			 res.put("response","Your blood glucose is entered and it is in normal range. That is great, keep up the good job!");
			 return res;
		 } 
	}
	else if(nodeVisted.equalsIgnoreCase(bpNode)){
		String  bpValue = (String) context.get("BP");
		vitalStatus =vitalBodyBp(bpValue);
		 if(vitalStatus.equalsIgnoreCase("Abnormal_High") || vitalStatus.equalsIgnoreCase("Abnormal_Low"))
		 {	
			 res.put("response","Your BP is entered and it is in abnormal range. Please make sure you are following your doctor's advice.Take care and nurse/doctor will soon follow up with you on this reading.");
		 	return res;
		 }
		
		 
		 if(vitalStatus.equalsIgnoreCase("Critical_High") || vitalStatus.equalsIgnoreCase("Critical_Low"))
		 {	
			 res.put("response","Your BP  value is entered and it is critical. You need to consult your doctor as early as possible");
		 	return res;
		 }

		 
		 if(vitalStatus.equalsIgnoreCase("Normal")){
			 res.put("response","Your BP is entered and it is in normal range. That is great, keep up the good job!");
			 return res;
		 }
	}
	
	
/*	((Cache) caches).evict("userContext");
	((Cache) caches).put(userContext, context);
	System.out.println("Context : " + context);
	*/

	if (responseText.size() > 0)
	{
			res.put("response",responseText.get(0).toString() );
			//System.out.println(response);
	} 		
	

	
	return res;
	}	


	private ConversationService getConvertions() {
		ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
		service.setUsernameAndPassword("11467c9f-f8e7-4faf-83b5-daf0251c4f76", "BEfwN7Csun1Z");
		return service;
	}

private String vitalBodySpo2(String spo2Value) throws InterruptedException, JsonParseException, JsonMappingException, IOException {
	//String spo2Value = (String) context.get("spo2");
	
	 String vitalStatus= prePush.pushVitals("spo2", spo2Value, "napier");
	 return vitalStatus;
}

private String vitalBodyTemp (String tempValue) throws JsonParseException, JsonMappingException, IOException{
	//String  tempValue = (String) context.get("Temperature");
	
	 String vitalStatus= prePush.pushVitals("temperature", tempValue, "napier");
	 return vitalStatus;
}

private String vitalBodyPulse (String pulseValue) throws JsonParseException, JsonMappingException, IOException{
//		String  pulseValue = (String) context.get("pulse");
	
	 String vitalStatus= prePush.pushVitals("pulse", pulseValue, "napier");
	 return vitalStatus;
}
private String vitalBodyWeight (String weightValue) throws JsonParseException, JsonMappingException, IOException{
//	String  weightValue = (String) context.get("weight");
	
	 String vitalStatus= prePush.pushVitals("weight", weightValue, "napier");
	 return vitalStatus;
}

private String vitalBodyGlucose (String glucoseValue ) throws JsonParseException, JsonMappingException, IOException{
//	String  glucoseValue = (String) context.get("glucose");
	
	 String vitalStatus= prePush.pushVitals("glucose",glucoseValue, "napier");
	return vitalStatus;
}

private String vitalBodyBp (String bpValue ) throws JsonParseException, JsonMappingException, IOException{

    String vitalStatus=    prePush.pushVitals("temperature", bpValue, "napier");
    return vitalStatus;
}

/*@Cacheable(value="getUserInput" ,key="#message.getUserId()")
public MessageResponse getUserInput(UserMessage message) {
	Map<String,Object> map = new HashMap<String, Object>();                                                
	ConversationService service = getConvertions();
	MessageRequest request = new MessageRequest.Builder().inputText(message.getUserMsg()).context(map).build();
	MessageResponse response = service.message(WORKSPACE_ID, request).execute();
	map = response.getContext();
	System.out.println("the context is::::::::::::"+map);
	return response;
}
*/

	
}
