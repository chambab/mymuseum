package com.mm.core.weave.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseController {

	private Log logger = LogFactory.getLog(this.getClass());
	protected Log getLogger() {
		return this.logger;
	}
}
