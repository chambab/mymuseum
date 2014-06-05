package com.mm.ana.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.ana.service.AnalizeService;
import com.mm.core.weave.web.BaseController;
import com.mm.imgmgt.service.MsgService;

@Controller
public class AnalizeController extends BaseController {
	
	@Autowired
	private AnalizeService analizeService = null;
	
	/**
	 * 사용자 통계
	 * @return
	 */
	@RequestMapping(value="/1/ana/userCnt.json")	
	public @ResponseBody List selectUserCnt() {		
		try {			
			return analizeService.selectUserCnt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	/**
	 * 메시지 통계
	 * @return
	 */
	@RequestMapping(value="/1/ana/msgCnt.json")	
	public @ResponseBody List selectMsgCnt() {		
		try {			
			return analizeService.selectMsgCnt();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}	
	/**
	 * 사용자별 메시지 등록 통계
	 * @return
	 */
	@RequestMapping(value="/1/ana/userMsgCnt.json")	
	public @ResponseBody List selectUserMsgCnt() {		
		try {			
			return analizeService.selectUserMesageAna();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}
	/**
	 * 작성자별 메시지 통계
	 * @return
	 */
	@RequestMapping(value="/1/ana/writerMsgCnt.json")	
	public @ResponseBody List selectWriterMsgCnt() {		
		try {			
			return analizeService.selectWriterMesageAna();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}	
	}	
}
