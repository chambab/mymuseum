package com.mm.imgmgt.model;

import java.util.List;
import java.util.Map;

import com.mm.user.model.User;

public class Museum extends User {
		
	private String msgId = null;
	private String msgCn = null;
	private String writerId = null;
	private String imgRegDt = null;
	private String latitude = null;
	private String longitude = null;
	private String regnInfId = null;
	private String compass = null;
	private String userInfo = null;
	private String openYn = null;
	private String openYnTmp = null;
	
	
	private List<String> imgUrl = null;
	private List<String> imgId = null;
	
	private List<Map<String, String>> imageList = null;
	private List<Map<String, String>> replyList = null;
		 

	public String getOpenYnTmp() {
		return openYnTmp;
	}

	public void setOpenYnTmp(String openYnTmp) {
		this.openYnTmp = openYnTmp;
	}

	public String getOpenYn() {
		return openYn;
	}

	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}

	public List<Map<String, String>> getReplyList() {
		return replyList;
	}

	public void setReplyList(List<Map<String, String>> replyList) {
		this.replyList = replyList;
	}

	public String getUserInfo() {
		return userInfo;
	}

	public void setUserInfo(String userInfo) {
		this.userInfo = userInfo;
	}

	public List<Map<String, String>> getImageList() {
		return imageList;
	}

	public void setImageList(List<Map<String, String>> imageList) {
		this.imageList = imageList;
	}

	public List<String> getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(List<String> imgUrl) {
		this.imgUrl = imgUrl;
	}

	public List<String> getImgId() {
		return imgId;
	}

	public void setImgId(List<String> imgId) {
		this.imgId = imgId;
	}

	public String getCompass() {
		return compass;
	}

	public void setCompass(String compass) {
		this.compass = compass;
	}

	public String getRegnInfId() {
		return regnInfId;
	}

	public void setRegnInfId(String regnInfId) {
		this.regnInfId = regnInfId;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getMsgId() {
		return msgId;
	}

	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}

	public String getImgRegDt() {
		return imgRegDt;
	}

	public void setImgRegDt(String imgRegDt) {
		this.imgRegDt = imgRegDt;
	}

	public String getWriterId() {
		return writerId;
	}

	public void setWriterId(String writerId) {
		this.writerId = writerId;
	}

	public String getMsgCn() {
		return msgCn;
	}

	public void setMsgCn(String msgCn) {
		this.msgCn = msgCn;
	}

	
}
