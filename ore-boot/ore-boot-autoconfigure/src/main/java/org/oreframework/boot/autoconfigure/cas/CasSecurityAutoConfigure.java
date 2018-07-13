package org.oreframework.boot.autoconfigure.cas;

import org.jasig.cas.client.authentication.AuthenticationFilter;
import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.session.SingleSignOutHttpSessionListener;
import org.jasig.cas.client.util.AssertionThreadLocalFilter;
import org.jasig.cas.client.util.HttpServletRequestWrapperFilter;
import org.jasig.cas.client.validation.AbstractTicketValidationFilter;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.oreframework.boot.autoconfigure.cas.config.AppProperties;
import org.oreframework.boot.autoconfigure.cas.config.CasProperties;
import org.oreframework.boot.security.cas.CasFilter;
import org.oreframework.boot.security.UserCache;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.boot.security.cas.AppAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author huangzz 2017年3月19日
 */
@Configuration
@EnableCaching
@ConditionalOnClass(AbstractTicketValidationFilter.class)
@EnableConfigurationProperties({ CasProperties.class, AppProperties.class })
public class CasSecurityAutoConfigure {

	@Autowired
	private CasProperties casProperties;

	@Autowired
	private AppProperties appProperties;

	@Bean
	public UserCache userCache() {
		UserCache userCache = new UserCache();
		return userCache;
	}
	
	
	@Bean
	public UserInfoHolder userInfoHolder() {
		UserInfoHolder holder = new UserInfoHolder();
		return holder;
	}
	
	@Bean
	@ConditionalOnProperty(prefix = "ore.cas", name = "server-login-url")
	public ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> singleSignOutHttpSessionListener() {
		SingleSignOutHttpSessionListener listener = new SingleSignOutHttpSessionListener();
		ServletListenerRegistrationBean<SingleSignOutHttpSessionListener> bean = new ServletListenerRegistrationBean<SingleSignOutHttpSessionListener>();
		bean.setListener(listener);
		return bean;
	}

	/**
	 * 该过滤器用于实现单点登出功能，可选配置
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "ore.cas", name = "server-login-url")
	public FilterRegistrationBean singleSignOutFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		registration.setFilter(singleSignOutFilter);
		registration.addInitParameter("casServerLoginUrl", casProperties.getServerLoginUrl());
		registration.addInitParameter("service", appProperties.getServerUrl());
		registration.setName("CAS Single Sign Out Filter");
		registration.addUrlPatterns("/*");
		registration.addInitParameter("casServerUrlPrefix", casProperties.getServerUrl());
		// registration.setOrder(1);
		return registration;
	}

	/**
	 * 该过滤器负责用户的认证工作，必须启用它
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "ore.cas", name = "server-login-url")
	public FilterRegistrationBean authenticationFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		AuthenticationFilter authenticationFilter = new AuthenticationFilter();
		registration.setFilter(authenticationFilter);
		registration.setName("AuthenticationFilter");
		registration.addUrlPatterns("/*");
		registration.addInitParameter("casServerLoginUrl", casProperties.getServerLoginUrl());
		registration.addInitParameter("ignorePattern", appProperties.getIgnorePattern());
		// registration.addInitParameter("serverName",
		// casProperties.getServerUrl());
		registration.addInitParameter("service", appProperties.getServerUrl());
		registration.addInitParameter("encoding", "UTF-8");
		return registration;
	}

	/**
	 * 该过滤器负责对Ticket的校验工作，必须启用它
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "ore.cas", name = "server-login-url")
	public FilterRegistrationBean cas20ProxyReceivingTicketValidationFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		Cas20ProxyReceivingTicketValidationFilter ticketValidationFilter = new Cas20ProxyReceivingTicketValidationFilter();
		registration.setFilter(ticketValidationFilter);
		registration.setName("CAS Validation Filter");
		registration.addUrlPatterns("/*");
		registration.addInitParameter("casServerUrlPrefix", casProperties.getServerUrl());
		registration.addInitParameter("proxyReceptorUrl", casProperties.getServerUrl());
		registration.addInitParameter("casServerLoginUrl", casProperties.getServerLoginUrl());
		registration.addInitParameter("service", appProperties.getServerUrl());
		registration.addInitParameter("ignorePattern", appProperties.getIgnorePattern());
		// registration.addInitParameter("serverName",
		// casProperties.getServerUrl());
		registration.addInitParameter("useSession", "true");
		registration.addInitParameter("redirectAfterValidation", "true");
		registration.addInitParameter("encoding", "UTF-8");
		return registration;
	}

	/**
	 * 该过滤器负责实现HttpServletRequest请求的包裹，
	 * 比如允许开发者通过HttpServletRequest的getRemoteUser()方法获得SSO登录用户的登录名，可选配置
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "ore.cas", name = "server-login-url")
	public FilterRegistrationBean httpServletRequestWrapperFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		HttpServletRequestWrapperFilter httpServletRequestWrapperFilter = new HttpServletRequestWrapperFilter();
		registration.setFilter(httpServletRequestWrapperFilter);
		registration.setName("CAS HttpServletRequest Wrapper Filter");
		registration.addInitParameter("casServerLoginUrl", casProperties.getServerLoginUrl());
		registration.addInitParameter("service", appProperties.getServerUrl());
		registration.addInitParameter("ignorePattern", appProperties.getIgnorePattern());
		registration.addUrlPatterns("/*");
		return registration;
	}

	/**
	 * 该过滤器使得开发者可以通过org.jasig.cas.client.util.AssertionHolder来获取用户的登录名。
	 * 比如AssertionHolder.getAssertion().getPrincipal().getName()
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "ore.cas", name = "server-login-url")
	public FilterRegistrationBean assertionThreadLocalFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		AssertionThreadLocalFilter assertionThreadLocalFilter = new AssertionThreadLocalFilter();
		registration.setFilter(assertionThreadLocalFilter);
		registration.setName("CAS Assertion Thread Local Filter");
		registration.addInitParameter("casServerLoginUrl", casProperties.getServerLoginUrl());
		registration.addInitParameter("service", appProperties.getServerUrl());
		registration.addInitParameter("ignorePattern", appProperties.getIgnorePattern());
		registration.addUrlPatterns("/*");
		return registration;
	}

	/**
	 * 自动根据单点登录的结果设置本系统的用户信息
	 * 
	 * @return
	 */
	@Bean
	@ConditionalOnProperty(prefix = "ore.cas", name = "server-login-url")
	public FilterRegistrationBean casFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		CasFilter casFilter = new CasFilter();
		registration.setFilter(casFilter);
		registration.addUrlPatterns("/*");
		registration.setName("AutoSetUserAdapterFilter");
		//registration.setOrder(1);
		return registration;
	}

	
	@Bean
	@ConditionalOnProperty(prefix = "ore.app", name = "boss-url")
	public FilterRegistrationBean appAuthenticationFilter() {

		FilterRegistrationBean registration = new FilterRegistrationBean();
		AppAuthenticationFilter appAuthenticationFilter = new AppAuthenticationFilter();
		registration.setFilter(appAuthenticationFilter);
		registration.addUrlPatterns("/*");
		registration.addInitParameter("casServerUrlPrefix", casProperties.getServerUrl());
		registration.addInitParameter("bossUrl", appProperties.getBossUrl());
		registration.setName("AppAuthenticationFilter");
		return registration;
	}
	
	
}
