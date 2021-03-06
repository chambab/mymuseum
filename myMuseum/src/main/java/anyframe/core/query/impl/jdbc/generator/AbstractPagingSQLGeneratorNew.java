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


/**
 * @author Soyon Lim
 */
public abstract class AbstractPagingSQLGeneratorNew implements IPagingSQLGenerator {

    private Object[] args = null;
    private int[] argTypes = null;

    public abstract String getPaginationSQL(String originalSql,
            Object[] originalArgs, int[] originalArgTypes, int first,
            int pageSize) throws Exception;

    /**
     * Generate sql for counting total size of result
     * rows
     * Postgresql에서 alias 없음 오류로 CNT 추가
     * @param originalSql
     *        original SQL
     * @return generated SQL
     */
    public String getCountSQL(String originalSql) {
        StringBuffer sql = new StringBuffer("SELECT count(*) FROM ( ");
        sql.append(originalSql);
        sql.append(" ) CNT ");
        return sql.toString();
    }

    public int[] getArgTypes() {
        return this.argTypes;
    }

    public Object[] getArgs() {
        return this.args;
    }

    protected void setArgs(Object[] args) {
        this.args = args;
    }

    protected void setArgTypes(int[] argTypes) {
        this.argTypes = argTypes;
    }

}
