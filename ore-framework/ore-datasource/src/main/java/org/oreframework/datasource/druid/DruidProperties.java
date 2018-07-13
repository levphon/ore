package org.oreframework.datasource.druid;

import org.springframework.boot.context.properties.ConfigurationProperties;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author huangzz
 * 2017年3月18日
 */
@ConfigurationProperties
public class DruidProperties
{
    private DruidDataSource druid;
    
    public DruidDataSource getDruid()
    {
        return druid;
    }
    
    public void setDruid(DruidDataSource druid)
    {
        this.druid = druid;
    }
}
