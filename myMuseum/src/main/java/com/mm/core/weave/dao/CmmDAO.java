package com.mm.core.weave.dao;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import anyframe.common.Page;
import anyframe.core.query.AbstractDAO;
import anyframe.core.query.IQueryService;
import anyframe.core.query.QueryServiceException;

/**
 * <pre>
 * 설       명 : Anyframe 공통 DAO 클래스
 * &#064;author 홍 정 환
 * &#064;since 2011. 2. 22.
 * &#064;version 1.0
 * @see
 * 
 * <pre>
 * &lt;&lt; 개정이력(Modification Information) &gt;&gt;
 *   
 *   수정일         	수정자          	 수정내용
 *  -------       --------    ---------------------------
 *  2011. 2. 22.  	홍정환    	 최초 생성
 * 
 * </pre>
 */

@Repository
public class CmmDAO extends AbstractDAO {
	@Value("#{contextProperties['pageSize'] ?: 10}")
	int pageSize;

	@Value("#{contextProperties['pageUnit'] ?: 10}")
	int pageUnit;

	@Inject
	public void setQueryService(IQueryService queryService) {
		super.setQueryService(queryService);
		super.setFindListPostfix("");
		super.setFindPrefix("");
		super.setUpdateId("");
		super.setRemoveId("");
		super.setCreateId("");
		super.setFindByPkPostfix("");
	}

	/**
	 * Paging 조회
	 * 
	 * @param queryId
	 * @param variableMap
	 * @return
	 * @throws Exception
	 */
	public Page getPagingList(String queryId, Map variableMap, int pageIndex)
			throws Exception {
		return getPagingList(queryId, variableMap, pageIndex, pageSize);
	}

	public Page getPagingList(String queryId, Map variableMap, int pageIndex,
			int pageSize) throws Exception {
		return getPagingList(queryId, variableMap, pageIndex, pageSize, pageUnit);
	}

	public Page getPagingList(String queryId, Map variableMap, int pageIndex,
			int pageSize, int pageUnit) throws Exception {
		return findListWithPaging(queryId, variableMap, pageIndex, pageSize,
				pageUnit);
		
	}

	/**
	 * 단건 조회
	 * 
	 * @param queryId
	 * @param variableMap
	 * @return
	 * @throws Exception
	 */
	public List get(String queryId, Map variableMap)
			throws QueryServiceException {
		return (List) findByPk(queryId, variableMap);
	}

	/**
	 * List 조회
	 * 
	 * @param queryId
	 * @param variableMap
	 * @return
	 * @throws Exception
	 */
	public List getList(String queryId, Map variableMap) throws Exception {
		return (List) findList(queryId, variableMap);
	}

	/**
	 * Insert Query 수행
	 * 
	 * @param queryId
	 * @param variableList
	 * @return
	 * @throws Exception
	 */
	public int insert(String queryId, List variableList) throws Exception {
		return create(queryId, variableList);
	}

	/**
	 * Insert Query 수행
	 * 
	 * @param queryId
	 * @param variableMap
	 * @return
	 * @throws Exception
	 */
	public int insert(String queryId, Map variableMap) throws Exception {
		return create(queryId, variableMap);
	}

	/**
	 * Update Query 수행
	 * 
	 * @param queryId
	 * @param variableList
	 * @return
	 * @throws Exception
	 */
	public int modify(String queryId, List variableList) throws Exception {
		return update(queryId, variableList);
	}

	/**
	 * Update Query 수행
	 * 
	 * @param queryId
	 * @param variableMap
	 * @return
	 * @throws Exception
	 */
	public int modify(String queryId, Map variableMap) throws Exception {
		return update(queryId, variableMap);
	}

	/**
	 * Delete Query 수행
	 * 
	 * @param queryId
	 * @param variableList
	 * @return
	 * @throws Exception
	 */
	public int delete(String queryId, List variableList) throws Exception {
		return remove(queryId, variableList);
	}

	/**
	 * Delete Query 수행
	 * 
	 * @param queryId
	 * @param variableMap
	 * @return
	 * @throws Exception
	 */
	public int delete(String queryId, Map variableMap) throws Exception {
		return remove(queryId, variableMap);
	}

	/**
	 * Callable Statement(Function , Stored Procedure 수행)
	 * 
	 * @param queryId
	 * @param variableMap
	 * @throws Exception
	 */
	public Map execute(String queryId, Map variableMap) throws Exception {
		return getQueryService().execute(queryId, variableMap);
	}

	/**
	 * batch update 수행
	 * 
	 * @param queryId
	 * @param variableList
	 * @throws Exception
	 */
	public int[] batchUpdate(String queryId, List variableList)
			throws QueryServiceException {
		return getQueryService().batchUpdate(queryId, variableList);
	}

}
