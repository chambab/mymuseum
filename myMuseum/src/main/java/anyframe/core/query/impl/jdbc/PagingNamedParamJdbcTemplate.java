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

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.SQLExceptionTranslator;
import org.springframework.jdbc.support.lob.LobHandler;

import anyframe.core.query.impl.LiveScrollPaginationVO;
import anyframe.core.query.impl.PaginationVO;
import anyframe.core.query.impl.jdbc.setter.PreparedStatementArgTypeSetter;
import anyframe.core.query.impl.util.NamedParameterUtils;
import anyframe.core.query.impl.util.ParsedSql;

/**
 * @author Soyon Lim
 */
public class PagingNamedParamJdbcTemplate extends NamedParameterJdbcTemplate {
	private PagingJdbcTemplate pagingJdbcTemplate = null;

	// 2009.04.28 
	public List query(String sql, Map data, RowMapper rowMapper,
			PaginationVO paginationVO) throws Exception {

		MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource(
				data);
		SqlParameterSetter sqlParameterSetter = setSqlParameter(sql,
				mapSqlParameterSource, data);
		return pagingJdbcTemplate.queryWithPagination(sqlParameterSetter
				.getSubstitutedSql(), sqlParameterSetter.getArgs(),
				sqlParameterSetter.getArgTypes(), rowMapper, paginationVO);
	}

	public PagingNamedParamJdbcTemplate(PagingJdbcTemplate jdbcTemplate,
			DataSource dataSource) {
		super(dataSource);
		pagingJdbcTemplate = jdbcTemplate;
	}

	public void setExceptionTranslator(
			SQLExceptionTranslator exceptionTranslator) {
		pagingJdbcTemplate.setExceptionTranslator(exceptionTranslator);
		((JdbcTemplate) getJdbcOperations())
				.setExceptionTranslator(exceptionTranslator);
	}

	public void query(String sql, SqlParameterSource sqlParameterSource,
			RowCallbackHandler rch, PaginationVO paginationVO) throws Exception {

		SqlParameterSetter sqlParameterSetter = setSqlParameter(sql,
				sqlParameterSource);
		if (isPaging(paginationVO)) {
			pagingJdbcTemplate.queryWithPagination(sqlParameterSetter
					.getSubstitutedSql(), sqlParameterSetter.getArgs(),
					sqlParameterSetter.getArgTypes(), rch, paginationVO);
		} else {
			pagingJdbcTemplate.query(sqlParameterSetter.getSubstitutedSql(),
					sqlParameterSetter.getArgs(), sqlParameterSetter
							.getArgTypes(), rch);
		}
	}

	public int update(String sql, SqlParameterSource sqlParameterSource,
			LobHandler lobHandler) {
		SqlParameterSetter sqlParameterSetter = setSqlParameter(sql,
				sqlParameterSource);
		return getJdbcOperations().update(
				sqlParameterSetter.getSubstitutedSql(),
				new PreparedStatementArgTypeSetter(
						sqlParameterSetter.getArgs(), sqlParameterSetter
								.getArgTypes(), lobHandler));
	}

	protected boolean isPaging(PaginationVO paginationVO) {
		return (paginationVO.getPageIndex() > 0 && paginationVO.getPageSize() > 0)
				|| (paginationVO instanceof LiveScrollPaginationVO);
	}

	private SqlParameterSetter setSqlParameter(String sql,
			SqlParameterSource sqlParameterSource) {
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		Object[] args = NamedParameterUtils.buildValueArray(parsedSql,
				sqlParameterSource);
		int[] argTypes = NamedParameterUtils.buildSqlTypeArray(parsedSql,
				sqlParameterSource);
		String substitutedSql = NamedParameterUtils.substituteNamedParameters(
				sql, sqlParameterSource);

		return new SqlParameterSetter(substitutedSql, args, argTypes);
	}

	// 2009.04.28
	private SqlParameterSetter setSqlParameter(String sql,
			SqlParameterSource sqlParameterSource, Map paramMap) {
		ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
		Object[] args = NamedParameterUtils.buildValueArray(sql, paramMap);
		int[] argTypes = NamedParameterUtils.buildSqlTypeArray(parsedSql,
				sqlParameterSource);
		String substitutedSql = NamedParameterUtils.substituteNamedParameters(
				sql, sqlParameterSource);

		return new SqlParameterSetter(substitutedSql, args, argTypes);
	}

	private class SqlParameterSetter {

		private Object[] args;
		private int[] argTypes;
		private String substitutedSql;

		public SqlParameterSetter(String substitutedSql, Object[] args,
				int[] argTypes) {
			this.substitutedSql = substitutedSql;
			this.args = args;
			this.argTypes = argTypes;
		}

		public Object[] getArgs() {
			return args;
		}

		public int[] getArgTypes() {
			return argTypes;
		}

		public String getSubstitutedSql() {
			return substitutedSql;
		}
	}
}
