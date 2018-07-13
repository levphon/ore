package org.oreframework.boot.security.cas;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.validation.Assertion;
import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.context.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasFilter extends AbstractConfigurationFilter {

	public static String SESSION_USER_KEY = "CAS_USER";
	public static String SESSION_CAS_KEY = "_const_cas_assertion_";
	private static final Logger LOGGER = LoggerFactory.getLogger(CasFilter.class);

	private String setSessionId(HttpServletRequest httpRequest, String jsessionid) {
		if (null == jsessionid) {
			Cookie[] cookies = httpRequest.getCookies();
			for (Cookie cookie : cookies) {
				if ("JSESSIONID".equals(cookie.getName())) {
					jsessionid = cookie.getValue();
				}
			}
		}
		return jsessionid;
	}

	public void destroy() {
	}

	public boolean isExcluded(String path) {
		// 如果指定了excludes，并且当前requestURI匹配任何一个exclude pattern，
		// 则立即放弃控制，将控制还给servlet engine。
		// 但对于internal path，不应该被排除掉，否则internal页面会无法正常显示。
		return false;
	}

	/**
	 * 过滤逻辑：首先判断单点登录的账户是否已经存在本系统中， 如果不存在使用用户查询接口查询出用户对象并设置在Session中
	 * 
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		UserInfoHolder userInfoHolder = (UserInfoHolder) SpringContext.getBean("userInfoHolder");

		// _const_cas_assertion_是CAS中存放登录用户名的session标志
		Object object = httpRequest.getSession().getAttribute(SESSION_CAS_KEY);
		if (object != null) {
			User user = (User) httpRequest.getSession().getAttribute(SESSION_USER_KEY);
			String uri = httpRequest.getRequestURI();
			String jsessionid = null;
			if (uri.indexOf("=") > 0) {
				jsessionid = uri.substring(uri.indexOf("=") + 1, uri.length());
			}
			if (null == user) {
				Assertion assertion = (Assertion) object;
				String username = assertion.getPrincipal().getName();
				user = new User();
				user.setUsername(username);

				AttributePrincipal principal = (AttributePrincipal) httpRequest.getUserPrincipal();
				Map<String, Object> attributes = principal.getAttributes();

				user.setId(Integer.parseInt(attributes.get("id").toString()));
				user.setIsadmin(Integer.parseInt(attributes.get("isadmin").toString()));
				if (null != attributes.get("realname")) {
					user.setRealname(attributes.get("realname").toString());
				}
				if (null != attributes.get("roles")) {
					user.setRoles(attributes.get("roles").toString());
				}
				if (null != attributes.get("mobile")) {
					user.setMobile(attributes.get("mobile").toString());
				}
				if (null != attributes.get("email")) {
					user.setEmail(attributes.get("email").toString());
				}

				httpRequest.getSession().setAttribute(SESSION_USER_KEY, user);
				jsessionid = setSessionId(httpRequest, jsessionid);
				if (null != jsessionid) {
					user.setJsessionid(jsessionid);
					userInfoHolder.putUserInfo(user);
					LOGGER.info("login...{}", userInfoHolder.getUserInfo(jsessionid));
				}
			} else {
				jsessionid = setSessionId(httpRequest, jsessionid);
				if (null != jsessionid && null == userInfoHolder.getUserInfo(jsessionid).getId()) {
					user.setJsessionid(jsessionid);
					userInfoHolder.putUserInfo(user);
					LOGGER.info("login...{}", userInfoHolder.getUserInfo(jsessionid));
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig filterConfig) throws ServletException {

	}

}
