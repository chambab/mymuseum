package com.mm.core.weave.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

@Repository
public abstract class AbstractIBatisDAO {

	@Autowired
	protected SqlMapClientTemplate sqlMapClientTemplate;
}
