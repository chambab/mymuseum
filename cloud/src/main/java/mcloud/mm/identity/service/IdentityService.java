package mcloud.mm.identity.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mcloud.mm.core.weave.service.AbstractService;

@Service
public class IdentityService extends AbstractService {
	public List selectMyMuseumList(String userId) throws Exception {
		
		Map<String, Object> map = null;
		this.cmmDAO.selectList("test", map);
		return null;
	}
	public List selectUserInfo() throws Exception {
		
		Map<String, Object> map = null;
		List<Map<String, Object>> list = null;
		list = this.cmmDAO.selectList("compute.selectImages", map);
		return list;
	}	
}
