/*
 * Copyright 2002-2008 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package anyframe.core.query.impl.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.util.Assert;

import anyframe.core.query.IRowCallbackHandler;
import anyframe.core.query.impl.LiveScrollPaginationVO;
import anyframe.core.query.impl.PaginationVO;
import anyframe.core.query.impl.jdbc.generator.IPagingSQLGenerator;
import anyframe.core.query.impl.jdbc.lob.AnyframeOracle8iLobHandler;
import anyframe.core.query.impl.jdbc.setter.PreparedStatementArgSetter;
import anyframe.core.query.impl.jdbc.setter.PreparedStatementArgTypeSetter;
import anyframe.core.query.impl.util.SQLTypeTransfer;

/**
 * extend from Spring's JdbcTemplate, with pagination function.
 * 
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public class PagingJdbcTemplate extends JdbcTemplate {

	private IPagingSQLGenerator paginationSQLGetter;

	protected Integer maxFetchSize = null;

	public PagingJdbcTemplate() { 
		super();
	}

	public PagingJdbcTemplate(DataSource dataSource) {
		super(dataSource);
	}

	public void setPaginationSQLGetter(IPagingSQLGenerator paginationSQLGetter) {
		this.paginationSQLGetter = paginationSQLGetter;
	}

	// 2009.04.28
	public IPagingSQLGenerator getPaginationSQLGetter() {
		return paginationSQLGetter;
	}

	public Integer getMaxFetchSize() {
		return maxFetchSize;
	}

	public void setMaxFetchSize(Integer maxFetchSize) {
		this.maxFetchSize = maxFetchSize;
	}

	// 2009.04.28
	public List query(String sql, PreparedStatementSetter pss,
			RowMapper rowMapper, PaginationVO paginationVO) {
		if (pss == null)
			return (List) query(new PagingPreparedStatementCreator(sql),
					new PagingRowMapperResultSetExtractor(rowMapper,
							paginationVO));

		return (List) query(new PagingPreparedStatementCreator(sql), pss,
				new PagingRowMapperResultSetExtractor(rowMapper, paginationVO));
	}

	/**
	 * 정의된 SQLGenerator가 없을 경우 입력된 SQL을 그대로 실행한다. 정의된 SQL Generator가 있을 경우 입력된
	 * SQL 실행 결과에 대한 전체 건수와 페이징 처리된 결과를 구한다.
	 * 
	 * @param sql
	 *            query statement
	 * @param args
	 *            a set of variable values for executing query
	 * @param argTypes
	 *            is matched with input parameters. A type must belong to fields
	 *            defined java.sql.Types package. *
	 * @param rowMapper
	 *            조회 결과로부터 하나의 Row를 특정 객체 형태에 매핑하기 위한 Mapper
	 * @param context
	 *            페이징 처리를 위해 필요한 기본 정보
	 * @return 쿼리 수행 결과
	 */
	public List queryWithPagination(String sql, Object[] args, int[] argTypes,
			RowMapper rowMapper, PaginationVO paginationVO) throws Exception {
		if (paginationSQLGetter == null)
			return query(sql, args, argTypes, rowMapper, paginationVO);

		if (paginationVO.isCountRecordSize()) {
			long recordCount = executeCountSQL(sql, args, argTypes);
			paginationVO.setRecordCount(recordCount);
			paginationVO.setPageIndexToLast();
		}

		// generate pagination sql
		String paginationSql = getPaginationSQL(sql, args, argTypes,
				paginationVO);
		Object[] paginationArgs = getPaginationArgs();
		int[] paginationArgTypes = getPaginationArgTypes();

		return query(paginationSql, paginationArgs, paginationArgTypes,
				rowMapper);
	}

	public void queryWithPagination(String sql, Object[] args, int[] argTypes,
			RowCallbackHandler rch, PaginationVO paginationVO) throws Exception {
		if (paginationSQLGetter.getArgs() == null) {
			query(new PagingPreparedStatementCreator(sql),
					new PreparedStatementArgTypeSetter(args, argTypes, null),
					new PagingRowCallbackHandlerResultSetExtractor(rch,
							paginationVO));
			return;
		}

		if (paginationVO.isCountRecordSize()) {
			long recordCount = queryForLong(getCountSQL(sql), args, argTypes);
			paginationVO.setRecordCount(recordCount);
			paginationVO.setPageIndexToLast();
		}

		//	TODO paginationArgs is null when the method, getPaginationArgs() is called
		//  TODO chambab
		// generate pagination sql
		String paginationSql = getPaginationSQL(sql, args, argTypes,
				paginationVO);
		Object[] paginationArgs = getPaginationArgs();
		int[] paginationArgTypes = getPaginationArgTypes();

		query(paginationSql, paginationArgs, paginationArgTypes, rch);
		// TODO EDIT
		//query(paginationSql, args, argTypes, rch);
	}

	/**
	 * 정의된 SQLGenerator가 없을 경우 입력된 SQL을 그대로 실행한다. 정의된 SQL Generator가 있을 경우 입력된
	 * SQL 실행 결과에 대한 전체 건수와 페이징 처리된 결과를 구한다.
	 * 
	 * @param sql
	 *            query statement
	 * @param rowMapper
	 *            조회 결과로부터 하나의 Row를 특정 객체 형태에 매핑하기 위한 Mapper
	 * @param context
	 *            페이징 처리를 위해 필요한 기본 정보
	 * @return 쿼리 수행 결과
	 */
	public List queryWithPagination(String sql, RowMapper rowMapper,
			PaginationVO paginationVO) throws Exception {
		if (paginationSQLGetter == null)
			return query(sql, null, null, rowMapper, paginationVO);
		if (paginationVO.isCountRecordSize()) {
			long recordCount = executeCountSQL(sql, null, null);
			paginationVO.setRecordCount(recordCount);
			paginationVO.setPageIndexToLast();
		}

		String paginationSql = getPaginationSQL(sql, new Object[0], new int[0],
				paginationVO);
		Object[] paginationArgs = getPaginationArgs();

		return query(paginationSql, paginationArgs, rowMapper);
	}

	/**
	 * 정의된 SQLGenerator가 없을 경우 입력된 SQL을 그대로 실행한다. 정의된 SQL Generator가 있을 경우 입력된
	 * SQL 실행 결과에 대한 전체 건수와 페이징 처리된 결과를 구한다.
	 * 
	 * @param sql
	 *            query statement
	 * @param args
	 *            a set of variable values for executing query
	 * @param rowMapper
	 *            조회 결과로부터 하나의 Row를 특정 객체 형태에 매핑하기 위한 Mapper
	 * @param context
	 *            페이징 처리를 위해 필요한 기본 정보
	 * @return 쿼리 수행 결과
	 */
	public List queryWithPagination(String sql, Object[] args,
			RowMapper rowMapper, PaginationVO paginationVO) throws Exception {
		if (paginationSQLGetter == null)
			return query(sql, args, null, rowMapper, paginationVO);

		if (paginationVO.isCountRecordSize()) {
			long recordCount = executeCountSQL(sql, args, null);
			paginationVO.setRecordCount(recordCount);
			paginationVO.setPageIndexToLast();
		}

		// generate pagination sql
		String paginationSql = getPaginationSQL(sql, args, new int[0],
				paginationVO);
		Object[] paginationArgs = getPaginationArgs();

		return query(paginationSql, paginationArgs, rowMapper);
	}

	public List queryForListWithPagination(String sql, Object[] args,
			int[] argTypes, PaginationVO paginationVO) throws Exception {
		return queryWithPagination(sql, args, argTypes,
				getColumnMapRowMapper(), paginationVO);
	}

	/**
	 * Generic method to execute the update given arguments. All other update()
	 * methods invoke this method.
	 * 
	 * @return the number of rows affected by the update
	 */
	// 2008.05.08 - add for Handling Lob of Oracle 8i
	public int update(String sql, Object[] values, LobHandler lobHandler,
			String lobStatement, String[] lobTypes, Object[] lobKeys,
			Object[] lobValues) {
		int updateCount = update(sql, values);
		LinkedList lobParameters = null;
		lobParameters = new LinkedList();
		for (int i = 0; i < lobTypes.length; i++) {
			int type = SQLTypeTransfer.getSQLType(lobTypes[i].toUpperCase());
			lobParameters.add(new SqlParameter(type));
		}

		PreparedStatementCreatorFactory preparedStatementFactory = new PreparedStatementCreatorFactory(
				lobStatement, lobParameters);

		query(preparedStatementFactory.newPreparedStatementCreator(lobKeys),
				new Oracle8iResultSetExtractor(
						(AnyframeOracle8iLobHandler) lobHandler, lobValues));

		return updateCount;
	}

	// 2008.05.22 - query override
	// 2008.07.21 reopen - cf.) need to fix
	// ResultSetMapperSupport VO byte[] -
	// not getBytes() --> BLOB
	public void query(String sql, Object[] args, int[] argTypes,
			RowCallbackHandler rch) {
		if (paginationSQLGetter.getArgs() == null) {
			query(new PagingPreparedStatementCreator(sql),
					new PreparedStatementArgTypeSetter(args, argTypes, null),
					new NonPagingRowCallbackHandlerResultSetExtractor(rch));
			return;
		}

		query(sql, new PreparedStatementArgTypeSetter(args, argTypes, null),
				new NonPagingRowCallbackHandlerResultSetExtractor(rch));
	}

	/** ************* PROTECTED METHODS ************** */

	protected String getPaginationSQL(String originalSql, Object[] args,
			int[] argTypes, PaginationVO context) throws Exception {
		int pageIndex = context.getPageIndex();
		int pageSize = context.getPageSize();
		return paginationSQLGetter.getPaginationSQL(originalSql, args,
				argTypes, pageIndex, pageSize);
	}

	protected Object[] getPaginationArgs() {
		return paginationSQLGetter.getArgs();
	}

	protected int[] getPaginationArgTypes() {
		return paginationSQLGetter.getArgTypes();
	}

	// 2008.05.08, 2009.04.28
	public String getCountSQL(String originalSql) {
		return paginationSQLGetter.getCountSQL(originalSql);
	}

	/** ************* PRIVATE METHODS ************** */

	private List query(String sql, Object[] args, int[] argTypes,
			RowMapper rowMapper, PaginationVO paginationVO) {
		if (args == null)
			return (List) query(new PagingPreparedStatementCreator(sql),
					new PagingRowMapperResultSetExtractor(rowMapper,
							paginationVO));
		if (argTypes == null)
			return (List) query(new PagingPreparedStatementCreator(sql),
					new PreparedStatementArgSetter(args),
					new PagingRowMapperResultSetExtractor(rowMapper,
							paginationVO));
		return (List) query(new PagingPreparedStatementCreator(sql),
				new PreparedStatementArgTypeSetter(args, argTypes, null),
				new PagingRowMapperResultSetExtractor(rowMapper, paginationVO));
	}

	// 2009.04.28 : method modifier private -> public,
	// return type int -> long
	public long executeCountSQL(String sql, Object[] args, int[] argTypes) {
		if (args == null)
			return queryForLong(getCountSQL(sql));
		if (argTypes == null)
			return queryForLong(getCountSQL(sql), args);
		return queryForLong(getCountSQL(sql), args, argTypes);
	}

	/** ************* INNER CLASSES ************** */

	/**
	 * Simple adapter for PreparedStatementCreator, allowing to use a plain SQL
	 * statement.
	 */
	private static class PagingPreparedStatementCreator implements
			PreparedStatementCreator {

		private final String sql;

		public PagingPreparedStatementCreator(String sql) {
			Assert.notNull(sql, "Query Service : SQL must not be null");
			this.sql = sql;
		}

		public PreparedStatement createPreparedStatement(Connection conn)
				throws SQLException {
			return conn.prepareStatement(this.sql,
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
		}
	}

	class PagingRowMapperResultSetExtractor implements ResultSetExtractor {

		private final RowMapper rowMapper;

		private PaginationVO paginationVO;

		public PagingRowMapperResultSetExtractor(RowMapper rowMapper,
				PaginationVO paginationVO) {
			Assert.notNull(rowMapper, "Query Service : RowMapper is required");
			this.rowMapper = rowMapper;
			this.paginationVO = paginationVO;
		}

		public Object extractData(ResultSet rs) throws SQLException {
			int pageIndex = paginationVO.getPageIndex();
			int pageSize = paginationVO.getPageSize();
			rs.last();
			paginationVO.setRecordCount(rs.getRow());
			if (pageIndex == 1) {
				rs.beforeFirst();
			} else if (pageIndex > 1) {
				int pageCount = paginationVO.getPageCount();
				// 2009.05.12
				if (pageCount < pageIndex) {
					rs.last();
				} else {
					rs.absolute((pageIndex - 1) * pageSize);
				}
			}
			List results = new ArrayList();
			int rowNum = 0;

			// 2009.04.28 - maxFetchSize
			if (maxFetchSize == null) {
				while (rs.next() && rowNum < pageSize) {
					results.add(this.rowMapper.mapRow(rs, rowNum++));
				}
			} else {
				while (rs.next() && rowNum < pageSize) {
					if (rowNum > maxFetchSize.intValue()) {
						throw new DataRetrievalFailureException(
								"Too many data in ResultSet. maxFetchSize is "
										+ maxFetchSize);
					}
					results.add(this.rowMapper.mapRow(rs, rowNum++));
				}
			}
			return results;
		}
	}

	// 2008.05.08 - add for Handling Lob of Oracle 8i
	private class Oracle8iResultSetExtractor implements ResultSetExtractor {

		private AnyframeOracle8iLobHandler lobHandler;

		private Object[] lobValues;

		public Oracle8iResultSetExtractor(
				AnyframeOracle8iLobHandler lobHandler, Object[] lobValues) {
			this.lobHandler = lobHandler;
			this.lobValues = lobValues;
		}

		public Object extractData(ResultSet rs) throws SQLException {
			ResultSetMetaData meta = rs.getMetaData();
			while (rs.next()) {
				for (int i = 1; i < meta.getColumnCount() + 1; i++) {
					Object tObj = lobValues[i - 1];
					if (tObj instanceof String) {
						lobHandler.setClobOutputValue(rs, i, (String) tObj);
					} else if (tObj instanceof byte[]) {
						lobHandler.setBlobOutputValue(rs, i, (byte[]) tObj);
					}
				}
			}

			return null;
		}
	}

	/**
	 * @author Administrator
	 */
	private class NonPagingRowCallbackHandlerResultSetExtractor implements
			ResultSetExtractor {

		private final RowCallbackHandler rch;

		public NonPagingRowCallbackHandlerResultSetExtractor(
				RowCallbackHandler rch) {
			this.rch = rch;
		}

		public Object extractData(ResultSet rs) throws SQLException {
			int rowNum = 0;

			if (rch instanceof IRowCallbackHandler) {
				((IRowCallbackHandler) rch).processMetaData(rs);
			}

			// 2009.04.28 - maxFetchSize
			if (maxFetchSize == null) {
				while (rs.next()) {
					this.rch.processRow(rs);
					rowNum++;
				}
			} else {
				while (rs.next()) {
					if (rowNum > maxFetchSize.intValue()) {
						throw new DataRetrievalFailureException(
								"Too many data in ResultSet. maxFetchSize is "
										+ maxFetchSize);
					}
					this.rch.processRow(rs);
					rowNum++;
				}
			}

			return null;
		}
	}

	/**
	 * @author Administrator
	 */
	private class PagingRowCallbackHandlerResultSetExtractor implements
			ResultSetExtractor {

		private final RowCallbackHandler rch;
		private PaginationVO paginationVO;

		public PagingRowCallbackHandlerResultSetExtractor(
				RowCallbackHandler rch, PaginationVO paginationVO) {
			this.rch = rch;
			this.paginationVO = paginationVO;
		}

		public Object extractData(ResultSet rs) throws SQLException {

			int pageIndex = paginationVO.getPageIndex();
			int pageSize = paginationVO.getPageSize();
			rs.last();
			paginationVO.setRecordCount(rs.getRow());
			if (paginationVO instanceof LiveScrollPaginationVO) {
				int targetIndex = ((LiveScrollPaginationVO) paginationVO)
						.getStartIndex();
				if (targetIndex == 0) {
					rs.beforeFirst();
				} else {
					rs.absolute(targetIndex);
				}
			} else {
				if (pageIndex == 1) {
					rs.beforeFirst();
				} else if (pageIndex > 1) {

					int pageCount = paginationVO.getPageCount();
					if (pageCount < pageIndex) {
						rs.next();
						// rs.absolute((pageCount - 1) * pageSize);
					} else {
						rs.absolute((pageIndex - 1) * pageSize);
					}
				}
			}

			int rowNum = 0;

			// 2008.04.11 - 데이터가 없는 경우라도 meta data 설정은
			// 필요할 수 있음.
			// processMetaData 처리는 각 rch 의 구현에 따름. 현재
			// Gauce 에만 구현
			if (rowNum == 0 && this.rch instanceof IRowCallbackHandler) {
				((IRowCallbackHandler) this.rch).processMetaData(rs);
			}

			// 2009.04.28 - maxFetchSize
			if (maxFetchSize == null) {
				while (rs.next() && rowNum < pageSize) {
					this.rch.processRow(rs);
					rowNum++;
				}
			} else {
				while (rs.next() && rowNum < pageSize) {
					if (rowNum > maxFetchSize.intValue()) {
						throw new DataRetrievalFailureException(
								"Too many data in ResultSet maxFetchSize is "
										+ maxFetchSize);
					}
					this.rch.processRow(rs);
					rowNum++;
				}
			}

			return null;
		}
	}
}
