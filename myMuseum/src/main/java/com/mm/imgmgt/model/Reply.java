package com.mm.imgmgt.model;

public class Reply {

	private String rplId = null;
	private String msgId = null;
	private String userId = null;
	private String rplMsgCn = null;
	private String regDt = null;
	private String userImg = null;
	
	
	
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getRplId() {
		return rplId;
	}
	public void setRplId(String rplId) {
		this.rplId = rplId;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getRplMsgCn() {
		return rplMsgCn;
	}
	public void setRplMsgCn(String rplMsgCn) {
		this.rplMsgCn = rplMsgCn;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	
	
}
