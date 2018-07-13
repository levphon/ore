/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.oreframework.boot.autoconfigure.datasource.customize.mybatis.mapper;

import java.sql.SQLException;

import javax.annotation.PostConstruct;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.oreframework.boot.autoconfigure.datasource.BeanNameConstants;
import org.oreframework.boot.autoconfigure.datasource.customize.config.MapperProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.context.annotation.Configuration;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

/**
 * @author huangzz 2017年3月18日
 */
@Configuration
@ConditionalOnClass({SqlSessionFactory.class, SqlSessionFactoryBean.class })
@ConditionalOnBean(name = "secondaryDataSource", search = SearchStrategy.ALL)
@AutoConfigureAfter(SecondaryMybatisAutoConfiguration.class)
public class SecondaryMapperAutoConfiguration {

	private final SqlSessionFactory sqlSessionFactory;

	private MapperProperties mapperProperties;

	public SecondaryMapperAutoConfiguration(
			@Qualifier(BeanNameConstants.SECONDARY_SQLSESSIONFACTORY) SqlSessionFactory sqlSessionFactory,
			@Qualifier(BeanNameConstants.SECONDARY_MAPPERPROPERTIES) MapperProperties mapperProperties) {
		this.sqlSessionFactory = sqlSessionFactory;
		this.mapperProperties = mapperProperties;
	}

	@PostConstruct
	@SuppressWarnings("rawtypes")
	public void addPageInterceptor() throws SQLException {
		MapperHelper mapperHelper = new MapperHelper();
		mapperHelper.setConfig(mapperProperties.getMapper());
		if (mapperProperties.getMapper().getMappers().size() > 0) {
			for (Class mapper : mapperProperties.getMapper().getMappers()) {
				mapperHelper.registerMapper(mapper);
			}
		} else {
			mapperHelper.registerMapper(Mapper.class);
		}
		System.out.println(sqlSessionFactory.getConfiguration().getEnvironment().getDataSource().getConnection()
				.getMetaData().getURL());
		mapperHelper.processConfiguration(sqlSessionFactory.getConfiguration());
	}
}
