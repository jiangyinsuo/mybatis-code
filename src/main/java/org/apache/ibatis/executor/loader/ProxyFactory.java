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
package org.apache.ibatis.executor.loader;

import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.session.Configuration;

import java.util.List;
import java.util.Properties;

/**
 * @author Eduardo Macarron
 * 代理工厂接口，用于创建需要延迟加载属性的结果对象
 */
public interface ProxyFactory {

  /**
   * 设置属性，目前是空实现。可以暂时无视该方法
   *
   * @param properties properties
   */
  default void setProperties(Properties properties) {
    // NOP
  }

  /**
   * 创建代理对象
   *
   * @param target              Object
   * @param lazyLoader          ResultLoaderMap
   * @param configuration       Configuration
   * @param objectFactory       ObjectFactory
   * @param constructorArgTypes List<Class<?>>
   * @param constructorArgs     List<Object>
   * @return Object
   */
  Object createProxy(Object target, ResultLoaderMap lazyLoader, Configuration configuration, ObjectFactory objectFactory, List<Class<?>> constructorArgTypes, List<Object> constructorArgs);

}
