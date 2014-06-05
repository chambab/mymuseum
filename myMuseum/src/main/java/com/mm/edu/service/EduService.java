package com.mm.edu.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.inject.Inject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.mm.core.weave.service.AbstractService;
import com.mm.edu.dao.EduDAO;
import com.mm.edu.model.EduQufyServiceVO;

@Service
public class EduService extends AbstractService {
	
	@Inject
	private EduDAO eduDAO = null;
	
	private EduQufyServiceVO eduVO = null;
	
	public Collection<EduQufyServiceVO> getQufyService(String welMbdNm, String welMbdIdeNo) {
		
		
		Collection<EduQufyServiceVO> col = null;
		
		eduVO = new EduQufyServiceVO();
		eduVO.setWelMbdNm(welMbdNm);
		eduVO.setWelMbdIdeNo(welMbdIdeNo);
		
		col = this.eduDAO.getEduQufyService(eduVO);
	    
		
		return col;
	}

}
