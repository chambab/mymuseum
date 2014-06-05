package com.mm.edu.dao;

import java.util.Collection;

import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.mem.ImgSnd;
import com.mm.core.weave.dao.AbstractJdbcDAO;
import com.mm.edu.model.EduQufyServiceVO;

@Repository
public class EduDAO extends AbstractJdbcDAO {
	
	public Collection<EduQufyServiceVO> getEduQufyService(EduQufyServiceVO eduVO) {
		String sql = "SELECT A.WEL_MBD_ID, A.WEL_MBD_NM, A.WEL_MBD_IDE_NO, " +
					 "       B.BNF_SERV_ST_YMD, B.BNF_SERV_END_YMD, B.SERV_ID, C.SERV_NM " +
					 "FROM   UWFDTB0201 A, UWFHTB0110 B, UWFSTB1000 C " +
					 "WHERE  A.WEL_MBD_ID=B.BNF_MBD_ID " + 
					 "AND    B.SERV_ID=C.SERV_ID ";					
		Collection<EduQufyServiceVO> col = null;		
		//Object[] params = {eduVO.getWelMbdNm(), eduVO.getWelMbdIdeNo()};
		Object[] params = {};
		col = this.jdbcTemplate.query(sql, params, 
				ParameterizedBeanPropertyRowMapper.newInstance(EduQufyServiceVO.class));
		return col;
	}
}
