package org.oreframework.boot.autoconfigure.diamond;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.ClassUtils;
import org.oreframework.boot.autoconfigure.diamond.config.DiamondConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.boot.env.PropertySourceLoader;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;


/**
 * @author huangzz
 * 2017年3月18日
 */
public class DiamondAutoConfiguration implements PropertySourceLoader
{
    private static Logger logger = LoggerFactory.getLogger(DiamondAutoConfiguration.class);
    
    public String[] getFileExtensions()
    {
        return new String[] {"properties"};
    }
    
    public PropertySource<?> load(String name, Resource resource, String profile)
        throws IOException
    {
        if (profile == null)
        {
            try
            {
                ClassUtils.getClass("com.taobao.diamond.manager.ManagerListener");
            }
            catch (ClassNotFoundException e)
            {
                logger.warn(MarkerFactory.getMarker("oreframework"),"com.taobao.diamond.manager.ManagerListener not config");
                Properties properties = PropertiesLoaderUtils.loadProperties(resource);
                if (!properties.isEmpty()) {
                    return new PropertiesPropertySource(name, properties);
                }
            }
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            DiamondConfig diamondConfig = new DiamondConfig(properties);
            if (!properties.isEmpty())
            {
                return new PropertiesPropertySource(name, diamondConfig.getDiamondConfig());
            }
        }
        return null;
    }
    
}
