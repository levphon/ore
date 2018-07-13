package org.oreframework.boot.security.cas;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.oreframework.boot.security.UserInfoHolder;
import org.oreframework.context.SpringContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONObject;

public class AppAuthenticationFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(AppAuthenticationFilter.class);

	private static String bossUrl;
	private static String casServerUrlPrefix;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		bossUrl = filterConfig.getInitParameter("bossUrl");
		casServerUrlPrefix = filterConfig.getInitParameter("casServerUrlPrefix");
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) servletRequest;
		final HttpServletResponse response = (HttpServletResponse) servletResponse;
		String jsessionid = request.getParameter("jsessionid");

		String uri = request.getRequestURI();
		if (uri.indexOf(".html") > 0) {
			filterChain.doFilter(request, response);
			return;
		} else if (uri.indexOf(".js") > 0) {
			filterChain.doFilter(request, response);
			return;
		} else if (uri.indexOf(".css") > 0) {
			filterChain.doFilter(request, response);
			return;
		} else {
			if (null == jsessionid) {
				setResponse(response);
				return;
			} else {
				UserInfoHolder userInfoHolder = (UserInfoHolder) SpringContext.getBean("userInfoHolder");
				if (null == userInfoHolder.getUserInfo(jsessionid).getId()) {
					RestTemplate restTemplate = new RestTemplate();
					String retString = restTemplate.getForObject(bossUrl + "user?jsessionid=" + jsessionid,
							String.class);
					if (null != retString) {
						JSONObject jsonObject = JSONObject.fromObject(retString);
						User user = (User) JSONObject.toBean(jsonObject, User.class);
						if (null == user.getId()) {
							setResponse(response);
							return;
						}
						user.setJsessionid(jsessionid);
						userInfoHolder.putUserInfo(user);
						logger.info("login name:{}", userInfoHolder.getUserInfo(jsessionid));
						filterChain.doFilter(request, response);
						return;
					} else {
						setResponse(response);
						return;
					}
				} else {
					filterChain.doFilter(request, response);
					return;
				}
			}
		}
	}

	private void setResponse(final HttpServletResponse response) throws IOException {
		logger.info("not login name:{}\n");
		String retString = "{\"error\":\"login system !\",\"code\":\"99\",\"login_url\":\"" + casServerUrlPrefix
				+ "/login\"}";
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
		response.setHeader("Access-Control-Max-Age", "0");
		response.setHeader("Access-Control-Allow-Headers",
				"Origin, No-Cache, X-Requested-With, If-Modified-Since, Pragma, Last-Modified, Cache-Control, Expires, Content-Type, X-E4M-With,userId,token");
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("XDomainRequestAllowed", "1");
		PrintWriter pw = response.getWriter();
		pw.append(retString);
		pw.flush();
		pw.close();
	}
}
