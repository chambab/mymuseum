package mcloud.mm.core.weave.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class BaseController {
	private Log logger = LogFactory.getLog(this.getClass());
	protected Log getLogger() {
		return this.logger;
	}
}
