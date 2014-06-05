package com.mm.user.dao;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.mm.core.weave.dao.AbstractIBatisDAO;
import com.mm.imgmgt.model.Museum;
import com.mm.user.model.Rel;
import com.mm.user.model.User;

@Repository
public class UserDAO extends  AbstractIBatisDAO { //AbstractDao

	public User getUserInfo(String userId) {
		User user = (User) this.sqlMapClientTemplate.queryForObject("selectUser", userId);
		return user;
	}
	public User getUserInfoByPw(String userId) throws Exception {
		User rtnuser = (User) this.sqlMapClientTemplate.queryForObject("selectUserByPw", userId);
		return rtnuser;
	}	
	/**
	 * Following
	 * @param userId
	 * @return
	 */
	public Collection<User> getFollowingUserList(String userId) {
		return null;
	}
	
	public int createUser(User user) throws Exception {		
		return this.sqlMapClientTemplate.update("createUser", user);			
	}
	public int createMuseum(Museum museum) throws Exception {		
		return this.sqlMapClientTemplate.update("createMuseum", museum);			
	}	
	public int createRel(Rel rel) throws Exception {		
		return this.sqlMapClientTemplate.update("createRel", rel);			
	}


}
