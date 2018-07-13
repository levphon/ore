package org.oreframework.boot.autoconfigure.datasource.customize.mybatis;

import java.util.List;

import org.mybatis.spring.mapper.ClassPathMapperScanner;
import org.oreframework.boot.annotation.PrimaryMapper;
import org.oreframework.boot.annotation.SecondaryMapper;
import org.oreframework.boot.autoconfigure.datasource.BeanNameConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

/**
 * @author huangzz 2017年3月18日
 */
public class MybatisScannerAutoConfiguration
		implements BeanFactoryAware, ImportBeanDefinitionRegistrar, ResourceLoaderAware {

	private static final Logger logger = LoggerFactory.getLogger(MybatisScannerAutoConfiguration.class);
	private BeanFactory beanFactory;
	private ResourceLoader resourceLoader;

	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		logger.debug("Searching for mappers annotated with @Mapper");

		try {
			ClassPathMapperScanner scanner = new ClassPathMapperScanner(registry);

			if (this.resourceLoader != null) {
				scanner.setResourceLoader(this.resourceLoader);
			}

			List<String> packages = AutoConfigurationPackages.get(this.beanFactory);
			if (logger.isDebugEnabled()) {
				for (String pkg : packages) {
					logger.debug("Using auto-configuration base package '{}'", pkg);
				}
			}

			scanner.setAnnotationClass(PrimaryMapper.class);
			scanner.registerFilters();
			// scanner.setSqlSessionFactoryBeanName(Constants.PRIMARY_SQLSESSIONFACTORY);
			scanner.setSqlSessionTemplateBeanName(BeanNameConstants.PRIMARY_SQLSESSIONTEMPLATE);
			scanner.doScan(StringUtils.toStringArray(packages));

			ClassPathMapperScanner scannerSecondary = new ClassPathMapperScanner(registry);
			scannerSecondary.setAnnotationClass(SecondaryMapper.class);
			scannerSecondary.registerFilters();
			// scannerSecondary.setSqlSessionFactoryBeanName(Constants.SECONDARY_SQLSESSIONFACTORY);
			scannerSecondary.setSqlSessionTemplateBeanName(BeanNameConstants.SECONDARY_SQLSESSIONTEMPLATE);
			scannerSecondary.doScan(StringUtils.toStringArray(packages));
		} catch (IllegalStateException ex) {
			logger.debug("Could not determine auto-configuration package, automatic mapper scanning disabled.", ex);
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void setResourceLoader(ResourceLoader resourceLoader) {
		this.resourceLoader = resourceLoader;
	}

}