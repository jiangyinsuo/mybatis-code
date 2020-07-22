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
package org.apache.ibatis.annotations;

import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.mapping.StatementType;

import java.lang.annotation.*;

/**
 * The annotation that specify options for customizing default behaviors.
 *
 * <p>
 * <b>How to use:</b>
 *
 * <pre>
 * public interface UserMapper {
 *   &#064;Option(useGeneratedKeys = true, keyProperty = "id")
 *   &#064;Insert("INSERT INTO users (name) VALUES(#{name})")
 *   boolean insert(User user);
 * }
 * </pre>
 *
 * @author Clinton Begin
 * 操作可选项
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(Options.List.class)
public @interface Options {
  /**
   * Returns whether use the 2nd cache feature if assigned the cache.
   *
   * @return {@code true} if use; {@code false} if otherwise
   * 是否使用缓存
   */
  boolean useCache() default true;

  /**
   * Returns the 2nd cache flush strategy.
   *
   * @return the 2nd cache flush strategy
   * 刷新缓存的策略
   */
  FlushCachePolicy flushCache() default FlushCachePolicy.DEFAULT;

  /**
   * Returns the result set type.
   *
   * @return the result set type
   * 结果类型
   */
  ResultSetType resultSetType() default ResultSetType.DEFAULT;

  /**
   * Return the statement type.
   *
   * @return the statement type
   * 语句类型
   */
  StatementType statementType() default StatementType.PREPARED;

  /**
   * Returns the fetch size.
   *
   * @return the fetch size
   * 加载数量
   */
  int fetchSize() default -1;

  /**
   * Returns the statement timeout.
   *
   * @return the statement timeout
   * 超时时间
   */
  int timeout() default -1;

  /**
   * Returns whether use the generated keys feature supported by JDBC 3.0
   *
   * @return {@code true} if use; {@code false} if otherwise
   * 是否生成主键
   */
  boolean useGeneratedKeys() default false;

  /**
   * Returns property names that holds a key value.
   * <p>
   * If you specify multiple property, please separate using comma(',').
   * </p>
   *
   * @return property names that separate with comma(',')
   * 主键在 Java 类中的属性
   */
  String keyProperty() default "";

  /**
   * Returns column names that retrieves a key value.
   * <p>
   * If you specify multiple column, please separate using comma(',').
   * </p>
   *
   * @return column names that separate with comma(',')
   * 主键在数据库中的字段
   */
  String keyColumn() default "";

  /**
   * Returns result set names.
   * <p>
   * If you specify multiple result set, please separate using comma(',').
   * </p>
   *
   * @return result set names that separate with comma(',')
   * 结果集
   */
  String resultSets() default "";

  /**
   * @return A database id that correspond this options
   * @since 3.5.5
   */
  String databaseId() default "";

  /**
   * The options for the {@link Options#flushCache()}.
   * The default is {@link FlushCachePolicy#DEFAULT}
   */
  enum FlushCachePolicy {
    /**
     * <code>false</code> for select statement; <code>true</code> for insert/update/delete statement.
     */
    DEFAULT,
    /**
     * Flushes cache regardless of the statement type.
     */
    TRUE,
    /**
     * Does not flush cache regardless of the statement type.
     */
    FALSE
  }

  /**
   * The container annotation for {@link Options}.
   *
   * @author Kazuki Shimizu
   * @since 3.5.5
   */
  @Documented
  @Retention(RetentionPolicy.RUNTIME)
  @Target(ElementType.METHOD)
  @interface List {
    Options[] value();
  }

}
