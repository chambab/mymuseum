package com.mm.core.resource;

public class Msg {

	private String msgKey;
	private String msgValue;
	public String getMsgKey() {
		return msgKey;
	}
	public void setMsgKey(String msgKey) {
		this.msgKey = msgKey;
	}
	public String getMsgValue() {
		return msgValue;
	}
	public void setMsgValue(String msgValue) {
		this.msgValue = msgValue;
	}
	public void setMessage(String msgKey, String msgValue) {
		this.msgKey = msgKey;
		this.msgValue = msgValue;
	}
}
