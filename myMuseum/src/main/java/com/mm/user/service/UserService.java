package com.mm.user.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mm.core.weave.service.AbstractService;
import com.mm.imgmgt.dao.MsgDAO;
import com.mm.imgmgt.model.Museum;
import com.mm.imgmgt.model.RegnInfo;
import com.mm.user.dao.UserDAO;
import com.mm.user.model.Rel;
import com.mm.user.model.User;

@Service
public class UserService  extends AbstractService {
	
	@Inject
	private MsgDAO msgDAO = null;
	@Inject
	private UserDAO userDAO = null;
	/**
	 * 사용자 정보조회
	 * @param userId
	 * @return
	 */
	public User selectUserInfo(String userId) throws Exception  {
		return userDAO.getUserInfo(userId);		
	}
	/**
	 * 사용자 001, 뮤지음 002의 총 카운트를 리턴함
	 * @return
	 * @throws Exception
	 */
	public List selectUserCnt() throws Exception {
		Map variableMap = new HashMap();
		return this.cmmDAO.getList("user.selectUserCnt", variableMap);
	}
	/**
	 * 사용자의 Follower 목록을 리턴한다
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List selectMyFollowList(String userId) throws Exception {
		Map variableMap = new HashMap();
		variableMap.put("userId", userId);
		return this.cmmDAO.getList("user.selectMyFollowList", variableMap);
	}
	/**
	 * 사용자의 Following 목록을 리턴한다
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List selectMyFollowingList(String userId) throws Exception {
		Map variableMap = new HashMap();
		variableMap.put("userId", userId);
		return this.cmmDAO.getList("user.selectMyFollowingList", variableMap);
	}	
	/**
	 * Login시 사용하는 비밀번호로 사용자 조회
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public User selectUserInfoByPw(User user) throws Exception {
		this.logger.info("selectUserInfoByPw : " + user.toString());		
		User rtnUser = userDAO.getUserInfoByPw(user.getUserId());
		if(rtnUser == null)
			return null;
		
		StandardPasswordEncoder encoder = new StandardPasswordEncoder("secret.m.mymuseum.co.kr");
		if(encoder.matches(user.getUserPw(), rtnUser.getUserPw())) {
			return rtnUser;
		} else {
			return null;
		}
	}	
	
	/**
	 * 사용자등록
	 * @param user
	 * @return
	 */
	public int createUser(User user) throws Exception  {
		try {

		StandardPasswordEncoder encoder = new StandardPasswordEncoder("secret.m.mymuseum.co.kr");
		
		String encPasswd = encoder.encode(user.getUserPw());
		user.setUserPw(encPasswd);
		
		Rel rel = new Rel();
		rel.setMstUserId(user.getUserId());
		rel.setSubUserId(user.getUserId());
		
		userDAO.createUser(user);
		return userDAO.createRel(rel);
		} catch(Exception e) {
			return 0;
		}
	}
	/**
	 * 나의 미술관 생성
	 * @param user
	 * @return
	 */
	public int createMuseum(Museum museum) throws Exception  {
		Rel rel = new Rel();
		try {
			String regnId = (msgDAO.selectRegnId()).getRegnInfId();
			Map<String, String> map = new HashMap<String, String>();
			
			// 미술관 생성
			museum.setRegnInfId(regnId);	
			museum.setOpenYn(museum.getOpenYnTmp());
			userDAO.createMuseum(museum);
			// 관계 생성
			rel.setMstUserId(museum.getUserId());
			rel.setSubUserId(museum.getRegId());
			userDAO.createRel(rel);
			
			// 위치정보 등록
			map.put("regnInfId", regnId);
			map.put("latitude", museum.getLatitude());
			map.put("longitude", museum.getLongitude());
			map.put("compass", museum.getCompass());
			
		
			return cmmDAO.insert("museum.insertRegnInfo", map);
			
		} catch(Exception e) {
			return 0;		
		}
	}	
}
