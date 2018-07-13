package org.oreframework.boot.autoconfigure.diamond.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.Executor;

import org.oreframework.boot.env.EnvConfigurer;
import org.oreframework.commons.beanutils.properties.PropertiesUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MarkerFactory;
import org.springframework.util.StringUtils;

import com.taobao.diamond.manager.DiamondManager;
import com.taobao.diamond.manager.ManagerListener;
import com.taobao.diamond.manager.impl.DefaultDiamondManager;
import com.taobao.diamond.utils.ResourceUtils;

/**
 * @author huangzz 2017年3月18日
 */
public class DiamondConfig
{
    private static Logger logger = LoggerFactory.getLogger(DiamondConfig.class);
    
    private static String LOCALHOST_CONFIG = "META-INF/config.properties";
    
    public final static String ORE_SERVER_CONFIG = "ore.server.config";
    
    public final static String ORE_SERVER_CONFIG_PATH = "ore.server.config-path";
    
    public final static String ORE_DIAMOND = "ore.diamond";
    
    public final static String ORE_DIAMOND_GROUP = "ore.diamond.group";
    
    public final static String ORE_DIAMOND_DATAID = "ore.diamond.dataId";
    
    public final static String ORE_DIAMOND_ENV = "ore.diamond.env";
    
    private Properties properties;
    
    public DiamondConfig(Properties properties)
    {
        this.properties = properties;
        
        // 取本地配置
        String config = properties.getProperty(ORE_SERVER_CONFIG);
        
        // 本地配置路径
        String path = properties.getProperty(ORE_SERVER_CONFIG_PATH);
        if (null != path)
        {
            LOCALHOST_CONFIG = path;
        }
        
        if (null == config)
        {
            config = "remote";
        }
        
        if ("localhost".equals(config) && !"remote".equals(config))
        {
            try
            {
                getLocalhostConfig(properties);
                return;
            }
            catch (IOException e)
            {
                logger.error(e.toString());
            }
        }
        String group = properties.getProperty(ORE_DIAMOND_GROUP);
        String dataId = properties.getProperty(ORE_DIAMOND_DATAID);
        String env = properties.getProperty(ORE_DIAMOND_ENV);
        if (null != EnvConfigurer.env)
        {
            env = EnvConfigurer.env;
        }
        init(group, dataId, env, properties);
    }
    
    public void init(String group, String dataId, String env, final Properties properties)
    {
        dataId = dataId + "-" + env;
        
        DiamondManager manager = new DefaultDiamondManager(group, dataId, new ManagerListener()
        {
            public Executor getExecutor()
            {
                return null;
            }
            
            public void receiveConfigInfo(String configInfo)
            {
                // 客户端处理数据的逻辑
                logger.warn(MarkerFactory.getMarker("oreframework"), "config is update：\n{}", configInfo);
                StringReader reader = new StringReader(configInfo);
                
                try
                {
                    properties.load(reader);
                }
                catch (Exception e)
                {
                    logger.error(MarkerFactory.getMarker("oreframework"), "Get Diamon Config error!", e);
                }
            }
        });
        
        try
        {
            String configInfo = manager.getAvailableConfigureInfomation(30000);
            logger.info(MarkerFactory.getMarker("oreframework"), "dataId {} config info: \n{}", dataId, configInfo);
            
            if (null == configInfo)
            {
                getLocalhostConfig(properties);
                
                return;
            }
            
            if (!StringUtils.isEmpty(configInfo))
            {
                StringReader reader = new StringReader(configInfo);
                properties.load(reader);
            }
            else
            {
                logger.error(MarkerFactory.getMarker("oreframework"), "Get Diamon Config error!");
            }
            
        }
        catch (Exception e)
        {
            logger.error(MarkerFactory.getMarker("oreframework"), "Get Diamon Config error!", e);
        }
        
    }
    
    private void getLocalhostConfig(final Properties properties)
        throws IOException
    {
        InputStream is = ResourceUtils.getResourceAsStream(DiamondConfig.class.getClassLoader(), LOCALHOST_CONFIG);
        PropertiesUtil propertiesUtil = new PropertiesUtil(is);
        @SuppressWarnings("unchecked")
        Map<String, String> pMap = propertiesUtil.getAllProperty();
        Set<String> set = pMap.keySet();
        Iterator<String> it = set.iterator();
        StringBuilder sb = new StringBuilder();
        while (it.hasNext())
        {
            String key = it.next();
            sb.append(key + "=" + pMap.get(key) + "\n");
            properties.setProperty(key, pMap.get(key));
        }
        // propertiesUtil.printProperties();
        logger.info(MarkerFactory.getMarker("oreframework"), "localhost config info: \n{}", sb.toString());
    }
    
    public Properties getDiamondConfig()
    {
        return properties;
        
    }
    
}
