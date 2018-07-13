package org.oreframework.boot.autoconfigure.dubbo;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.config.spring.AnnotationBean;

import org.oreframework.boot.autoconfigure.dubbo.metadata.DubboProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangzz
 * 2017年3月18日
 */
@Configuration
@ConditionalOnClass({ApplicationConfig.class, RegistryConfig.class, AnnotationBean.class})
@EnableConfigurationProperties(DubboProperty.class)
public class DubboAutoConfiguration
{
    
    @Autowired
    private DubboProperty dubboProperty;
    
    @Bean
    public ApplicationConfig requestApplicationConfig()
    {
        return dubboProperty.getApplication();
    }
    
    @Bean
    public RegistryConfig requestRegistryConfig()
    {
        return dubboProperty.getRegistry();
    }
    
    @Bean
    public ProtocolConfig requestProtocolConfig()
    {
        return dubboProperty.getProtocol();
    }
    
}