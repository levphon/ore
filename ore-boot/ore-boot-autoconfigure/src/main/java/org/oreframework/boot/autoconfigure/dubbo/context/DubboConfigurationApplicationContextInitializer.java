package org.oreframework.boot.autoconfigure.dubbo.context;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 * @author huangzz 2017年3月18日
 */
@ConditionalOnClass({ ApplicationConfig.class, RegistryConfig.class, AnnotationBean.class })
public class DubboConfigurationApplicationContextInitializer
		implements ApplicationContextInitializer<ConfigurableApplicationContext> {

	@Override
	public void initialize(ConfigurableApplicationContext applicationContext) {
		Environment env = applicationContext.getEnvironment();
		String scan = env.getProperty("ore.dubbo.scan");
		if (scan != null) {
			if (null != env.getProperty("spring.aop.auto") && env.getProperty("spring.aop.auto").equals("true")) {
				org.oreframework.boot.autoconfigure.dubbo.AnnotationBean scanner = BeanUtils
						.instantiate(org.oreframework.boot.autoconfigure.dubbo.AnnotationBean.class);
				scanner.setPackage(scan);
				scanner.setApplicationContext(applicationContext);
				applicationContext.addBeanFactoryPostProcessor(scanner);
				applicationContext.getBeanFactory().addBeanPostProcessor(scanner);
				applicationContext.getBeanFactory().registerSingleton("dubboAnnotation", scanner);
			} else {
				AnnotationBean scanner = BeanUtils.instantiate(AnnotationBean.class);
				scanner.setPackage(scan);
				scanner.setApplicationContext(applicationContext);
				applicationContext.addBeanFactoryPostProcessor(scanner);
				applicationContext.getBeanFactory().addBeanPostProcessor(scanner);
				applicationContext.getBeanFactory().registerSingleton("dubboAnnotation", scanner);
			}

		}

	}

}