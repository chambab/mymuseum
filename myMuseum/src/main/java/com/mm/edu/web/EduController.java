package com.mm.edu.web;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mm.core.parse.ParseHandler;
import com.mm.edu.model.EduQufyServiceVO;
import com.mm.edu.service.EduService;

@Controller
public class EduController {
	
	@Inject
	private EduService eduService = null;
	
	
	@RequestMapping("/mm/eduQufyService")
	public @ResponseBody String getQufyService(@ModelAttribute("qufyService") 
			                                   EduQufyServiceVO qufyService) {
		String rtnMsg = null;
		ArrayList<EduQufyServiceVO> list = (ArrayList<EduQufyServiceVO>) 
		                                 eduService.getQufyService(qufyService.getWelMbdNm(), qufyService.getWelMbdIdeNo());
		try {
			rtnMsg = ParseHandler.parseObject(list);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rtnMsg;
	}
}
