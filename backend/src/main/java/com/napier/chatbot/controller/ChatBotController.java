package com.napier.chatbot.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.ibm.watson.developer_cloud.conversation.v1.model.MessageResponse;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/app")
@Scope("session")
public class ChatBotController {
	
	
	@Autowired
	IWatsonService IWatsonService;	
	
	
	
	
@RequestMapping(value = "/testProgram", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,String> > receive() {
		
		Map<String,String>res=new HashMap<String,String>();
		res.put("status","success");
	
        System.out.println("Inside receive Message");
		return new ResponseEntity<Map<String,String> >(res, HttpStatus.OK);
		
}
/*@RequestMapping(value="/receiveMessage/{message}", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
public boolean userMsgMethod(@PathVariable ("message") String urlMsg) throws InterruptedException{
	
	UserMessage msg1= new UserMessage();
	
	
	msg1.setUserMsg(urlMsg);
	WatsonMain watson = new WatsonMain();
	watson.getUserInput(msg1.getUserMsg());
	
	watson.conversationVitals();

	System.out.println("Method came upto here");
	return true;
}
*/

@RequestMapping(value="/receiveMessage", method=RequestMethod.POST, produces={ MediaType.APPLICATION_JSON_VALUE })
public ResponseEntity<Map<String,String>> msgRecieved(@RequestBody UserMessage message) throws InterruptedException, JsonParseException, JsonMappingException, IOException{
	Map<String,String> res=new HashMap<String,String>();
		ResponseMessage rsm = new ResponseMessage();
		//HttpSession session = request.getSession();
	
		System.out.println(message.getUserId());
	      res =   IWatsonService.conversationVitals(message);
		
    return new ResponseEntity<Map<String,String> >(res, HttpStatus.OK);
}

@RequestMapping(value="/responseMessage", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
public ArrayList<ResponseMessage> responseMethod(){
	ResponseMessage resMessage = new ResponseMessage();
	
	
	ArrayList<ResponseMessage> res = new ArrayList<ResponseMessage>();
	res.add(resMessage);
	System.out.println("response");
	return res;
}

public void checkContext(String userId){
	
	Cache cache = CacheManager.getInstance().getCache("getUserInput");
	List<String> list = cache.getKeys();
	for (String string : list) {
		
	}
	
	
	
	
}














}