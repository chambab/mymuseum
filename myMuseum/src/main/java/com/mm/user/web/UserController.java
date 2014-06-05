package com.mm.user.web;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.core.parse.ParseHandler;
import com.mm.imgmgt.model.Museum;
import com.mm.user.model.User;
import com.mm.user.service.UserService;

@Controller
public class UserController {

	private Log logger = LogFactory.getLog(this.getClass());	
	
	@Inject
	private UserService userService;
	
	@RequestMapping(value="/1/user/{userId}/follower.json", method=RequestMethod.POST)
	public @ResponseBody List selectMyFollowList(@PathVariable String userId) throws Exception {
		return userService.selectMyFollowList(userId);			
	}
	/**
	 * 내가 따르는 대상 조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/1/user/{userId}/following.json", method=RequestMethod.POST)
	public @ResponseBody List selectMyFollowingList(@PathVariable String userId) throws Exception {
		return userService.selectMyFollowingList(userId);			
	}	
	/**
	 * 나의미술관생성
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/1/User/MakeMyMuseum")
	public @ResponseBody String createMyMuseum() throws IOException {
		return "MuseumMsgs";
	}	
	/**
	 * 나의미술관조회(카페목록조회)
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/1/User/MyMuseum")
	public @ResponseBody String listMyMuseum() throws IOException {
		return "MuseumMsgs";
	}		
	/**
	 * 사용자생성
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/1/user/makeuser.json", method=RequestMethod.POST)
	public @ResponseBody String createUser(@RequestBody User user) throws IOException {
		user.setUserPw(user.getUsrPw1());
		int rtn = 0;
		try {
			rtn = userService.createUser(user);
		} catch(Exception e) {
			logger.debug("ERROR in createMuseum : " + e.toString());
		}		
		if(rtn > 0) {
			return "SUCCESS";
		} else {
			return "Failure";
		}
	}	
	/**
	 * 나의 미술관 생성
	 * @param user
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/1/user/makeMuseum.json", method=RequestMethod.POST)
	public @ResponseBody String createMuseum(@RequestBody Museum museum) {
		int rtn = 0;
		try {
			rtn = userService.createMuseum(museum);
		} catch(Exception e) {
			logger.debug("ERROR in createMuseum : " + e.toString());
		}
		if(rtn > 0) {
			return "SUCCESS";
		} else {
			return "Failure";
		}
	}		
	/**
	 * 사용자정보조회
	 * @return
	 * @throws Exception 
	 */	
	@RequestMapping(value="/1/user/{userId}/profile.json", method=RequestMethod.POST)
	public @ResponseBody User selectUserInfo(@PathVariable String userId) throws Exception {
		User user = userService.selectUserInfo(userId);		
		return user;
	}
	/**
	 * 사용자 존재여부를 return 한다
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/1/user/{userId}/user.json", method=RequestMethod.GET)
	public @ResponseBody String selectUserInfoJson(@PathVariable String userId) throws Exception {
		String rtnMsg = null;
		User rtnUser = userService.selectUserInfo(userId);
		if(rtnUser == null) {
			//return "not exist";
			rtnMsg = "not exist";
		} else {
			rtnMsg = "exist";
			//return "exist";
		}
		return rtnMsg;
	}	
	/**
	 * 사용자목록조회(Following)
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/1/User/Follow")
	public @ResponseBody String createRelsInfo() throws IOException {
		return "MuseumMsgs";
	}	
	
	@RequestMapping("/1/User/DelRelsInfo")
	public @ResponseBody String deleteRelsInfo() throws IOException {
		return "MuseumMsgs";
	}
	
	@RequestMapping(value="/1/user/loginuser.json", method=RequestMethod.POST)
	public @ResponseBody User loginUser(@RequestBody User user) throws IOException {
		User rtnUser = null;
		try {
			rtnUser = userService.selectUserInfoByPw(user);
		} catch(Exception e) {
			logger.debug(e.toString());
		}
		
		if(rtnUser == null) {
			rtnUser = new User();
			rtnUser.setUserId("FAIL");			
		} else {

		}
		return rtnUser;
	}	
}
