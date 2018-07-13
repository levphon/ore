package org.oreframework.boot.autoconfigure.datasource.customize.mybatis.mapper;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.oreframework.boot.autoconfigure.datasource.mybatis.ConfigurationCustomizer;
import org.oreframework.boot.autoconfigure.datasource.BeanNameConstants;
import org.oreframework.boot.autoconfigure.datasource.customize.config.MybatisProperties;
import org.oreframework.boot.autoconfigure.datasource.mybatis.SpringBootVFS;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.SearchStrategy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

/**
 * @author huangzz 2017年3月18日
 */
@org.springframework.context.annotation.Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@ConditionalOnBean(name = "secondaryDataSource", search = SearchStrategy.ALL)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class SecondaryMybatisAutoConfiguration {

	private final MybatisProperties properties;

	private final Interceptor[] interceptors;

	private final ResourceLoader resourceLoader;

	private final DatabaseIdProvider databaseIdProvider;

	private final List<ConfigurationCustomizer> configurationCustomizers;

	public SecondaryMybatisAutoConfiguration(@Qualifier(BeanNameConstants.SECONDARY_MYBATISPROPERTIES) MybatisProperties properties,
			ObjectProvider<Interceptor[]> interceptorsProvider, ResourceLoader resourceLoader,
			ObjectProvider<DatabaseIdProvider> databaseIdProvider,
			ObjectProvider<List<ConfigurationCustomizer>> configurationCustomizersProvider) {
		this.properties = properties;
		this.interceptors = interceptorsProvider.getIfAvailable();
		this.resourceLoader = resourceLoader;
		this.databaseIdProvider = databaseIdProvider.getIfAvailable();
		this.configurationCustomizers = configurationCustomizersProvider.getIfAvailable();
	}

	@PostConstruct
	public void checkConfigFileExists() {
		if (this.properties.getMybatis().isCheckConfigLocation()
				&& StringUtils.hasText(this.properties.getMybatis().getConfigLocation())) {
			Resource resource = this.resourceLoader.getResource(this.properties.getMybatis().getConfigLocation());
			Assert.state(resource.exists(), "Cannot find config location: " + resource
					+ " (please add config file or check your Mybatis configuration)");
		}
	}

	@Bean(name = BeanNameConstants.SECONDARY_SQLSESSIONFACTORY)
	@Qualifier(BeanNameConstants.SECONDARY_SQLSESSIONFACTORY)
	public SqlSessionFactory sqlSessionFactory(@Qualifier(BeanNameConstants.SECONDARY_DATASOURCE) DataSource dataSource)
			throws Exception {
		SqlSessionFactoryBean factory = new SqlSessionFactoryBean();
		factory.setDataSource(dataSource);
		factory.setVfs(SpringBootVFS.class);
		if (StringUtils.hasText(this.properties.getMybatis().getConfigLocation())) {
			factory.setConfigLocation(
					this.resourceLoader.getResource(this.properties.getMybatis().getConfigLocation()));
		}
		Configuration configuration = this.properties.getMybatis().getConfiguration();
		if (configuration == null && !StringUtils.hasText(this.properties.getMybatis().getConfigLocation())) {
			configuration = new Configuration();
		}
		if (configuration != null && !CollectionUtils.isEmpty(this.configurationCustomizers)) {
			for (ConfigurationCustomizer customizer : this.configurationCustomizers) {
				customizer.customize(configuration);
			}
		}
		factory.setConfiguration(configuration);
		if (this.properties.getMybatis().getConfigurationProperties() != null) {
			factory.setConfigurationProperties(this.properties.getMybatis().getConfigurationProperties());
		}
		if (!ObjectUtils.isEmpty(this.interceptors)) {
			factory.setPlugins(this.interceptors);
		}
		if (this.databaseIdProvider != null) {
			factory.setDatabaseIdProvider(this.databaseIdProvider);
		}
		if (StringUtils.hasLength(this.properties.getMybatis().getTypeAliasesPackage())) {
			factory.setTypeAliasesPackage(this.properties.getMybatis().getTypeAliasesPackage());
		}
		if (StringUtils.hasLength(this.properties.getMybatis().getTypeHandlersPackage())) {
			factory.setTypeHandlersPackage(this.properties.getMybatis().getTypeHandlersPackage());
		}
		if (!ObjectUtils.isEmpty(this.properties.getMybatis().resolveMapperLocations())) {
			factory.setMapperLocations(this.properties.getMybatis().resolveMapperLocations());
		}

		return factory.getObject();
	}

	@Bean(name = BeanNameConstants.SECONDARY_SQLSESSIONTEMPLATE)
	@Qualifier(BeanNameConstants.SECONDARY_SQLSESSIONTEMPLATE)
	public SqlSessionTemplate sqlSessionTemplate(
			@Qualifier(BeanNameConstants.SECONDARY_SQLSESSIONFACTORY) SqlSessionFactory sqlSessionFactory) {
		ExecutorType executorType = this.properties.getMybatis().getExecutorType();
		if (executorType != null) {
			return new SqlSessionTemplate(sqlSessionFactory, executorType);
		} else {
			return new SqlSessionTemplate(sqlSessionFactory);
		}
	}
}
