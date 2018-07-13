package org.oreframework.boot.autoconfigure.cas.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * CAS的配置参数
 * 
 * @author huangzz 2017年3月19日
 */
@ConfigurationProperties(prefix = "ore.cas")
public class CasProperties
{
    
    // CAS服务地址
    private String serverUrl;
    
    // CAS服务登录地址
    private String serverLoginUrl;
    
    //CAS服务登出地址
    private String serverLogoutUrl;
    
    public String getServerUrl()
    {
        return serverUrl;
    }
    
    public void setServerUrl(String serverUrl)
    {
        this.serverUrl = serverUrl;
    }
    
    public String getServerLoginUrl()
    {
        return serverLoginUrl;
    }
    
    public void setServerLoginUrl(String serverLoginUrl)
    {
        this.serverLoginUrl = serverLoginUrl;
    }
    
    public String getServerLogoutUrl()
    {
        return serverLogoutUrl;
    }
    
    public void setServerLogoutUrl(String serverLogoutUrl)
    {
        this.serverLogoutUrl = serverLogoutUrl;
    }
    
}
