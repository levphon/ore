package org.oreframework.boot.autoconfigure;

import org.jasig.cas.client.session.SingleSignOutFilter;
import org.jasig.cas.client.validation.Cas20ServiceTicketValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.cas.ServiceProperties;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.cas.web.CasAuthenticationFilter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionFixationProtectionStrategy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.edu.uw.dsk.konferator.security.*;
import pl.edu.uw.dsk.konferator.web.filter.CsrfCookieGeneratorFilter;

import javax.inject.Inject;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	private static final String CAS_URL_LOGIN = "cas.url.login";
	private static final String CAS_URL_LOGOUT = "cas.url.logout";
	private static final String CAS_URL_PREFIX = "cas.url.prefix";
	private static final String CAS_SERVICE_URL = "app.service.security";
	private static final String CAS_CALLBACK = "/auth/cas";

	@Inject
	private Environment env;

	@Inject
	private AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

	@Inject
	private AjaxAuthenticationSuccessHandler ajaxAuthenticationSuccessHandler;

	@Inject
	private AjaxAuthenticationFailureHandler ajaxAuthenticationFailureHandler;

	@Inject
	private AuthenticationUserDetailsService<CasAssertionAuthenticationToken> userDetailsService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ServiceProperties serviceProperties() {
		ServiceProperties sp = new ServiceProperties();
		sp.setService(env.getRequiredProperty(CAS_SERVICE_URL));
		sp.setSendRenew(false);
		return sp;
	}

	@Bean
	public SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler() {
		SimpleUrlAuthenticationSuccessHandler authenticationSuccessHandler = new SimpleUrlAuthenticationSuccessHandler();
		authenticationSuccessHandler.setDefaultTargetUrl("/");
		authenticationSuccessHandler.setTargetUrlParameter("spring-security-redirect");
		return authenticationSuccessHandler;
	}

	@Bean
	public RememberCasAuthenticationProvider casAuthenticationProvider() {
		RememberCasAuthenticationProvider casAuthenticationProvider = new RememberCasAuthenticationProvider();
		casAuthenticationProvider.setAuthenticationUserDetailsService(userDetailsService);
		casAuthenticationProvider.setServiceProperties(serviceProperties());
		casAuthenticationProvider.setTicketValidator(cas20ServiceTicketValidator());
		casAuthenticationProvider.setKey("CAS_AUTHENTICATION_PROVIDER");
		return casAuthenticationProvider;
	}

	@Bean
	public SessionAuthenticationStrategy sessionStrategy() {
		SessionFixationProtectionStrategy sessionStrategy = new SessionFixationProtectionStrategy();
		sessionStrategy.setMigrateSessionAttributes(false);
		// sessionStrategy.setRetainedAttributes(Arrays.asList("CALLBACKURL"));
		return sessionStrategy;
	}

	@Bean
	public Saml11TicketValidator casSamlServiceTicketValidator() {
		return new Saml11TicketValidator(env.getRequiredProperty(CAS_URL_PREFIX));
	}

	@Bean
	public Cas20ServiceTicketValidator cas20ServiceTicketValidator() {
		return new Cas20ServiceTicketValidator(env.getRequiredProperty(CAS_URL_PREFIX));
	}

	@Bean
	public CasAuthenticationFilter casAuthenticationFilter() throws Exception {
		CasAuthenticationFilter casAuthenticationFilter = new CasAuthenticationFilter();
		casAuthenticationFilter.setAuthenticationManager(authenticationManager());
		casAuthenticationFilter.setAuthenticationDetailsSource(new RememberWebAuthenticationDetailsSource());
		casAuthenticationFilter.setSessionAuthenticationStrategy(sessionStrategy());
		casAuthenticationFilter.setAuthenticationFailureHandler(ajaxAuthenticationFailureHandler);
		casAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
		casAuthenticationFilter.setFilterProcessesUrl(CAS_CALLBACK);
		// casAuthenticationFilter.setRequiresAuthenticationRequestMatcher(new
		// AntPathRequestMatcher("/login", "GET"));
		return casAuthenticationFilter;
	}

	@Bean
	public RememberCasAuthenticationEntryPoint casAuthenticationEntryPoint() {
		RememberCasAuthenticationEntryPoint casAuthenticationEntryPoint = new RememberCasAuthenticationEntryPoint();
		casAuthenticationEntryPoint.setLoginUrl(env.getRequiredProperty(CAS_URL_LOGIN));
		casAuthenticationEntryPoint.setServiceProperties(serviceProperties());
		// move to /app/login due to cachebuster instead of api/authenticate
		casAuthenticationEntryPoint.setPathLogin("/app/login");
		return casAuthenticationEntryPoint;
	}

	@Bean
	public SingleSignOutFilter singleSignOutFilter() {
		SingleSignOutFilter singleSignOutFilter = new SingleSignOutFilter();
		singleSignOutFilter.setCasServerUrlPrefix(env.getRequiredProperty(CAS_URL_PREFIX));
		return singleSignOutFilter;
	}

	@Bean
	public LogoutFilter requestCasGlobalLogoutFilter() {
		LogoutFilter logoutFilter = new LogoutFilter(
				env.getRequiredProperty(CAS_URL_LOGOUT) + "?service=" + env.getRequiredProperty(CAS_SERVICE_URL),
				new SecurityContextLogoutHandler());
		// logoutFilter.setFilterProcessesUrl("/logout");
		// logoutFilter.setFilterProcessesUrl("/j_spring_cas_security_logout");
		logoutFilter.setLogoutRequestMatcher(new AntPathRequestMatcher("/api/logout", "POST"));
		return logoutFilter;
	}

	@Inject
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(casAuthenticationProvider());
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/scripts/**/*.{js,html}").antMatchers("/bower_components/**")
				.antMatchers("/i18n/**").antMatchers("/assets/**").antMatchers("/swagger-ui/index.html")
				.antMatchers("/test/**").antMatchers("/h2-console/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.addFilterAfter(new CsrfCookieGeneratorFilter(), CsrfFilter.class)
				.addFilterBefore(casAuthenticationFilter(), BasicAuthenticationFilter.class)
				.addFilterBefore(singleSignOutFilter(), CasAuthenticationFilter.class)
				.addFilterBefore(requestCasGlobalLogoutFilter(), LogoutFilter.class).exceptionHandling()
				.authenticationEntryPoint(casAuthenticationEntryPoint())
				// .and()
				// .rememberMe()
				// .rememberMeServices(rememberMeServices)
				// .key(env.getProperty("jhipster.security.rememberme.key"))
				// .and()
				// .formLogin()
				// .loginProcessingUrl("/api/authentication")
				// .successHandler(ajaxAuthenticationSuccessHandler)
				// .failureHandler(ajaxAuthenticationFailureHandler)
				// .usernameParameter("j_username")
				// .passwordParameter("j_password")
				// .permitAll()
				.and().logout().logoutUrl("/api/logout").logoutSuccessHandler(ajaxLogoutSuccessHandler)
				.invalidateHttpSession(true).deleteCookies("JSESSIONID").permitAll().and().headers().frameOptions()
				.disable().and().authorizeRequests().antMatchers("/app/**").authenticated().antMatchers("/api/register")
				.permitAll().antMatchers("/api/activate").permitAll().antMatchers("/api/authenticate").permitAll()
				.antMatchers("/api/account/reset_password/init").permitAll()
				.antMatchers("/api/account/reset_password/finish").permitAll().antMatchers("/api/logs/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/api/audits/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/api/**").authenticated()
				.antMatchers("/metrics/**").hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/health/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/trace/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/dump/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/shutdown/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/beans/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/configprops/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/info/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/autoconfig/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/env/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/trace/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/mappings/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/liquibase/**")
				.hasAuthority(AuthoritiesConstants.ADMIN).antMatchers("/v2/api-docs/**").permitAll()
				.antMatchers("/configuration/security").permitAll().antMatchers("/configuration/ui").permitAll()
				.antMatchers("/swagger-ui/index.html").hasAuthority(AuthoritiesConstants.ADMIN)
				.antMatchers("/protected/**").authenticated();
	}

	@EnableGlobalMethodSecurity(prePostEnabled = true, jsr250Enabled = true)
	private static class GlobalSecurityConfiguration extends GlobalMethodSecurityConfiguration {
		public GlobalSecurityConfiguration() {
			super();
		}
	}

}