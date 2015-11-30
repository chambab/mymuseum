package com.mm.imgmgt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import anyframe.common.Page;

import com.mm.core.weave.service.AbstractService;
import com.mm.imgmgt.dao.MsgDAO;
import com.mm.imgmgt.model.Museum;
import com.mm.imgmgt.model.Reply;
import com.mm.imgmgt.model.Tag;
import com.util.CommonUtil;

@Service
public class MsgService  extends AbstractService {

	@Inject
	private MsgDAO msgDAO;
	@Inject
	private ImgService imgService;
	
	public int createMsg(Museum museum) throws Exception {
		
		List<String> list = null;
		
		String cn = museum.getMsgCn();		
		String[] strTag = CommonUtil.getHashTag(cn, "#");
		String[] strTag2 = CommonUtil.getHashTag(cn, "$");		
		
		String msgId = (msgDAO.selectMsgId()).getMsgId();
		museum.setMsgId(msgId);
		
		// 메시지 등록
		msgDAO.createMsg(museum);

		
		// Tag 등록
		ArrayList<Map<String, String>> paramList = new ArrayList<Map<String, String>>();
		Map<String, String> map = null;
		
		for(String tag : strTag) {
			map = new HashMap<String, String>();
			map.put("msgId", msgId);
			map.put("tagNm", tag);
			
			paramList.add(map);
		}
		for(String tag : strTag2) {
			map = new HashMap<String, String>();
			map.put("msgId", msgId);
			map.put("tagNm", tag);
			
			paramList.add(map);
		}	
			
		
		//	Image 등록
		ArrayList<Map<String, String>> imgParamList = new  ArrayList<Map<String, String>>();
		Map<String, String> imgMap = null;
		
		int i=0;
		List<String> imgIdList = museum.getImgId();
		if(imgIdList != null) {
			for(String imgId: imgIdList) {
				imgMap = new HashMap<String, String>();
				if(imgId.equals("IMSI")) {
					i++;
					continue;
				}
				
				imgMap.put("imgId", imgId);
				imgMap.put("userId", museum.getUserId());
				imgMap.put("msgId", msgId);
				imgMap.put("imgNm", "");
				imgMap.put("imgCn", "");
				imgMap.put("imgUrl", museum.getImgUrl().get(i));
				i++;
				
				this.cmmDAO.modify("museum.insertImage", imgMap);
				imgParamList.add(imgMap);
			}
			//this.cmmDAO.batchUpdate("museum.insertImage", imgParamList);
			
		}
		
		//this.cmmDAO.batchUpdate("museum.insertTag", paramList);
		for(int j=0; j < paramList.size(); j++) {
			this.cmmDAO.modify("museum.insertTag", paramList.get(j));
			
		}
		
		
		return 1;
	}
	/**
	 * 내가 등록한 아이템 목록을 조회함
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List selectMyMessageList(String userId) throws Exception {
		return msgDAO.selectMyMessageList(userId);
	}
	/**
	 * 내가 등록한 메시지 조회 페이지 포함
	 * @param userId
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	public Page selectMyMessageList(String userId, String pageIndex) throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put("userId", userId);
		return this.cmmDAO.getPagingList("museum.selectMyMessageList", variableMap, Integer.parseInt(pageIndex));
	}	
	/**
	 * 내가 등록한 아이템 목록을 조회함
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List selectFollowMuseum(String userId) throws Exception {
		this.logger.info("selectFollowMuseum userId : [" + userId + "]");	
		return msgDAO.selectFollowMuseum(userId);
	}
	
	/**
	 * 내가 Follow 하고 있는 메시지 목록을 조회함 (페이지 추가)
	 * @param userId
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	public Page selectFollowMessageList(String userId, String pageIndex) throws Exception {
		this.logger.info("selectFollowMessageList userId : [" + userId + "]");
		
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put("userId", userId);
		return this.cmmDAO.getPagingList("museum.selectFollowMessageList", variableMap, 
				                         Integer.parseInt(pageIndex));
	}
	
	/**
	 * 내가 등록한 Images의 목록을 조회함 (페이지 추가)
	 * @param userId
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	public Page selectMyImages(String userId, String pageIndex) throws Exception {
		this.logger.info("selectMyImages userId : [" + userId + "]");
		
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put("userId", userId);
		return this.cmmDAO.getPagingList("museum.selectMyImages", variableMap, 
				                         Integer.parseInt(pageIndex));
	}
	
	/**
	 * 미술관 이미지 조회
	 * @param userId
	 * @param pageIndex
	 * @return
	 * @throws Exception
	 */
	public Page selectMyMuseumImages(String userId, String pageIndex) throws Exception {
		this.logger.info("selectMyImages userId : [" + userId + "]");
		
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put("userId", userId);
		return this.cmmDAO.getPagingList("museum.selectMyMuseumImages", variableMap, 
				                         Integer.parseInt(pageIndex));
	}	
	
	/**
	 * userId, msgId로 메시지조회, 이미지도 조회
	 * @param museum
	 * @return
	 * @throws Exception
	 */
	public Museum selectMessage(Museum museum) throws Exception {
		this.logger.info("selectFollowMuseum userId : [" + museum.getMsgId() + "]");
				
		
		Museum mymuseum = null;
		mymuseum = msgDAO.selectMessage(museum);
		mymuseum.setImageList(imgService.selectImageList(museum.getMsgId()));
								
		
		Map<String, String> variableMap = new HashMap<String, String>();
		variableMap.put("msgId", museum.getMsgId());
		
		mymuseum.setReplyList(cmmDAO.getList("museum.selectRplMessage", variableMap));
		
		cmmDAO.modify("museum.updateViewCnt", variableMap);
		return mymuseum;
	}	
	
	/**
	 * 검색에 의한 메시지 목록 조회
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public Page selectSearchMessageList(String query, String pageIndex) throws Exception {
		HashMap<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("query", query);
		
		return this.cmmDAO.getPagingList("museum.selectSearchMessageList", paramMap, Integer.parseInt(pageIndex));
				
	}
	
	/**
	 * 나의 미술관 목록조회
	 * @param userId
	 * @return
	 */
	public List selectMyMuseumList(String userId) throws Exception {
		return msgDAO.selectMyMuseumList(userId);
	}
	
	public List selectMuseumMain() throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>(); 
		variableMap.put("userSrCode", "002");
		return cmmDAO.getList("museum.selectMuseumMain", variableMap);
	}
	/**
	 * 공개된 미술관과 나의 미술관만 조회
	 * @return
	 * @throws Exception
	 */
	public List selectMyMuseumNPublicMain(String userId) throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>(); 		
		variableMap.put("userId", userId);
		return cmmDAO.getList("museum.selectMyMuseumNPublicMain", variableMap);
	}	
	/**
	 * 실시간 뮤즘 조회
	 * @param queryMuseum
	 * @return
	 * @throws Exception
	 */
	public List selectRealMuseumMain(String queryMuseum) throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>(); 
		variableMap.put("userSrCode", "002");
		variableMap.put("queryMuseum", queryMuseum);
		return cmmDAO.getList("museum.selectRealMuseumMain", variableMap);
	}	
	
	/**
	 * Follow 하다
	 * @param mstUserId
	 * @param subUserId
	 * @return
	 * @throws Exception
	 */
	public int insertFollow(String mstUserId, String subUserId) throws Exception {
		Map<String, String> variableList = new HashMap<String, String>();
		variableList.put("mstUserId", mstUserId);
		variableList.put("subUserId", subUserId);
		
		return this.cmmDAO.insert("museum.insertFollow", variableList);
	}
	/**
	 * Follow 여부 확인
	 * @param mstUserId
	 * @param subUserId
	 * @return
	 * @throws Exception
	 */
	public List getFollow(String mstUserId, String subUserId) throws Exception {
		Map<String, String> variableList = new HashMap<String, String>();
		variableList.put("mstUserId", mstUserId);
		variableList.put("subUserId", subUserId);
		
		return this.cmmDAO.getList("museum.getFollow", variableList);		
	}
	/**
	 * Unfollow
	 * @param mstUserId
	 * @param subUserId
	 * @return
	 * @throws Exception
	 */
	public int deleteFollow(String mstUserId, String subUserId) throws Exception {
		Map<String, String> variableList = new HashMap<String, String>();
		variableList.put("mstUserId", mstUserId);
		variableList.put("subUserId", subUserId);
		
		return this.cmmDAO.insert("museum.deleteFollow", variableList);		
	}	
	
	/**
	 * Reply 등록
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> insertRplMessage(Reply reply) throws Exception {
		Map<String, String> variableList = new HashMap<String, String>();
		variableList.put("msgId", reply.getMsgId());
		variableList.put("userId", reply.getUserId());
		variableList.put("rplMsgCn", reply.getRplMsgCn());
		
		this.cmmDAO.insert("museum.insertRplMessage", variableList);
		return variableList;
	}
}
