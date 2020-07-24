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
package org.apache.ibatis.executor;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.cursor.Cursor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.Transaction;

import java.sql.SQLException;
import java.util.List;

/**
 * @author Clinton Begin
 * 主要负责维护一级缓存和二级缓存，并提供事务管理的相关操作，
 * 它会将数据库相关操作委托给 StatementHandler 完成
 * 执行器接口
 * 本地缓存就是一级缓存
 */
public interface Executor {

  /**
   * 空 ResultHandler 对象的枚举
   */
  ResultHandler NO_RESULT_HANDLER = null;

  /**
   * 更新 or 插入 or 删除，由传入的 MappedStatement 的 SQL 所决定
   *
   * @param ms        MappedStatement
   * @param parameter Object
   * @return int
   * @throws SQLException SQLException
   */
  int update(MappedStatement ms, Object parameter) throws SQLException;

  /**
   * 查询，带 ResultHandler + CacheKey + BoundSql
   *
   * @param ms            MappedStatement
   * @param parameter     Object
   * @param rowBounds     RowBounds
   * @param resultHandler ResultHandler
   * @param cacheKey      CacheKey
   * @param boundSql      BoundSql
   * @param <E>           E
   * @return E
   * @throws SQLException SQLException
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler, CacheKey cacheKey, BoundSql boundSql) throws SQLException;

  /**
   * 查询，带 ResultHandler
   *
   * @param ms            MappedStatement
   * @param parameter     Object
   * @param rowBounds     RowBounds
   * @param resultHandler ResultHandler
   * @param <E>           E
   * @return E
   * @throws SQLException SQLException
   */
  <E> List<E> query(MappedStatement ms, Object parameter, RowBounds rowBounds, ResultHandler resultHandler) throws SQLException;

  /**
   * 查询，返回值为 Cursor
   *
   * @param ms        MappedStatement
   * @param parameter Object
   * @param rowBounds RowBounds
   * @param <E>       E
   * @return E
   * @throws SQLException SQLException
   */
  <E> Cursor<E> queryCursor(MappedStatement ms, Object parameter, RowBounds rowBounds) throws SQLException;

  /**
   * 刷入批处理语句
   *
   * @return List<BatchResult>
   * @throws SQLException SQLException
   */
  List<BatchResult> flushStatements() throws SQLException;

  /**
   * 提交事务
   *
   * @param required TF
   * @throws SQLException SQLException
   */
  void commit(boolean required) throws SQLException;

  /**
   * 回滚事务
   *
   * @param required TF
   * @throws SQLException SQLException
   */
  void rollback(boolean required) throws SQLException;

  /**
   * 创建 CacheKey 对象
   *
   * @param ms              MappedStatement
   * @param parameterObject Object
   * @param rowBounds       RowBounds
   * @param boundSql        BoundSql
   * @return CacheKey
   */
  CacheKey createCacheKey(MappedStatement ms, Object parameterObject, RowBounds rowBounds, BoundSql boundSql);

  /**
   * 判断是否缓存
   *
   * @param ms  MappedStatement
   * @param key CacheKey
   * @return TF
   */
  boolean isCached(MappedStatement ms, CacheKey key);

  /**
   * 清除本地缓存
   */
  void clearLocalCache();

  /**
   * 延迟加载
   *
   * @param ms           MappedStatement
   * @param resultObject MetaObject
   * @param property     String
   * @param key          CacheKey
   * @param targetType   Class<?>
   */
  void deferLoad(MappedStatement ms, MetaObject resultObject, String property, CacheKey key, Class<?> targetType);

  /**
   * 获得事务
   *
   * @return Transaction
   */
  Transaction getTransaction();

  /**
   * 关闭事务
   *
   * @param forceRollback TF
   */
  void close(boolean forceRollback);

  /**
   * 判断事务是否关闭
   *
   * @return TF
   */
  boolean isClosed();

  /**
   * 设置包装的 Executor 对象
   *
   * @param executor Executor
   */
  void setExecutorWrapper(Executor executor);

}
