package org.oreframework.boot.autoconfigure.rop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import org.oreframework.boot.autoconfigure.rop.interceptor.ReservedInterceptor;
import org.oreframework.boot.autoconfigure.rop.listeners.RopAfterDoServiceEventListener;
import org.oreframework.boot.autoconfigure.rop.listeners.RopPreDoServiceEventListener;
import org.oreframework.boot.autoconfigure.rop.metadata.RopProperty;
import org.oreframework.boot.autoconfigure.rop.security.SecurityAppSecretManager;
import org.oreframework.context.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import com.rop.Interceptor;
import com.rop.RopServlet;
import com.rop.ThreadFerry;
import com.rop.config.InterceptorHolder;
import com.rop.config.RopEventListenerHodler;
import com.rop.config.SystemParameterNames;
import com.rop.event.RopEventListener;
import com.rop.impl.AnnotationServletServiceRouter;
import com.rop.impl.DefaultServiceAccessController;
import com.rop.security.AppSecretManager;
import com.rop.security.DefaultFileUploadController;
import com.rop.security.DefaultInvokeTimesController;
import com.rop.security.DefaultSecurityManager;
import com.rop.security.InvokeTimesController;
import com.rop.security.ServiceAccessController;
import com.rop.session.DefaultSessionManager;
import com.rop.session.SessionManager;

/**
 * @author huangzz 2017年7月6日
 */
@Configuration
@ConditionalOnClass(com.rop.RopServlet.class)
@EnableConfigurationProperties(RopProperty.class)
public class RapidOpenPlatformAutoConfigure {

	private static Logger logger = LoggerFactory.getLogger(RapidOpenPlatformAutoConfigure.class);

	public static final int DEFAULT_CORE_POOL_SIZE = 30;

	public static final int DEFAULT_MAX_POOL_SIZE = 120;

	public static final int DEFAULT_KEEP_ALIVE_SECONDS = 3 * 60;

	public static final int DEFAULT_QUENE_CAPACITY = 10;

	private static final String ALL_FILE_TYPES = "*";

	private AnnotationServletServiceRouter serviceRouter;

	@Autowired
	private RopProperty ropProperty;

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private AppSecretManager appSecretManager;

	@Autowired
	private ServiceAccessController serviceAccessController;

	@Autowired
	private FormattingConversionService formattingConversionService;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private InvokeTimesController invokeTimesController;

	@SuppressWarnings("rawtypes")
	@Bean
	public AnnotationServletServiceRouter serviceRouter() {
		// 实例化一个AnnotationServletServiceRouter
		serviceRouter = new AnnotationServletServiceRouter();
		int serviceTimeoutSeconds = Integer.parseInt(ropProperty.getAnnotationDriven().getServiceTimeoutSeconds());

		// 设置国际化错误资源
		if (ropProperty.getAnnotationDriven().getExtErrorBasename() != null) {
			serviceRouter.setExtErrorBasename(ropProperty.getAnnotationDriven().getExtErrorBasename());

		}

		// 设置formattingConversionService
		String fcs = ropProperty.getAnnotationDriven().getFormattingConversionService();
		if (StringUtils.hasText(fcs)) {
			formattingConversionService = (FormattingConversionService) SpringContext.getApplicationContext()
					.getBean(fcs);
		}

		// 会话管理器
		String defSessionManager = ropProperty.getAnnotationDriven().getSessionManager();
		if (StringUtils.hasText(defSessionManager)) {
			sessionManager = (SessionManager) SpringContext.getBean(defSessionManager);
		}

		// 密钥管理器
		String defAppSecretManager = ropProperty.getAnnotationDriven().getAppSecretManager();
		if (StringUtils.hasText(defAppSecretManager)) {
			appSecretManager = (AppSecretManager) SpringContext.getBean(defAppSecretManager);
		}

		// 服务访问控制器
		String defServiceAccessController = ropProperty.getAnnotationDriven().getServiceAccessController();
		if (StringUtils.hasText(defServiceAccessController)) {
			serviceAccessController = (ServiceAccessController) SpringContext.getBean(defServiceAccessController);
		}

		// 访问次数/频度控制器
		String defInvokeTimesController = ropProperty.getAnnotationDriven().getInvokeTimesController();
		if (StringUtils.hasText(defInvokeTimesController)) {
			invokeTimesController = (InvokeTimesController) SpringContext.getBean(defInvokeTimesController);
		}

		DefaultSecurityManager securityManager = BeanUtils.instantiate(DefaultSecurityManager.class);

		securityManager.setSessionManager(sessionManager);
		securityManager.setAppSecretManager(appSecretManager);
		securityManager.setServiceAccessController(serviceAccessController);
		securityManager.setInvokeTimesController(invokeTimesController);
		securityManager.setFileUploadController(buildFileUploadController());

		serviceRouter.setSecurityManager(securityManager);
		serviceRouter.setThreadPoolExecutor(threadPoolTaskExecutor.getThreadPoolExecutor());
		serviceRouter.setSignEnable(Boolean.parseBoolean(ropProperty.getAnnotationDriven().getSignEnable()));
		serviceRouter.setServiceTimeoutSeconds(serviceTimeoutSeconds);
		serviceRouter.setFormattingConversionService(formattingConversionService);
		serviceRouter.setSessionManager(sessionManager);
		serviceRouter.setThreadFerryClass(setThreadFerry());
		serviceRouter.setInvokeTimesController(invokeTimesController);
		
		

		// 注册拦截器
		ArrayList<Interceptor> interceptors = getInterceptors();
		if (interceptors != null) {
			for (Interceptor interceptor : interceptors) {
				serviceRouter.addInterceptor(interceptor);
			}
			if (logger.isInfoEnabled()) {
				logger.info("register total {} interceptors", interceptors.size());
			}
		}

		// 注册监听器
		ArrayList<RopEventListener> listeners = getListeners();
		if (listeners != null) {
			for (RopEventListener listener : listeners) {
				serviceRouter.addListener(listener);
			}
			if (logger.isInfoEnabled()) {
				logger.info("register total {} listeners", listeners.size());
			}
		}

		// 设置Spring上下文信息
		serviceRouter.setApplicationContext(SpringContext.getApplicationContext());

		// 启动之
		serviceRouter.startup();

		return serviceRouter;
	}

	@Bean
	public FormattingConversionService formattingConversionService() {
		FormattingConversionServiceFactoryBean factoryBean = new FormattingConversionServiceFactoryBean();
		return factoryBean.getObject();
	}

	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		// ThreadPoolExecutorFactoryBean factoryBean = new
		// ThreadPoolExecutorFactoryBean();
		ThreadPoolTaskExecutor threadPool = new ThreadPoolTaskExecutor();
		
		//核心线程数  
		String corePoolSize = ropProperty.getAnnotationDriven().getCorePoolSize();
		if (StringUtils.hasText(corePoolSize)) {
			threadPool.setCorePoolSize(Integer.parseInt(corePoolSize));
		} else {
			threadPool.setCorePoolSize(DEFAULT_CORE_POOL_SIZE);
		}

		//最大线程数 
		String maxPoolSize = ropProperty.getAnnotationDriven().getMaxPoolSize();
		if (StringUtils.hasText(maxPoolSize)) {
			threadPool.setMaxPoolSize(Integer.parseInt(maxPoolSize));
		} else {
			threadPool.setMaxPoolSize(DEFAULT_MAX_POOL_SIZE);
		}

		//线程池维护线程所允许的空闲时间 
		String keepAliveSeconds = ropProperty.getAnnotationDriven().getKeepAliveSeconds();
		if (StringUtils.hasText(keepAliveSeconds)) {
			threadPool.setKeepAliveSeconds(Integer.parseInt(keepAliveSeconds));
		} else {
			threadPool.setKeepAliveSeconds(DEFAULT_KEEP_ALIVE_SECONDS);
		}

		//队列最大长度 
		String queueCapacity = ropProperty.getAnnotationDriven().getQueueCapacity();
		if (StringUtils.hasText(queueCapacity)) {
			threadPool.setQueueCapacity(Integer.parseInt(queueCapacity));
		} else {
			threadPool.setQueueCapacity(DEFAULT_QUENE_CAPACITY);
		}
		
		//(1)ThreadPoolExecutor.AbortPolicy策略，是默认的策略,处理程序遭到拒绝将抛出运行时 RejectedExecutionException。 
		//(2)ThreadPoolExecutor.CallerRunsPolicy策略 ,调用者的线程会执行该任务,如果执行器已关闭,则丢弃. 
		//(3)ThreadPoolExecutor.DiscardPolicy策略，不能执行的任务将被丢弃. 
		//(4)ThreadPoolExecutor.DiscardOldestPolicy策略，如果执行程序尚未关闭，则位于工作队列头部的任务将被删除，然后重试执行程序
		//线程池对拒绝任务(无线程可用)的处理策略 
		threadPool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

		threadPool.initialize();
		return threadPool;
	}

	@Bean
	public SystemParameterNames sysparams() {
		String appKey = ropProperty.getSysparams().getAppkeyParamName();
		String sessionId = ropProperty.getSysparams().getSessionidParamName();
		String method = ropProperty.getSysparams().getMethodParamName();
		String version = ropProperty.getSysparams().getVersionParamName();
		String format = ropProperty.getSysparams().getFormatParamName();
		String locale = ropProperty.getSysparams().getLocaleParamName();
		String sign = ropProperty.getSysparams().getSignParamName();

		if (StringUtils.hasText(appKey)) {
			SystemParameterNames.setAppKey(appKey);
		}
		if (StringUtils.hasText(sessionId)) {
			SystemParameterNames.setSessionId(sessionId);
		}
		if (StringUtils.hasText(method)) {
			SystemParameterNames.setMethod(method);
		}
		if (StringUtils.hasText(version)) {
			SystemParameterNames.setVersion(version);
		}
		if (StringUtils.hasText(format)) {
			SystemParameterNames.setFormat(format);
		}
		if (StringUtils.hasText(locale)) {
			SystemParameterNames.setLocale(locale);
		}
		if (StringUtils.hasText(sessionId)) {
			SystemParameterNames.setSign(sign);
		}
		return new SystemParameterNames();
	}

	/**
	 * 会话管理器
	 */
	@Bean
	public SessionManager sessionManager() {
		DefaultSessionManager defaultSessionManager = new DefaultSessionManager();
		return defaultSessionManager;
	}

	/**
	 * 密钥管理器
	 */
	@Bean
	public SecurityAppSecretManager appSecretManager() {
		SecurityAppSecretManager appSecretManager = new SecurityAppSecretManager();
		return appSecretManager;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "ore.rop", name = "listener")
	public RopEventListenerHodler preDoServiceEventListener() {
		RopPreDoServiceEventListener ropEventListener = new RopPreDoServiceEventListener();
		RopEventListenerHodler listener = new RopEventListenerHodler(ropEventListener);
		return listener;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "ore.rop", name = "listener")
	public RopEventListenerHodler afterDoServiceEventListener() {
		RopAfterDoServiceEventListener ropEventListener = new RopAfterDoServiceEventListener();
		RopEventListenerHodler listener = new RopEventListenerHodler(ropEventListener);
		return listener;
	}

	@Bean
	public ServiceAccessController serviceAccessController() {
		DefaultServiceAccessController serviceAccessController = new DefaultServiceAccessController();
		return serviceAccessController;
	}
	
	@Bean
	public InterceptorHolder reservedInterceptor() {
		ReservedInterceptor reservedInterceptor = new ReservedInterceptor();
		InterceptorHolder interceptorHolder = new InterceptorHolder(reservedInterceptor);
		return interceptorHolder;
	}


	@Bean
	public InvokeTimesController invokeTimesController() {
		DefaultInvokeTimesController invokeTimesController = new DefaultInvokeTimesController();
		return invokeTimesController;
	}

	@Bean
	public ServletRegistrationBean indexServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new RopServlet());
		registration.addUrlMappings("/router");
		return registration;
	}

	@SuppressWarnings("unchecked")
	private Class<? extends ThreadFerry> setThreadFerry() {
		String threadFerryClassName = ropProperty.getAnnotationDriven().getThreadFerryClass();
		if (StringUtils.hasText(threadFerryClassName)) {
			try {
				return (Class<? extends ThreadFerry>) Class.forName(threadFerryClassName);
			} catch (ClassNotFoundException e) {
				logger.error("ClassNotFoundException {}/", threadFerryClassName);
			}
		}
		return null;
	}

	private ArrayList<Interceptor> getInterceptors() {
		Map<String, InterceptorHolder> interceptorMap = SpringContext.getApplicationContext()
				.getBeansOfType(InterceptorHolder.class);
		if (interceptorMap != null && interceptorMap.size() > 0) {
			ArrayList<Interceptor> interceptors = new ArrayList<Interceptor>(interceptorMap.size());

			// 从Spring容器中获取Interceptor
			for (InterceptorHolder interceptorHolder : interceptorMap.values()) {
				interceptors.add(interceptorHolder.getInterceptor());
			}

			// 根据getOrder()值排序
			Collections.sort(interceptors, new Comparator<Interceptor>() {
				public int compare(Interceptor o1, Interceptor o2) {
					if (o1.getOrder() > o2.getOrder()) {
						return 1;
					} else if (o1.getOrder() < o2.getOrder()) {
						return -1;
					} else {
						return 0;
					}
				}
			});
			return interceptors;
		} else {
			return null;
		}
	}

	@SuppressWarnings("rawtypes")
	private ArrayList<RopEventListener> getListeners() {
		Map<String, RopEventListenerHodler> listenerMap = SpringContext.getApplicationContext()
				.getBeansOfType(RopEventListenerHodler.class);
		if (listenerMap != null && listenerMap.size() > 0) {
			ArrayList<RopEventListener> ropEventListeners = new ArrayList<RopEventListener>(listenerMap.size());

			// 从Spring容器中获取Interceptor
			for (RopEventListenerHodler listenerHolder : listenerMap.values()) {
				ropEventListeners.add(listenerHolder.getRopEventListener());
			}
			return ropEventListeners;
		} else {
			return null;
		}
	}

	private DefaultFileUploadController buildFileUploadController() {
		Assert.notNull(ropProperty.getAnnotationDriven().getUploadFileTypes(),
				"Please set the updateFileTypes,if all,set *");
		if (ALL_FILE_TYPES.equals(ropProperty.getAnnotationDriven().getUploadFileTypes().trim())) {
			return new DefaultFileUploadController(
					Integer.parseInt(ropProperty.getAnnotationDriven().getUploadFileMaxSize()));
		} else {
			String[] items = ropProperty.getAnnotationDriven().getUploadFileTypes().split(",");
			List<String> fileTypes = Arrays.asList(items);
			return new DefaultFileUploadController(fileTypes,
					Integer.parseInt(ropProperty.getAnnotationDriven().getUploadFileMaxSize()));
		}
	}

}
