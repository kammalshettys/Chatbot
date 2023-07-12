package com.napier.chatbot.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/app")
public class TestingController {
@RequestMapping(value="/testMsg" , method=RequestMethod.GET)
public ArrayList<UserMessage> testMsg(){
	
	UserMessage usm = new UserMessage();
	usm.setUserMsg("hie");
	ArrayList<UserMessage> u1 = new ArrayList<UserMessage>();
	u1.add(usm);
	return u1;
}
}
