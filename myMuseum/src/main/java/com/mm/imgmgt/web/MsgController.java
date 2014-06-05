package com.mm.imgmgt.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import anyframe.common.Page;

import com.mm.core.resource.Msg;
import com.mm.core.weave.web.BaseController;
import com.mm.imgmgt.model.Museum;
import com.mm.imgmgt.model.Reply;
import com.mm.imgmgt.service.MsgService;
import com.mm.user.model.User;
import com.mm.user.service.UserService;
import com.util.CommonUtil;

@Controller
public class MsgController extends BaseController {

	@Autowired
	private MsgService msgService = null;
	@Autowired
	private UserService userService = null;
	/**
	 * 나의 미술관 목록조히
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/1/msg/{userId}/MyMuseumList.json", method=RequestMethod.GET)	
	public @ResponseBody List selectMyMuseumList(@PathVariable String userId) {
		List<Museum> rtnList = null;
		try {
			rtnList = msgService.selectMyMuseumList(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rtnList;
		}
		return rtnList;			
	}	
	
	/**
	 * 메인화면의 뮤지음리스트를 목록으로 제공함
	 * @return
	 */
	@RequestMapping(value="/1/museum/museumlist.json", method=RequestMethod.GET)	
	public @ResponseBody List selectMuseumMain() {		
		try {
			return msgService.selectMuseumMain();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	/**
	 * 나의 미술관과 공개된 미술관만을 조회함
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/1/museum/{userId}/museumPubliclist.json", method=RequestMethod.GET)	
	public @ResponseBody Map<String, List> selectMyMuseumNPublicList(@PathVariable String userId) {
		Map<String, List> rtnMap = new HashMap<String, List>();
		
		List<Museum> rtnList = null;
		List<Map<String, String>> usrList = null;
		try {
			rtnList = msgService.selectMyMuseumNPublicMain(userId);
			usrList = userService.selectUserCnt();
			rtnMap.put("museum", rtnList);
			rtnMap.put("user", usrList);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rtnMap;
		}
		return rtnMap;			
	}	
	/**
	 * 실시간 뮤즘 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/1/museum/museumReallist.json", method=RequestMethod.POST)	
	public @ResponseBody List selectRealMuseumMain(HttpServletRequest request) {		
		try {
			String queryMuseum = request.getParameter("queryMuseum");
			return msgService.selectRealMuseumMain(queryMuseum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}	
	/**
	 * 나의 글 목록조회
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/1/msg/{userId}/MyMuseumMsg.json", method=RequestMethod.POST)	
	public @ResponseBody List getMessageList(@PathVariable String userId) {
				
		List<Museum> rtnList = null;
		try {
			rtnList = msgService.selectMyMessageList(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rtnList;
		}
		return rtnList;
	}
	@RequestMapping(value="/2/msg/{userId}/{pageIndex}/MyMuseumMsg.json", method=RequestMethod.POST)	
	public @ResponseBody Page getMessageList(@PathVariable String userId,
			                                 @PathVariable String pageIndex) {
				
		Page page = null;
		try {
			page = msgService.selectMyMessageList(userId, pageIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return page;
		}
		return page;
	}
	
	/**
	 * 나의 첨부된 이미지 목록을 조회함
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value="/1/msg/{userId}/{pageIndex}/MyImages.json", method=RequestMethod.POST)
	public @ResponseBody Page getMyImages(@PathVariable String userId,
            							  @PathVariable String pageIndex) {

		Page page = null;
		try {
			page = msgService.selectMyImages(userId, pageIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return page;
		}
		return page;
	}
	/**
	 * 미술관이미지 조회
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value="/1/msg/{userId}/{pageIndex}/MyMuseumImages.json", method=RequestMethod.POST)
	public @ResponseBody Page getMyMuseumImages(@PathVariable String userId,
            							  @PathVariable String pageIndex) {

		Page page = null;
		try {
			page = msgService.selectMyMuseumImages(userId, pageIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return page;
		}
		return page;
	}	
	
	/**
	 * 
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/1/msg/{userId}/MyMuseumMsg.json", method=RequestMethod.GET)	
	private @ResponseBody List getMessageListGet(@PathVariable String userId) {
				
		List<Museum> rtnList = null;
		try {
			rtnList = msgService.selectMyMessageList(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rtnList;
		}
		return rtnList;
	}	
	/**
	 * 특정사용자의 메시지를 상세조회함
	 * @param userId
	 * @param msgId
	 * @return
	 */
	@RequestMapping(value="/1/msg/{msgId}/message.json", method=RequestMethod.GET)	
	public @ResponseBody Museum getMessage(@PathVariable("msgId") String msgId) {
				
		Museum museum = new Museum();		
		museum.setMsgId(msgId);
		try {
			museum = msgService.selectMessage(museum);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return museum;
		}
		return museum;
	}
	/**
	 * 검색에 의한 메시지 목록 조회
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/1/search/search.json", method=RequestMethod.POST)
	public @ResponseBody Page getSearchMessage(HttpServletRequest request) {
		String query = request.getParameter("query");
		String pageIndex = request.getParameter("pageIndex");
		
		getLogger().info("SEARCH : " + query);
		Page page = null;
		if(pageIndex == null) pageIndex = "1";
		
		try {
			page = msgService.selectSearchMessageList(query, pageIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return page;
	}
	
	/**
	 * 내가 Follow 하는 사용자의 Subscribe
	 * @param userId
	 * @return
	 */
	@RequestMapping(value="/1/msg/{userId}/FollowMuseumMsg.json", method=RequestMethod.POST)	
	public @ResponseBody List getFollowMuseumMsg(@PathVariable String userId) {
				
		List<Museum> rtnList = null;
		try {
			rtnList = msgService.selectFollowMuseum(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return rtnList;
		}
		return rtnList;
	}
	
	/**
	 * 내가 Follow 하는 사용자의 Subscribe Page 추가
	 * @param userId
	 * @param pageIndex
	 * @return
	 */
	@RequestMapping(value="/2/msg/{userId}/{pageIndex}/FollowMuseumMsg.json", method=RequestMethod.POST)	
	public @ResponseBody Page getFollowMessageList(@PathVariable String userId, 
			                                     @PathVariable String pageIndex) {
				
		Page page = null;
		if(pageIndex == null) pageIndex = "1";
		try {
			page = msgService.selectFollowMessageList(userId, pageIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return page;
		}
		return page;
	}	
	
	/**
	 * Follow 한다
	 * @param mstUserId
	 * @param subUserId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/1/rel/{mstUserId}/{subUserId}/follow.json")
	public @ResponseBody String insertFollow(@PathVariable String mstUserId,
			                                 @PathVariable String subUserId) throws IOException {
		
		int nRtn = 0;
		try {
			nRtn = msgService.insertFollow(mstUserId, subUserId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILURE";
		}
		return "SUCCESS";
	}	
	/**
	 * UnFollow 처리
	 * @param mstUserId
	 * @param subUserId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/1/rel/{mstUserId}/{subUserId}/unfollow.json")
	public @ResponseBody String deleteFollow(@PathVariable String mstUserId,
			                                 @PathVariable String subUserId) throws IOException {
		
		int nRtn = 0;
		try {
			nRtn = msgService.deleteFollow(mstUserId, subUserId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "FAILURE";
		}
		return "SUCCESS";
	}	
	/**
	 * Follow 여부 확인
	 * @param mstUserId
	 * @param subUserId
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/1/rel/{mstUserId}/{subUserId}/isfollow.json")
	public @ResponseBody List getFollow(@PathVariable String mstUserId,
			                              @PathVariable String subUserId) throws IOException {
		List list = null;
		try {
			list = msgService.getFollow(mstUserId, subUserId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}	
	
	@RequestMapping("/1/Msg/MuseumMsg")
	public @ResponseBody String getMuseumMsg() throws IOException {
		return "MuseumMsgs";
	}	
	
	@RequestMapping("/1/Msg/DMMsg")
	public @ResponseBody String getDMMsg() throws IOException {
		return "MuseumMsgs";
	}		
	
	@RequestMapping("/1/Msg/MensionMsg")
	public @ResponseBody String getMensionMsg() throws IOException {
		return "MensionMsg";
	}	
	/**
	 * 글 등록
	 * @param museum
	 * @return
	 */
	@RequestMapping(value="/1/msg/makemsg.json", method=RequestMethod.POST)
	public @ResponseBody String createMsg(@RequestBody Museum museum) {
		Msg msg = new Msg();
		List<String> list = null;
		
		try {
			String[] strTos = CommonUtil.getHashTag(museum.getMsgCn(), "@");
			
			for(String strTo : strTos) {
				list = new ArrayList<String>();
				list.add(strTo.substring(1));
			}	
			if(list == null || list.size() == 0) {
				museum.setUserId(museum.getWriterId());
			} else {
				museum.setUserId(list.get(0));
			}
			
/*			if(museum.getUserId().length() < 1 || museum.getUserId() == null) {
				museum.setUserId(museum.getWriterId());
			}*/
			int nRtn =  msgService.createMsg(museum);
			if(nRtn > 0) {
				//msg.setMessage("RTN", "SUCCESS");
				return "SUCCESS";
			} else {
				return "FAILURE";
				//msg.setMessage("RTN", "FAILUE");				
			}
			//return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//msg.setMessage("RTN", "FAILUE");	
			//return msg;
			return "FAILURE";
		}
	}		
	
	/**
	 * Reply 등록
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/1/msg/makereply.json", method=RequestMethod.POST)	
	public @ResponseBody Map insertRplMessage(HttpServletRequest request) {
			
		User user = null;
		Reply reply = new Reply();
		reply.setMsgId(request.getParameter("msgId"));
		reply.setUserId(request.getParameter("userId"));
		reply.setRplMsgCn(request.getParameter("rplMsgCn"));
		
		Map<String, String> map = null;
		try {
			map = msgService.insertRplMessage(reply);
			user = userService.selectUserInfo(reply.getUserId());
			map.put("userImg", user.getUserImg());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return map;
	}
	
}
