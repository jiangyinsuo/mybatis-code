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
package org.apache.ibatis.submitted.raw_sql_source;

import org.apache.ibatis.BaseDataTest;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.scripting.defaults.RawSqlSource;
import org.apache.ibatis.scripting.xmltags.DynamicSqlSource;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.Reader;

class RawSqlSourceTest {

  private static SqlSessionFactory sqlSessionFactory;

  @BeforeAll
  static void setUp() throws Exception {
    // create an SqlSessionFactory
    try (Reader reader = Resources
      .getResourceAsReader("org/apache/ibatis/submitted/raw_sql_source/mybatis-config.xml")) {
      sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
    }

    // populate in-memory database
    BaseDataTest.runScript(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(),
      "org/apache/ibatis/submitted/raw_sql_source/CreateDB.sql");
  }

  @Test
  void shouldUseRawSqlSourceForAnStaticStatement() {
    test("getUser1", RawSqlSource.class);
  }

  @Test
  void shouldUseDynamicSqlSourceForAnStatementWithInlineArguments() {
    test("getUser2", DynamicSqlSource.class);
  }

  @Test
  void shouldUseDynamicSqlSourceForAnStatementWithXmlTags() {
    test("getUser3", DynamicSqlSource.class);
  }

  private void test(String statement, Class<? extends SqlSource> sqlSource) {
    try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
      Assertions.assertEquals(sqlSource,
        sqlSession.getConfiguration().getMappedStatement(statement).getSqlSource().getClass());
      String sql = sqlSession.getConfiguration().getMappedStatement(statement).getSqlSource().getBoundSql('?').getSql();
      Assertions.assertEquals("select * from users where id = ?", sql);
      User user = sqlSession.selectOne(statement, 1);
      Assertions.assertEquals("User1", user.getName());
    }
  }

}
