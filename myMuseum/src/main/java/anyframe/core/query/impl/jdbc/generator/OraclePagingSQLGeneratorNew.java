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
package anyframe.core.query.impl.jdbc.generator;

import java.sql.Types;

/**
 * oracle implements of PagingSQLGenerator
 * 
 * Postgresql 문법으로 변경으로 인한 수정
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public class OraclePagingSQLGeneratorNew extends AbstractPagingSQLGeneratorNew {
	public String getPaginationSQL(String originalSql, Object[] originalArgs,
			int[] originalArgTypes, int pageIndex, int pageSize) {
		//StringBuffer sql = new StringBuffer(
		//		" SELECT * FROM ( SELECT   INNER_TABLE.* , ROWNUM AS ROW_SEQ FROM ( \n");
		// Postgresql query
		StringBuffer sql = new StringBuffer(
				" SELECT * FROM ( SELECT   INNER_TABLE.* , ROW_NUMBER() OVER () AS ROW_SEQ FROM ( \n");		
		sql.append(originalSql);

		//sql.append(" ) INNER_TABLE WHERE ROWNUM <= ? )  WHERE ROW_SEQ BETWEEN ? AND ?");
		// Postgresql 
		sql.append(" ) INNER_TABLE ) AA WHERE ROW_SEQ BETWEEN ? AND ?");

		setQueryArgs(originalArgs, pageIndex, pageSize);
		setQueryArgTypes(originalArgTypes);
		return sql.toString();
	}

	//protected void setQueryArgs(Object[] originalArgs, int pageIndex,
	//		int pageSize) {
	public String[] setQueryArgs(Object[] originalArgs, int pageIndex,
			int pageSize) {		
		Object[] args = new Object[originalArgs.length + 3];

		for (int i = 0; i < originalArgs.length; i++) {
			args[i] = originalArgs[i];
		}

		args[originalArgs.length] = String.valueOf(new Long(pageIndex
				* pageSize));
		args[originalArgs.length + 1] = String.valueOf(new Long((pageIndex - 1)
				* pageSize + 1));
		args[originalArgs.length + 2] = String.valueOf(new Long(pageIndex
				* pageSize));

		setArgs(args);
		return null;
	}

	//protected void setQueryArgTypes(int[] originalArgTypes) {
	public int[] setQueryArgTypes(int[] originalArgTypes) {
		int[] argTypes = new int[originalArgTypes.length + 3];

		for (int i = 0; i < originalArgTypes.length; i++) {
			argTypes[i] = originalArgTypes[i];
		}

		argTypes[originalArgTypes.length] = Types.VARCHAR;
		argTypes[originalArgTypes.length + 1] = Types.VARCHAR;
		argTypes[originalArgTypes.length + 2] = Types.VARCHAR;

		setArgTypes(argTypes);
		
		return new int[0];
	}
}
