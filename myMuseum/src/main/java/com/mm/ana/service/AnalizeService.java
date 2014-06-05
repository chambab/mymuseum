package com.mm.ana.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.mm.core.weave.service.AbstractService;

@Service
public class AnalizeService extends AbstractService {

	
	/**
	 * 사용자의 증가 통계
	 * 사용자 날짜별 증가 통계
	 * @return
	 * @throws Exception
	 */
	public List selectUserCnt() throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>(); 
		variableMap.put("userSrCode", "002");
		return cmmDAO.getList("analize.selectUserCnt", variableMap);
	}	
	/**
	 * 메시지 날짜별 증가 및 메시지 등록 사용자수
	 * @return
	 * @throws Exception
	 */
	public List selectMsgCnt() throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>(); 
		variableMap.put("userSrCode", "002");
		return cmmDAO.getList("analize.selectMsgCnt", variableMap);
	}		
	/**
	 * 작성자별 통계
	 * @return
	 * @throws Exception
	 */
	public List selectWriterMesageAna() throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>(); 
		variableMap.put("userSrCode", "002");
		return cmmDAO.getList("analize.selectWriterMesageAna", variableMap);		
	}
	/**
	 * 사용자별 통계
	 * @return
	 * @throws Exception
	 */
	public List selectUserMesageAna() throws Exception {
		Map<String, String> variableMap = new HashMap<String, String>(); 
		variableMap.put("userSrCode", "002");
		return cmmDAO.getList("analize.selectUserMesageAna", variableMap);		
	}	
}