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
 * The pagination SQL generator.
 * @author SoYon Lim
 * @author JongHoon Kim
 */
public interface IPagingSQLGenerator {

    /**
     * get the pagination SQL basing the original SQL
     * and the first record index and the page size.
     * @param originalSql
     *        original SQL
     * @param originalArgs
     *        original arguments
     * @param originalArgTypes
     *        original argument types
     * @param first
     *        first record index
     * @param pageSize
     *        page size
     * @return pagination SQL
     */
    String getPaginationSQL(String originalSql, Object[] originalArgs,
            int[] originalArgTypes, int first, int pageSize) throws Exception;

    /**
     * get the count SQL basing the original SQL.
     * @param originalSql
     *        original SQL
     * @return count SQL
     */
    String getCountSQL(String originalSql);

    /**
     * get arguments for pagination SQL
     * @return arguments
     */
    Object[] getArgs();

    /**
     * get argument types for pagination SQL
     * @return arguments
     */
    int[] getArgTypes();

}
