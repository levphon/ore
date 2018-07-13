package org.oreframework.boot.seed.framework.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangzz
 * 2017年3月18日
 */
@Configuration
@ConfigurationProperties("static")
public class StaticProperty
{
    private String resource;
    
    public String getResource()
    {
        return resource;
    }
    
    public void setResource(String resource)
    {
        this.resource = resource;
    }
    
}
