package com.mm.imgmgt.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mm.core.weave.service.AbstractService;
/**
 * 이미지 관련 서비스 클래스
 * @author chambab
 *
 */
@Service
public class ImgService   extends AbstractService {

	/**
	 * ImgID NEXTVAL 값을 Return한다
	 * @return
	 * @throws Exception
	 */
	public String selectImgId() throws Exception {
		Map variableMap = new HashMap();		
		List <Map<String, String>> list = this.cmmDAO.getList("museum.selectImgId", variableMap);
		Map<String, String> map = list.get(0);
		String imgId = map.get("imgId");
		return imgId;
	}
	
	/**
	 * 개인 프로파일 이미지 등록
	 * @param imgUrl
	 * @return
	 * @throws Exception
	 */
	public int insertProfileImg(HashMap paramMap) throws Exception {
		return this.cmmDAO.insert("museum.insertProfileImg", paramMap);		
	}
	
	/**
	 * 메시지에 해당하는 이미지 목록을 조회함
	 * @param msgId
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, String>> selectImageList(String msgId) throws Exception  {
		Map<String, String> map = new HashMap<String, String>();
		map.put("msgId", msgId);
		return this.cmmDAO.getList("museum.selectImageList", map);
	}
}
