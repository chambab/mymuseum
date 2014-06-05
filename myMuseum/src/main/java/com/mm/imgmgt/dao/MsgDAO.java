package com.mm.imgmgt.dao;

import java.util.List;

import org.anyframe.query.dao.AbstractDao;
import org.springframework.stereotype.Repository;

import com.mm.core.weave.dao.AbstractIBatisDAO;
import com.mm.imgmgt.model.Museum;

@Repository
public class MsgDAO extends  AbstractIBatisDAO { //AbstractDao 

	public int createMsg(Museum museum) throws Exception {		
		return this.sqlMapClientTemplate.update("createMsg", museum);			
	}
	public List selectMyMessageList(String userId) throws Exception {
		return this.sqlMapClientTemplate.queryForList("selectMyMessageList", userId);
	}
	public List selectFollowMuseum(String userId) throws Exception {
		return this.sqlMapClientTemplate.queryForList("selectFollowMuseum", userId);
	}	
	/**
	 * QueryService로 변경
	 * @param museum
	 * @return
	 * @throws Exception
	 */
	public Museum selectMessage(Museum museum) throws Exception {		
		return (Museum) this.sqlMapClientTemplate.queryForObject("selectMessage", museum);
	}
	public Museum selectMsgId() throws Exception {
		return (Museum) this.sqlMapClientTemplate.queryForObject("selectMsgId");
	}
	public Museum selectRegnId() throws Exception {
		return (Museum) this.sqlMapClientTemplate.queryForObject("selectRegnId");
	}		
	/**
	 * 나의 미술관 목록 조회
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	public List selectMyMuseumList(String userId) throws Exception {		
		return this.sqlMapClientTemplate.queryForList("selectMyMuseum", userId);			
	}		
}
