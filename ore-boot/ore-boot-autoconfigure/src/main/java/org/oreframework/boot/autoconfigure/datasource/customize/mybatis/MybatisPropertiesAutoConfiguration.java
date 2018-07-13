package org.oreframework.boot.autoconfigure.datasource.customize.mybatis;

import org.oreframework.boot.autoconfigure.datasource.customize.config.MybatisProperties;
import org.oreframework.boot.autoconfigure.datasource.BeanNameConstants;
import org.oreframework.boot.autoconfigure.datasource.customize.config.MapperProperties;
import org.oreframework.boot.autoconfigure.datasource.customize.mybatis.druid.DruidAutoConfiguration;
import org.oreframework.datasource.druid.DruidProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

import tk.mybatis.mapper.entity.Config;

/**
 * @author huangzz 2017年3月18日
 */
@Configuration
@ConditionalOnClass({DruidDataSource.class, Config.class})
@EnableConfigurationProperties({DruidProperties.class, MapperProperties.class, MybatisProperties.class})
@AutoConfigureBefore(DruidAutoConfiguration.class)
@Import(MybatisScannerAutoConfiguration.class)
public class MybatisPropertiesAutoConfiguration
{
    
    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "ore.druid", name = "url")
    @ConfigurationProperties(prefix = "ore")
    public DruidProperties druidProperties()
    {
        return new DruidProperties();
    }
    
    @Bean
    @Qualifier(BeanNameConstants.PRIMARY_DRUIDPROPERTIES)
    @ConditionalOnProperty(prefix = "ore.primary.druid", name = "url")
    @ConfigurationProperties(prefix = "ore.primary")
    public DruidProperties primaryDruidProperties()
    {
        return new DruidProperties();
    }
    
    @Bean
    @Qualifier(BeanNameConstants.PRIMARY_MAPPERPROPERTIES)
    @ConditionalOnProperty(prefix = "ore.primary.druid", name = "url")
    @ConfigurationProperties(prefix = "ore.primary")
    public MapperProperties primaryMapperProperties()
    {
        return new MapperProperties();
    }
    
    @Bean
    @Qualifier(BeanNameConstants.PRIMARY_MYBATISPROPERTIES)
    @ConditionalOnProperty(prefix = "ore.primary.druid", name = "url")
    @ConfigurationProperties(prefix = "ore.primary")
    public MybatisProperties primaryMybatisProperties()
    {
        return new MybatisProperties();
    }
    
    @Bean
    @Qualifier(BeanNameConstants.SECONDARY_DRUIDPROPERTIES)
    @ConditionalOnProperty(prefix = "ore.secondary.druid", name = "url")
    @ConfigurationProperties(prefix = "ore.secondary")
    public DruidProperties secondaryDruidProperties()
    {
        return new DruidProperties();
    }
    
    @Bean
    @Qualifier(BeanNameConstants.SECONDARY_MAPPERPROPERTIES)
    @ConditionalOnProperty(prefix = "ore.secondary.druid", name = "url")
    @ConfigurationProperties(prefix = "ore.secondary")
    public MapperProperties secondaryMapperProperties()
    {
        return new MapperProperties();
    }
    
    @Bean
    @Qualifier(BeanNameConstants.SECONDARY_MYBATISPROPERTIES)
    @ConditionalOnProperty(prefix = "ore.secondary.druid", name = "url")
    @ConfigurationProperties(prefix = "ore.secondary")
    public MybatisProperties secondaryMybatisProperties()
    {
        return new MybatisProperties();
    }
}
