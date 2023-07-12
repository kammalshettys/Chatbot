package com.napier.chatbot.controller;

public class UserMessage {
private String userMsg;
private int patientId;
private String tenantName;

private String userId;




public String getUserId() {
	return userId;
}

public void setUserId(String userId) {
	this.userId = userId;
}

public String getUserMsg() {
	return userMsg;
}

public void setUserMsg(String userMsg) {
	this.userMsg = userMsg;
}

public int getPatientId() {
	return patientId;
}

public void setPatientId(int patientId) {
	this.patientId = patientId;
}

public String getTenantName() {
	return tenantName;
}

public void setTenantName(String tenantName) {
	this.tenantName = tenantName;
}


}
