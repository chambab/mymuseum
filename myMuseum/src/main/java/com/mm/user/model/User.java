package com.mm.user.model;

/**
 * 사용자 Resource
 * @author chambab
 *
 */
public class User {

	/**
	 * 사용자 기본정보
	 */
	private String regId = null;
	private String userId = null;
	private String userNm = null;
	private String userPw = null;
	private String usrPw1 = null;
	private String usrPw2 = null;
	private String usrEmail = null;
	
	private String userSrCode = null;
	private String cn = null;
	private String webUrl = null;
	private String openYn = null;
	private String regDt = null;
	private String regInfo = null;
	private String userImg = null;
	
	/**
	 * 사용자의 Follow 정보조회
	 */
	
	
	private String followerCnt = null;
	
	
	
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}
	public String getUserImg() {
		return userImg;
	}
	public void setUserImg(String userImg) {
		this.userImg = userImg;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUsrPw1() {
		return usrPw1;
	}
	public void setUsrPw1(String usrPw1) {
		this.usrPw1 = usrPw1;
	}
	public String getUsrPw2() {
		return usrPw2;
	}
	public void setUsrPw2(String usrPw2) {
		this.usrPw2 = usrPw2;
	}
	public String getUsrEmail() {
		return usrEmail;
	}
	public void setUsrEmail(String usrEmail) {
		this.usrEmail = usrEmail;
	}
	private String followingCnt = null;
	private String messageCnt = null;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserNm() {
		return userNm;
	}
	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}
	public String getUserSrCode() {
		return userSrCode;
	}
	public void setUserSrCode(String userSrCode) {
		this.userSrCode = userSrCode;
	}
	public String getCn() {
		return cn;
	}
	public void setCn(String cn) {
		this.cn = cn;
	}
	public String getWebUrl() {
		return webUrl;
	}
	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}
	public String getOpenYn() {
		return openYn;
	}
	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}
	public String getRegDt() {
		return regDt;
	}
	public void setRegDt(String regDt) {
		this.regDt = regDt;
	}
	public String getRegInfo() {
		return regInfo;
	}
	public void setRegInfo(String regInfo) {
		this.regInfo = regInfo;
	}
	public String getFollowerCnt() {
		return followerCnt;
	}
	public void setFollowerCnt(String followerCnt) {
		this.followerCnt = followerCnt;
	}
	public String getFollowingCnt() {
		return followingCnt;
	}
	public void setFollowingCnt(String followingCnt) {
		this.followingCnt = followingCnt;
	}
	public String getMessageCnt() {
		return messageCnt;
	}
	public void setMessageCnt(String messageCnt) {
		this.messageCnt = messageCnt;
	}
	
	
}
