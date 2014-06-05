package com.mm.core.weave.service;

import javax.inject.Inject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.mm.core.weave.dao.CmmDAO;

public class AbstractService {
	
	protected Log logger = LogFactory.getLog(this.getClass());	
	
	@Inject
	protected CmmDAO cmmDAO;
}
