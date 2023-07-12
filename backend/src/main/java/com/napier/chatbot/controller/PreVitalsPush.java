package com.napier.chatbot.controller;

import com.napier.chatbot.integration.IntegrationImpl;

public class PreVitalsPush {
	private String spo2Push;
	private String pulsePush;
	private String bpPush;
	private String weightPush;
	private String glucosePush;
	private String tempPush;
	
	
	
	public String getSpo2Push() {
		return spo2Push;
	}
	public void setSpo2Push(String spo2Push) {
		this.spo2Push = spo2Push;
		IntegrationImpl push = new IntegrationImpl() ;
		push.pushVitals("spo2", this.spo2Push, "napier");
		//System.out.println(this.spo2Push);
	}
	public String getPulsePush() {
		return pulsePush;
	}
	
	public void setPulsePush(String pulsePush) {
		this.pulsePush = pulsePush;
		IntegrationImpl push = new IntegrationImpl() ;
		push.pushVitals("pulse", this.pulsePush, "napier"); /// heartrate check it
	}
	public String getBpPush() {
		return bpPush;
	}
	public void setBpPush(String bpPush) {
		this.bpPush = bpPush;
		IntegrationImpl push = new IntegrationImpl() ;
		push.pushVitals("bp", this.bpPush, "napier");
	}
	public String getWeightPush() {
		return weightPush;
	}
	public void setWeightPush(String weightPush) {
		this.weightPush = weightPush;
		IntegrationImpl push = new IntegrationImpl() ;
		push.pushVitals("weight", this.weightPush, "napier");
	}
	public String getGlucosePush() {
		return glucosePush;
	}
	public void setGlucosePush(String glucosePush) {
		this.glucosePush = glucosePush;
		IntegrationImpl push = new IntegrationImpl() ;
		push.pushVitals("glucose", this.glucosePush, "napier");
		
	}
	public String getTempPush() {
		return tempPush;
	}
	public void setTempPush(String tempPush) {
		this.tempPush = tempPush;
		IntegrationImpl push = new IntegrationImpl() ;
		push.pushVitals("temperature", this.tempPush, "napier");
		
	}
	
	
}
