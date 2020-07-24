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
package org.apache.ibatis.executor.keygen;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;

import java.sql.Statement;

/**
 * @author Clinton Begin
 * 主键生成器接口
 */
public interface KeyGenerator {

  /**
   * SQL执行前
   *
   * @param executor  Executor
   * @param ms        MappedStatement
   * @param stmt      Statement
   * @param parameter Object
   */
  void processBefore(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

  /**
   * SQL执行后
   *
   * @param executor  Executor
   * @param ms        MappedStatement
   * @param stmt      Statement
   * @param parameter Object
   */
  void processAfter(Executor executor, MappedStatement ms, Statement stmt, Object parameter);

}
