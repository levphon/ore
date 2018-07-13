package org.oreframework.boot.autoconfigure.rop.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.rop.security.AppSecretManager;

/**
 * <pre>
 *   基于文件管理的应用密钥
 * </pre>
 *
 * @version 1.0
 */
public class SecurityAppSecretManager implements AppSecretManager 
{

    /**
     * 日志对象
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired  
    private Environment env;  
      
    
    /**
     * 获取密钥
     * @param appKey String
     * @return String
     */
    public String getSecret(String appKey) 
    {
        String secret = env.getProperty(appKey);  
        if (secret == null) 
        {
            logger.error("不存在应用键为{0}的密钥,请检查应用密钥的配置文件。", appKey);
        }
        return secret;
    }

    /**
     * 验证APPKey
     * @param appKey String
     * @return
     */
    @Override
    public boolean isValidAppKey(String appKey) 
    {
        return getSecret(appKey) != null;
    }
}


