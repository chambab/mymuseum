package mcloud.mm.core.weave.service;


import javax.inject.Inject;

import mcloud.mm.core.weave.dao.CmmDAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class AbstractService {
	
	protected Log logger = LogFactory.getLog(this.getClass());	
	@Inject
	protected CmmDAO cmmDAO;	
}
