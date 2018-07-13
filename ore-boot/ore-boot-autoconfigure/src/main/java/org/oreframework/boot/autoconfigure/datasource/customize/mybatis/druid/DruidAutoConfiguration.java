package org.oreframework.boot.autoconfigure.datasource.customize.mybatis.druid;

import javax.sql.DataSource;

import org.oreframework.boot.autoconfigure.datasource.BeanNameConstants;
import org.oreframework.datasource.druid.DruidProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author huangzz 2017年3月18日
 */
@Configuration
@ConditionalOnClass({DruidDataSource.class})
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class DruidAutoConfiguration
{
    
    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "ore.druid", name = "url")
    public DataSource dataSource(DruidProperties properties)
    {
        return properties.getDruid();
    }
    
    @Bean(name = BeanNameConstants.PRIMARY_DATASOURCE)
    @Qualifier(BeanNameConstants.PRIMARY_DATASOURCE)
    @ConditionalOnProperty(prefix = "ore.primary.druid", name = "url")
    public DataSource primaryDataSource(
        @Qualifier(BeanNameConstants.PRIMARY_DRUIDPROPERTIES) DruidProperties properties)
    {
        return properties.getDruid();
    }
    
    @Bean(name = BeanNameConstants.SECONDARY_DATASOURCE)
    @Qualifier(BeanNameConstants.SECONDARY_DATASOURCE)
    @ConditionalOnProperty(prefix = "ore.secondary.druid", name = "url")
    public DataSource secondaryDataSource(
        @Qualifier(BeanNameConstants.SECONDARY_DRUIDPROPERTIES) DruidProperties properties)
    {
        return properties.getDruid();
    }
}
