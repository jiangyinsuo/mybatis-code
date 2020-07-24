/**
 * Copyright ${license.git.copyrightYears} the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.ibatis.executor.statement;

import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * @author Clinton Begin
 * 首先通过 ParameterHandler 完成 SQL 语句的实参绑定，
 * 然后通过 java.sql.Statement 对象执行 SQL 语句并得到结果集，
 * 最后通过 ResultSetHandler 完成结果集的映射，得到结果对象并返回。
 */
public interface StatementHandler {

  Statement prepare(Connection connection, Integer transactionTimeout)
    throws SQLException;

  void parameterize(Statement statement)
    throws SQLException;

  void batch(Statement statement)
    throws SQLException;

  int update(Statement statement)
    throws SQLException;

  <E> List<E> query(Statement statement, ResultHandler resultHandler)
    throws SQLException;

  <E> Cursor<E> queryCursor(Statement statement)
    throws SQLException;

  BoundSql getBoundSql();

  ParameterHandler getParameterHandler();

}
