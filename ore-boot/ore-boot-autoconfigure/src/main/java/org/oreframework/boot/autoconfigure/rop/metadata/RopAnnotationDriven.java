package org.oreframework.boot.autoconfigure.rop.metadata;

public class RopAnnotationDriven
{
    /**
     * 设置formattingConversionService
     */
    private String formattingConversionService;
    
    /**
     * 会话管理器
     */
    private String sessionManager;
    
    /**
     * 密钥管理器
     */
    private String appSecretManager;
    
    /**
     * 服务访问控制器
     */
    private String serviceAccessController;
    
    /**
     * 访问次数/频度控制器
     */
    private String invokeTimesController;
    
    private String corePoolSize;
    
    private String maxPoolSize;
    
    private String keepAliveSeconds;
    
    private String queueCapacity;
    
    // 设置signEnable
    private String signEnable;
    
    // 设置threadFerryClass
    private String threadFerryClass;
    
    // 设置国际化错误文件
    private String extErrorBasenames;
    
    // 设置服务过期时间
    private String serviceTimeoutSeconds;
    
    private String uploadFileMaxSize;
    
    private String uploadFileTypes;
    
    private String extErrorBasename;
    
    public String getFormattingConversionService()
    {
        return formattingConversionService;
    }
    
    public void setFormattingConversionService(String formattingConversionService)
    {
        this.formattingConversionService = formattingConversionService;
    }
    
    public String getSessionManager()
    {
        return sessionManager;
    }
    
    public void setSessionManager(String sessionManager)
    {
        this.sessionManager = sessionManager;
    }
    
    public String getAppSecretManager()
    {
        return appSecretManager;
    }
    
    public void setAppSecretManager(String appSecretManager)
    {
        this.appSecretManager = appSecretManager;
    }
    
    public String getServiceAccessController()
    {
        return serviceAccessController;
    }
    
    public void setServiceAccessController(String serviceAccessController)
    {
        this.serviceAccessController = serviceAccessController;
    }
    
    public String getInvokeTimesController()
    {
        return invokeTimesController;
    }
    
    public void setInvokeTimesController(String invokeTimesController)
    {
        this.invokeTimesController = invokeTimesController;
    }
    
    public String getSignEnable()
    {
        return signEnable;
    }
    
    public void setSignEnable(String signEnable)
    {
        this.signEnable = signEnable;
    }
    
   
    
    public String getThreadFerryClass()
    {
        return threadFerryClass;
    }

    public void setThreadFerryClass(String threadFerryClass)
    {
        this.threadFerryClass = threadFerryClass;
    }

    public String getExtErrorBasenames()
    {
        return extErrorBasenames;
    }
    
    public void setExtErrorBasenames(String extErrorBasenames)
    {
        this.extErrorBasenames = extErrorBasenames;
    }
    
    public String getServiceTimeoutSeconds()
    {
        return serviceTimeoutSeconds;
    }
    
    public void setServiceTimeoutSeconds(String serviceTimeoutSeconds)
    {
        this.serviceTimeoutSeconds = serviceTimeoutSeconds;
    }
    
    public String getUploadFileMaxSize()
    {
        return uploadFileMaxSize;
    }
    
    public void setUploadFileMaxSize(String uploadFileMaxSize)
    {
        this.uploadFileMaxSize = uploadFileMaxSize;
    }
    
    public String getUploadFileTypes()
    {
        return uploadFileTypes;
    }
    
    public void setUploadFileTypes(String uploadFileTypes)
    {
        this.uploadFileTypes = uploadFileTypes;
    }
    
    public String getExtErrorBasename()
    {
        return extErrorBasename;
    }
    
    public void setExtErrorBasename(String extErrorBasename)
    {
        this.extErrorBasename = extErrorBasename;
    }
    
    public String getCorePoolSize()
    {
        return corePoolSize;
    }
    
    public void setCorePoolSize(String corePoolSize)
    {
        this.corePoolSize = corePoolSize;
    }
    
    public String getMaxPoolSize()
    {
        return maxPoolSize;
    }
    
    public void setMaxPoolSize(String maxPoolSize)
    {
        this.maxPoolSize = maxPoolSize;
    }
    
    public String getKeepAliveSeconds()
    {
        return keepAliveSeconds;
    }
    
    public void setKeepAliveSeconds(String keepAliveSeconds)
    {
        this.keepAliveSeconds = keepAliveSeconds;
    }
    
    public String getQueueCapacity()
    {
        return queueCapacity;
    }
    
    public void setQueueCapacity(String queueCapacity)
    {
        this.queueCapacity = queueCapacity;
    }
    
}
